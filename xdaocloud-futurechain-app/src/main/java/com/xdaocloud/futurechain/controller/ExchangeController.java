package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.exchange.GetAchievementABRequest;
import com.xdaocloud.futurechain.dto.req.exchange.PutForwardRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.service.ExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * 候选代理
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "ExchangeController", description = "代理商")
@RequestMapping("/api/app/")
public class ExchangeController{

    @Autowired
    private ExchangeService exchangeService;

    /**
     * 申请成为代理商候选人
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "申请成为代理候选人")
    @PostMapping("v2/exchange/apply/candidate")
    public ResultInfo<?> applyCandidate(@Valid @RequestBody UserIdRequest request)
            throws Exception {

        return exchangeService.applyCandidate(Long.valueOf(request.getUserid()));
    }


    /**
     * 业绩查询
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询业绩")
    @PostMapping("v2/exchange/get/achievement")
    public ResultInfo<?> findAchievement(@Valid @RequestBody UserIdRequest request)
            throws Exception {

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, exchangeService.findAchievement(Long.valueOf(request.getUserid())));
    }

    /**
     * 业绩查询AB
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询业绩AB")
    @PostMapping("v2/exchange/get/achievementAB")
    public ResultInfo<?> getAchievement(@Valid @RequestBody GetAchievementABRequest request)
            throws Exception {

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, exchangeService.getAchievement(request));
    }

    /**
     * 申请成为代理商
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "申请成为代理商")
    @PostMapping("v2/exchange/apply/agent")
    public ResultInfo<?> applyAgent(@Valid @RequestBody UserIdRequest request)
            throws Exception {

        return exchangeService.applyAgent(Long.valueOf(request.getUserid()));
    }

    /**
     * 代理商提现
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "代理商提现")
    @PostMapping("v2/exchange/put/forward")
    public ResultInfo<?> putForward(@Valid @RequestBody PutForwardRequest request)
            throws Exception {
        if (request.getEosAmount() == null) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, ResultInfo.MSG_INVALID_PARAM);
        }
        BigDecimal num = request.getEosAmount();
        if (num.compareTo(BigDecimal.valueOf(0.0001)) <= 0) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, "提现数量太低");
        }
        return exchangeService.putForward(request);
    }


    /**
     * 兑换记录
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "兑换记录")
    @PostMapping("v2/exchange/forward/record")
    public ResultInfo<?> forwardRecord(@Valid @RequestBody Page_Request request)
            throws Exception {

        return exchangeService.forwardRecord(request);
    }
}
