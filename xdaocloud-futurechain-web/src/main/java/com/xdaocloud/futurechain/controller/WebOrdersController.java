package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 订单管理
 *
 * @author LuoFuMin
 * @date 2018年6月28日
 */

@RestController
@RequestMapping("/api/web")
@Api(value = "OrdersController", description = "订单管理")
public class WebOrdersController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单
     *
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取订单")
    @PostMapping("v2/order/get/orders")
    @RequiresPermissions("maichain_order_getlist")
    public ResultInfo<?> getOrders(@Valid @RequestBody PageBase_Request pageBaseRequest)
            throws Exception {
        if (pageBaseRequest.getPage() >= 1) {
            pageBaseRequest.setPage(pageBaseRequest.getPage() - 1);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, orderService.getWebOrdersList(pageBaseRequest));
    }
}
