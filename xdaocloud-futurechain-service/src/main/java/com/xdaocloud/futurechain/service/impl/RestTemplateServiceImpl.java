package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.service.RestTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author LuoFuMin
 * @data 2018/7/13
 */

@Service
public class RestTemplateServiceImpl implements RestTemplateService {

    private static Logger LOG = LoggerFactory.getLogger(RestTemplateServiceImpl.class);

    @Resource(name = "http-client")
    private RestTemplate restTemplate;


    @Resource(name = "eureka-client")
    private RestTemplate eurekaClient;


    /**
     * post 请求
     *
     * @param url   路径
     * @param param 参数
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    @Override
    public ResultInfo<?> doPost(String url, MultiValueMap<String, String> param) {
        return post(url, param, restTemplate);
    }


    /**
     * post 请求
     *
     * @param url   路径
     * @param param 参数
     * @param token 令牌
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    @Override
    public ResultInfo<?> doPost(String url, JSONObject param, String token) {
        return post(url, param, token, restTemplate);
    }


    /**
     * post 请求
     *
     * @param url   路径
     * @param param 参数
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    @Override
    public String doPost(String url, JSONObject param) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        // 设置body、header
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(param, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        LOG.info("》》》response == " + response);
        return response;
    }

    /**
     * get 请求
     *
     * @param url   路径
     * @param param 参数
     * @param token 令牌
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    @Override
    public ResultInfo<?> doGet(String url, MultiValueMap<String, String> param, String token) {
        return get(url, param, token, restTemplate);
    }


    /**
     * get 请求
     *
     * @param url   路径
     * @param token 令牌
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    @Override
    public ResultInfo<?> doGet(String url, String token) {
        return get(url, token, restTemplate);
    }


    /**
     * 微服务 psot 请求
     *
     * @param url   路径
     * @param param 参数
     * @return ResultInfo
     * @author LuoFuMin
     * @date 2018/8/22
     */
    @Override
    public ResultInfo<?> eurekaPost(String url, MultiValueMap<String, String> param) {
        return post(url, param, eurekaClient);
    }

    /**
     * 微服务 psot 请求
     *
     * @param url   路径
     * @param param 参数
     * @param token 令牌
     * @param appId 应用id
     * @return ResultInfo
     * @author LuoFuMin
     * @date 2018/8/22
     */
    @Override
    public ResultInfo<?> eurekaPost(String url, JSONObject param, String token, String appId) {
        return post(url, param, token, appId,eurekaClient);
    }


    /**
     * 微服务 post 请求
     *
     * @param url：路径, param 参数
     * @return com.xdaocloud.base.info.ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    @Override
    public ResultInfo<?> eurekaPost(String url, JSONObject param, String appId) {
        return postAPPID(url, param, appId, eurekaClient);
    }

    /**
     * 微服务 get 请求
     *
     * @param url：路径, param：参数, token：路径
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    @Override
    public ResultInfo<?> eurekaGet(String url, MultiValueMap<String, String> param, String token, String appId) {
        return get(url, param, token, appId, eurekaClient);
    }

    /**
     * 微服务 get 请求
     *
     * @param url：请求路径, token：令牌
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    @Override
    public ResultInfo<?> eurekaGet(String url, String token, String appId) {
        return get(url, token, appId, eurekaClient);
    }


    /**
     * post 请求
     *
     * @param url    路径
     * @param param  参数
     * @param client 客户端
     * @return
     */
    private ResultInfo<?> post(String url, MultiValueMap<String, String> param, RestTemplate client) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 设置body、header
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(param, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String response = client.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        return getResultInfo(response);
    }


    /**
     * post 请求
     *
     * @param url：请求路径, param ：请求参数, token：令牌, client：请求工具
     * @return esultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    private ResultInfo<?> post(String url, JSONObject param, String token, RestTemplate client) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer "+token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        // 设置body、header
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(param, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String response = client.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        return getResultInfo(response);
    }

    /**
     * post 请求
     *
     * @param url：请求路径, param ：请求参数, token：令牌, client：请求工具
     * @return esultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    private ResultInfo<?> postAPPID(String url, JSONObject param, String appId, RestTemplate client) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("appId", appId);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        //requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 设置body、header
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(param, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String response = client.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        return getResultInfo(response);
    }


    /**
     * post 请求
     *
     * @param url：请求路径, param ：请求参数, token：令牌, client：请求工具
     * @return esultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    private ResultInfo<?> post(String url, JSONObject param, String token, String appId, RestTemplate client) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", token);
        requestHeaders.add("appId", appId);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        // 设置body、header
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(param, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String response = client.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        return getResultInfo(response);
    }


    /**
     * get 请求
     *
     * @param url：请求路径, param ：请求参数, token：令牌, client：请求工具
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    private ResultInfo<?> get(String url, MultiValueMap<String, String> param, String token, RestTemplate client) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", token);
        //requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 设置body、header
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(param, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String paramKey = "";
        String[] paramValue = null;
        if (param != null && !param.isEmpty()) {
            paramValue = new String[param.size()];
            paramKey = getParamString(param, paramKey, paramValue);
        }
        LOG.info("》》》paramKey==" + paramKey);
        String response = client.exchange(url + paramKey, HttpMethod.GET, requestEntity, String.class, paramValue).getBody();
        return getResultInfo(response);
    }

    /**
     * 拼接参数
     *
     * @param param      参数集合
     * @param paramKey   参数 key 拼接路径（userId=?&appId=?）
     * @param paramValue 参数值
     * @return
     */
    private String getParamString(MultiValueMap<String, String> param, String paramKey, String[] paramValue) {
        if (param == null) {
            return "";
        }
        int n = 0;
        for (Map.Entry entry : param.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString().replace("[", "").replace("]", "");
            if (n == 0) {
                paramKey = paramKey + "?" + key + "={" + key + "}";
            } else {
                paramKey = paramKey + "&" + key + "={" + key + "}";
            }
            paramValue[n] = value;
            n++;
        }
        return paramKey;
    }


    /**
     * get 请求
     *
     * @param url：请求路径, param ：请求参数, token：令牌, client：请求工具
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    private ResultInfo<?> get(String url, MultiValueMap<String, String> param, String token, String appId, RestTemplate client) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", token);
        requestHeaders.add("appId", appId);
        //requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 设置body、header
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(param, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String paramKey = "";
        String[] paramValue = null;
        if (param != null && !param.isEmpty()) {
            paramValue = new String[param.size()];
            paramKey = getParamString(param, paramKey, paramValue);
        }
        LOG.info("》》》paramKey==" + paramKey);
        String response = client.exchange(url + paramKey, HttpMethod.GET, requestEntity, String.class, paramValue).getBody();
        return getResultInfo(response);
    }


    /**
     * get 请求
     *
     * @param url：请求路径, param ：请求参数, token：令牌, client：请求工具
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    private ResultInfo<?> get(String url, String token, String appId, RestTemplate client) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", token);
        requestHeaders.add("appId", appId);
        // 设置body、header
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String response = client.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
        return getResultInfo(response);
    }

    /**
     * get 请求
     *
     * @param url：请求路径, token：令牌, client：请求工具
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/22
     */
    private ResultInfo<?> get(String url, String token, RestTemplate client) {
        LOG.info("》》》url == " + url);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", token);
        //requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 设置body、header
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        String response = client.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
        return getResultInfo(response);
    }

    /**
     * 解析返回结果
     *
     * @param response
     * @return
     */
    private ResultInfo<?> getResultInfo(String response) {
        LOG.info("》》》response == " + response);
        JSONObject json = JSON.parseObject(response);
        Integer code = Integer.valueOf(json.get("code").toString());
        String message = json.get("message").toString();
        if (code.intValue() == 200) {
            if (null != json.get("data")) {
                return new ResultInfo<>(code, message, json.get("data"));
            }
            return new ResultInfo<>(code, message);
        }
        return new ResultInfo<>(code, message);
    }

}
