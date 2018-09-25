package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.orders.OrdersRequest;
import com.xdaocloud.futurechain.dto.req.product.ProductRequest;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {


    /**
     * 创建订单
     *
     * @param productRequest
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> placeOrder(ProductRequest productRequest) throws Exception;

    /**
     * app支付（深脑科技）
     *
     * @param productRequest
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> appPlaceOrder(ProductRequest productRequest) throws Exception;


    /**
     * app支付(麦链科技)
     *
     * @param productRequest
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> appPlaceOrder_2(ProductRequest productRequest) throws Exception;


    /**
     * 查询历史订单
     *
     * @param findOrderRequest
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> findByUserId(Page_Request findOrderRequest) throws Exception;

    /**
     * 微信H5支付异步回调（深脑科技）
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    String receiveWxpayNotify(HttpServletRequest request) throws Exception;

    /**
     * 微信支付异步回调(麦链科技)
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    String receiveWxpayNotify_2(HttpServletRequest request) throws Exception;

    /**
     * 支付宝App支付异步回调
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    String receiveAlipayNotify(HttpServletRequest request) throws Exception;

    /**
     * 支付宝App支付异步回调
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    String receiveAlipayNotify_2(HttpServletRequest request) throws Exception;


    /**
     * 查询微信订单
     *
     * @param orderNo 内部订单号
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> queryOrders(String orderNo) throws Exception;

    /**
     * 继续支付
     *
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> continuePay(OrdersRequest request) throws Exception;


    /**
     * 微信App支付同步通知
     *
     * @param str
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> wxAppPlaceSynchronization(String str);

    /**
     * 支付宝App支付同步通知
     *
     * @param resultStatus
     * @param result
     * @param memo
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> aliAppPlaceSynchronization(String resultStatus, String result, String memo) throws Exception;

    /**
     * 获取订单
     *
     * @param pageBaseRequest
     * @return
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    PageResponse getOrders(PageBase_Request pageBaseRequest);


    /**
     * 获取订单
     *
     * @param page_request
     * @return
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    PageResponse getWebOrdersList(PageBase_Request page_request) throws Exception;
}
