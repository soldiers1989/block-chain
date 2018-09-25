package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.football.*;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.service.FootballMatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;


/**
 * 足球竞猜
 */
@RestController
@Api(value = "FootballMatchController", description="足球竞猜")
@RequestMapping("/api/app/")
public class FootballMatchController{


    @Autowired
    private FootballMatchService footballMatchService;

    /**
     * 获取脉链比赛列表
     *
     * @param userIdRequest
     * @return
     * @throws ParseException
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取脉链比赛列表")
    @PostMapping("v2/foot/mai/getMatchList")
    public ResultInfo<?> matchList(@Valid @RequestBody UserIdRequest userIdRequest) throws ParseException {
        return footballMatchService.getMaiMatchList(userIdRequest);
    }

    /**
     * 下订单
     *
     * @param addOrdersRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "下订单")
    @PostMapping("v2/foot/mai/addOrders")
    public ResultInfo<?> addOrders(@Valid @RequestBody AddOrdersRequest addOrdersRequest) throws Exception {
        return footballMatchService.addOrders(addOrdersRequest);
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
    @ApiOperation(value = "修改订单")
    @PostMapping("v2/foot/mai/updateOrders")
    public ResultInfo<?> updateOrders(@Valid @RequestBody UpdateOrdersRequest updateOrdersRequest) throws Exception {
        return footballMatchService.updateOrders(updateOrdersRequest);
    }

    /**
     * 撤销订单
     *
     * @param retreatOrdersRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "撤销订单")
    @PostMapping("v2/foot/mai/retreatOrders")
    public ResultInfo<?> retreatOrders(@Valid @RequestBody RetreatOrdersRequest retreatOrdersRequest) throws Exception {
        return footballMatchService.retreatOrders(retreatOrdersRequest);
    }

    /**
     * 获取订单
     *
     * @param getMatchTypeDTO
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取订单")
    @PostMapping("v2/foot/mai/getOrders")
    public ResultInfo<?> getOrders(@Valid @RequestBody GetMatchTypeRequest getMatchTypeDTO) throws Exception {
        return footballMatchService.getOrders(getMatchTypeDTO);
    }

    /**
     * 获取球队信息
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取球队信息")
    @PostMapping("v2/foot/mai/getTeamMsg")
    public ResultInfo<?> getTeamMsg(@Valid @RequestBody GetTeamMsgRequest request) throws Exception {
        return footballMatchService.getTeamMsg(request);
    }

    /**
     * 一键发奖励
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "一键发奖励")
    @GetMapping("v2/foot/mai/getMoney")
    public ResultInfo<?> getMoney() throws Exception {
        return footballMatchService.getMoney();
    }
}
