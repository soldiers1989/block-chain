package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSON;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.constant.AESConstant;
import com.xdaocloud.futurechain.dto.req.ore.CreateOreEnvelopeRequest;
import com.xdaocloud.futurechain.dto.req.ore.GrabOreEnvelopeRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.dto.resp.ore.GrabOreEnvelopeDTO;
import com.xdaocloud.futurechain.dto.resp.ore.GrabOreEnvelopeResponse;
import com.xdaocloud.futurechain.dto.resp.ore.OreEnvelopeListDTO;
import com.xdaocloud.futurechain.eos.EosAccount;
import com.xdaocloud.futurechain.mapper.*;
import com.xdaocloud.futurechain.model.*;
import com.xdaocloud.futurechain.service.OreEnvelopeService;
import com.xdaocloud.futurechain.util.RedPacketUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xdaocloud.futurechain.constant.Constant.REDIS_ENVELOP;
import static com.xdaocloud.futurechain.constant.RewardTypeConstant.GRAD_OREENVELOPE;
import static com.xdaocloud.futurechain.constant.RewardTypeConstant.OREENVELOPE;
import static com.xdaocloud.futurechain.util.EncoderUtils.decryptB;


/**
 * 矿包服务类
 */
@Service
public class OreEnvelopeServiceImpl implements OreEnvelopeService {

    private static Logger LOG = LoggerFactory.getLogger(OreEnvelopeServiceImpl.class);

    @Autowired
    private OreEnvelopeMapper oreEnvelopeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GrabOreEnvelopeMapper grabOreEnvelopeMapper;

    @Autowired
    private EosAccount eosAccount;

    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private OreTransactionMapper oreTransactionMapper;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 创建矿包
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> createOreEnvelope(CreateOreEnvelopeRequest request) throws Exception {

        EosWallet eosWallet = eosWalletMapper.findOneByUserId(Long.valueOf(request.getUserid()));
        if (eosWallet == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, "钱包不存在");
        }
        //检查交易密码
        /*ResultInfo<?> x = eosService.checkTransactionPassword(eosSum, transactionPassword, user);
        if (x != null) return x;*/

        String balance = "";
        String activePrivateKey = decryptB(AESConstant.KEY, eosWallet.getActivePrivateKey());
        balance = eosAccount.getBalance(eosWallet.getWalletName());
        LOG.info("===balance==" + balance);
        BigDecimal bigDecimal = new BigDecimal(balance);
        if (bigDecimal.compareTo(new BigDecimal(request.getAmount())) < 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, "余额不足");
        }
        List<BigDecimal> menoyList = RedPacketUtil.math(new BigDecimal(request.getAmount()), request.getCount(), 10000);
        userMapper.addOreByUserid(Long.valueOf(request.getUserid()), (long) 10);
        oreTransactionMapper.insertSelective(new OreTransaction(Long.valueOf(request.getUserid()), (long) 10, OREENVELOPE));
        //转账给平台
        eosAccount.toTempTransaction(Long.valueOf(request.getUserid()), eosWallet.getWalletName(), request.getAmount() + "", OREENVELOPE, activePrivateKey);
        OreEnvelope oreEnvelope = new OreEnvelope(Long.valueOf(request.getUserid()), request.getCount(), new BigDecimal(request.getAmount()), request.getOre_title(), JSON.toJSONString(menoyList), request.getCount());
        int i = oreEnvelopeMapper.insertSelective(oreEnvelope);
        oreEnvelope.setGmtCreate(new Date().toString());
        oreEnvelope.setOreState(true);
        if (i > 0) {
            //插入redis,设置失效时间为24小，24小时后RedisKeyExpiredListener会收到 "ore-envelope-id:"+oreEnvelope.getId() 键的失效通知，然后做退款处理
            stringRedisTemplate.opsForValue().set(REDIS_ENVELOP + oreEnvelope.getId(), "message", 60 * 60 * 24, TimeUnit.SECONDS);
            oreEnvelope.setRandom("");
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, oreEnvelope);
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }

    /**
     * 抢矿包
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> grabOreEnvelope(GrabOreEnvelopeRequest request) throws Exception {
        User user = userMapper.selectByPrimaryKey(Long.valueOf(request.getUserid()));
        if (user == null) {
            return new ResultInfo<>(ResultInfo.FORBIDOM, ResultInfo.MSG_FORBIDOM);
        }
        OreEnvelope oreEnvelope = oreEnvelopeMapper.selectByPrimaryKey(Long.valueOf(request.getOreEnvelopeId()));
        if (oreEnvelope == null) {
            return new ResultInfo<>(ResultInfo.DATA_NOT_FOUND, ResultInfo.MSG_NOT_FOUND);
        }
        //根据矿包id查询矿包个数
        Integer sum = oreEnvelope.getCount();
        //已经领取了几个矿包
        Integer index = sum - oreEnvelope.getResidue();
        List<BigDecimal> menoyList = JSON.parseArray(oreEnvelope.getRandom(), BigDecimal.class);
        List<GrabOreEnvelopeDTO> list = null;
        GrabOreEnvelopeResponse grabOreEnvelopeResponse = null;
        if (oreEnvelope.getOreState()) {
            if (oreEnvelope.getResidue() > 0) {//判断矿包是否已抢完
                //查询是否存在抢矿记录
                Integer i = grabOreEnvelopeMapper.findCountByOreEnvelopeIdAndUserId(Long.valueOf(request.getOreEnvelopeId()), Long.valueOf(request.getUserid()));
                if (i == 0) {//没有记录插入抢矿包记录
                    BigDecimal menoy = menoyList.get(index);
                    //红包剩余数量减1
                    oreEnvelope.setResidue(oreEnvelope.getResidue() - 1);
                    //更新剩余数量
                    oreEnvelopeMapper.updateByPrimaryKeySelective(new OreEnvelope(oreEnvelope.getId(), oreEnvelope.getResidue()));
                    //扣除0.01手续费
                    menoy = menoy.multiply(BigDecimal.valueOf(0.99)).setScale(4, BigDecimal.ROUND_DOWN);
                    //抢矿结算，给用户发奖励（eos币）
                    sendReward(oreEnvelope.getId(), menoy, Long.valueOf(request.getUserid()));
                    //查询抢矿记录
                    list = grabOreEnvelopeMapper.findByOreEnvelopeId(Long.valueOf(request.getOreEnvelopeId()));
                    grabOreEnvelopeResponse = new GrabOreEnvelopeResponse(oreEnvelope.getOreTitle(), Long.valueOf(request.getUserid()), Long.valueOf(request.getOreEnvelopeId()), menoy.toString(), sum, sum - oreEnvelope.getResidue(), 1, user.getNickname(), user.getAvatar(), list);
                    return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, grabOreEnvelopeResponse);
                } else {//已经抢过矿包了
                    list = grabOreEnvelopeMapper.findByOreEnvelopeId(Long.valueOf(request.getOreEnvelopeId()));
                    BigDecimal menoy = grabOreEnvelopeMapper.findRandomNumberByOreEnvelopeIdAndUserId(Long.valueOf(request.getOreEnvelopeId()), Long.valueOf(request.getUserid()));
                    grabOreEnvelopeResponse = new GrabOreEnvelopeResponse(oreEnvelope.getOreTitle(), Long.valueOf(request.getUserid()), Long.valueOf(request.getOreEnvelopeId()), menoy.toString(), sum, sum - oreEnvelope.getResidue(), 0, user.getNickname(), user.getAvatar(), list);
                    return new ResultInfo<>(ResultInfo.SUCCESS, "已经抢过矿包了！！！", grabOreEnvelopeResponse);
                }
            }
            Integer i = grabOreEnvelopeMapper.findCountByOreEnvelopeIdAndUserId(Long.valueOf(request.getOreEnvelopeId()), Long.valueOf(request.getUserid()));
            if (i == 0) {//没有记录插入抢矿包记录
                saveFalse(request);
            }
            list = grabOreEnvelopeMapper.findByOreEnvelopeId(Long.valueOf(request.getOreEnvelopeId()));
            grabOreEnvelopeResponse = new GrabOreEnvelopeResponse(oreEnvelope.getOreTitle(), Long.valueOf(request.getUserid()), Long.valueOf(request.getOreEnvelopeId()), "0", sum, sum - oreEnvelope.getResidue(), 0, user.getNickname(), user.getAvatar(), list);
            return new ResultInfo<>(ResultInfo.SUCCESS, "矿包已经抢完了！！！", grabOreEnvelopeResponse);
        } else {
            Integer i = grabOreEnvelopeMapper.findCountByOreEnvelopeIdAndUserId(Long.valueOf(request.getOreEnvelopeId()), Long.valueOf(request.getUserid()));
            if (i == 0) {//没有记录插入抢矿包记录
                saveFalse(request);
            }
            list = grabOreEnvelopeMapper.findByOreEnvelopeId(Long.valueOf(request.getOreEnvelopeId()));
            grabOreEnvelopeResponse = new GrabOreEnvelopeResponse(oreEnvelope.getOreTitle(), Long.valueOf(request.getUserid()), Long.valueOf(request.getOreEnvelopeId()), "0", sum, sum - oreEnvelope.getResidue(), 0, user.getNickname(), user.getAvatar(), list);
            return new ResultInfo<>(ResultInfo.SUCCESS, "红包已过期！", grabOreEnvelopeResponse);
        }
    }

    private void saveFalse(GrabOreEnvelopeRequest request) {
        GrabOreEnvelope grabOreEnvelope = new GrabOreEnvelope();
        grabOreEnvelope.setOreEnvelopeId(Long.valueOf(request.getOreEnvelopeId()));
        grabOreEnvelope.setUserId(Long.valueOf(request.getUserid()));
        grabOreEnvelope.setState(false);
        grabOreEnvelope.setRandomNumber(BigDecimal.valueOf(0));
        //保存抢矿包记录
        grabOreEnvelopeMapper.insertSelective(grabOreEnvelope);
    }

    /**
     * 发放奖励
     *
     * @param menoy  获取的eos币
     * @param userid 用户id
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    private void sendReward(Long oreEnvelopeId, BigDecimal menoy, Long userid) throws Exception {
        System.out.println("=====发放奖励======" + menoy.toString());
        String walletName = eosWalletMapper.findWalletNameByUserId(userid);
        GrabOreEnvelope grabOreEnvelope = new GrabOreEnvelope();
        grabOreEnvelope.setOreEnvelopeId(oreEnvelopeId);
        grabOreEnvelope.setUserId(userid);
        grabOreEnvelope.setRandomNumber(menoy);
        //判断钱包名是否为空
        if (StringUtils.isNoneEmpty(walletName)) {
            eosAccount.sysTransaction(walletName, menoy + "", GRAD_OREENVELOPE);
            LOG.info("领取矿包成功，userid：：" + userid);
            grabOreEnvelope.setState(true);
        } else {
            LOG.info("领取矿包失败，userid：：" + userid);
            grabOreEnvelope.setState(false);
        }
        //保存抢矿包记录
        grabOreEnvelopeMapper.insertSelective(grabOreEnvelope);
    }

    /**
     * 抢矿结算，给用户发奖励（ftc币）
     *
     * @param oreEnvelopeId
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    private void settlement(Long oreEnvelopeId, Long userid) throws Exception {
        //财主地址
/*        String WALLET = "0x958c03531fe0458b64fecc13647239162dc812db";
        String PASSPHRASE = "123456";
        GrabOreEnvelope grabOreEnvelope = grabOreEnvelopeMapper.findByOreEnvelopeIdAndUserId(oreEnvelopeId, userid);
        boolean transaction = true;
        if (grabOreEnvelope.getState()) {//判断是否已经发放奖励
            String walletAddress = ethereumWalletMapper.findWalletAddressByUserid(userid);
            if (StringUtils.isNoneBlank(walletAddress)) {
                GasPriceDTO gasPriceDTO = ethereumTrade.ethGasAndGasPrice();
                LOG.info("=========发起交易=======");
                LOG.info("gas==" + gasPriceDTO.getGas() + ",gasPrice==" + gasPriceDTO.getGasPrice());
                TransactionRequest transactionRequest = new TransactionRequest(String.valueOf(0), WALLET, walletAddress, gasPriceDTO.getGas(), gasPriceDTO.getGasPrice(), new BigInteger(String.valueOf(grabOreEnvelope.getRandomNumber())), "", PASSPHRASE);
                ResultInfo<?> resultInfo = ethereumTrade.ethTransaction(transactionRequest);
                if (resultInfo.getCode() != 200) {
                    LOG.error("=========交易失败=========" + resultInfo.getData());
                    transaction = false;
                }
            } else {
                transaction = false;
                LOG.error("=========获奖者以太坊钱包地址为空=========");
            }
        }
        if (!transaction) grabOreEnvelope.setState(transaction);
        grabOreEnvelopeMapper.updateByPrimaryKeySelective(grabOreEnvelope);*/
    }

    /**
     * 获取矿包列表
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo getOreEnvelopes(UserIdRequest request) {
        List<OreEnvelopeListDTO> list = oreEnvelopeMapper.findByUserId(Long.valueOf(request.getUserid()));
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

}
