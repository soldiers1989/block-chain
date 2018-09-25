package com.xdaocloud.futurechain.config.shiro;

import com.alibaba.fastjson.JSONArray;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.constant.Constant;
import com.xdaocloud.futurechain.service.RestTemplateService;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 重写的两个方法分别是实现身份认证以及权限认证，ShiroController 中有个作登陆操作的 Subject.login() 方法，
 * 当我们把封装了用户名，密码的 token 作为参数传入，便会跑进这两个方法里面（不一定两个方法都会进入）
 *
 * @author LuoFuMin
 * @data 2018/7/13
 */
public class ShiroCustomRealm extends AuthorizingRealm {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroCustomRealm.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplateService restTemplateService;

    /**
     * 认证信息.(身份验证)
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOG.info("》》》身份认证方法》》》");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();  //获得用户名 String
        Object Principal = token.getPrincipal(); //获得用户名 Object
        char[] password = token.getPassword();  //获得密码 char[]
        token.getCredentials(); //获得密码 Object
        //登录接口已经完成去用户中心验证，所以这里不需要在验证
        return new SimpleAuthenticationInfo(Principal, password, username);
    }

    /**
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOG.info("》》》权限认证》》》");
        /**
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获得该用户角色
        String token = stringRedisTemplate.opsForValue().get(Constant.WEB_USER_TOKEN + username);
        if (!StringUtils.isEmpty(token)) {
            String userId = Jwts.parser().setSigningKey(Constant.JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
            //查询角色
            ResultInfo resultInfo = restTemplateService.eurekaGet("http://uc/api/v1/user/get_roles/" + userId, token, Constant.APPID);
            if (resultInfo.getCode().intValue() == 200) {
                JSONArray jsonArray = (JSONArray) resultInfo.getData();
                for (int i = 0; i < jsonArray.size(); i++) {
                    LOG.info("》》》 角色 ：：" + jsonArray.getString(i));
                    //设置该用户拥有的角色
                    authorizationInfo.addRole(jsonArray.getString(i));
                }
            }

            MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
            param.add("userId", userId);
            param.add("appId", "maichain");
            //查询权限
            ResultInfo resultPermission = restTemplateService.eurekaGet("http://uc/api/auth/v1/findPermission/list", param, token, Constant.APPID);

            if (resultPermission.getCode().intValue() == 200) {
                JSONArray jsonArray = (JSONArray) resultPermission.getData();
                for (int i = 0; i < jsonArray.size(); i++) {
                    LOG.info("》》》 权限 ：：" + jsonArray.getString(i));
                    //设置该用户拥有的权限
                    authorizationInfo.addStringPermission(jsonArray.getString(i));
                }
            }
        }
        return authorizationInfo;
    }
}
