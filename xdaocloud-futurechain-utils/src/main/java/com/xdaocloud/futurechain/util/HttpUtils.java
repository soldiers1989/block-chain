package com.xdaocloud.futurechain.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class HttpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);


    /**
     * TODO //获得客户端的ip地址
     *
     * @param request
     * @return
     * @author LuoFuMin
     * @date 2017年2月22日 下午3:55:40
     */
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("X-Forwarded-For") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("X-Forwarded-For");
    }



    /**
     * TODO //获得客户端的ip地址
     *
     * @param request
     * @return
     * @author LuoFuMin
     * @date 2017年2月22日 下午3:55:40
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        LOG.debug("X-Forwarded-For = {}", ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            LOG.debug("Proxy-Client-IP = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            LOG.debug("WL-Proxy-Client-IP = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            LOG.debug("RemoteAddr-IP = {}", ip);
        }
        if (StringUtils.isNotBlank(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }


    public static HttpEntity getHeaders(HttpServletRequest request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeaders.add(key, value);
        }
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        return requestEntity;
    }

    /**
     * 添加请求头
     *
     * @param request    请求头信息
     * @param jsonObject 请求参数
     * @return
     */
    public static HttpEntity enterHeadersAddParam(HttpServletRequest request, JSONObject jsonObject) {
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeaders.add(key, value);
        }
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, requestHeaders);
        return requestEntity;
    }

    public static HttpEntity enterHeadersAddFile(HttpServletRequest request, MultiValueMap<String, Object> file) {
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeaders.add(key, value);
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(file, requestHeaders);
        return requestEntity;
    }


}
