package com.xdaocloud.futurechain.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.constant.*;
import com.xdaocloud.futurechain.dto.orders.BuyRecordDTO;
import com.xdaocloud.futurechain.dto.req.ali.Result;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.orders.OrdersRequest;
import com.xdaocloud.futurechain.dto.req.product.ProductRequest;
import com.xdaocloud.futurechain.eos.EosAccount;
import com.xdaocloud.futurechain.mapper.*;
import com.xdaocloud.futurechain.model.EosWallet;
import com.xdaocloud.futurechain.model.Orders;
import com.xdaocloud.futurechain.model.OreTransaction;
import com.xdaocloud.futurechain.model.Product;
import com.xdaocloud.futurechain.service.EosService;
import com.xdaocloud.futurechain.service.OrderService;
import com.xdaocloud.futurechain.util.ProductOrderUtils;
import com.xdaocloud.futurechain.util.WXPayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import static com.xdaocloud.futurechain.constant.AlipayConstant.ALIPAY_PUBLIC_KEY;
import static com.xdaocloud.futurechain.constant.AlipayConstant.SIGN_TYPE;
import static com.xdaocloud.futurechain.constant.RewardTypeConstant.ALI_APPRECIATE;
import static com.xdaocloud.futurechain.constant.RewardTypeConstant.WEIXIN_APPRECIATE;
import static com.xdaocloud.futurechain.constant.WXPayConstant.*;
import static com.xdaocloud.futurechain.util.WXPayUtils.OrderInfoByWeiXinPay;

@Service
public class OrderServiceImpl implements OrderService {

    private static Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private EosAccount eosAccount;


    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private OreTransactionMapper oreTransactionMapper;

    /**
     * 微信回调地址(深脑科技)
     */
    @Value("${wxpay.notify}")
    private String wxH5PayNotify;

    /**
     * 支付宝回调地址(深脑科技)
     */
    @Value("${alipay.notify}")
    private String alipayNotify;

    /**
     * 支付宝回调地址（麦链科技）
     */
    @Value("${alipay2.notify}")
    private String alipayNotify_2;


    /**
     * 微信回调地址（麦链科技）
     */
    @Value("${wxpay2.notify}")
    private String wxAppPayNotify_2;
    /**
     * ip地址
     */
    @Value("${wxpay.ip}")
    private String wxpayIp;

    /**
     * 锁eos基数
     */
    @Value("${lock.eos.base}")
    private String lockEosBase;

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private EosService eosService;

    @Autowired
    private EOSConstant eosConstant;


    /**
     * 创建订单
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> placeOrder(ProductRequest request) throws Exception {
        Product product = productMapper.findByProductCode(request.getProductCode());
        if (null == product) {
            return new ResultInfo<>(ResultInfo.NOT_FOUND, ResultInfo.MSG_DATA_NOT_FOUND);
        }
        String orderNumber = ProductOrderUtils.createOrderNumber();
        BigDecimal menoy = new BigDecimal(request.getMoney());
        //product.getPrice().multiply(new BigDecimal(request.getAmount())).multiply(new BigDecimal(100)).setScale(0);//支付金额 单位为分
        BigDecimal fee = menoy.multiply(new BigDecimal(100)).setScale(0);
        LOG.info("======fee===钱，已分为单位====" + fee);
        Orders ordersAlready = ordersMapper.findByOrderNo(orderNumber);
        if (ordersAlready != null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "订单以存在");
        }
        BigDecimal amount = menoy.divide(product.getPrice());
        LOG.info("======amount===币的数量====" + amount.toString());
        Orders orders = new Orders(Long.valueOf(request.getUserid()), orderNumber, request.getPayment(), product.getPrice().multiply(BigDecimal.valueOf(100)), amount, fee, request.getProductCode(), (byte) 0);
        ordersMapper.insertSelective(orders);
        Map<String, String> params = null;
        if ("payWeiXin".equals(request.getPayment())) {//微信支付
            //调用微信支付
            if (StringUtils.isNoneEmpty(request.getSpbillCreateIp())) {//获取终端IP
                LOG.info("==ip==" + request.getSpbillCreateIp());
                //h5支付
                params = OrderInfoByWeiXinPay(orderNumber, fee, request.getSpbillCreateIp(), H5_TRADE_TYPE, H5_MCH_ID, H5_APP_ID, H5_KEY, wxH5PayNotify);
            } else {//使用配置文件设定的IP
                LOG.info("==wxpayIp==" + wxpayIp);
                params = OrderInfoByWeiXinPay(orderNumber, fee, wxpayIp, H5_TRADE_TYPE, H5_MCH_ID, H5_APP_ID, H5_KEY, wxH5PayNotify);
            }
            /*for (Map.Entry<String, String> entry : params.entrySet()) {
                LOG.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }*/
            LOG.info("==跳转地址==" + request.getRedirect_url());
            //redirect_url进行urlencode处理
            String redirect_url = URLEncoder.encode(request.getRedirect_url() + "?out_trade_no=" + orderNumber, "utf-8");
            String mweb_url = params.get("mweb_url") + "&redirect_url=" + redirect_url;
            params.put("mweb_url", mweb_url);
            params.put("out_trade_no", orderNumber);

            String return_code = params.get("return_code");
            String result_code = params.get("result_code");
            if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
                Map<String, String> params_success = new HashMap<>();
                params_success.put("mweb_url", mweb_url);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, params_success);
            } else {
                return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
            }
        }
        if ("alipay".equals(request.getPayment())) {//支付宝支付
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }

    /**
     * app 支付(深脑科技)
     *
     * @param productRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> appPlaceOrder(ProductRequest productRequest) throws Exception {
        Product product = productMapper.findByProductCode(productRequest.getProductCode());
        if (null == product) {
            return new ResultInfo<>(ResultInfo.NOT_FOUND, ResultInfo.MSG_DATA_NOT_FOUND);
        }
        String orderNumber = ProductOrderUtils.createOrderNumber();
        BigDecimal menoy = new BigDecimal(productRequest.getMoney());
        BigDecimal fee = menoy.multiply(new BigDecimal(100)).setScale(0);
        LOG.info("》》》fee==钱，已分为单位==" + fee);
        Orders ordersAlready = ordersMapper.findByOrderNo(orderNumber);
        if (ordersAlready != null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "订单以存在");
        }
        BigDecimal amount = menoy.divide(product.getPrice(), 4, BigDecimal.ROUND_DOWN);
        LOG.info("》》》amount==币的数量==" + amount.toString());
        Orders orders = new Orders(Long.valueOf(productRequest.getUserid()), orderNumber, productRequest.getPayment(), product.getPrice().multiply(BigDecimal.valueOf(100)), amount, fee, productRequest.getProductCode(), (byte) 0);
        ordersMapper.insertSelective(orders);
        Map<String, String> params = new HashMap<>();
        if ("alipay".equals(productRequest.getPayment())) {//支付宝支付
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody("赞赏麦钻");  // 商品描述，可空
            model.setSubject("赞赏麦钻");//订单名称，必填
            model.setOutTradeNo(orderNumber);//商户订单号，商户网站订单系统中唯一订单号，必填
            model.setTimeoutExpress("30m");  // 超时时间 可空
            model.setTotalAmount(menoy.toString());//订单总金额，单位为元 必填
            model.setProductCode("QUICK_MSECURITY_PAY");  // 销售产品码 必填
            request.setBizModel(model);
            request.setNotifyUrl(alipayNotify);
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
                String orderStr = response.getBody();
                params.put("aliSign", orderStr);
                LOG.info("》》》支付宝签名 sign==" + orderStr);//就是orderString 可以直接给客户端请求，无需再做处理。
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, params);
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
        if ("payWeiXin".equals(productRequest.getPayment())) {//微信支付
            params = OrderInfoByWeiXinPay(orderNumber, fee, productRequest.getSpbillCreateIp(), "APP", APP_MCH_ID, APP_ID, APP_KEY, wxH5PayNotify);
            String return_code = params.get("return_code");
            if ("SUCCESS".equals(return_code)) {
                //二次签名
                SortedMap<String, String> params2 = new TreeMap<String, String>();
                params2.put("appid", params.get("appid"));//应用ID
                params2.put("partnerid", params.get("mch_id"));//partnerid商户号
                params2.put("prepayid", params.get("prepay_id"));//预支付交易会话ID
                params2.put("package", "Sign=WXPay");//package扩展字段
                params2.put("noncestr", params.get("nonce_str"));//随机字符串
                params2.put("timestamp", String.valueOf(getSecondTimestamp(new Date())));//随机字符串
                String sign = WXPayUtils.createSignStr("UTF-8", params2, APP_KEY);//生成签名
                params2.put("sign", sign);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, params2);
            }
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, params);
    }



    /**
     * app 支付（麦链科技）
     *
     * @param productRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> appPlaceOrder_2(ProductRequest productRequest) throws Exception {
        Product product = productMapper.findByProductCode(productRequest.getProductCode());
        if (null == product) {
            return new ResultInfo<>(ResultInfo.NOT_FOUND, ResultInfo.MSG_DATA_NOT_FOUND);
        }
        String orderNumber = ProductOrderUtils.createOrderNumber();
        BigDecimal menoy = new BigDecimal(productRequest.getMoney());
        BigDecimal fee = menoy.multiply(new BigDecimal(100)).setScale(0);
        LOG.info("》》》fee==钱，已分为单位==" + fee);
        Orders ordersAlready = ordersMapper.findByOrderNo(orderNumber);
        if (ordersAlready != null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "订单以存在");
        }
        BigDecimal amount = menoy.divide(product.getPrice(), 4, BigDecimal.ROUND_DOWN);
        LOG.info("》》》amount==币的数量==" + amount.toString());
        Orders orders = new Orders(Long.valueOf(productRequest.getUserid()), orderNumber, productRequest.getPayment(), product.getPrice().multiply(BigDecimal.valueOf(100)), amount, fee, productRequest.getProductCode(), (byte) 0);
        ordersMapper.insertSelective(orders);
        Map<String, String> params = new HashMap<>();
        if ("alipay".equals(productRequest.getPayment())) {//支付宝支付
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody("赞赏麦钻");  // 商品描述，可空
            model.setSubject("赞赏麦钻");//订单名称，必填
            model.setOutTradeNo(orderNumber);//商户订单号，商户网站订单系统中唯一订单号，必填
            model.setTimeoutExpress("30m");  // 超时时间 可空
            model.setTotalAmount(menoy.toString());//订单总金额，单位为元 必填
            model.setProductCode("QUICK_MSECURITY_PAY");  // 销售产品码 必填
            request.setBizModel(model);
            request.setNotifyUrl(alipayNotify_2);
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = AliPay_2_Constant.getInstance().sdkExecute(request);
                String orderStr = response.getBody();
                params.put("aliSign", orderStr);
                LOG.info("》》》支付宝签名 sign==" + orderStr);//就是orderString 可以直接给客户端请求，无需再做处理。
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, params);
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
        if ("payWeiXin".equals(productRequest.getPayment())) {//微信支付
            params = OrderInfoByWeiXinPay(orderNumber, fee, productRequest.getSpbillCreateIp(), "APP", WXPay_2_Constant.APP_MCH_ID, WXPay_2_Constant.APP_ID, WXPay_2_Constant.APP_KEY, wxAppPayNotify_2);
            String return_code = params.get("return_code");
            if ("SUCCESS".equals(return_code)) {
                //二次签名
                SortedMap<String, String> params2 = new TreeMap<String, String>();
                params2.put("appid", params.get("appid"));//应用ID
                params2.put("partnerid", params.get("mch_id"));//partnerid商户号
                params2.put("prepayid", params.get("prepay_id"));//预支付交易会话ID
                params2.put("package", "Sign=WXPay");//package扩展字段
                params2.put("noncestr", params.get("nonce_str"));//随机字符串
                params2.put("timestamp", String.valueOf(getSecondTimestamp(new Date())));//随机字符串
                String sign = WXPayUtils.createSignStr("UTF-8", params2, WXPay_2_Constant.APP_KEY);//生成签名
                params2.put("sign", sign);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, params2);
            }
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, params);
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }


    /**
     * 微信App支付同步通知
     *
     * @param str
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> wxAppPlaceSynchronization(String str) {
        return null;
    }

    /**
     * 支付宝同步通知
     *
     * @param resultStatus
     * @param result
     * @param memo
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> aliAppPlaceSynchronization(String resultStatus, String result, String memo) throws Exception {
        if (StringUtils.isNoneBlank(resultStatus)) {
            JSONObject resultStatusJSONObject = JSONObject.parseObject(resultStatus);
            Result result1 = null;
            if (StringUtils.isNoneBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    LOG.info(" key::" + entry.getKey() + "， vaule::" + entry.getValue());
                }
                result1 = JSON.parseObject(result, new TypeReference<Result>() {
                });
            }
            if (result1 != null) {
                String orderNumber = result1.getAlipay_trade_app_pay_response().getOut_trade_no();
                String trade_no = result1.getAlipay_trade_app_pay_response().getTrade_no();
                LOG.info("=====getOut_trade_no========" + orderNumber);
                LOG.info("=====trade_no========" + trade_no);
                Orders orders = ordersMapper.findByOrderNo(orderNumber);
                if (orders != null) {
                    orders.setOrderNo(trade_no);
                    if ("9000".equals(resultStatusJSONObject.get("resultStatus"))) {
                        LOG.info("=====resultStatus========" + "9000'");
                    }
                    if ("8000".equals(resultStatusJSONObject.get("resultStatus"))) {
                        LOG.info("=====resultStatus========" + "8000'");
                        orders.setState((byte) 0);
                    }
                    orders.setTradeDesc(resultStatus);
                    ordersMapper.updateByPrimaryKeySelective(orders);
                }
            }
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 获取订单
     *
     * @throws Exception 异常
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse getOrders(PageBase_Request pageBaseRequest) {

        Integer page = 0;
        if (pageBaseRequest.getPage() != 0) {
            page = pageBaseRequest.getSize() * pageBaseRequest.getPage() + 1;
        }
        int count = ordersMapper.findCount();


        List list = ordersMapper.findSumFeeAndEos(page, pageBaseRequest.getSize());
        PageResponse pageResponse = new PageResponse();
        pageResponse.setItems(list);
        pageResponse.setPageNum(page);
        pageResponse.setPageSize(pageBaseRequest.getSize());
        pageResponse.setPageCount(list.size());
        pageResponse.setTotalCount(count);
        pageResponse.setTotalPages(count / pageBaseRequest.getSize());
        return pageResponse;
    }


    /**
     * 获取订单
     *
     * @throws Exception 异常
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse getWebOrdersList(PageBase_Request page_request) throws Exception {
        PageHelper.startPage(page_request.getPage(), page_request.getSize(), "id DESC");
        List<BuyRecordDTO> list = new ArrayList<>();
        if (StringUtils.isEmpty(page_request.getCondition())) {
            list = ordersMapper.findListByState(1);
        } else {
            list = ordersMapper.findListByStateAndPhone(1, page_request.getCondition());
        }
        PageInfo<BuyRecordDTO> pageInfo = new PageInfo<BuyRecordDTO>(list);
        PageResponse response = new PageResponse(pageInfo);
        return response;
    }

    /**
     * 微信异步回调（深脑科技）
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public String receiveWxpayNotify(HttpServletRequest request) throws Exception {
        LOG.info("微信异步通知来啦！");
        String result = "";
        Map<String, String> params = WXPayUtils.getParamsMapFromXml(request.getInputStream());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            LOG.info("》》》Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        // 判断通知状态 return_code:SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        if ("SUCCESS".equals(params.get("return_code"))) {// SUCCESS/FAIL

    /*        String nonce_str = params.get("nonce_str");//随机字符串
            String bank_type = params.get("bank_type");//付款银行
            String openid = params.get("openid");//用户标识
            String sign = params.get("sign");// 获取签名
            String fee_type = params.get("fee_type");//货币种类
            String mch_id = params.get("mch_id");//商户号
            String appid = params.get("appid");//应用ID
            String total_fee = params.get("total_fee");// 获取订单金额
            String trade_type = params.get("trade_type");//交易类型
            String result_code = params.get("result_code");// 业务结果
            String time_end = params.get("time_end");//支付完成时间
            String is_subscribe = params.get("is_subscribe");//是否关注公众账号*/

            String transaction_id = params.get("transaction_id");//微信支付订单号
            String cash_fee = params.get("cash_fee");//现金支付金额
            String out_trade_no = params.get("out_trade_no");// 获取商户订单号
            String return_code = params.get("return_code");//交易是否成功
            params.put("key", WXPayConstant.APP_KEY);

            if (WXPayUtils.checkSign(params)) {
                result = WXPayUtils.setResponseXml("SUCCESS", "OK");
                Orders orders = ordersMapper.findByOrderNo(out_trade_no);
                if ("SUCCESS".equals(return_code)) {
                    LOG.info("=============微信异步回调 交易成功==============");
                    if (orders != null) {
                        if (orders.getFee().setScale(0).toString().equals(cash_fee)) {
                            //添加eos和麦粒
                            recordMoney(orders, WEIXIN_APPRECIATE);
                            orders.setTradeNo(transaction_id);
                            orders.setState((byte) 1);
                            //orders.setTradeDesc(OrdersConstant.PAYMENT_SUCCESS);
                            orders.setTradeDesc(OrdersConstant.LOCK_POSITION);
                            ordersMapper.updateByPrimaryKeySelective(orders);
                            LOG.info("=============微信异步回调 订单支付支付完成==============");
                        } else {
                            LOG.error("===订单和实际支付金额不匹配===，订单金额==" + orders.getFee() + "，实际支付金额==" + cash_fee);
                            orders.setTradeNo(transaction_id);
                            orders.setState((byte) 6);
                            orders.setTradeDesc("订单和实际支付金额不匹配，订单金额==" + orders.getFee() + "，实际支付金额==" + cash_fee);
                            ordersMapper.updateByPrimaryKeySelective(orders);
                        }
                    } else {
                        LOG.error("=============无法查询到此订单==============");
                    }
                } else {
                    LOG.error("=============微信异步回调 交易失败==============");
                    if (orders != null) {
                        orders.setTradeNo(transaction_id);
                        orders.setState((byte) 2);
                        ordersMapper.updateByPrimaryKeySelective(orders);
                    }
                }
            } else {
                result = WXPayUtils.setResponseXml("FAIL", "签名不一致！");
                LOG.error("=============微信异步回调验证签名失败==============");
            }
        }
        return result;
    }


    /**
     * 微信异步回调（麦链科技）
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public String receiveWxpayNotify_2(HttpServletRequest request) throws Exception {
        LOG.info("微信异步通知来啦！");
        String result = "";
        Map<String, String> params = WXPayUtils.getParamsMapFromXml(request.getInputStream());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            LOG.info("》》》Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        // 判断通知状态 return_code:SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        if ("SUCCESS".equals(params.get("return_code"))) {// SUCCESS/FAIL

            String transaction_id = params.get("transaction_id");//微信支付订单号
            String cash_fee = params.get("cash_fee");//现金支付金额
            String out_trade_no = params.get("out_trade_no");// 获取商户订单号
            String return_code = params.get("return_code");//交易是否成功
            params.put("key", WXPay_2_Constant.APP_KEY);

            if (WXPayUtils.checkSign(params)) {
                result = WXPayUtils.setResponseXml("SUCCESS", "OK");
                Orders orders = ordersMapper.findByOrderNo(out_trade_no);
                if ("SUCCESS".equals(return_code)) {
                    LOG.info("=============微信异步回调 交易成功==============");
                    if (orders != null) {
                        if (orders.getFee().setScale(0).toString().equals(cash_fee)) {
                            //添加eos和麦粒
                            recordMoney(orders, WEIXIN_APPRECIATE);
                            orders.setTradeNo(transaction_id);
                            orders.setState((byte) 1);
                            //orders.setTradeDesc(OrdersConstant.PAYMENT_SUCCESS);
                            orders.setTradeDesc(OrdersConstant.LOCK_POSITION);
                            ordersMapper.updateByPrimaryKeySelective(orders);
                            LOG.info("=============微信异步回调 订单支付支付完成==============");
                        } else {
                            LOG.error("===订单和实际支付金额不匹配===，订单金额==" + orders.getFee() + "，实际支付金额==" + cash_fee);
                            orders.setTradeNo(transaction_id);
                            orders.setState((byte) 6);
                            orders.setTradeDesc("订单和实际支付金额不匹配，订单金额==" + orders.getFee() + "，实际支付金额==" + cash_fee);
                            ordersMapper.updateByPrimaryKeySelective(orders);
                        }
                    } else {
                        LOG.error("=============无法查询到此订单==============");
                    }
                } else {
                    LOG.error("=============微信异步回调 交易失败==============");
                    if (orders != null) {
                        orders.setTradeNo(transaction_id);
                        orders.setState((byte) 2);
                        ordersMapper.updateByPrimaryKeySelective(orders);
                    }
                }
            } else {
                result = WXPayUtils.setResponseXml("FAIL", "签名不一致！");
                LOG.error("=============微信异步回调验证签名失败==============");
            }
        }
        return result;
    }

    /**
     * 支付宝异步回调（深脑科技）
     * <p>
     * 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
//有用户赞赏清除查询业绩缓存数据
    public String receiveAlipayNotify(HttpServletRequest request) throws Exception {
        LOG.info("支付宝异步通知来了！");
        //将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            LOG.info("》》》  key：：" + name + "，value：：" + valueStr);
            params.put(name, valueStr);
        }
        boolean signVerified = false;//调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8", SIGN_TYPE);
            LOG.info("=======支付宝：：验签=====signVerified====" + signVerified);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        /**
         * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；
         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；
         * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）；
         * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明同步校验结果是无效的，只有全部验证通过后，才可以认定买家付款成功。
         *
         *  TRADE_FINISHED 	交易完成 	true（触发通知）
         *  TRADE_SUCCESS 	支付成功 	true（触发通知）
         *  WAIT_BUYER_PAY 	交易创建 	false（不触发通知）
         *  TRADE_CLOSED 	交易关闭 	true（触发通知）
         */
        String trade_status = params.get("trade_status");//交易状态
        String out_trade_no = params.get("out_trade_no");//商户订单号
        String trade_no = params.get("trade_no");//支付宝交易号
        String total_amount = params.get("total_amount");//订单金额
        String receipt_amount = params.get("receipt_amount");//实收金额
        Orders orders = ordersMapper.findByOrderNo(out_trade_no);
        if (signVerified) { //验签成功后
            if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                //转换成分,去除小数点
                receipt_amount = new BigDecimal(receipt_amount).multiply(BigDecimal.valueOf(100)).setScale(0).toString();
                if (orders.getFee().setScale(0).toString().equals(receipt_amount)) {
                    //添加eos和麦粒
                    recordMoney(orders, ALI_APPRECIATE);
                    orders.setTradeNo(trade_no);
                    //orders.setTradeDesc(OrdersConstant.PAYMENT_SUCCESS);
                    orders.setTradeDesc(OrdersConstant.LOCK_POSITION);
                    orders.setState((byte) 1);
                    ordersMapper.updateByPrimaryKeySelective(orders);
                } else {
                    LOG.error("===订单和实际支付金额不匹配===，订单金额==" + orders.getFee() + "，实际支付金额==" + receipt_amount);
                    orders.setTradeNo(trade_no);
                    orders.setState((byte) 6);
                    orders.setTradeDesc("订单和实际支付金额不匹配，订单金额==" + orders.getFee() + "，实际支付金额==" + receipt_amount);
                    ordersMapper.updateByPrimaryKeySelective(orders);
                }

            }
            return "success";
        } else {  //验签失败则记录异常日志，并在response中返回failure
            if (orders != null) {
                orders.setTradeNo(trade_no);
                orders.setTradeDesc("支付错误");
                orders.setState((byte) 5);
                ordersMapper.updateByPrimaryKeySelective(orders);
            }
            return "failure";
        }
    }


    /**
     * 支付宝异步回调(麦链科技)
     * <p>
     * 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public String receiveAlipayNotify_2(HttpServletRequest request) throws Exception {
        LOG.info("支付宝异步通知来了！");
        //将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            LOG.info("》》》  key：：" + name + "，value：：" + valueStr);
            params.put(name, valueStr);
        }
        boolean signVerified = false;//调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AliPay_2_Constant.ALIPAY_PUBLIC_KEY, "UTF-8", AliPay_2_Constant.SIGN_TYPE);
            LOG.info("=======支付宝：：验签=====signVerified====" + signVerified);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        /**
         * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；
         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；
         * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）；
         * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明同步校验结果是无效的，只有全部验证通过后，才可以认定买家付款成功。
         *
         *  TRADE_FINISHED 	交易完成 	true（触发通知）
         *  TRADE_SUCCESS 	支付成功 	true（触发通知）
         *  WAIT_BUYER_PAY 	交易创建 	false（不触发通知）
         *  TRADE_CLOSED 	交易关闭 	true（触发通知）
         */
        String trade_status = params.get("trade_status");//交易状态
        String out_trade_no = params.get("out_trade_no");//商户订单号
        String trade_no = params.get("trade_no");//支付宝交易号
        String total_amount = params.get("total_amount");//订单金额
        String receipt_amount = params.get("receipt_amount");//实收金额
        Orders orders = ordersMapper.findByOrderNo(out_trade_no);
        if (signVerified) { //验签成功后
            if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                //转换成分,去除小数点
                receipt_amount = new BigDecimal(receipt_amount).multiply(BigDecimal.valueOf(100)).setScale(0).toString();
                if (orders.getFee().setScale(0).toString().equals(receipt_amount)) {
                    //添加eos和麦粒
                    recordMoney(orders, ALI_APPRECIATE);
                    orders.setTradeNo(trade_no);
                    //orders.setTradeDesc(OrdersConstant.PAYMENT_SUCCESS);
                    orders.setTradeDesc(OrdersConstant.LOCK_POSITION);
                    orders.setState((byte) 1);
                    ordersMapper.updateByPrimaryKeySelective(orders);
                } else {
                    LOG.error("===订单和实际支付金额不匹配===，订单金额==" + orders.getFee() + "，实际支付金额==" + receipt_amount);
                    orders.setTradeNo(trade_no);
                    orders.setState((byte) 6);
                    orders.setTradeDesc("订单和实际支付金额不匹配，订单金额==" + orders.getFee() + "，实际支付金额==" + receipt_amount);
                    ordersMapper.updateByPrimaryKeySelective(orders);
                }
            }
            return "success";
        } else {  //验签失败则记录异常日志，并在response中返回failure
            if (orders != null) {
                orders.setTradeNo(trade_no);
                orders.setTradeDesc("支付错误");
                orders.setState((byte) 5);
                ordersMapper.updateByPrimaryKeySelective(orders);
            }
            return "failure";
        }
    }

    /**
     * 给用户添加eos和麦粒并保存记录成功
     *
     * @param orders
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    private void recordMoney(Orders orders, String desc) {
        EosWallet eosWallet = new EosWallet(orders.getUserId());
        EosWallet wallet = eosWalletMapper.findOneByParam(eosWallet);
        if (wallet != null) {
            //转币给买家（锁仓后不转币，只保存购买记录，以便统计锁仓数量）
            //eosAccount.fromOperateTransaction(eosAccount.EOS_OperateAccount, orders.getAmount() + "", desc);
            eosService.saveTransaction((long) 0, eosConstant.maioperate, wallet.getWalletName(), orders.getAmount().toString(), OrdersConstant.LOCK_POSITION, 7);
        }
        userMapper.addOreByUserid(orders.getUserId(), (long) 10);
        oreTransactionMapper.insertSelective(new OreTransaction(orders.getUserId(), (long) 10, desc));
        LOG.info("》》》给用户添加eos和麦粒并保存记录成功》》》");
    }


    /**
     * 继续支付
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> continuePay(OrdersRequest request) throws Exception {
        Orders orders = ordersMapper.findByOrderNo(request.getOrderNo());
        if (orders == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "不存在订单");
        }
        Map<String, String> params = null;
        if ("payWeiXin".equals(request.getPayment())) {//微信支付
            //调用微信支付
            params = OrderInfoByWeiXinPay(orders.getOrderNo(), orders.getFee(), request.getSpbillCreateIp(), H5_TRADE_TYPE, H5_MCH_ID, H5_APP_ID, H5_KEY, wxH5PayNotify);
            //redirect_url进行urlencode处理
            String redirect_url = URLEncoder.encode(request.getRedirect_url() + "?out_trade_no=" + orders.getOrderNo(), "utf-8");
            String mweb_url = params.get("mweb_url") + "&redirect_url=" + redirect_url;
            params.put("mweb_url", mweb_url);
            params.put("out_trade_no", orders.getOrderNo());
            String return_code = params.get("return_code");
            String result_code = params.get("result_code");
            if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, params);
            } else {
                return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, params);
            }
        }
        if ("alipay".equals(request.getRedirect_url())) {//支付宝支付
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "暂时不支持");
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }


    /**
     * 查询微信订单
     *
     * @param orderNo 内部订单号
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> queryOrders(String orderNo) throws Exception {
        Map<String, String> stringMap = new HashMap<>();
        Orders orders = ordersMapper.findByOrderNo(orderNo);
        if (orders == null) {
            return new ResultInfo<>(ResultInfo.NOT_FOUND, ResultInfo.MSG_DATA_NOT_FOUND);
        }
        if ("1".equals(orders.getState())) {
            stringMap.put("tradeState", "支付成功");
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, stringMap);
        }
        SortedMap<String, String> orderQueryRequestParams = new TreeMap<String, String>();
        orderQueryRequestParams.put("appid", WXPayConstant.H5_APP_ID);
        orderQueryRequestParams.put("mch_id", WXPayConstant.H5_MCH_ID);
        orderQueryRequestParams.put("transaction_id", orders.getTradeNo());
        orderQueryRequestParams.put("nonce_str", WXPayUtils.getNonceStr());
        String orderQuerySign = WXPayUtils.createSignStr("UTF-8", orderQueryRequestParams, H5_KEY);//生成签名
        orderQueryRequestParams.put("sign", orderQuerySign);
        String orderQueryRequestXml = WXPayUtils.getRequestXml(orderQueryRequestParams);
        //查询订单请求
        String orderQueryResponse = HttpUtil.post(WXPayConstant.ORDER_QUERY_URL, orderQueryRequestXml);
        InputStream inputStream = new ByteArrayInputStream(orderQueryResponse.getBytes());
        Map<String, String> orderQueryResponseMap = WXPayUtils.getParamsMapFromXml(inputStream);
        LOG.info("==========查询订单============");
        for (Map.Entry<String, String> entry : orderQueryResponseMap.entrySet()) {
            LOG.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String orderQuery_return_code = orderQueryResponseMap.get("return_code");
        String orderQuery_result_code = orderQueryResponseMap.get("result_code");
        String orderQuery_trade_state_desc = orderQueryResponseMap.get("trade_state_desc");
        if ("SUCCESS".equals(orderQuery_return_code) && "SUCCESS".equals(orderQuery_result_code) && "支付成功".equals(orderQuery_trade_state_desc)) {
            orders.setState((byte) 1);
            orders.setTradeDesc("支付成功");
            ordersMapper.updateByPrimaryKeySelective(orders);
            stringMap.put("tradeState", "支付成功");
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, stringMap);
        } else {
            orders.setState((byte) 5);
            orders.setTradeDesc("支付错误");
            ordersMapper.updateByPrimaryKeySelective(orders);
            stringMap.put("tradeState", "支付错误");
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, stringMap);
        }
    }

    /**
     * 查询历史订单
     *
     * @param findOrderRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findByUserId(Page_Request findOrderRequest) throws Exception {
        PageHelper.startPage(findOrderRequest.getPage(), findOrderRequest.getSize(), "id DESC");
        List<Orders> ordersList = ordersMapper.findByUserId(Long.valueOf(findOrderRequest.getUserid()));
        PageInfo<Orders> pageInfo = new PageInfo<Orders>(ordersList);
        PageResponse response = new PageResponse(pageInfo);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, response);
    }

}
