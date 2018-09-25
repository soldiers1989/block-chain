package com.xdaocloud.futurechain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.constant.EOSConstant;
import com.xdaocloud.futurechain.constant.ExchangeConstant;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.exchange.GetAchievementABRequest;
import com.xdaocloud.futurechain.dto.req.exchange.PutForwardRequest;
import com.xdaocloud.futurechain.dto.req.exchange.PutMoneyRequest;
import com.xdaocloud.futurechain.dto.resp.exchange.AllForwardRecordResponse;
import com.xdaocloud.futurechain.dto.resp.exchange.GetCandidatesResponse;
import com.xdaocloud.futurechain.dto.resp.orders.AchievementResponse;
import com.xdaocloud.futurechain.dto.resp.user.UserInfoResponse;
import com.xdaocloud.futurechain.dto.user.UserChildrenDTO;
import com.xdaocloud.futurechain.eos.EosAccount;
import com.xdaocloud.futurechain.mapper.*;
import com.xdaocloud.futurechain.model.Exchange;
import com.xdaocloud.futurechain.model.User;
import com.xdaocloud.futurechain.service.EosService;
import com.xdaocloud.futurechain.service.ExchangeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class ExchangeServiceImpl implements ExchangeService {

    private static Logger LOG = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    @Autowired
    private ExchangeMapper exchangeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EosTransactionMapper eosTransactionMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private EosAccount eosAccount;

    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private EosService eosService;

    /**
     * 代理
     */
    @Value("${become.agent}")
    private String becomeAgentMoney;

    /**
     * 代理候选人
     */
    @Value("${candidate.agent}")
    private String candidateAgentMoney;

    @Value("${put.forward.a}")
    private String put_forward_a;

    @Value("${put.forward.b}")
    private String put_forward_b;


    @Autowired
    private UnlockEosMapper unlockEosMapper;

    @Autowired
    private PutMoneyMapper putMoneyMapper;


    @Autowired
    private EOSConstant eosConstant;


    @Override
    public ResultInfo<?> apply(Long userId) {
        return null;
    }

    /**
     * 申请成为候选代理人
     *
     * @param userId 用户id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> applyCandidate(Long userId) {
        BigDecimal candidateAgent = new BigDecimal(candidateAgentMoney);
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getAgent() != 4 && user.getAgent() != 1) {
            BigDecimal feeSum = ordersMapper.findSumFeeByUserId(userId);
            if (feeSum == null) {
                return new ResultInfo<>(ResultInfo.FAILURE, "资格不够");
            }
            if (feeSum.compareTo(candidateAgent) >= 0) {
                User userUpdate = new User(userId, (byte) 4);
                userUpdate.setAgreeTime(com.xdaocloud.futurechain.util.DateUtils.getNowDateTime());
                userMapper.updateByPrimaryKeySelective(userUpdate);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
            } else {
                return new ResultInfo<>(ResultInfo.FAILURE, "资格不够");
            }
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, "请不要重复申请");
        }
    }

    /**
     * 查询业绩
     *
     * @param userId 用户id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public AchievementResponse findAchievement(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        AchievementResponse achievementResponse = new AchievementResponse();
        if (user != null) {
            //查询两级下线用户的id
            List<Long> aLongs = getTwoLayerUserIds(user.getInviteCode());

            BigDecimal rmb = ordersMapper.findSumFeeByUserId(userId);
            if (rmb == null) {
                rmb = BigDecimal.valueOf(0);
            }
            if (aLongs.isEmpty()) {
                achievementResponse.setRmb(rmb.divide(BigDecimal.valueOf(100)));
                achievementResponse.setRmbSum(BigDecimal.valueOf(0));
                achievementResponse.setEosSum(BigDecimal.valueOf(0));
                achievementResponse.setStaging(BigDecimal.valueOf(0));
                achievementResponse.setPresented(BigDecimal.valueOf(0));
                achievementResponse.setAgent((byte) 3);
                achievementResponse.setPeople(0);
                achievementResponse.setBecomeAgent(Integer.valueOf(becomeAgentMoney) / 100);
                achievementResponse.setCandidateAgent(Integer.valueOf(candidateAgentMoney) / 100);
                return achievementResponse;
            }

            //AB级下线赞赏eos总数
            BigDecimal amount = ordersMapper.findSumEosByUserIds(aLongs);
            if (amount == null) {
                amount = BigDecimal.valueOf(0);
            }
            //AB级下线赞赏rmb总数
            BigDecimal fee = ordersMapper.findSumFeeByUserIds(aLongs);
            if (fee == null) {
                fee = BigDecimal.valueOf(0);
            }

            //查询已经兑换的总数
            BigDecimal presented = exchangeMapper.findEosPutForwardSumByUserId(user.getId());
            achievementResponse = new AchievementResponse();
            achievementResponse.setEosSum(amount);
            achievementResponse.setRmbSum(fee.divide(BigDecimal.valueOf(100)));
            if (presented == null) {
                presented = BigDecimal.valueOf(0);
            }
            achievementResponse.setPresented(presented);
            //统计AB级下线10%提成
            //amount = amount.divide(BigDecimal.valueOf(10)).setScale(4);
            //设置余额
            achievementResponse.setStaging(amount.subtract(presented.multiply(BigDecimal.valueOf(10))));
            achievementResponse.setRmb(rmb.divide(BigDecimal.valueOf(100)));
            achievementResponse.setAgent(user.getAgent());
            achievementResponse.setPeople(aLongs.size());
            achievementResponse.setBecomeAgent(Integer.valueOf(becomeAgentMoney) / 100);
            achievementResponse.setCandidateAgent(Integer.valueOf(candidateAgentMoney) / 100);
            return achievementResponse;
        }
        return achievementResponse;
    }


    /**
     * 申请成为代理商
     *
     * @param userId 用户id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> applyAgent(Long userId) {
        BigDecimal becomeAgent = new BigDecimal(becomeAgentMoney);
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getAgent() != 1) {
            List<Long> aLongs = getTwoLayerUserIds(user.getInviteCode());
            aLongs.add(userId);
            BigDecimal feeSum = ordersMapper.findSumFeeByUserIds(aLongs);
            LOG.info("》》》（下线业绩，单位分） feeSum==" + feeSum);
            if (feeSum.compareTo(becomeAgent) >= 0) {
                User userUpdate = new User(userId, (byte) 2);
                userMapper.updateByPrimaryKeySelective(userUpdate);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
            } else {
                return new ResultInfo<>(ResultInfo.FAILURE, "资格不够");
            }
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, "请不要重复申请");
        }

    }

    /**
     * 代理商提现
     *
     * @param putForwardRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> putForward(PutForwardRequest putForwardRequest) {
        Long userId = Long.valueOf(putForwardRequest.getUserid());
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getAgent() == 2) {
            return new ResultInfo<>(ResultInfo.FAILURE, "平台审核中，请耐心等候！");
        }
        if (user.getAgent() == 1) {
            BigDecimal candidateAgent = new BigDecimal(candidateAgentMoney);
            BigDecimal feeSum = ordersMapper.findSumFeeByUserId(userId);
            if (feeSum.compareTo(candidateAgent) >= 0) {
                List<Long> aLongs = new ArrayList<>();
                //查询下线用户的id
                if (putForwardRequest.getType() == 1) {
                    aLongs = get_OneLayerUserIds(user.getInviteCode());
                }
                if (putForwardRequest.getType() == 2) {
                    aLongs = get_TwoLayerUserIds(user.getInviteCode());
                }
                BigDecimal amount = null;
                //一级或二级下线赞赏eos总数
                if (!aLongs.isEmpty()) {
                    amount = ordersMapper.findSumEosByUserIds(aLongs);
                }
                if (amount == null) {
                    amount = new BigDecimal(0);
                }

                BigDecimal presented = BigDecimal.valueOf(0);

                //BigDecimal amount_rmb = ordersMapper.findSumRmbByUserIds(aLongs);
                //BigDecimal presented_rmb = BigDecimal.valueOf(0);

                LOG.info("》》》下级赞赏eos数量==" + amount);
                //LOG.info("》》》下级赞赏rmb数量==" + amount_rmb);
                //统计一级下线提成
                if (putForwardRequest.getType() == 1) {
                    presented = exchangeMapper.findEosPutForwardSumByUserIdAndType(userId, 1);
                    //presented_rmb = exchangeMapper.findRmbPutForwardSumByUserIdAndType(userId, 1);
                }
                //统计一级下线提成
                if (putForwardRequest.getType() == 2) {
                    presented = exchangeMapper.findEosPutForwardSumByUserIdAndType(userId, 2);
                    //presented_rmb = exchangeMapper.findRmbPutForwardSumByUserIdAndType(userId, 2);
                }
                LOG.info("》》》已经兑换eos数量==" + presented);
                //LOG.info("》》》已经兑换rmb数量==" + presented_rmb);
                if (presented == null) {
                    presented = BigDecimal.valueOf(0);
                }
                /*if (presented_rmb == null) {
                    presented_rmb = BigDecimal.valueOf(0);
                }*/
                //计算剩余量
                BigDecimal available = amount.subtract(presented);
                //BigDecimal available_rmb = amount_rmb.subtract(presented_rmb);
                LOG.info("》》》剩余未兑换eos数量==" + available);
                //LOG.info("》》》剩余未兑换rmb数量==" + available_rmb);
                LOG.info("》》》本次兑换数量数量==" + putForwardRequest.getEosAmount());
                if (available.compareTo(putForwardRequest.getEosAmount()) >= 0) {
                    String eosWalletName = eosWalletMapper.findWalletNameByUserId(userId);
                    if (StringUtils.isNoneBlank(eosWalletName)) {
                        BigDecimal num = new BigDecimal(0);
                        //BigDecimal num_rmb = new BigDecimal(0);
                        if (putForwardRequest.getType() == 1) {
                            num = putForwardRequest.getEosAmount().multiply(new BigDecimal(put_forward_a)).setScale(4, BigDecimal.ROUND_DOWN);
                            LOG.info("》》》本次兑换数量==" + num);
                            //num_rmb = available_rmb.multiply(new BigDecimal(put_forward_a)).setScale(4);
                            //LOG.info("》》》本次兑换rmb数量==" + num_rmb);
                        }
                        if (putForwardRequest.getType() == 2) {
                            num = putForwardRequest.getEosAmount().multiply(new BigDecimal(put_forward_b)).setScale(4, BigDecimal.ROUND_DOWN);
                            LOG.info("》》》本次兑换数量==" + num);
                            //num_rmb = available_rmb.multiply(new BigDecimal(put_forward_b)).setScale(4);
                            //LOG.info("》》》本次兑换rmb数量==" + num_rmb);
                        }

                        BigDecimal rmb = new BigDecimal(0);
                        //eosAccount.fromOperateTransaction(eosWalletName, num.toString(), ExchangeConstant.AGENT_REWARD);

                        eosService.saveTransaction(userId, eosConstant.maioperate, eosWalletName, num.toString(), ExchangeConstant.AGENT_LOCK_POSITION, 7);

                        Exchange exchange = new Exchange();
                        exchange.setUserId(userId);
                        exchange.setDownline(putForwardRequest.getType());
                        exchange.setDevoteEos(putForwardRequest.getEosAmount());
                        exchange.setEos(num);
                        exchange.setRemark(ExchangeConstant.AGENT_LOCK_POSITION);

                        exchangeMapper.insertSelective(exchange);
                        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
                    }
                } else {
                    return new ResultInfo<>(ResultInfo.FAILURE, "余额不足");
                }
            }
        }
        return new ResultInfo<>(ResultInfo.FAILURE, "资格不够");
    }

    /**
     * 兑换记录
     *
     * @param page_request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> forwardRecord(Page_Request page_request) {
        Integer page = 0;
        if (page_request.getPage() != 0) {
            page = page_request.getSize() * page_request.getPage() + 1;
        }
        List<Exchange> exchangeList = exchangeMapper.findListByUserId(Long.valueOf(page_request.getUserid()), page, page_request.getSize());
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, exchangeList);
    }


    /**
     * 兑换记录
     *
     * @param page_request
     * @return
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    @Override
    public PageResponse AllForwardRecord(PageBase_Request page_request) {
        PageHelper.startPage(page_request.getPage(), page_request.getSize(), "id DESC");
        List<AllForwardRecordResponse> exchangeList = new ArrayList<>();
        if (StringUtils.isEmpty(page_request.getCondition())) {
            exchangeList = exchangeMapper.findAllList();
        } else {
            exchangeList = exchangeMapper.findAllListByPhone(page_request.getCondition());
        }
        PageInfo<AllForwardRecordResponse> pageInfo = new PageInfo<AllForwardRecordResponse>(exchangeList);
        PageResponse response = new PageResponse(pageInfo);
        return response;
    }

    /**
     * 查询下线业绩
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    public AchievementResponse getAchievement(GetAchievementABRequest request) {
        Long userId = Long.valueOf(request.getUserid());
        User user = userMapper.selectByPrimaryKey(userId);
        AchievementResponse achievementResponse = new AchievementResponse();
        if (request.getType() == 1) {
            if (user != null) {
                //查询一级下线
                return getAchievementResponse(userId, user, achievementResponse, request.getType());
            }
        }
        if (request.getType() == 2) {
            //查询二级下线
            return getAchievementResponse(userId, user, achievementResponse, request.getType());
        }
        return achievementResponse;
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
     * 查询代理候选人
     *
     * @param pageBaseRequest
     * @return
     * @date 2018年6月23日
     * @author LuoFuMin
     */
    @Override
    public PageResponse getCandidates(PageBase_Request pageBaseRequest) {

        PageHelper.startPage(pageBaseRequest.getPage(), pageBaseRequest.getSize(), "id DESC");
        List<UserInfoResponse> list = new ArrayList<>();
        if (StringUtils.isEmpty(pageBaseRequest.getCondition())) {
            list = userMapper.findListByAgent(4);
        } else {
            list = userMapper.findListByAgentAndPhone(4, pageBaseRequest.getCondition());
        }

        List<GetCandidatesResponse> candidatesResponseList = new ArrayList<>();

        for (UserInfoResponse userInfoResponse : list) {
            Long userId = Long.valueOf(userInfoResponse.getUserid());
            BigDecimal sumRmb = ordersMapper.findSumFeeByUserId(userId);
            BigDecimal sumEos = ordersMapper.findSumAmountByUserId(userId);

            LOG.info("》》》getInviteCode==" + userInfoResponse.getInviteCode());

            List<Long> longs = getTwoLayerUserIds(userInfoResponse.getInviteCode());

            LOG.info("》》》longs==" + longs.size());

            List<Long> a = get_OneLayerUserIds(userInfoResponse.getInviteCode());
            List<Long> b = get_TwoLayerUserIds(userInfoResponse.getInviteCode());

            BigDecimal abSumEos = new BigDecimal(0);
            BigDecimal abSumFee = new BigDecimal(0);
            if (!longs.isEmpty()) {
                abSumEos = ordersMapper.findSumEosByUserIds(longs);
                abSumFee = ordersMapper.findSumFeeByUserIds(longs);
                LOG.info("》》》abSumEos==" + abSumEos);
                LOG.info("》》》abSumFee==" + abSumFee);
            }
            if (abSumEos == null) {
                abSumEos = new BigDecimal(0);
            }
            if (abSumFee == null) {
                abSumFee = new BigDecimal(0);
            }

            if (sumRmb == null) {
                sumRmb = new BigDecimal(0);
            }
            if (sumEos == null) {
                sumEos = new BigDecimal(0);
            }
            GetCandidatesResponse getCandidatesResponse = new GetCandidatesResponse();
            getCandidatesResponse.setUserId(userId);
            getCandidatesResponse.setMobileNumber(userInfoResponse.getMobileNumber());
            getCandidatesResponse.setName(userInfoResponse.getName());
            getCandidatesResponse.setNickname(userInfoResponse.getNickname());
            getCandidatesResponse.setSumEos(sumEos);
            getCandidatesResponse.setSumRmb(sumRmb);
            getCandidatesResponse.setSumEosAB(abSumEos);
            getCandidatesResponse.setSumRmbAB(abSumFee);
            getCandidatesResponse.setPeopleA(a.size());
            getCandidatesResponse.setPeopleB(b.size());
            getCandidatesResponse.setAgent((int) userInfoResponse.getAgent());
            candidatesResponseList.add(getCandidatesResponse);
        }

        PageInfo<GetCandidatesResponse> pageInfo = new PageInfo<GetCandidatesResponse>(candidatesResponseList);
        PageResponse response = new PageResponse(pageInfo);
        return response;

    }

    /**
     * 代理审核
     *
     * @param pageBaseRequest
     * @return
     * @date 2018年6月23日
     * @author LuoFuMin
     */
    @Override
    public PageResponse getAgent(PageBase_Request pageBaseRequest) {
        PageHelper.startPage(pageBaseRequest.getPage(), pageBaseRequest.getSize(), "id DESC");
        List<UserInfoResponse> list = new ArrayList<>();
        List<Integer> lists = new ArrayList();
        lists.add(0);
        lists.add(1);
        lists.add(2);
        if (StringUtils.isEmpty(pageBaseRequest.getCondition())) {

            list = userMapper.findListByAgents(lists);
        } else {
            list = userMapper.findListByAgentsAndPhone(lists, pageBaseRequest.getCondition());
        }

        List<GetCandidatesResponse> candidatesResponseList = new ArrayList<>();

        for (UserInfoResponse userInfoResponse : list) {
            Long userId = Long.valueOf(userInfoResponse.getUserid());
            BigDecimal sumRmb = ordersMapper.findSumFeeByUserId(userId);
            BigDecimal sumEos = ordersMapper.findSumAmountByUserId(userId);

            LOG.info("》》》getInviteCode==" + userInfoResponse.getInviteCode());

            List<Long> longs = getTwoLayerUserIds(userInfoResponse.getInviteCode());

            LOG.info("》》》longs==" + longs.size());

            List<Long> a = get_OneLayerUserIds(userInfoResponse.getInviteCode());
            List<Long> b = get_TwoLayerUserIds(userInfoResponse.getInviteCode());

            BigDecimal abSumEos = new BigDecimal(0);
            BigDecimal abSumFee = new BigDecimal(0);
            if (!longs.isEmpty()) {
                abSumEos = ordersMapper.findSumEosByUserIds(longs);
                abSumFee = ordersMapper.findSumFeeByUserIds(longs);
                LOG.info("》》》abSumEos==" + abSumEos);
                LOG.info("》》》abSumFee==" + abSumFee);
            }
            if (abSumEos == null) {
                abSumEos = new BigDecimal(0);
            }
            if (abSumFee == null) {
                abSumFee = new BigDecimal(0);
            }

            if (sumRmb == null) {
                sumRmb = new BigDecimal(0);
            }
            if (sumEos == null) {
                sumEos = new BigDecimal(0);
            }
            GetCandidatesResponse getCandidatesResponse = new GetCandidatesResponse();
            getCandidatesResponse.setUserId(userId);
            getCandidatesResponse.setMobileNumber(userInfoResponse.getMobileNumber());
            getCandidatesResponse.setName(userInfoResponse.getName());
            getCandidatesResponse.setNickname(userInfoResponse.getNickname());
            getCandidatesResponse.setSumEos(sumEos);
            getCandidatesResponse.setSumRmb(sumRmb);
            getCandidatesResponse.setSumEosAB(abSumEos);
            getCandidatesResponse.setSumRmbAB(abSumFee);
            getCandidatesResponse.setPeopleA(a.size());
            getCandidatesResponse.setPeopleB(b.size());
            getCandidatesResponse.setAgent((int) userInfoResponse.getAgent());
            candidatesResponseList.add(getCandidatesResponse);
        }

        PageInfo<GetCandidatesResponse> pageInfo = new PageInfo<GetCandidatesResponse>(candidatesResponseList);
        PageResponse response = new PageResponse(pageInfo);

        return response;

    }

    /**
     * 代理商提现(人民币)
     *
     * @param request
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    @Override
    public Boolean putMoney(PutMoneyRequest request) {

        Long userId = Long.valueOf(request.getUserid());

        BigDecimal adevoteRmb = putMoneyMapper.findSumDevoteRmb(userId, 1);

        BigDecimal bdevoteRmb = putMoneyMapper.findSumDevoteRmb(userId, 2);

        if (adevoteRmb == null) {
            adevoteRmb = new BigDecimal(0);
        }
        if (bdevoteRmb == null) {
            bdevoteRmb = new BigDecimal(0);
        }

        String inviteCode = userMapper.findInviteCodeByUserId(userId);

        List<Long> aLongs = get_OneLayerUserIds(inviteCode);
        List<Long> bLongs = get_TwoLayerUserIds(inviteCode);

        BigDecimal aFee = ordersMapper.findSumFeeByUserIds(aLongs);

        BigDecimal bFee = ordersMapper.findSumFeeByUserIds(bLongs);

        if (aFee == null) {
            aFee = new BigDecimal(0);
        }
        if (bFee == null) {
            bFee = new BigDecimal(0);
        }

        if (request.getA_menoy().compareTo(aFee.subtract(adevoteRmb)) > 0) {
            return false;
        }
        if (request.getB_menoy().compareTo(bFee.subtract(bdevoteRmb)) > 0) {
            return false;
        }
        return false;
    }

    /**
     * 查询下线业绩
     *
     * @param userId
     * @param user
     * @param achievementResponse
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    private AchievementResponse getAchievementResponse(Long userId, User user, AchievementResponse achievementResponse, int i) {
        List<Long> aLongs = new ArrayList<>();
        if (i == 1) {
            aLongs = get_OneLayerUserIds(user.getInviteCode());
        }
        if (i == 2) {
            aLongs = get_TwoLayerUserIds(user.getInviteCode());
        }
        BigDecimal rmb = ordersMapper.findSumFeeByUserId(userId);
        if (rmb == null) {
            rmb = BigDecimal.valueOf(0);
        }
        if (aLongs.isEmpty()) {
            achievementResponse.setRmb(rmb.divide(BigDecimal.valueOf(100)));
            achievementResponse.setRmbSum(BigDecimal.valueOf(0));
            achievementResponse.setEosSum(BigDecimal.valueOf(0));
            achievementResponse.setStaging(BigDecimal.valueOf(0));
            achievementResponse.setPresented(BigDecimal.valueOf(0));
            achievementResponse.setAgent((byte) 3);
            achievementResponse.setPeople(0);
            achievementResponse.setBecomeAgent(Integer.valueOf(becomeAgentMoney) / 100);
            achievementResponse.setCandidateAgent(Integer.valueOf(candidateAgentMoney) / 100);
            return achievementResponse;
        }
        //下线赞赏eos总数
        BigDecimal amount = ordersMapper.findSumEosByUserIds(aLongs);
        if (amount == null) {
            amount = BigDecimal.valueOf(0);
        }
        //下线赞赏rmb总数
        BigDecimal fee = ordersMapper.findSumFeeByUserIds(aLongs);
        if (fee == null) {
            fee = BigDecimal.valueOf(0);
        }
        //查询已经兑换的总数
        BigDecimal presented = null;
        if (i == 1) {
            presented = exchangeMapper.findEosPutForwardSumByUserIdAndType(userId, 1);
        }
        if (i == 2) {
            presented = exchangeMapper.findEosPutForwardSumByUserIdAndType(userId, 2);
        }
        if (presented == null) {
            presented = new BigDecimal(0);
        }
        LOG.info("》》》 已经兑换 presented==" + presented);

        LOG.info("》》》 赞赏总数 amount==" + amount);

        LOG.info("》》》 剩余 amount.subtract(presented)==" + amount.subtract(presented));

        achievementResponse = new AchievementResponse();
        achievementResponse.setEosSum(amount);
        achievementResponse.setRmbSum(fee.divide(BigDecimal.valueOf(100)));
        achievementResponse.setPresented(presented);
        //设置未兑换余额
        achievementResponse.setStaging(amount.subtract(presented));
        achievementResponse.setRmb(rmb.divide(BigDecimal.valueOf(100)));
        achievementResponse.setAgent(user.getAgent());
        achievementResponse.setPeople(aLongs.size());
        achievementResponse.setBecomeAgent(Integer.valueOf(becomeAgentMoney) / 100);
        achievementResponse.setCandidateAgent(Integer.valueOf(candidateAgentMoney) / 100);
        return achievementResponse;
    }

    /**
     * 集合用户id（递归）
     *
     * @param userChildrenDTOList
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    public static List<Long> mergeUserIds(List<UserChildrenDTO> userChildrenDTOList, List<Long> aLongs) {
        if (aLongs == null) {
            aLongs = new ArrayList<>();
        }
        //合并ab级用户id到aLongs
        for (UserChildrenDTO userChildrenDTO : userChildrenDTOList) {
            aLongs.add(userChildrenDTO.getId());
            if (!userChildrenDTO.getChildrenUserIds().isEmpty()) {
                //递归
                mergeUserIds(userChildrenDTO.getChildrenUserIds(), aLongs);
            }
        }
        return aLongs;
    }

    /**
     * 查询两级下线用户的id
     *
     * @param inviteCode
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    private List<Long> getTwoLayerUserIds(String inviteCode) {
        List<Long> aLongs = new ArrayList<>();
        List<UserChildrenDTO> userChildrenDTOList = userMapper.findUserIdsByInviteCode(inviteCode);
        if (!userChildrenDTOList.isEmpty()) {
            for (UserChildrenDTO userChildrenDTO : userChildrenDTOList) {
                aLongs.add(userChildrenDTO.getId());
                List<UserChildrenDTO> userChildrenDTOList2 = userMapper.findUserIdsByInviteCode(userChildrenDTO.getInviteCode());
                if (!userChildrenDTOList2.isEmpty()) {
                    for (UserChildrenDTO userChildrenDTO2 : userChildrenDTOList2) {
                        aLongs.add(userChildrenDTO2.getId());
                    }
                }
            }
        }
        return aLongs;
    }

    /**
     * 查询一级下线用户的id
     *
     * @param inviteCode
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    private List<Long> get_OneLayerUserIds(String inviteCode) {
        List<Long> aLongs = new ArrayList<>();
        List<UserChildrenDTO> userChildrenDTOList = userMapper.findUserIdsByInviteCode(inviteCode);
        if (!userChildrenDTOList.isEmpty()) {
            for (UserChildrenDTO userChildrenDTO : userChildrenDTOList) {
                aLongs.add(userChildrenDTO.getId());
            }
        }
        return aLongs;
    }


    /**
     * 查询二级下线用户的id
     *
     * @param inviteCode
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    private List<Long> get_TwoLayerUserIds(String inviteCode) {
        List<Long> aLongs = new ArrayList<>();
        List<UserChildrenDTO> userChildrenDTOList = userMapper.findUserIdsByInviteCode(inviteCode);
        if (!userChildrenDTOList.isEmpty()) {
            for (UserChildrenDTO userChildrenDTO : userChildrenDTOList) {
                List<UserChildrenDTO> userChildrenDTOList2 = userMapper.findUserIdsByInviteCode(userChildrenDTO.getInviteCode());
                if (!userChildrenDTOList2.isEmpty()) {
                    for (UserChildrenDTO userChildrenDTO2 : userChildrenDTOList2) {
                        aLongs.add(userChildrenDTO2.getId());
                    }
                }
            }
        }
        return aLongs;
    }
}
