package com.xdaocloud.futurechain.huanxin;

import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.futurechain.dto.req.huanxin.SendMessageRequest;
import com.xdaocloud.futurechain.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class HuanxinMessage {

    /**
     * 微服务调用类
     */
    @Resource(name = "http-client")
    private RestTemplate restTemplate;

    @Autowired
    private HXConfig config;


    @Autowired
    private HuanxinToken huanxinToken;

    /**
     * 发送文本消息
     *
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> sendTextMessage(SendMessageRequest request) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "messages";
        //设置body、header
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        //将对象转化为json格式对象
        //转化为JSONObject对象
        JSONObject jsonObject = JSONUtils.turnJSON(request);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(jsonObject, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseRuslt.class).getBody();
    }
}
