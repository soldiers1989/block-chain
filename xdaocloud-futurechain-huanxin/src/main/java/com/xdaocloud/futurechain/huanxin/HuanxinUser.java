package com.xdaocloud.futurechain.huanxin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "huanxinCache", keyGenerator = "myKeyGenerator")
public class HuanxinUser {

    private static Logger LOG = LoggerFactory.getLogger(HuanxinUser.class);

    /**
     * 微服务调用类
     */
    @Resource(name = "http-client")
    private RestTemplate restTemplate;

    @Autowired
    private HXConfig config;

    @Autowired
    private HuanxinToken huanxinToken;

/*
    */
/**
 * 环信获取token
 *
 * @return token信息
 * @date 2017年11月7日
 * @author ShiLijin
 *//*

    @Cacheable
    public TokenDTO generateToken() {
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "token";
        Map<String, Object> request = new HashMap<String, Object>();
        request.put("grant_type", "client_credentials");
        request.put("client_id", config.getClientId());
        request.put("client_secret", config.getClientSecret());
        return restTemplate.postForObject(url, request, TokenDTO.class);
    }
*/

    /**
     * 注册IM用户（单个）
     *
     * @param username 账号
     * @param password 密码
     * @param nickname 昵称（可以为空）
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate regiestUser(String username, String password, String nickname) {

        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users";
        Map<String, Object> request = new HashMap<String, Object>();
        if (nickname != null) {
            request.put("nickname", nickname);
        }
        request.put("username", username);
        request.put("password", password);
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(request, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @param token    身份令牌
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate getUserInfo(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username;
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 删除用户(单个)
     *
     * @param username 用户名
     * @param token    身份令牌
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate deleteUser(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username;
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 修改密码
     *
     * @param username 用户名
     * @param token    身份令牌
     * @param password 新密码
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate resetPassword(String username, String token, String password) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/password";
        // 设置body、header
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("newpassword", password);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 修改昵称
     *
     * @param username 用户名
     * @param nickname 新昵称
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate updateNickname(String username, String nickname) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username;
        LOG.info("==url==" + url);
        LOG.info("==token==" + huanxinToken.generateToken());
        // 设置body、header
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nickname", nickname);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 用户账号禁用
     *
     * @param username 用户名
     * @param token    身份令牌
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate disabledUsername(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/deactivate";
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 用户账号解禁
     *
     * @param username 用户名
     * @param token    身份令牌
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate enabledUsername(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/activate";
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 添加好友
     *
     * @param username       自己的用户名
     * @param friendUsername 朋友的用户名
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate addFriend(String username, String friendUsername) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/contacts/users/" + friendUsername;
        // 设置body、header
        Map<String, Object> request = new HashMap<String, Object>();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(request, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 解除好友关系
     *
     * @param username       自己的用户名
     * @param token          身份令牌
     * @param friendUsername 朋友的用户名
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate relieveFriend(String username, String token, String friendUsername) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/contacts/users/" + friendUsername;
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 获取好友列表
     *
     * @param username 自己的用户名
     * @param token    身份令牌
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate listFriend(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/contacts/users";
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 获取黑名单列表
     *
     * @param username 用户名
     * @param token    令牌
     * @return
     * @date 2017年11月8日
     * @author xiaodaotec
     */
    public ResponseTemplate listBlack(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/blocks/users";
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 往 IM 用户的黑名单中加人
     *
     * @param username 自己的用户名
     * @param token    身份令牌
     * @param list     朋友的用户名
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate addToBlackList(String username, String token, List<String> list) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/blocks/users";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usernames", list);
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * 往用户的黑名单中移除人
     *
     * @param username        自己的用户名
     * @param token           身份令牌
     * @param balckedUsername 被拉黑的用户名
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate removeToBlackList(String username, String token, String balckedUsername) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/blocks/users/" + balckedUsername;
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ResponseTemplate.class).getBody();
    }

    /**
     * "stliu": "online" 注意：这里返回的是用户名和在线状态的键值对，值为 online 或者 offline 查看用户在线状态
     *
     * @param username 自己的用户名
     * @param token    身份令牌
     * @return ResponseStatus
     * @date 2017年11月9日
     * @author ShiLijin
     */
    public ResponseStatus userStatus(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/status";
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseStatus.class).getBody();
    }

    /**
     * “data” : {“v3y0kf9arx” : 0 } —- 用户名：v3y0kf9arx，离线消息数：0条 查询离线消息数
     *
     * @param username 自己的用户名
     * @param token    身份令牌
     * @return ResponseStatus
     * @date 2017年11月9日
     * @author ShiLijin
     */
    public ResponseStatus offlineMessage(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/offline_msg_count";
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseStatus.class).getBody();
    }

    /**
     * 格式："{消息id}":"{状态}"，状态的值有两个: deliverd表示此用户的该条离线消息已经收到过了，undelivered表示此用户的该条离线消息还未收到 查询离线消息数
     *
     * @param username 自己的用户名
     * @param token    身份令牌
     * @return ResponseStatus
     * @date 2017年11月9日
     * @author ShiLijin
     */
    public ResponseStatus offlineMessageStatus(String username, String token, Long messageId) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/offline_msg_status/" + messageId;
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseStatus.class).getBody();
    }

    /**
     * "Result" : true // true表示强制下线成功，false表示强制用户下线失败 强制用户下线
     *
     * @param username 用户名
     * @param token    身份令牌
     * @return ResponseStatus
     * @date 2017年11月9日
     * @author ShiLijin
     */
    public ResponseStatus disconnect(String username, String token) {
        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users" + "/" + username + "/disconnect";
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseStatus.class).getBody();
    }
    /**************************************************************************************************************************************************************************/

    /**
     * 注册IM用户（单个）
     *
     * @param username 账号
     * @param password 密码
     * @param nickname 昵称（可以为空）
     * @param token 令牌
     * @return ResponseTemplate
     * @date 2017年11月8日
     * @author ShiLijin
     */
    public ResponseTemplate regiestUser(String username, String password, String nickname, String token) {

        // 设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users";
        Map<String, Object> request = new HashMap<String, Object>();
        if (nickname != null) {
            request.put("nickname", nickname);
        }
        request.put("username", username);
        request.put("password", password);
        // 设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(request, requestHeaders);
        // 请求地址、请求方式、 请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTemplate.class).getBody();
    }
}
