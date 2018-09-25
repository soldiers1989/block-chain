package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.football.*;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;

import java.text.ParseException;
import java.util.Map;

public interface FootballMatchService {

    /**
     * 获取脉链比赛列表
     *
     * @return
     */
    ResultInfo<?> getMaiMatchList(UserIdRequest userIdRequest) throws ParseException;

    /**
     * 加入订单
     * @param addOrdersRequest
     * @return
     */
    ResultInfo<?> addOrders(AddOrdersRequest addOrdersRequest) throws Exception;


    ResultInfo<?> updateOrders(UpdateOrdersRequest updateOrdersRequest) throws Exception;


    ResultInfo<?> retreatOrders(RetreatOrdersRequest retreatOrdersRequest) throws Exception;


    ResultInfo<?> getOrders(GetMatchTypeRequest getMatchTypeDTO);

    ResultInfo<?> getTeamMsg(GetTeamMsgRequest request);

    ResultInfo<?> getMoney();

    /**
     * 添加脉链比赛
     * @param map
     * @return
     */
    ResultInfo<?> addFootballMatch(Map<String, Object> map);

    /**
     *麦链杯比赛列表
     * @return
     */
    ResultInfo<?> footballMatchList();

    /**
     * 国家队员列表
     * @return
     */
    ResultInfo<?> footballTeamList();

    /**
     *根据国家(球队ID)查询队员列表
     * @param map
     * @return
     */
    ResultInfo<?> footballTeamMemberList(Map<String, Object> map);

    /**
     * 根据球队ID和队员ID删除队员
     * @param map
     * @return
     */
    ResultInfo<?> deleteTeamMember(Map<String, Object> map);

    /**
     * 公布比赛结果，根据比赛结果，进行结算工资
     * @param map
     * @return
     */
    ResultInfo<?> settlement(Map<String, Object> map);

    /**
     * 修改让球分
     * @param map
     * @return
     */
    ResultInfo<?> updateConcede(Map<String, Object> map);
}
