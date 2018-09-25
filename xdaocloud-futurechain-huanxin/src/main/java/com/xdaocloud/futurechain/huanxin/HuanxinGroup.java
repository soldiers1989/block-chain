package com.xdaocloud.futurechain.huanxin;

import com.xdaocloud.futurechain.dto.req.huanxin.CreateGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HuanxinGroup {

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
     * 获取一个用户所有的群组
     * "groupid" : "1413193977786197",
     * "groupname" : "kenshingrou"
     *
     * @param username 用户名
     * @return 群组列表
     * @date 2017年11月14日
     * @author ShiLijin
     */
    public ResponseStatus getChatsGroup(String username) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "users/" + username + "/joined_chatgroups";

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseStatus.class).getBody();
    }

    /**
     * 获取群组详情
     *
     * @param ids   群组Id，多个用逗号隔开
     * @return 群组详情
     * @date 2017年11月16日
     * @author ShiLijin
     */
    public ResponseRuslt<?> getGroupDetail(String ids) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + ids;

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseRuslt.class).getBody();

    }

    /**
     * 创建一个群
     *
     * @return 群组详情
     * @date 2017年11月16日
     * @author ShiLijin
     */
    public ResponseRuslt<?> createGroup(CreateGroupRequest request) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups";

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("groupname", request.getGroupname());
        map.put("desc", request.getDesc());
        map.put("public", request.getCommon());
        map.put("maxusers", request.getMaxusers());
        map.put("members_only", request.getMembers_only());
        map.put("allowinvites", request.getAllowinvites());
        map.put("owner", request.getOwner());
        if (request.getMembers() != null && request.getMembers().length > 0) {
            map.put("members", request.getMembers());
        }
        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 修改群组信息
     *
     * @param groupId     群组Id
     * @param groupname   群组名称，修改时值不能包含斜杠（"/"）
     * @param description 群组描述，修改时值不能包含斜杠（"/"）
     * @param maxusers    群组成员最大数（包括群主），值为数值类型
     * @return 群组详情
     * @date 2017年11月16日
     * @author ShiLijin
     */
    public ResponseRuslt<?> updateGroupInfo( String groupId, String groupname, String description, Integer maxusers) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupname", groupname);
        map.put("description", description);
        if (maxusers != null) {
            map.put("maxusers", maxusers);
        }
        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 删除群组
     *
     * @param groupId 群组Id
     * @return 无
     * @date 2017年11月16日
     * @author ShiLijin
     */
    public ResponseRuslt<?> deleteGroup( String groupId) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId;
        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 批量添加群成员
     *
     * @param groupId   群组Id
     * @param usernames 多个用户
     * @return 无
     * @date 2017年11月16日
     * @author ShiLijin
     */
    public ResponseRuslt<?> batchAddGroupUser( String groupId, List<String> usernames) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/users";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usernames", usernames);

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        ResponseEntity<ResponseRuslt> responseEntity = null;
        ResponseRuslt responseResult = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseRuslt.class);
            responseResult = responseEntity.getBody();
            responseResult.setHttpCode(responseEntity.getStatusCodeValue());
        } catch (HttpClientErrorException e) {
            responseResult = new ResponseRuslt();
            responseResult.setHttpCode(e.getRawStatusCode());
        }
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return responseResult;
    }

    /**
     * 批量移除群成员
     *
     * @param groupId   群组Id
     * @param usernames 多个群成员username以逗号拼接的字符串
     * @return 无
     * @date 2017年11月16日
     * @author ShiLijin
     */
    public ResponseRuslt<?> batchRemoveGroupUser( String groupId, String usernames) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/" + "users/" + usernames;

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 获取管理员列表
     *
     * @param groupId 群组Id
     * @return 管理员列表
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> listGroupManagers( String groupId) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/" + "admin";

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 添加管理员
     *
     * @param groupId  群组Id
     * @param username 欲添加的管理员
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> addGroupManager( String groupId, String username) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/" + "admin";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("newadmin", username);

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 移除管理员
     *
     * @param groupId  群组Id
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> removeGroupManager( String groupId) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/" + "admin";

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 转让群组
     *
     * @param groupId  群组Id
     * @param username 新群主
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> transferGroup( String groupId, String username) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("newowner", username);
        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 查询一个群组黑名单中的用户列表。位于黑名单中的用户查看不到该群组的信息，也无法收到该群组的消息。
     *
     * @param groupId 群组Id
     * @return 黑名单中的用户列表
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> listBlack( String groupId) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/blocks/users";

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 批量添加至黑名单
     * <p>
     * 添加多个用户进入一个群组的黑名单，一次性最多可以添加60个用户。群主无法被加入群组的黑名单。
     * 用户进入群组黑名单后，会收到消息：You are kicked out of the group xxx。之后，
     * 该用户查看不到该群组的信息，也收不到该群组的消息。
     *
     * @param groupId  群组Id
     * @param userList 多个用户列表
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> bitchAddToBlack(String groupId, List<String> userList) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/blocks/users";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usernames", userList);
        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 批量从黑名单移除
     * <p>
     * 从群组黑名单中移除多个用户。对于群组黑名单中的用户，如果需要将其再次加入群组，需要先将其从群组黑名单中移除。
     *
     * @param groupId   群组Id
     * @param usernames 多个用户逗号拼接
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> bitchRemoveToBlack( String groupId, String usernames) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/blocks/users/" + usernames;

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 添加禁言
     * 将用户禁言。用户被禁言后，将无法在群中发送消息。
     *
     * @param groupId  群组Id
     * @param userList 用户列表
     * @param duration 是禁言的时长，单位是毫秒
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> addMute( String groupId, List<String> userList, Long duration) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/mute";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usernames", userList);
        map.put("mute_duration", duration);
        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 移除禁言
     * 将用户从禁言列表中移除。移除后，用户可以正常在群中发送消息。
     *
     * @param groupId   群组Id
     * @param usernames 多个用户以逗号拼接
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> addMute( String groupId, String usernames) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/mute/" + usernames;

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ResponseRuslt.class).getBody();
    }

    /**
     * 获取禁言列表
     *
     * @param groupId 群组Id
     * @return 无
     * @date 2017年11月17日
     * @author ShiLijin
     */
    public ResponseRuslt<?> listMute( String groupId) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + huanxinToken.generateToken());
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups/" + groupId + "/mute";

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(null, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseRuslt.class).getBody();
    }

    /*************************************************************************************************************************************************************/


    /**
     * 创建一个群
     * @param token 身份令牌
     * @param ids 群组Id，多个用逗号隔开
     * @return 群组详情
     * @date 2017年11月16日
     * @author ShiLijin
     */
    public ResponseRuslt<?> createGroup(String token, CreateGroupRequest request) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer "+token);
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "chatgroups";

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("groupname", request.getGroupname());
        map.put("desc", request.getDesc());
        map.put("public", request.getCommon());
        map.put("maxusers", request.getMaxusers());
        map.put("members_only", request.getMembers_only());
        map.put("allowinvites", request.getAllowinvites());
        map.put("owner", request.getOwner());
        if(request.getMembers() != null && request.getMembers().length > 0) {
            map.put("members", request.getMembers());
        }
        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return  restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseRuslt.class).getBody();
    }


    /**
     * 批量添加群成员
     * @param token 身份令牌
     * @param groupId 群组Id
     * @param usernames 多个用户
     * @return 无
     * @date 2017年11月16日
     * @author ShiLijin
     */
    public ResponseRuslt<?> batchAddGroupUser(String token, String groupId,List<String> usernames) {
        //设置http header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer "+token);
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/"+ "chatgroups/" + groupId + "/users";

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("usernames", usernames);

        //设置body、header
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, requestHeaders);
        ResponseEntity<ResponseRuslt> responseEntity =  null;
        ResponseRuslt responseResult = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseRuslt.class);
            responseResult = responseEntity.getBody();
            responseResult.setHttpCode(responseEntity.getStatusCodeValue());
        } catch (HttpClientErrorException e) {
            responseResult = new ResponseRuslt();
            responseResult.setHttpCode(e.getRawStatusCode());
        }
        //请求地址、请求方式、	请求体、返回结果集接收模版
        return responseResult;
    }
}
