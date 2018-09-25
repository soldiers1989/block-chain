package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.constant.Constant;
import com.xdaocloud.futurechain.dto.resp.user.WebUserLoginResponse;
import com.xdaocloud.futurechain.service.RestTemplateService;
import com.xdaocloud.futurechain.service.WebUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.TimeUnit;

/**
 * web用户管理
 *
 * @author LuoFuMin
 * @data 2018/7/13
 */
@Service
public class WebUserServiceImpl implements WebUserService {

    private static Logger LOG = LoggerFactory.getLogger(WebUserServiceImpl.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplateService restTemplateService;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     * @author LuoFuMin
     * @data 2018/7/13
     */
    @Override
    public WebUserLoginResponse doLoin(String username, String password) {
        String url = "http://uc/api/v1/user/login";
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("username", username);
        jsonRequest.put("password", password);

        ResultInfo<?> resultInfo = restTemplateService.eurekaPost(url, jsonRequest,Constant.APPID);

        if (resultInfo.getCode().intValue() != 200) {
            WebUserLoginResponse response = new WebUserLoginResponse(resultInfo.getMessage());
            return response;
        }
        JSONObject jsonObject = JSON.parseObject(resultInfo.getData().toString());
        WebUserLoginResponse response = new WebUserLoginResponse();
        response.setId(Long.valueOf(jsonObject.get("id").toString()));
        response.setName(jsonObject.get("name").toString());
        response.setUserToken(jsonObject.get("userToken").toString());
        response.setToken(jsonObject.get("userToken").toString());

        //根据权限，指定返回数据
        stringRedisTemplate.opsForValue().set(Constant.WEB_USER_TOKEN + username, response.getToken(), 3, TimeUnit.HOURS);
        MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add("userId", String.valueOf(response.getId()));
        param.add("appId", "maichain");
        //查询菜单权限
        ResultInfo result = restTemplateService.eurekaGet("http://uc/api/auth/v1/user/permission/tree", param, jsonObject.get("userToken").toString(),Constant.APPID);
        if (result.getCode().intValue() == 200) {
            response.setAuthority(result.getData());
        } else {
            //response.setErrorMsg(resultInfo.getMessage());
        }
        return response;
    }

    /**
     * 修改密码
     *
     * @param userId 用户id, password 新密码, oldPassword 旧密码, token 令牌
     * @return com.xdaocloud.base.info.ResultInfo<?>
     * @author LuoFuMIn
     * @date 2018/7/19
     */
    @Override
    public ResultInfo<?> updatePwd(String userId, String password, String oldPassword, String token) {
        JSONObject param = new JSONObject();
        param.put("id", userId);
        param.put("password", password);
        param.put("oldPassword", oldPassword);
        String url = "http:/uc//api/auth/v1/user/pwd";
        ResultInfo<?> resultInfo = restTemplateService.eurekaPost(url, param, token);
        return resultInfo;
    }

}
