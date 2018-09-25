package com.xdaocloud.futurechain.service;

import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.base.info.ResultInfo;
import org.springframework.util.MultiValueMap;

/**
 * http 请求服务
 *
 * @author LuoFuMin
 * @data 2018/7/13
 */
public interface RestTemplateService {


    /**
     * post 请求
     *
     * @param url   路径
     * @param param 参数
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    ResultInfo<?> doPost(String url, MultiValueMap<String, String> param);

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
    ResultInfo<?> doPost(String url, JSONObject param, String token);


    /**
     * post 请求
     *
     * @param url   路径
     * @param param 参数
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    String doPost(String url, JSONObject param);

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
    ResultInfo<?> doGet(String url, MultiValueMap<String, String> param, String token);

    /**
     * get 请求
     *
     * @param url   路径
     * @param token 令牌
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    ResultInfo<?> doGet(String url, String token);

    /**
     * post 请求
     *
     * @param url   路径
     * @param param 参数
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    ResultInfo<?> eurekaPost(String url, MultiValueMap<String, String> param);

    /**
     * post 请求
     *
     * @param url   路径
     * @param param 参数
     * @param token 令牌
     * @param appId 应用id
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    ResultInfo<?> eurekaPost(String url, JSONObject param, String token, String appId);


    /**
     * post 请求
     *
     * @param url   路径
     * @param param 参数
     * @param appId 应用id
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    ResultInfo<?> eurekaPost(String url, JSONObject param, String appId);

    /**
     * get 请求
     *
     * @param url   路径
     * @param param 参数
     * @param token 令牌
     * @param appId 应用id
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    ResultInfo<?> eurekaGet(String url, MultiValueMap<String, String> param, String token, String appId);

    /**
     * get 请求
     *
     * @param url   路径
     * @param token 令牌
     * @param appId 应用id
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    ResultInfo<?> eurekaGet(String url, String token, String appId);
}
