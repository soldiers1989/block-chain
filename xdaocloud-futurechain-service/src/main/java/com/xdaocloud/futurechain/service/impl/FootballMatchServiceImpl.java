package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.constant.AESConstant;
import com.xdaocloud.futurechain.dto.req.football.*;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.dto.resp.football.*;
import com.xdaocloud.futurechain.eos.EosAccount;
import com.xdaocloud.futurechain.mapper.*;
import com.xdaocloud.futurechain.model.*;
import com.xdaocloud.futurechain.service.EosService;
import com.xdaocloud.futurechain.service.FootballMatchService;
import com.xdaocloud.futurechain.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static com.xdaocloud.futurechain.constant.AESConstant.KEY;
import static com.xdaocloud.futurechain.util.DateUtils.DATE_FORMAT_FULL;
import static com.xdaocloud.futurechain.util.EncoderUtils.decryptB;


@Service
public class FootballMatchServiceImpl implements FootballMatchService {

    private static Logger LOG = LoggerFactory.getLogger(FootballMatchServiceImpl.class);

    @Autowired
    private FootballMatchMapper footballMatchMapper;

    @Autowired
    private FootballTeamMapper footballTeamMapper;

    @Autowired
    private VieOrdersMapper vieOrdersMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private EosAccount eosAccount;

    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private QuotaMapper quotaMapper;

    @Autowired
    private EosService eosService;

    @Autowired
    private FootballTeamMemberMapper footballTeamMemberMapper;


    /**
     * 获取脉链比赛列表
     *
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getMaiMatchList(UserIdRequest userIdRequest) throws ParseException {

        Long userid = Long.valueOf(userIdRequest.getUserid());
        List<Long> friends = friendMapper.findFriendsByFriendId(userid);
        List<Long> friends2 = friendMapper.findFriendsByUserId(userid);
        if (!friends2.isEmpty()) {
            friends.addAll(friends2);
        }
        List<FootballMatch> list = footballMatchMapper.findListByTypeAndStartTime(1, new Date());
        JSONArray jsonArray = new JSONArray();
        Iterator<FootballMatch> footballMatchIterator = list.iterator();
        JSONArray array = new JSONArray();
        while (footballMatchIterator.hasNext()) {
            FootballMatch footballMatch = footballMatchIterator.next();
            String startTime = footballMatch.getStartTime().substring(0, footballMatch.getStartTime().length() - 8);
            List<MatchDTO> matchDTOList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject();

            if (!array.contains(startTime)) {
                for (FootballMatch match : list) {
                    String start_time = match.getStartTime().substring(0, match.getStartTime().length() - 8);
                    if (start_time.equals(startTime)) {
                        array.add(startTime);
                        //String end_time = match.getEndTime().substring(0, match.getEndTime().length() - 3);
                        FootballTeam footballTeamA = footballTeamMapper.selectByPrimaryKey(match.getTeamA());
                        FootballTeam footballTeamB = footballTeamMapper.selectByPrimaryKey(match.getTeamB());

                        Integer sumPeople = vieOrdersMapper.findCountById(match.getId());
                        Integer sumFriends = 0;
                        if (!friends.isEmpty()) {
                            sumFriends = vieOrdersMapper.findCountByIds(friends, match.getId());
                        }
                        MatchDTO matchDTO = new MatchDTO();
                        matchDTO.setMatchId(match.getId());
                        matchDTO.setTeamNameA(match.getTeamNameA());
                        matchDTO.setTeamIdA(footballTeamA.getId().toString());
                        matchDTO.setTeamNameB(match.getTeamNameB());
                        matchDTO.setTeamIdB(footballTeamB.getId().toString());
                        matchDTO.setStartTime(match.getStartTime().substring(0, match.getStartTime().length() - 3));
                        matchDTO.setEndTime("");
                        matchDTO.setConcede(match.getConcede());
                        matchDTO.setField(match.getField());
                        matchDTO.setNationalFlagA(footballTeamA.getNationalFlag());
                        matchDTO.setNationalFlagB(footballTeamB.getNationalFlag());
                        matchDTO.setSumPeople(sumPeople);
                        matchDTO.setSumFriends(sumFriends);
                        BigDecimal total = vieOrdersMapper.findSumByMatchId(match.getId());
                        matchDTO.setTotal(total);
                        matchDTO.setEndTimestamp(DateUtils.dateToTimeStamp(match.getStartTime(), DATE_FORMAT_FULL));
                        matchDTOList.add(matchDTO);
                    }
                }
                jsonObject.put("date", startTime + DateUtils.dateToWeek(startTime));
                jsonObject.put("match", matchDTOList);
                jsonArray.add(jsonObject);
            }
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, jsonArray);
    }

    /**
     * 加入订单
     *
     * @param addOrdersRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> addOrders(AddOrdersRequest addOrdersRequest) throws Exception {
        Long userId = Long.valueOf(addOrdersRequest.getUserid());
        EosWallet eosWallet = eosWalletMapper.findOneByUserId(userId);
        String transactionPassword = addOrdersRequest.getTransactionPassword();
        User user = userMapper.selectByPrimaryKey(userId);
        EosWallet wallet = eosWalletMapper.findOneByUserId(user.getId());
        List<MatchOrderDTO> matchOrderDTOList = addOrdersRequest.getOrder();
        if (!matchOrderDTOList.isEmpty()) {
            BigDecimal eosSum = new BigDecimal(0);
            for (MatchOrderDTO matchOrderDTO : matchOrderDTOList) {
                BigDecimal eosNum = new BigDecimal(matchOrderDTO.getSubtotal());
                if (eosNum.compareTo(BigDecimal.valueOf(100000)) > 0) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "超过最大金额");
                }
                FootballMatch footballMatch = footballMatchMapper.selectByPrimaryKey(Long.valueOf(matchOrderDTO.getMatchId()));
                if (null != footballMatch) {
                    if (!DateUtils.compare_date(footballMatch.getStartTime(), DateUtils.getNowDateTime())) {
                        return new ResultInfo<>(ResultInfo.FAILURE, footballMatch.getTeamNameA() + "VS" + footballMatch.getTeamNameB() + "-比赛已经开始，请重新下单");
                    }
                }
                eosSum = eosSum.add(new BigDecimal(matchOrderDTO.getSubtotal()));
            }

            //检查交易密码
            //ResultInfo<?> x = eosService.checkTransactionPassword(eosSum, transactionPassword, user);
            //if (x != null) return x;

            String activePrivateKey = decryptB(AESConstant.KEY, wallet.getActivePrivateKey());
            String balance = eosAccount.getBalance(eosWallet.getWalletName());
            Map<String, Integer> map = new HashMap<>();
            if (eosSum.compareTo(new BigDecimal(balance)) > 0) {
                map.put("status", 2);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
            }
            for (MatchOrderDTO matchOrderDTO : matchOrderDTOList) {
                FootballMatch footballMatch = footballMatchMapper.selectByPrimaryKey(Long.valueOf(matchOrderDTO.getMatchId()));
                eosAccount.toOperateTransaction(userId, eosWallet.getWalletName(), matchOrderDTO.getSubtotal(), "足球竞猜-" + footballMatch.getTeamNameA() + "VS" + footballMatch.getTeamNameB(), activePrivateKey);
                VieOrders vieOrders = new VieOrders();
                vieOrders.setMatchId(Long.valueOf(matchOrderDTO.getMatchId()));
                vieOrders.setAmount(new BigDecimal(matchOrderDTO.getSubtotal()));
                vieOrders.setUserId(userId);
                vieOrders.setVorl(Integer.valueOf(matchOrderDTO.getGameStatus()));
                vieOrders.setState((byte) 1);
                vieOrdersMapper.insertSelective(vieOrders);
            }
            map.put("status", 1);
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }


    /**
     * 修改订单
     *
     * @param updateOrdersRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> updateOrders(UpdateOrdersRequest updateOrdersRequest) throws Exception {
        Map<String, Integer> map = new HashMap<>();
        Long userId = Long.valueOf(updateOrdersRequest.getUserid());
        Long orderId = Long.valueOf(updateOrdersRequest.getOrderId());
        String transactionPassword = updateOrdersRequest.getTransactionPassword();
        BigDecimal eosSum = new BigDecimal(updateOrdersRequest.getSubtotal());
        User user = userMapper.selectByPrimaryKey(userId);
        EosWallet eosWallet = eosWalletMapper.findOneByUserId(userId);
        String pw = decryptB(KEY, eosWallet.getPassPhrase());
        VieOrders vieOrders = vieOrdersMapper.selectByPrimaryKey(orderId);
        if (!userId.equals(vieOrders.getUserId())) {
            return new ResultInfo<>(ResultInfo.NOT_FOUND, ResultInfo.MSG_NOT_FOUND);
        }
        if (eosSum.compareTo(BigDecimal.valueOf(100000)) > 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, "超过最大金额");
        }
        FootballMatch footballMatch = footballMatchMapper.selectByPrimaryKey(Long.valueOf(vieOrders.getMatchId()));

        if (null != footballMatch) {
            if (!DateUtils.compare_date(footballMatch.getStartTime(), DateUtils.getNowDateTime())) {
                return new ResultInfo<>(ResultInfo.FAILURE, footballMatch.getTeamNameA() + "VS" + footballMatch.getTeamNameB() + "-比赛已经开始，请重新下单");
            }
        }
        //检查交易密码
        //ResultInfo<?> x = eosService.checkTransactionPassword(eosSum, transactionPassword, user);
        //if (x != null) return x;

        int a = eosSum.compareTo(vieOrders.getAmount());
        if (a == 1) {
            BigDecimal num = eosSum.subtract(vieOrders.getAmount());
            String activePrivateKey = decryptB(AESConstant.KEY, eosWallet.getActivePrivateKey());
            String balance = eosAccount.getBalance(eosWallet.getWalletName());
            balance = balance.replace("MAI", "").replace(" ", "");
            if (num.compareTo(new BigDecimal(balance)) > 0) {
                map.put("status", 2);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
            }
            eosAccount.toOperateTransaction(userId, eosWallet.getWalletName(), num.toString(), "修改比赛-" + footballMatch.getTeamNameA() + "VS" + footballMatch.getTeamNameB(), activePrivateKey);
            vieOrders.setAmount(eosSum);
            vieOrders.setVorl(Integer.parseInt(updateOrdersRequest.getGameStatus()));
            vieOrdersMapper.updateByPrimaryKeySelective(vieOrders);
        }
        if (a == -1) {
            BigDecimal num = vieOrders.getAmount().subtract(eosSum);
            eosAccount.fromOperateTransaction(eosWallet.getWalletName(), num.toString(), "修改比赛-" + footballMatch.getTeamNameA() + "VS" + footballMatch.getTeamNameB());
            vieOrders.setAmount(eosSum);
            vieOrders.setVorl(Integer.parseInt(updateOrdersRequest.getGameStatus()));
            vieOrdersMapper.updateByPrimaryKeySelective(vieOrders);
        }
        if (a == 0) {
            vieOrders.setVorl(Integer.parseInt(updateOrdersRequest.getGameStatus()));
            vieOrdersMapper.updateByPrimaryKeySelective(vieOrders);
        }
        map.put("status", 1);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
    }

    /**
     * 撤销订单
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> retreatOrders(RetreatOrdersRequest request) throws Exception {
        Long userId = Long.valueOf(request.getUserid());
        Long orderId = Long.valueOf(request.getOrderId());
        EosWallet eosWallet = eosWalletMapper.findOneByUserId(userId);
        VieOrders vieOrders = vieOrdersMapper.selectByPrimaryKey(orderId);
        if (!userId.equals(vieOrders.getUserId())) {
            return new ResultInfo<>(ResultInfo.NOT_FOUND, ResultInfo.MSG_NOT_FOUND);
        }
        FootballMatch footballMatch = footballMatchMapper.selectByPrimaryKey(Long.valueOf(vieOrders.getMatchId()));
        if (null != footballMatch) {
            footballMatch.getStartTime();
            if (!DateUtils.compare_date(footballMatch.getStartTime(), DateUtils.getNowDateTime())) {
                return new ResultInfo<>(ResultInfo.FAILURE, footballMatch.getTeamNameA() + "VS" + footballMatch.getTeamNameB() + "-比赛已经开始，无法撤销");
            }
        }
        if (vieOrders.getState() != 1) {
            return new ResultInfo<>(ResultInfo.FAILURE, "订单无效");
        }
        eosAccount.fromOperateTransaction(eosWallet.getWalletName(), vieOrders.getAmount().toString(), footballMatch.getTeamNameA() + "VS" + footballMatch.getTeamNameB() + "-撤销竞猜");
        vieOrders.setState((byte) 6);
        vieOrdersMapper.updateByPrimaryKeySelective(vieOrders);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 获取下注订单
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getOrders(GetMatchTypeRequest request) {
        Long userid = Long.valueOf(request.getUserid());
        List<Long> friends = friendMapper.findFriendsByFriendId(userid);
        List<Long> friends2 = friendMapper.findFriendsByUserId(userid);
        if (!friends2.isEmpty()) {
            friends.addAll(friends2);
        }
        List<MatchDTO> matchDTOList = new ArrayList<>();
        List<VieOrders> vieOrdersList = vieOrdersMapper.findListByUserId(userid);
        if (!vieOrdersList.isEmpty()) {
            for (VieOrders vieOrders : vieOrdersList) {
                FootballMatch footballMatch = footballMatchMapper.selectByPrimaryKey(vieOrders.getMatchId());
                //String end_time = footballMatch.getEndTime().substring(0, footballMatch.getEndTime().length() - 3);
                FootballTeam footballTeamA = footballTeamMapper.selectByPrimaryKey(footballMatch.getTeamA());
                FootballTeam footballTeamB = footballTeamMapper.selectByPrimaryKey(footballMatch.getTeamB());

                Integer sumPeople = vieOrdersMapper.findCountById(footballMatch.getId());
                Integer sumFriends = 0;
                if (!friends.isEmpty()) {
                    sumFriends = vieOrdersMapper.findCountByIds(friends, footballMatch.getId());
                }
                if (request.getStatus() == 1) {//1:进行中
                    if (DateUtils.compare_date(footballMatch.getStartTime(), DateUtils.getNowDateTime()) && vieOrders.getState() == 1) {
                        MatchDTO matchDTO = new MatchDTO();
                        matchDTO.setMatchId(footballMatch.getId());
                        matchDTO.setTeamNameA(footballTeamA.getTeamName());
                        matchDTO.setTeamIdA(footballTeamA.getId().toString());
                        matchDTO.setTeamNameB(footballTeamB.getTeamName());
                        matchDTO.setTeamIdB(footballTeamB.getId().toString());
                        matchDTO.setStartTime(footballMatch.getStartTime().substring(0, footballMatch.getStartTime().length() - 3));
                        matchDTO.setEndTime("");
                        matchDTO.setConcede(footballMatch.getConcede());
                        matchDTO.setField(footballMatch.getField());
                        matchDTO.setNationalFlagA(footballTeamA.getNationalFlag());
                        matchDTO.setNationalFlagB(footballTeamB.getNationalFlag());
                        matchDTO.setSumPeople(sumPeople);
                        matchDTO.setSumFriends(sumFriends);
                        BigDecimal total = vieOrdersMapper.findSumByMatchId(footballMatch.getId());
                        matchDTO.setTotal(total);
                        matchDTO.setEndTimestamp(DateUtils.dateToTimeStamp(footballMatch.getStartTime(), DATE_FORMAT_FULL));
                        matchDTO.setOrderId(vieOrders.getId());
                        matchDTO.setSubtotal(vieOrders.getAmount());
                        matchDTO.setGameStatus(vieOrders.getVorl());
                        matchDTO.setStatus(1);
                        matchDTO.setWinEos(BigDecimal.valueOf(0));
                        matchDTO.setState(vieOrders.getState());
                        matchDTOList.add(matchDTO);
                    }
                } else {//2：已完成
                    if (!DateUtils.compare_date(footballMatch.getStartTime(), DateUtils.getNowDateTime()) || vieOrders.getState() != 1) {
                        MatchDTO matchDTO = new MatchDTO();
                        matchDTO.setMatchId(footballMatch.getId());
                        matchDTO.setTeamNameA(footballTeamA.getTeamName());
                        matchDTO.setTeamIdA(footballTeamA.getId().toString());
                        matchDTO.setTeamNameB(footballTeamB.getTeamName());
                        matchDTO.setTeamIdB(footballTeamB.getId().toString());
                        matchDTO.setStartTime(footballMatch.getStartTime().substring(0, footballMatch.getStartTime().length() - 3));
                        matchDTO.setEndTime("");
                        matchDTO.setConcede(footballMatch.getConcede());
                        matchDTO.setField(footballMatch.getField());
                        matchDTO.setNationalFlagA(footballTeamA.getNationalFlag());
                        matchDTO.setNationalFlagB(footballTeamB.getNationalFlag());
                        matchDTO.setSumPeople(sumPeople);
                        matchDTO.setSumFriends(sumFriends);
                        BigDecimal total = vieOrdersMapper.findSumByMatchId(footballMatch.getId());
                        matchDTO.setTotal(total);
                        matchDTO.setEndTimestamp(DateUtils.dateToTimeStamp(footballMatch.getStartTime(), DATE_FORMAT_FULL));
                        matchDTO.setOrderId(vieOrders.getId());
                        matchDTO.setSubtotal(vieOrders.getAmount());
                        matchDTO.setGameStatus(vieOrders.getVorl());
                        matchDTO.setStatus(2);
                        matchDTO.setWinEos(vieOrders.getWinEos());
                        matchDTO.setState(vieOrders.getState());
                        matchDTOList.add(matchDTO);
                    }
                }
            }
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, matchDTOList);
    }


    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getTeamMsg(GetTeamMsgRequest request) {
        FootballTeam footballTeam = footballTeamMapper.selectByPrimaryKey(Long.valueOf(request.getTeamId()));
        List<FootballTeamMember> footballTeamMemberList = footballTeamMemberMapper.findListByTeamId(footballTeam.getId());
        TeamMsgResponse teamMsgResponse = new TeamMsgResponse();
        //教练
        List<CoachDTO> coachDTOList = new ArrayList<>();
        //队长
        List<CaptainDTO> captainDTOList = new ArrayList<>();
        //首发队员
        List<TeamDTO> teamDTOList = new ArrayList<>();
        //替补队员
        List<SubstitutionDTO> substitutionDTOList = new ArrayList<>();
        //教练信息
        CoachDTO coachDTO = new CoachDTO();
        coachDTO.setName(footballTeam.getCoach());
        coachDTO.setAvatar(footballTeam.getCoachAvatar());
        coachDTOList.add(coachDTO);

        if (!footballTeamMemberList.isEmpty()) {
            for (FootballTeamMember teamMember : footballTeamMemberList) {
                if ("10".equals(teamMember.getIdentity())) {//队长信息
                    CaptainDTO captainDTO = new CaptainDTO();
                    captainDTO.setName(teamMember.getName());
                    captainDTO.setAvatar(teamMember.getAvatar());
                    captainDTO.setBallNumber(teamMember.getBallNumber().toString());
                    captainDTO.setCourtPosition(teamMember.getCourtPosition());
                    captainDTOList.add(captainDTO);
                } else if ("1".equals(teamMember.getIdentity())) {//首发队员
                    TeamDTO teamDTO = new TeamDTO();
                    teamDTO.setName(teamMember.getName());
                    teamDTO.setAvatar(teamMember.getAvatar());
                    teamDTO.setBallNumber(teamMember.getBallNumber().toString());
                    teamDTO.setCourtPosition(teamMember.getCourtPosition());
                    teamDTOList.add(teamDTO);
                } else if ("0".equals(teamMember.getIdentity())) {
                    SubstitutionDTO substitutionDTO = new SubstitutionDTO();
                    substitutionDTO.setName(teamMember.getName());
                    substitutionDTO.setAvatar(teamMember.getAvatar());
                    substitutionDTO.setBallNumber(teamMember.getBallNumber().toString());
                    substitutionDTO.setCourtPosition(teamMember.getCourtPosition());
                    substitutionDTOList.add(substitutionDTO);
                }
            }
        }
        teamMsgResponse.setTeam(teamDTOList);
        teamMsgResponse.setSubstitution(substitutionDTOList);
        teamMsgResponse.setCoach(coachDTOList);
        teamMsgResponse.setCaptain(captainDTOList);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, teamMsgResponse);
    }


    /**
     * 发放奖励
     *
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> getMoney() {
        List<VieOrders> vieOrdersList = vieOrdersMapper.findListByState(1);
        //发送奖励
        pushAward(vieOrdersList);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 添加脉链比赛
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> addFootballMatch(Map<String, Object> map) {

        try {
            //球队AID team_a
            //球队BID team_b
            //让球 -1 为A队让球   +1为B队让球  concede
            //让球  A队让球数量  score_a   B队让球数量  score_b
            //比赛地点 field
            //竞猜截止时间 start_time

            footballMatchMapper.addFootballMatch(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> footballMatchList() {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = footballMatchMapper.footballMatchList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> footballTeamList() {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = footballTeamMapper.footballTeamList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, list);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 根据国家(球队ID)查询队员列表
     *
     * @param map footballTeamId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> footballTeamMemberList(Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = footballTeamMemberMapper.footballTeamMemberList(Long.parseLong(map.get("footballTeamId").toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, list);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }


    /**
     * 根据球队ID和队员ID删除队员
     *
     * @param map teamMemberId  队员ID    footballTeamId 球队ID
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> deleteTeamMember(Map<String, Object> map) {
        try {
            footballTeamMemberMapper.deleteTeamMember(Long.parseLong(map.get("teamMemberId").toString()), Long.parseLong(map.get("footballTeamId").toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 公布比赛结果，根据比赛结果，进行结算工资
     *
     * @param map footballMatchId 比赛ID  win 1：球队a赢球，2：球队b赢球，0：平局', endTime
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> settlement(Map<String, Object> map) {
        try {
            /**
             * 根据比赛ID修改比赛结果
             */
            footballMatchMapper.updateByFootballMatchId(Long.parseLong(map.get("footballMatchId").toString()),
                    Integer.parseInt(map.get("win").toString()), map.get("endTime").toString(), Integer.parseInt(map.get("scoreA").toString()), Integer.parseInt(map.get("scoreB").toString()));

            //结算工资
            List<VieOrders> vieOrdersList = vieOrdersMapper.findListByFootballMatchId(Long.parseLong(map.get("footballMatchId").toString()));


            if (vieOrdersList != null && vieOrdersList.size() > 0) {
                //发送奖励
                pushAward(vieOrdersList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 发送奖励
     *
     * @param vieOrdersList
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    private void pushAward(List<VieOrders> vieOrdersList) {
        for (VieOrders vieOrders : vieOrdersList) {
            FootballMatch footballMatch = footballMatchMapper.selectByPrimaryKey(vieOrders.getMatchId());
            if (footballMatch.getEndTime() != null && !DateUtils.compare_date(footballMatch.getEndTime(), DateUtils.getNowDateTime())) {
                BigDecimal sum = vieOrdersMapper.findSumByMatchId(vieOrders.getMatchId());
                if (footballMatch.getConcedeWin() != null) {
                    if (footballMatch.getConcedeWin() == vieOrders.getVorl()) {
                        if (vieOrders.getState() == 1) {
                            BigDecimal yingde = vieOrdersMapper.findSumByMatchIdAndVorl(footballMatch.getId(), footballMatch.getConcedeWin());
                            BigDecimal shude = sum.subtract(yingde);
                            LOG.info("》》所有下注的总数===" + sum);
                            LOG.info("》》买赢得的总数===" + yingde);
                            LOG.info("》》卖输掉的总数===" + shude);
                            EosWallet eosWallet = eosWalletMapper.findOneByUserId(vieOrders.getUserId());
                            //计算得到的奖励
                            LOG.info("》》用户===" + vieOrders.getUserId() + "===买的数量===" + vieOrders.getAmount());
                            BigDecimal bili = vieOrders.getAmount().divide(yingde, 4, BigDecimal.ROUND_DOWN);
                            LOG.info("》》用户购买的数量 能够获取eos的比例===" + bili);
                            BigDecimal obtain = bili.multiply(shude);
                            LOG.info("》》赢得===" + obtain);
                            obtain = obtain.multiply(BigDecimal.valueOf(0.97)).setScale(4, BigDecimal.ROUND_DOWN);
                            LOG.info("》》扣除0.03后 实际赢得===" + obtain);
                            obtain = obtain.add(vieOrders.getAmount());
                            LOG.info("》》赢得加上本金===" + obtain);
                            try {
                                eosAccount.fromOperateTransaction(eosWallet.getWalletName(), obtain.toString(), "足球竞猜-" + footballMatch.getTeamNameA() + "VS" + footballMatch.getTeamNameB());
                                vieOrders.setState((byte) 8);
                                vieOrders.setWinEos(obtain);
                                vieOrdersMapper.updateByPrimaryKeySelective(vieOrders);
                            } catch (Exception e) {
                                e.printStackTrace();
                                LOG.error("》》》 结款错误，需要重新结款::userId::" + vieOrders.getUserId());
                                vieOrders.setWinEos(obtain);
                                vieOrders.setState((byte) 9);
                                vieOrdersMapper.updateByPrimaryKeySelective(vieOrders);
                            }
                        }
                    } else {
                        LOG.info("》》》 输掉比赛：：" + vieOrders.getMatchId() + "》》userId：：" + vieOrders.getUserId());
                        vieOrders.setState((byte) 7);
                        vieOrders.setWinEos(BigDecimal.valueOf(0));
                        vieOrdersMapper.updateByPrimaryKeySelective(vieOrders);
                    }
                }
            }
        }
    }

    /**
     * 修改让球分
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> updateConcede(Map<String, Object> map) {
        try {
            footballMatchMapper.updateConcede(Integer.parseInt(map.get("concede").toString()), Long.parseLong(map.get("teamMemberId").toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }
}
