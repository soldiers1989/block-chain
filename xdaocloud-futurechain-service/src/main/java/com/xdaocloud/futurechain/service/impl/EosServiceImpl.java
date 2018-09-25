package com.xdaocloud.futurechain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.constant.*;
import com.xdaocloud.futurechain.dto.EosResponse;
import com.xdaocloud.futurechain.dto.orders.BuyRecordDTO;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.eos.*;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.dto.resp.eos.*;
import com.xdaocloud.futurechain.eos.EosAccount;
import com.xdaocloud.futurechain.mapper.*;
import com.xdaocloud.futurechain.model.*;
import com.xdaocloud.futurechain.service.EosService;
import com.xdaocloud.futurechain.service.ExchangeService;
import com.xdaocloud.futurechain.util.EncoderUtils;
import io.eblock.eos4j.Ecc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.xdaocloud.futurechain.util.EncoderUtils.decryptB;
import static com.xdaocloud.futurechain.util.EncoderUtils.encryptA;


@Service
public class EosServiceImpl implements EosService {

    private static Logger LOG = LoggerFactory.getLogger(EosServiceImpl.class);

    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private EosAccount eosAccount;

    @Autowired
    private UserMapper userMapper;
    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EosTransactionMapper eosTransactionMapper;

    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private QuotaMapper quotaMapper;

    @Value("${mai.default.value}")
    private String defaultValue;

    @Autowired
    private OrdersMapper ordersMapper;


    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private EosService eosService;

    @Autowired
    private UnlockEosMapper unlockEosMapper;

    @Autowired
    private EOSConstant eosConstant;

    @Autowired
    private ExchangeMapper exchangeMapper;

    /**
     * 创建账户
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> create(EosCreateWalletRequest request) throws Exception {
        EosWallet eosUserId = new EosWallet(Long.valueOf(request.getUserid()), false);
        int j = eosWalletMapper.findCountByParam(eosUserId);
        if (j != 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, "用户已存在钱包");
        }
        EosWallet eosName = new EosWallet();
        eosName.setWalletName(request.getWalletName());
        int n = eosWalletMapper.findCountByParam(eosName);
        if (n != 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, "钱包名称已存");
        }
        User user = userMapper.selectByPrimaryKey(Long.valueOf(request.getUserid()));
        if (user == null) {
            return new ResultInfo<>(ResultInfo.FORBIDOM, ResultInfo.MSG_FORBIDOM);
        }
        //保存交易密码
        user.setTransactionPassword(request.getPassPhrase());
        User update = new User();
        update.setId(user.getId());
        update.setTransactionPassword(passwordEncoder.encode(request.getPassPhrase()));
        userMapper.updateByPrimaryKeySelective(update);
        //创建eos钱包
        if (!createEOSWallet(request)) {
            return new ResultInfo<>(ResultInfo.FAILURE, "请修改钱包名称");
        }
        //设置钱包免密金额
        Quota quota = new Quota();
        quota.setUserId(Long.valueOf(request.getUserid()));
        quota.setActivate(true);
        quota.setAmount(new BigDecimal(defaultValue));
        quotaMapper.insertSelective(quota);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 查询余额
     *
     * @param userId 用户id
     * @return 余额
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getBalance(Long userId) throws Exception {
        String walletName = eosWalletMapper.findWalletNameByUserId(userId);
        String balance = "";
        if (walletName != null) {
            balance = eosAccount.getBalance(walletName);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, new GetBalanceResponse(balance));
    }

    /**
     * 查询锁仓余额
     *
     * @param userId
     * @return
     * @date 2018年6月29日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public BigDecimal findlockEosBalance(Long userId) {
       /* BigDecimal lockEos = ordersMapper.findSumAmountByUserIdAndTradeDesc(userId, OrdersConstant.LOCK_POSITION);
        if (lockEos == null) {
            lockEos = new BigDecimal(0);
        }*/
        BigDecimal lockEos2 = exchangeMapper.findSumByUserIdAndRemark(userId, ExchangeConstant.AGENT_LOCK_POSITION);
        if (lockEos2 == null) {
            lockEos2 = new BigDecimal(0);
        }
        BigDecimal lockEosSun = new BigDecimal(0);
        BigDecimal unlockEos = unlockEosMapper.findSumAmountByUserId(userId);
        if (unlockEos == null) {
            unlockEos = new BigDecimal(0);
        }
        LOG.info("》》》解锁的数量==" + unlockEos);
        lockEosSun = lockEosSun.subtract(unlockEos);
        String eosWallet = eosWalletMapper.findWalletNameByUserId(userId);

        //出账
        BigDecimal from = eosTransactionMapper.findSumByFromWalletAndTranState(eosWallet, 7);
        if (from == null) {
            from = new BigDecimal(0);
        }
        LOG.info("》》》from==" + from);

        //入账
        BigDecimal to = eosTransactionMapper.findSumByToWalletAndTranState(eosWallet, 7);
        if (to == null) {
            to = new BigDecimal(0);
        }
        LOG.info("》》》to==" + to);
        BigDecimal lock_balance = new BigDecimal(0);
        lock_balance = lockEosSun.add(to).subtract(from);
        return lock_balance;
    }

    /**
     * 转账交易
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> transaction(EosTransactionRequest request) throws Exception {
        BigDecimal sum = new BigDecimal(request.getNum());
        String transactionPassword = request.getTransactionPassword();

        User user = userMapper.selectByPrimaryKey(Long.valueOf(request.getUserid()));
        EosWallet wallet = eosWalletMapper.findOneByUserId(user.getId());
        if (wallet == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, "钱包不存在");
        }
        String toAccount = eosWalletMapper.findWalletName(request.getToAccount());
        if (StringUtils.isEmpty(toAccount)) {
            return new ResultInfo<>(ResultInfo.FAILURE, "钱包不存在");
        }
        ResultInfo<?> x = checkTransactionPassword(sum, transactionPassword, user);
        if (x != null) return x;

        String activePrivateKey = decryptB(AESConstant.KEY, wallet.getActivePrivateKey());
        if (wallet != null) {
            String balance = eosAccount.getBalance(wallet.getWalletName());
            balance = balance.replace("MAI", "").replace(" ", "");
            LOG.info("===balance==" + balance);
            BigDecimal bigDecimal = new BigDecimal(balance);
            if (bigDecimal.compareTo(new BigDecimal(request.getNum())) < 0) {
                return new ResultInfo<>(ResultInfo.FAILURE, "余额不足");
            }
            //发起交易
            eosAccount.p2pTransaction(Long.valueOf(request.getUserid()), wallet.getWalletName(), request.getToAccount(), request.getNum(), request.getDesc(),
                    activePrivateKey);
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }
        return new ResultInfo<>(ResultInfo.FAILURE, "不存在钱包");
    }


    /**
     * 判断是否需要交易密码，判断交易密码是否正确
     *
     * @param sum                 交易数量
     * @param transactionPassword 用户输入交易密码
     * @param user                用户类
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> checkTransactionPassword(BigDecimal sum, String transactionPassword, User user) {
        Quota quota = quotaMapper.selectByPrimaryKey(user.getId());
        if (quota != null && quota.getActivate()) {//判断是否设置交易金额
            LOG.info("quota 》》》" + quota.getActivate());
            if (quota.getAmount().compareTo(sum) < 0) {//判断是否超过最大免密码交易金额
                if (StringUtils.isEmpty(transactionPassword)) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "请输入交易密码");
                } else {
                    Boolean boolTransactionPassword = passwordEncoder.matches(transactionPassword, user.getTransactionPassword());
                    if (!boolTransactionPassword) {
                        return new ResultInfo<>(ResultInfo.FAILURE, "交易密码错误");
                    }
                }
            }
        } else {
            LOG.info("quota 》》》" + "else");
            if (StringUtils.isEmpty(transactionPassword)) {
                return new ResultInfo<>(ResultInfo.FAILURE, "请输入交易密码");
            }
            Boolean boolTransactionPassword = passwordEncoder.matches(transactionPassword, user.getTransactionPassword());
            if (!boolTransactionPassword) {
                return new ResultInfo<>(ResultInfo.FAILURE, "交易密码错误");
            }
        }
        return null;
    }


    /**
     * 麦圈详情扣费-----判断是否需要交易密码，判断交易密码是否正确
     *
     * @param sum                 交易数量
     * @param transactionPassword 用户输入交易密码
     * @param user                用户类
     * @return
     * @date 2018年6月7日
     * @author lmd
     */
    @Override
    @Transactional(readOnly = true)
    public int maiQuanCheckTransactionPassword(BigDecimal sum, String transactionPassword, User user) {
        Quota quota = quotaMapper.selectByPrimaryKey(user.getId());
        if (quota != null && quota.getActivate()) {//判断是否设置交易金额
            LOG.info("quota 》》》" + quota.getActivate());
            if (quota.getAmount().compareTo(sum) < 0) {//判断是否超过最大免密码交易金额
                if (StringUtils.isEmpty(transactionPassword)) {
                    return 1;//new ResultInfo<>(ResultInfo.FAILURE, "请输入交易密码");
                } else {
                    Boolean boolTransactionPassword = passwordEncoder.matches(transactionPassword, user.getTransactionPassword());
                    if (!boolTransactionPassword) {
                        return 2;// new ResultInfo<>(ResultInfo.FAILURE, "交易密码错误");
                    }
                }
            }
        } else {
            LOG.info("quota 》》》" + "else");
            if (StringUtils.isEmpty(transactionPassword)) {
                return 1;//new ResultInfo<>(ResultInfo.FAILURE, "请输入交易密码");
            }
            Boolean boolTransactionPassword = passwordEncoder.matches(transactionPassword, user.getTransactionPassword());
            if (!boolTransactionPassword) {
                return 2;// new ResultInfo<>(ResultInfo.FAILURE, "交易密码错误");
            }
        }
        return 0;
    }

    /**
     * 补贴差价
     *
     * @return
     */
    @Override
    public ResultInfo<?> compensate() throws Exception {
        List<BuyRecordDTO> list = ordersMapper.findAllSumBuyRecord();
        for (BuyRecordDTO buyRecordDTO : list) {
            EosWallet eosWallet = eosWalletMapper.findOneByUserId(buyRecordDTO.getUserId());
            try {
                eosAccount.fromOperateTransaction(eosWallet.getWalletName(), buyRecordDTO.getEosSum().toString(), "平台价格补贴");
            } catch (Exception e) {
                e.printStackTrace();
                saveTransaction((long) 0, eosConstant.maioperate, eosWallet.getWalletName(), buyRecordDTO.getEosSum().toString(), "平台价格补贴", 4);
            }
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 保存交易记录
     *
     * @param userId      用户id
     * @param fromAccount 转出账户
     * @param toAccount   转入账户
     * @param num         数量
     * @param desc        备注
     * @param tran_state  交易状态
     */
    @Override
    @Transactional
    public void saveTransaction(Long userId, String fromAccount, String toAccount, String num, String desc, int tran_state) {
        EosTransaction eosTransaction = new EosTransaction();
        eosTransaction.setUserId(userId);
        eosTransaction.setAmount(new BigDecimal(num));
        eosTransaction.setFromWallet(fromAccount);
        eosTransaction.setToWallet(toAccount);
        if (StringUtils.isNoneEmpty(desc)) {
            eosTransaction.setRemarks(desc);
        }
        eosTransaction.setTranState((byte) tran_state);
        eosTransactionMapper.insertSelective(eosTransaction);
    }

    /**
     * 迁移错误的钱包账户
     *
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    @Override
    public ResultInfo<?> moveErrorWallet() throws Exception {
        List<EosWallet> walletList = eosWalletMapper.findMovenWalletByIsDelete(true);
        for (EosWallet wallet : walletList) {
            //迁移钱包
            moveEOSWallet(wallet);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 锁仓转账交易
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> lockTransaction(EosTransactionRequest request) throws Exception {
        BigDecimal sum = new BigDecimal(request.getNum());
        String transactionPassword = request.getTransactionPassword();
        User user = userMapper.selectByPrimaryKey(Long.valueOf(request.getUserid()));
        EosWallet wallet = eosWalletMapper.findOneByUserId(user.getId());
        if (wallet == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, "钱包不存在");
        }
        String toAccount = eosWalletMapper.findWalletName(request.getToAccount());
        if (StringUtils.isEmpty(toAccount)) {
            return new ResultInfo<>(ResultInfo.FAILURE, "钱包不存在");
        }
        ResultInfo<?> x = checkTransactionPassword(sum, transactionPassword, user);
        if (x != null) return x;
        if (wallet != null) {
            BigDecimal balance = exchangeService.findlockEosBalance(Long.valueOf(request.getUserid()));
            LOG.info("》》》 balance ==" + balance);
            if (balance.compareTo(new BigDecimal(request.getNum())) < 0) {
                return new ResultInfo<>(ResultInfo.FAILURE, "转移锁钻余额不足");
            }
            eosService.saveTransaction(Long.valueOf(request.getUserid()), wallet.getWalletName(), request.getToAccount(), sum.toString(), EosTransactionConstant.TURN_LOCK_POSITION, 7);

            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }


    /**
     * 系统转账
     *
     * @return Boolean 返回是否成功
     * @throws Exception 异常
     * @date 2018年6月29日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public Boolean sysToUserTransaction(SysToUserTransactionRequest request, User user) {
        BigDecimal money = new BigDecimal(request.getMoney());
        BigDecimal price = new BigDecimal(request.getPrice());
        BigDecimal amount = money.divide(price, 4, BigDecimal.ROUND_DOWN);
        Orders orders = null;
        if (!request.getLock()) {//不锁仓
            //发起交易
            try {
                eosAccount.fromOperateTransaction(request.getEosWallet(), amount.toString(), OrdersConstant.BANKCARD);
                orders = new Orders(user.getId(), 1 + "", "sys", price.multiply(BigDecimal.valueOf(100)), amount, money.multiply(BigDecimal.valueOf(100)), OrdersConstant.MAIZUAN, (byte) 1, OrdersConstant.DOWN_LINE);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {//锁仓
            //eosAccount.fromOperateTransaction(eosAccount.EOS_MaitempAccount, amount.toString(), OrdersConstant.BANKCARD);
            eosService.saveTransaction((long) 0, eosConstant.maioperate, request.getEosWallet(), amount.toString(), OrdersConstant.DOWN_LINE_LOCK, 7);
            orders = new Orders(user.getId(), 2 + "", OrdersConstant.BANKCARD, price.multiply(BigDecimal.valueOf(100)), amount, money.multiply(BigDecimal.valueOf(100)), OrdersConstant.MAIZUAN, (byte) 1, OrdersConstant.DOWN_LINE_LOCK);
        }
        ordersMapper.insertSelective(orders);
        return true;
    }

    @Override
    @Transactional
    public Boolean unlockEos(UnlockEosRequest request, User user) {
        try {
            eosAccount.fromOperateTransaction(request.getEosWallet(), request.getAmount().toString(), OrdersConstant.UNLOCK);
            UnlockEos unlockEos = new UnlockEos();
            unlockEos.setUserId(user.getId());
            unlockEos.setEosAmount(request.getAmount());
            unlockEosMapper.insertSelective(unlockEos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询是否存在钱包
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findWallet(Long userId) throws Exception {
        EosWallet eosWallet = new EosWallet(userId);
        EosWallet wallet = eosWalletMapper.findOneByParam(eosWallet);
        ExistResponse existResponse = null;
        if (wallet == null) {
            existResponse = new ExistResponse(false, "");
        } else {
            existResponse = new ExistResponse(true, wallet.getWalletName());
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, existResponse);
    }


    /**
     * 麦钻收支明细
     *
     * @param page_request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findMAI_Transaction(Page_Request page_request) throws Exception {
        EosWallet eosWallet = eosWalletMapper.findOneByParam(new EosWallet(Long.valueOf(page_request.getUserid()),false));
        PageHelper.startPage(page_request.getPage(), page_request.getSize(), "id DESC");
        List<EosTransaction> list = null;
        if (eosWallet != null) {
            list = eosTransactionMapper.findListByWalletName(eosWallet.getWalletName());
        } else {
            return new ResultInfo<>(ResultInfo.NOT_FOUND, ResultInfo.MSG_DATA_NOT_FOUND);
        }
        PageInfo<EosTransaction> pageInfo = new PageInfo<EosTransaction>(list);
        PageResponse response = new PageResponse(pageInfo);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findEOS_Account(EOS_Account request) throws Exception {
        String accountName = eosWalletMapper.findWalletName(request.getAccountName());
        if (StringUtils.isEmpty(accountName)) {
            return new ResultInfo<>(ResultInfo.SUCCESS, "钱包不存在");
        }
        return new ResultInfo<>(ResultInfo.FAILURE, "钱包已存在");
    }

    /**
     * 设置免密码金额
     *
     * @param setQuotaRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> setQuota(SetQuotaRequest setQuotaRequest) {
        Long userId = Long.valueOf(setQuotaRequest.getUserid());
        Quota q = quotaMapper.selectByPrimaryKey(userId);
        Quota quota = new Quota();
        quota.setAmount(new BigDecimal(setQuotaRequest.getAmount()));
        quota.setUserId(userId);
        if (setQuotaRequest.equals(1)) {
            quota.setActivate(true);
        }
        if (setQuotaRequest.equals(0)) {
            quota.setActivate(false);
        }
        if (q != null) {
            quotaMapper.updateByPrimaryKeySelective(quota);
        } else {
            quotaMapper.insertSelective(quota);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 查询免密金额
     *
     * @param userIdRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findQuota(UserIdRequest userIdRequest) {
        Quota quota = quotaMapper.selectByPrimaryKey(Long.valueOf(userIdRequest.getUserid()));

        FindQuotaResponse findQuotaResponse = new FindQuotaResponse();
        if (quota == null) {
            findQuotaResponse.setActivate(true);
            findQuotaResponse.setAmount(new BigDecimal(defaultValue));
            findQuotaResponse.setDefaultValue(defaultValue);
            quotaMapper.insertSelective(new Quota(Long.valueOf(userIdRequest.getUserid()), new BigDecimal(defaultValue), true));
        } else {
            findQuotaResponse.setActivate(quota.getActivate());
            findQuotaResponse.setAmount(quota.getAmount());
            findQuotaResponse.setDefaultValue(defaultValue);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, findQuotaResponse);
    }

    /**
     * 激活免密金额
     *
     * @param userIdRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> activateQuota(UserIdRequest userIdRequest) {
        Quota quota = quotaMapper.selectByPrimaryKey(Long.valueOf(userIdRequest.getUserid()));
        if (quota != null) {
            if (quota.getActivate()) {
                quota.setActivate(false);
            } else {
                quota.setActivate(true);
            }
            quotaMapper.updateByPrimaryKeySelective(quota);
        } else {
            //设置钱包免密金额
            Quota q = new Quota();
            q.setUserId(Long.valueOf(userIdRequest.getUserid()));
            q.setActivate(false);
            q.setAmount(new BigDecimal(defaultValue));
            quotaMapper.insertSelective(q);
            quota = q;
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, quota);
    }

    /**
     * 解锁记录
     *
     * @param pageBaseRequest
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse getUnlockEos(PageBase_Request pageBaseRequest) throws Exception {
        PageHelper.startPage(pageBaseRequest.getPage(), pageBaseRequest.getSize(), "id DESC");
        List<UnlockEosInfo> list = new ArrayList<>();
        if (StringUtils.isEmpty(pageBaseRequest.getCondition())) {
            list = unlockEosMapper.findList();

        } else {
            list = unlockEosMapper.findListByPhone(pageBaseRequest.getCondition());
        }
        for (UnlockEosInfo unlockEosInfo : list) {
            BigDecimal lockEosBalance = exchangeService.findlockEosBalance(unlockEosInfo.getUserId());
            unlockEosInfo.setLockAmount(lockEosBalance);
        }
        PageInfo<UnlockEosInfo> pageInfo = new PageInfo<UnlockEosInfo>(list);
        PageResponse response = new PageResponse(pageInfo);
        return response;
    }

    @Override
    @Transactional
    public Boolean importAccount(ImportAccountRequest request) throws Exception {
        Long userId = Long.valueOf(request.getUserid());
        User update = new User();
        update.setId(userId);
        update.setTransactionPassword(passwordEncoder.encode(request.getPassPhrase()));
        userMapper.updateByPrimaryKeySelective(update);

        LOG.info("============= 通过私钥生成公钥 ===============");
        String active_public_key = Ecc.privateToPublic(request.getPrivateKey());
        LOG.info("active_public_key:" + active_public_key);

        //TODO 导入钱包未完成
        EosWallet eosWallet = new EosWallet();
        eosWallet.setUserId(Long.valueOf(request.getUserid()));
        eosWallet.setWalletName(request.getWalletName());
        eosWallet.setActivePublicKey(active_public_key);
        eosWallet.setActivePrivateKey(request.getPrivateKey());

        //加密保存密码
        eosWalletMapper.insertSelective(eosWallet);
        Quota q = new Quota();
        q.setUserId(Long.valueOf(userId));
        q.setActivate(true);
        q.setAmount(new BigDecimal(defaultValue));
        quotaMapper.insertSelective(q);
        return true;
    }

    /**
     * 获取密匙
     *
     * @param request
     * @return String
     * @author LuoFuMIn
     * @date 2018/7/18
     */
    @Override
    @Transactional(readOnly = true)
    public String getPrivateKey(GetPrivateKeyRequest request) throws Exception {
        Long userId = Long.valueOf(request.getUserid());
        EosWallet eosWallet = eosWalletMapper.findOneByUserId(userId);
        return EncoderUtils.decryptB(AESConstant.KEY, eosWallet.getActivePrivateKey());
    }

    /**
     * 迁移eos钱包
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> moveWallet() throws Exception {
        List<EosWallet> walletList = eosWalletMapper.findAll();
        for (EosWallet wallet : walletList) {
            //迁移钱包
            moveEOSWallet(wallet);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 迁移eos 交易数据（真实转账一遍）
     *
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ResultInfo<?> moveTransaction() throws Exception {

        List<EosWallet> walletList = eosWalletMapper.findAll();
        for (EosWallet wallet : walletList) {

            BigDecimal to = eosTransactionMapper.findSumByToWallet(wallet.getWalletName());
            LOG.info("》》》 to ==" + to);
            BigDecimal from = eosTransactionMapper.findSumByFromWallet(wallet.getWalletName());
            LOG.info("》》》 from ==" + from);
            if (from == null) {
                from = new BigDecimal(0);
            }
            BigDecimal yue_er = new BigDecimal(0);
            yue_er = to.subtract(from);
            LOG.info("》》》 yue_er ==" + yue_er);
            if (yue_er.compareTo(BigDecimal.valueOf(0)) > 0) {
                try {
                    eosAccount.moveTransaction((long) 0, eosConstant.maiplatform, wallet.getWalletName(), yue_er.toString(), "迁移eos数据", eosConstant.maiplatform_private_key);
                } catch (Exception e) {
                    e.printStackTrace();
                    saveTransaction((long) 0, eosConstant.maiplatform, wallet.getWalletName(), yue_er.toString(), "迁移eos交易失败", 5);
                }
            } else {
                saveTransaction((long) 0, eosConstant.maiplatform, wallet.getWalletName(), yue_er.toString(), "迁移eos为0", 6);
            }
        }

  /*      List<EosTransaction> eosTransactions = eosTransactionMapper.findAllList();

        for (EosTransaction eosTransaction : eosTransactions) {
            if (eosTransaction.getFromWallet().equals("maiplatform")) {
                try {
                    eosAccount.moveTransaction(eosTransaction.getUserId(), eosTransaction.getFromWallet(), eosTransaction.getToWallet(), eosTransaction.getAmount().toString(), eosTransaction.getRemarks(), EOS_PlatformKey);
                } catch (Exception e) {
                    e.printStackTrace();
                    saveErrorTransaction((long) 0, eosTransaction.getFromWallet(), eosTransaction.getToWallet(), eosTransaction.getAmount().toString(), "迁移eos交易失败", 5);
                }
            }
            if (eosTransaction.getFromWallet().equals("maioperate")) {
                try {
                    eosAccount.moveTransaction(eosTransaction.getUserId(), eosTransaction.getFromWallet(), eosTransaction.getToWallet(), eosTransaction.getAmount().toString(), eosTransaction.getRemarks(), EOS_OperateKey);
                } catch (Exception e) {
                    e.printStackTrace();
                    saveErrorTransaction((long) 0, eosTransaction.getFromWallet(), eosTransaction.getToWallet(), eosTransaction.getAmount().toString(), "迁移eos交易失败", 5);
                }
            }
            if (eosTransaction.getUserId() ==0) {
                EosWallet eosWallet = eosWalletMapper.findOneByWalletName(eosTransaction.getFromWallet());
                String activePrivateKey = decryptBActivePrivateKey(eosWallet);
                try {
                    eosAccount.moveTransaction(eosTransaction.getUserId(), eosTransaction.getFromWallet(), eosTransaction.getToWallet(), eosTransaction.getAmount().toString(), eosTransaction.getRemarks(), activePrivateKey);
                } catch (Exception e) {
                    e.printStackTrace();
                    saveErrorTransaction(eosTransaction.getUserId(), eosTransaction.getFromWallet(), eosTransaction.getToWallet(), eosTransaction.getAmount().toString(), "迁移eos交易失败", 5);
                }
            }
        }*/

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 解密私钥
     *
     * @param eosWallet eos钱包
     * @return activePrivateKey eos账户私钥
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public String decryptBActivePrivateKey(EosWallet eosWallet) throws Exception {
        String activePrivateKey = decryptB(AESConstant.KEY, eosWallet.getActivePrivateKey());
        return activePrivateKey;
    }

    /**
     * 创建eos 钱包
     *
     * @param request
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    public boolean createEOSWallet(EosCreateWalletRequest request) throws Exception {
        String walletName = request.getWalletName();
        EosResponse eosResponse = eosAccount.newAccount(request.getWalletName());
        if (eosResponse.getCode() != 1) {
            return false;
        }
        KeyDTO keyDTO = eosResponse.getKeyDTO();
        EosWallet eosWallet = new EosWallet();
        eosWallet.setUserId(Long.valueOf(request.getUserid()));
        eosWallet.setWalletName(walletName);
        //加密保存
        eosWallet.setActivePrivateKey(encryptA(AESConstant.KEY, keyDTO.getActivePrivateKey()));
        eosWallet.setActivePublicKey(keyDTO.getActivePublicKey());
        //加密保存
        eosWallet.setOwnerPrivateKey(encryptA(AESConstant.KEY, keyDTO.getOwnerPrivateKey()));
        eosWallet.setOwnerPublicKey(keyDTO.getOwnerPublicKey());
        if (StringUtils.isNoneBlank(request.getRemarks())) {
            eosWallet.setRemark(request.getRemarks());
        }
        eosWalletMapper.insertSelective(eosWallet);
        Reward reward = rewardMapper.findOneByType(1);
        if (reward != null) {
            BigDecimal bili = new BigDecimal(0.2);
            BigDecimal lock_bili = new BigDecimal(0.8);
            eosAccount.sysTransaction(walletName, reward.getEosAmount().multiply(bili).setScale(4, BigDecimal.ROUND_DOWN).toString(), RewardTypeConstant.REGISTER);

            eosService.saveTransaction((long) 0, eosConstant.maioperate, eosWallet.getWalletName(), reward.getEosAmount().multiply(lock_bili).setScale(4, BigDecimal.ROUND_DOWN).toString(), RewardTypeConstant.REGISTER_LOCK_POSITION, 7);
        }
        return true;
    }


    /**
     * 迁移eos 钱包
     *
     * @param eosWallet
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    public Boolean moveEOSWallet(EosWallet eosWallet) throws Exception {
        try {
            String walletName = eosWallet.getWalletName();
            EosResponse eosResponse = eosAccount.newAccount(walletName);
            if (eosResponse.getCode() != 1) {
                LOG.error("》》》 创建钱包失败==walletName==" + eosWallet.getWalletName());
                eosWallet.setIsDeleted(true);
                eosWalletMapper.updateByPrimaryKey(eosWallet);
                return false;
            }
            //获取秘钥
            KeyDTO keyDTO = eosResponse.getKeyDTO();
            //加密保存
            eosWallet.setActivePrivateKey(encryptA(AESConstant.KEY, keyDTO.getActivePrivateKey()));
            eosWallet.setActivePublicKey(keyDTO.getActivePublicKey());
            //加密保存
            eosWallet.setOwnerPrivateKey(encryptA(AESConstant.KEY, keyDTO.getOwnerPrivateKey()));
            eosWallet.setOwnerPublicKey(keyDTO.getOwnerPublicKey());
            eosWallet.setIsDeleted(false);
            //更新新钱包
            eosWalletMapper.updateByPrimaryKey(eosWallet);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("》》》 创建失败==walletName==" + eosWallet.getWalletName());
            eosWallet.setIsDeleted(true);
            eosWalletMapper.updateByPrimaryKey(eosWallet);
            return false;
        }
        return true;
    }

}