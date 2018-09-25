package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.orders.OrderNoRequest;
import com.xdaocloud.futurechain.dto.req.orders.OrdersRequest;
import com.xdaocloud.futurechain.dto.req.product.ProductRequest;
import com.xdaocloud.futurechain.service.OrderService;
import com.xdaocloud.futurechain.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;

import static cn.hutool.core.lang.Validator.isChinese;


/**
 * 购物
 *
 * @author LuoFuMin
 */

@RestController
@Api(value = "ShoppingController", description="购物")
@RequestMapping("/api/app/")
public class ShoppingController{

    private static Logger LOG = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;


    /**
     * 获取麦粒奖励数目
     *
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取麦粒奖励数目", notes = "获取麦粒奖励数目")
    @GetMapping("v2/shopping/get/mai/reward")
    public ResultInfo<?> getReward() throws Exception {
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, productService.getReward());
    }


    /**
     * 获取商品
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取商品", notes = "获取商品")
    @GetMapping("v2/shopping/get/products")
    public ResultInfo<?> findProducts() throws Exception {
        return productService.findProducts();
    }

    /**
     * 微信h5购买商品
     *
     * @param productRequest
     * @param bindingResult
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "H5购买商品", notes = "amount: 购买数量, productCode: 商品编码, userid: 用户id,spbillCreateIp:ip地址，payment：payWeiXin or alipay")
    @PostMapping("v2/shopping/buy/product")
    public ResultInfo<?> buyProduct(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest httpServletRequest) throws Exception {
        int hStr = productRequest.getMoney().length();
        int dian = productRequest.getMoney().indexOf(".");
        if (dian != -1) {
            if (hStr - dian > 3) {
                return new ResultInfo<>(ResultInfo.FAILURE, "小数点超长");
            }
        }
        LOG.info("====终端IP地址--getRemortIP====" + getRemortIP(httpServletRequest));
        productRequest.setSpbillCreateIp(getRemortIP(httpServletRequest));
        return orderService.placeOrder(productRequest);
    }

    /**
     * app支付下单（深脑科技）
     *
     * @param productRequest
     * @param bindingResult
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "APP购买商品", notes = "amount: 购买数量, productCode: 商品编码, userid: 用户id,spbillCreateIp:ip地址，payment：payWeiXin or alipay")
    @PostMapping("v2/shopping/appPlace/order")
    public ResultInfo<?> placeAnOrder(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest httpServletRequest) throws Exception {
        LOG.info("====终端IP地址--getRemortIP====" + getRemortIP(httpServletRequest));
        productRequest.setSpbillCreateIp(getRemortIP(httpServletRequest));
        if (isChinese(productRequest.getMoney())) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户不能包含中文字符");
        try {
            BigDecimal menoy = new BigDecimal(productRequest.getMoney());
            if (menoy.compareTo(BigDecimal.valueOf(0)) < 1) {
                return new ResultInfo<>(ResultInfo.FAILURE, "支付金额错误");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, "支付金额错误");
        }
        return orderService.appPlaceOrder(productRequest);
    }


    /**
     * app支付下单(麦链科技)
     *
     * @param productRequest
     * @param bindingResult
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "APP购买商品", notes = "amount: 购买数量, productCode: 商品编码, userid: 用户id,spbillCreateIp:ip地址，payment：payWeiXin or alipay")
    @PostMapping("v2/shopping/appPlace/order_2")
    public ResultInfo<?> placeAnOrder_2(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest httpServletRequest) throws Exception {
        LOG.info("====终端IP地址--getRemortIP====" + getRemortIP(httpServletRequest));
        productRequest.setSpbillCreateIp(getRemortIP(httpServletRequest));
        if (isChinese(productRequest.getMoney())) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户不能包含中文字符");
        try {
            BigDecimal menoy = new BigDecimal(productRequest.getMoney());
            if (menoy.compareTo(BigDecimal.valueOf(0)) < 1) {
                return new ResultInfo<>(ResultInfo.FAILURE, "支付金额错误");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, "支付金额错误");
        }
        return orderService.appPlaceOrder_2(productRequest);
    }


    /**
     * 支付宝App支付同步通知
     *
     * @param resultStatus
     * @param result
     * @param memo
     * @param bindingResult
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "支付宝同步通知", notes = "支付宝同步通知")
    @PostMapping("v2/shopping/app/place/ali/synchronization")
    public ResultInfo<?> aliAppPlaceSynchronization(String resultStatus, String result, String memo) throws Exception {

        return orderService.aliAppPlaceSynchronization(resultStatus, result, memo);
    }


    /**
     * 微信App支付同步通知
     *
     * @param str
     * @param bindingResult
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "支付宝同步通知", notes = "支付宝同步通知")
    @PostMapping("v2/shopping/app/place/weixin/synchronization")
    public ResultInfo<?> wxAppPlaceSynchronization(String str) throws Exception {

        return orderService.wxAppPlaceSynchronization(str);
    }

    /**
     * 微信异步回掉（深脑科技）
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "微信异步回调接口(深脑科技)")
    @PostMapping("v2/shopping/wxpayNotify")
    public String receiveWxpayNotify(HttpServletRequest request) throws Exception {
        return orderService.receiveWxpayNotify(request);
    }
    /**
     * 微信异步回掉(麦链科技)
     *
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "微信异步回调接口(麦链科技)")
    @PostMapping("v2/shopping/wxpayNotify_2")
    public String receiveWxpayNotify_2(HttpServletRequest request) throws Exception {
        return orderService.receiveWxpayNotify_2(request);
    }

    /**
     * 支付宝异步回掉（深脑科技）
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "支付宝异步回调接口(深脑科技)")
    @PostMapping("v2/shopping/alipayNotify")
    public String receiveAlipayNotify(HttpServletRequest request) throws Exception {
        return orderService.receiveAlipayNotify(request);
    }

    /**
     * 支付宝异步回掉(麦链科技)
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "支付宝异步回调接口(麦链科技)")
    @PostMapping("v2/shopping/alipayNotify_2")
    public String receiveAlipayNotify_2(HttpServletRequest request) throws Exception {
        return orderService.receiveAlipayNotify_2(request);
    }

    /**
     * 查询微信订单
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询微信订单", notes = "orderNo:内部订单号")
    @PostMapping("v2/shopping/query/orders")
    public ResultInfo<?> queryOrders(@Valid @RequestBody OrderNoRequest request) throws Exception {
        return orderService.queryOrders(request.getOrderNo());
    }

    /**
     * 继续支付
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "继续支付", notes = "orderNo:内部订单号,payment:payWeiXin or alipay")
    @PostMapping("v2/shopping/continue/pay")
    public ResultInfo<?> continuePay(@Valid @RequestBody OrdersRequest request) throws Exception {
        return orderService.continuePay(request);
    }

    /**
     * 历史查询订单
     *
     * @param pageRequest
     * @param bindingResult
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "历史查询订单", notes = "page:起始页, size: 一页的数量, userid:用户id")
    @PostMapping("v2/shopping/get/orders")
    public ResultInfo<?> findByUserId(@Valid @RequestBody Page_Request pageRequest) throws Exception {

        if (pageRequest.getPage() < 1 || pageRequest.getSize() < 1) {
            return new ResultInfo(ResultInfo.INVALID_PARAM, ResultInfo.MSG_INVALID_PARAM);
        }
        return orderService.findByUserId(pageRequest);
    }


    /************************************************************************************************************************************************/


    /**
     * 获取商品
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取商品", notes = "获取商品")
    @GetMapping("v1/shopping/get/products")
    public ResultInfo<?> findProductsV1() throws Exception {
        return productService.findProducts();
    }

    /**
     * 购买商品
     *
     * @param productRequest
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "购买商品", notes = "amount: 购买数量, productCode: 商品编码, userid: 用户id,spbillCreateIp:ip地址，payment：payWeiXin or alipay")
    @PostMapping("v1/shopping/buy/product")
    public ResultInfo<?> buyProductV1(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest httpServletRequest) throws Exception {

        int hStr = productRequest.getMoney().length();
        int dian = productRequest.getMoney().indexOf(".");
        if (dian != -1) {
            if (hStr - dian > 3) {
                return new ResultInfo<>(ResultInfo.FAILURE, "小数点超长");
            }
        }

        LOG.info("====getRemortIP====" + getRemortIP(httpServletRequest));

        //LOG.info("====getClientIp====" + getClientIp(httpServletRequest));

        productRequest.setSpbillCreateIp(getRemortIP(httpServletRequest));

        return orderService.placeOrder(productRequest);
    }

    /**
     * TODO //获得客户端的ip地址
     *
     * @param request
     * @return
     * @author yqwang
     * @date 2017年2月22日 下午3:55:40
     */
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("X-Forwarded-For") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("X-Forwarded-For");
    }


    /**
     * 历史查询订单
     *
     * @param pageRequest
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "历史查询订单", notes = "page:起始页, size: 一页的数量, userid:用户id")
    @PostMapping("v1/shopping/get/orders")
    public ResultInfo<?> findByUserIdV1(@Valid @RequestBody Page_Request pageRequest) throws Exception {

        if (pageRequest.getPage() < 1 || pageRequest.getSize() < 1) {
            return new ResultInfo(ResultInfo.INVALID_PARAM, ResultInfo.MSG_INVALID_PARAM);
        }
        return orderService.findByUserId(pageRequest);
    }

    /**
     * 查询微信订单
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询微信订单", notes = "orderNo:内部订单号")
    @PostMapping("v1/shopping/query/orders")
    public ResultInfo<?> queryOrdersV1(@Valid @RequestBody OrderNoRequest request) throws Exception {
        return orderService.queryOrders(request.getOrderNo());
    }

    /**
     * 继续支付
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "继续支付", notes = "orderNo:内部订单号,payment:payWeiXin or alipay")
    @PostMapping("v1/shopping/continue/pay")
    public ResultInfo<?> continuePayV1(@Valid @RequestBody OrdersRequest request) throws Exception {
        return orderService.continuePay(request);
    }

    /**
     * 微信异步回掉
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "微信异步回调接口")
    @PostMapping("v1/shopping/wxpayNotify")
    public String receiveWxpayNotifyV(HttpServletRequest request) throws Exception {
        return orderService.receiveWxpayNotify(request);
    }

}
