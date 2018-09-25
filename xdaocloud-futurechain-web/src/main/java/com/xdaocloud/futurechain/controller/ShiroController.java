package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.constant.Constant;
import com.xdaocloud.futurechain.dto.req.user.WebUpdatePwdRequest;
import com.xdaocloud.futurechain.dto.req.user.WebUserLoginRequest;
import com.xdaocloud.futurechain.dto.resp.user.WebUserLoginResponse;
import com.xdaocloud.futurechain.service.WebUserService;
import com.xdaocloud.futurechain.util.VerifyCodeUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * Shiro 控制类
 *
 * @author LuoFuMin
 * @data 2018/7/14
 */
@RestController
public class ShiroController {

    private static Logger LOG = LoggerFactory.getLogger(ShiroController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WebUserService webUserService;


    /**
     * 图像验证码
     *
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "图像验证码")
    @GetMapping("/api/web/v1/get_erify_code")
    public void getVerifyCode(HttpServletResponse response)
            throws Exception {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        LOG.info("==验证码==" + verifyCode);
        stringRedisTemplate.opsForValue().set(Constant.WEB_VERIFY_CODE + verifyCode, verifyCode, 10, TimeUnit.MINUTES);
        // 生成图片
        int w = 100, h = 40;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    /**
     * Web登录
     *
     * @param webUserLoginRequest
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月14日
     * @author LiMaoDa
     */
    @ApiOperation(value = "Web登录")
    @PostMapping("/api/web/v1/login")
    public ResultInfo<?> doLoin(@Valid @RequestBody WebUserLoginRequest webUserLoginRequest) {

        String verifyCode = stringRedisTemplate.opsForValue().get(Constant.WEB_VERIFY_CODE + webUserLoginRequest.getVerifyCode().toUpperCase());
        LOG.info("》》》verifyCode==" + verifyCode);
        if (StringUtils.isEmpty(verifyCode)) {
            return new ResultInfo<>(ResultInfo.FAILURE, "请刷新验证码");
        }
        //忽略大小写的比较
        if (!webUserLoginRequest.getVerifyCode().equalsIgnoreCase(verifyCode)) {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码不正确");
        }
        WebUserLoginResponse webUserLoginResponse = webUserService.doLoin(webUserLoginRequest.getUserName(), webUserLoginRequest.getPassword());

        if (!StringUtils.isEmpty(webUserLoginResponse.getErrorMsg())) {
            return new ResultInfo<>(ResultInfo.FAILURE, webUserLoginResponse.getErrorMsg());
        }
        //从SecurityUtils里边创建一个subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(webUserLoginRequest.getUserName(), webUserLoginRequest.getPassword());
        // 执行认证登陆
        subject.login(token);
        stringRedisTemplate.opsForValue().set(Constant.WEB_USER_ID + webUserLoginResponse.getId(), webUserLoginResponse.getToken(), 3, TimeUnit.HOURS);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, webUserLoginResponse);
    }

    /**
     * 退出接口
     *
     * @return ResultInfo
     * @author LuoFuMIn
     * @date 2018/7/19
     */
    @RequestMapping(value = "/api/web/v1/shiro/logout", method = RequestMethod.GET)
    public ResultInfo logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new ResultInfo(ResultInfo.SUCCESS, "退出成功！");
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     * @author LuoFuMIn
     * @date 2018/7/19
     */
    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @PostMapping("/api/web/v1/user/updatePwd")
    public ResultInfo<?> resetPwd(@Valid @RequestBody WebUpdatePwdRequest request, HttpServletRequest httpServletRequest)
            throws Exception {
        String token = httpServletRequest.getHeader("Authorization");
        return webUserService.updatePwd(request.getUserId(), request.getPassword(), request.getOldPassword(), token);
    }

    /**
     * 拦截未登录后返回信息
     *
     * @return ResultInfo
     * @author LuoFuMIn
     * @date 2018/7/19
     */
    @RequestMapping(value = "/shiro/notLogin", method = RequestMethod.GET)
    public ResultInfo notLogin() {
        return new ResultInfo(ResultInfo.UNAUTHORIZED, "您尚未登陆！");
    }

    /**
     * 拦截无权限后返回信息
     *
     * @return ResultInfo
     * @author LuoFuMIn
     * @date 2018/7/19
     */
    @RequestMapping(value = "/shiro/notRole", method = RequestMethod.GET)
    public ResultInfo notRole() {
        return new ResultInfo(ResultInfo.FAILURE, "您没有权限！");
    }


    /**
     * 权限例子(按角色控制)
     *
     * @return
     */
    @GetMapping(value = "/user/getMessage")
    @RequiresRoles({"admin", "user"})
    public ResultInfo getMessage() {
        return new ResultInfo(ResultInfo.SUCCESS, "您拥有用户权限，可以获得该接口的信息！");
    }


    /**
     * 权限例子（按权限控制）
     *
     * @return
     */
    @GetMapping(value = "/user/do_some")
    @RequiresPermissions("/api/auth/v1/user/profile")
    public ResultInfo doSome() {
        return new ResultInfo(ResultInfo.SUCCESS, "您拥有用户权限，可以获得该接口的信息！");
    }


}
