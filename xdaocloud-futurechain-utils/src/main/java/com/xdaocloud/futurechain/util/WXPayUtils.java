package com.xdaocloud.futurechain.util;


import cn.hutool.http.HttpUtil;
import com.xdaocloud.futurechain.constant.WXPayConstant;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

public class WXPayUtils {

    private static Logger LOG = LoggerFactory.getLogger(WXPayUtils.class);

    /**
     * @return String
     * @Title: getNonceStr
     * @Description: 获取随机字符串
     */
    public static String getNonceStr() throws Exception {
        return MD5Utils.getMD5(UUID.randomUUID().toString()).substring(0, 32).toUpperCase();
    }


    /**
     * 定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
     *
     * @param characterEncoding 编码格式（"UTF-8"）
     * @param parameters        键值对参数
     */
    public static String createSignStr(String characterEncoding, SortedMap<String, String> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        String sign = MD5Utils.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        LOG.info("》》》sign=====" + sign);
        return sign;
    }


    /**
     * 将封装好的参数转换成Xml格式类型的字符串
     *
     * @param parameters 参数
     */

    public static String getRequestXml(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("sign".equalsIgnoreCase(k)) {

            } else if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("<" + "sign" + ">" + "<![CDATA[" + parameters.get("sign") + "]]></" + "sign" + ">");
        sb.append("</xml>");
        return sb.toString();
    }


    /**
     * 通过xml 发给微信消息
     *
     * @param return_code
     * @param return_msg
     * @return
     */
    public static String setResponseXml(String return_code, String return_msg) {
        SortedMap<String, String> parameters = new TreeMap<String, String>();
        parameters.put("return_code", return_code);
        parameters.put("return_msg", return_msg);
        return "<xml><return_code><![CDATA[" + return_code + "]]>" +
                "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }


    /**
     * 签名认证
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public static <T> boolean checkSign(Map<String, T> paramsMap) throws Exception {
        String sign = getSignFromParamMap(paramsMap);
        return paramsMap.get("sign").equals(sign);
    }


    /**
     * 从map中获取签名sign
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public static <T> String getSignFromParamMap(Map<String, T> paramsMap) throws Exception {
        if (paramsMap != null && paramsMap.size() > 0) {
            Map<String, T> params = new TreeMap<String, T>(new WXComparator());
            params.putAll(paramsMap);
            StringBuffer tempStr = new StringBuffer();
            for (Map.Entry<String, T> param : params.entrySet()) {
                if (!"sign".equals(param.getKey()) && !"key".equals(param.getKey()) && !"".equals(param.getValue())
                        && param.getValue() != null) {
                    tempStr.append(param.getKey() + "=" + param.getValue() + "&");
                }
            }
            String temp = tempStr.toString().concat("key=" + params.get("key"));
            return MD5Utils.getMD5(temp).toUpperCase();
        }
        return null;
    }

    /**
     * 从xml字符串中解析参数
     *
     * @param xml
     * @return
     * @throws Exception
     */
    public static Map<String, String> getParamsMapFromXml(InputStream xml) throws Exception {
        Map<String, String> params = new HashMap<String, String>(0);
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(xml);
        Element node = read.getRootElement();
        listNodes(node, params);
        return params;
    }

    /**
     * 解析
     *
     * @param node
     * @param params
     */
    public static void listNodes(Element node, Map<String, String> params) {
        // 获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        // 遍历属性节点
        if ((list == null || list.size() == 0) && !(node.getTextTrim().equals(""))) {
            if (node.getTextTrim().contains("<![CDATA[")) {
                String[] split = node.getTextTrim().split("<![CDATA[");
                split[1].replaceAll("]]>", "");
                params.put(node.getName(), split[1]);
            } else {
                params.put(node.getName(), node.getTextTrim());
            }
        }
        // 当前节点下面子节点迭代器
        Iterator<Element> it = node.elementIterator();
        // 遍历
        while (it.hasNext()) {
            // 获取某个子节点对象
            Element e = it.next();
            // 对子节点进行遍历
            listNodes(e, params);
        }
    }

    /**
     * 微信支付
     *
     * @param orderNumber 订单编号
     * @param fee         实际支付金额
     * @return
     */
    public static Map<String, String> OrderInfoByWeiXinPay(String orderNumber, BigDecimal fee, String spbillCreateIp, String trade_type,
                                                           String mch_id, String appid, String key, String wxNotify) throws Exception {
        LOG.info("：：微信回调地址：：" + wxNotify);
        SortedMap<String, String> unifiedOrderRequestParams = new TreeMap<String, String>();
        unifiedOrderRequestParams.put("appid", appid);//app_id
        unifiedOrderRequestParams.put("key", key);
        unifiedOrderRequestParams.put("body", "购买麦钻");//商品参数信息
        unifiedOrderRequestParams.put("mch_id", mch_id);//微信商户账号
        unifiedOrderRequestParams.put("nonce_str", WXPayUtils.getNonceStr());//32位不重复的编号
        unifiedOrderRequestParams.put("notify_url", wxNotify);//回调地址
        unifiedOrderRequestParams.put("out_trade_no", orderNumber);//订单编号
        unifiedOrderRequestParams.put("spbill_create_ip", spbillCreateIp);//请求的实际ip地址
        unifiedOrderRequestParams.put("total_fee", fee.toString());//支付金额 单位为分
        unifiedOrderRequestParams.put("trade_type", trade_type);//付款类型为
        String sign = WXPayUtils.createSignStr("UTF-8", unifiedOrderRequestParams, key);//生成签名
        unifiedOrderRequestParams.put("sign", sign);
        unifiedOrderRequestParams.remove("key");//商户应用密钥
        String unifiedOrderRequestXml = WXPayUtils.getRequestXml(unifiedOrderRequestParams);//生成Xml格式的字符串
        //调统一下单接口
        String unifiedOrderResponse = HttpUtil.post(WXPayConstant.UNIFIED_ORDER_URL, unifiedOrderRequestXml);
        InputStream inputStream = new ByteArrayInputStream(unifiedOrderResponse.getBytes());
        return WXPayUtils.getParamsMapFromXml(inputStream);
    }


    public static void main(String[] args) throws Exception {



        List<Long> longs = new ArrayList<>();

        longs.add((long) 1);
        longs.add((long) 2);
        longs.add((long) 3);
        longs.add((long) 4);

        System.out.println("===="+longs.contains((long)3));



    }


}
