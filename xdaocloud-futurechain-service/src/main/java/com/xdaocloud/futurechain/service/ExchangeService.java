package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.exchange.GetAchievementABRequest;
import com.xdaocloud.futurechain.dto.req.exchange.PutForwardRequest;
import com.xdaocloud.futurechain.dto.req.exchange.PutMoneyRequest;
import com.xdaocloud.futurechain.dto.resp.orders.AchievementResponse;

import java.math.BigDecimal;

public interface ExchangeService {

    ResultInfo<?> apply(Long userId);

    /**
     * 申请成为候选代理人
     *
     * @param userId 用户id
     * @return
     */
    ResultInfo<?> applyCandidate(Long userId);

    /**
     * 查询业绩
     *
     * @param userId 用户id
     * @return
     */
    AchievementResponse findAchievement(Long userId);

    /**
     * 申请成为代理商
     *
     * @param userId 用户id
     * @return
     */
    ResultInfo<?> applyAgent(Long userId);

    /**
     * 代理商提现
     *
     * @param request
     * @return
     */
    ResultInfo<?> putForward(PutForwardRequest request);

    /**
     * 兑换记录
     *
     * @param page_request
     * @return
     */
    ResultInfo<?> forwardRecord(Page_Request page_request);


    /**
     * 兑换记录
     *
     * @param page_request
     * @return
     */
    PageResponse AllForwardRecord(PageBase_Request page_request);

    /**
     * 查询业绩
     *
     * @return
     * @throws Exception
     * @date 2018年6月23日
     * @author LuoFuMin
     */
    AchievementResponse getAchievement(GetAchievementABRequest request);

    /**
     * 查询锁仓余额
     *
     * @param userId
     * @return
     * @date 2018年6月23日
     * @author LuoFuMin
     */
    BigDecimal findlockEosBalance(Long userId);


    /**
     * 查询代理候选人
     *
     * @param pageBaseRequest
     * @return
     * @date 2018年6月23日
     * @author LuoFuMin
     */
    PageResponse getCandidates(PageBase_Request pageBaseRequest);

    /**
     * 代理审核
     *
     * @param pageBaseRequest
     * @return
     * @date 2018年6月23日
     * @author LuoFuMin
     */
    PageResponse getAgent(PageBase_Request pageBaseRequest);


    /**
     * 代理商提现(人民币)
     *
     * @param request
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    Boolean putMoney(PutMoneyRequest request);
}
