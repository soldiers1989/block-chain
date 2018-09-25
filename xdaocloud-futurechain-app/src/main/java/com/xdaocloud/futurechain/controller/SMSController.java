package com.xdaocloud.futurechain.controller;


import static com.xdaocloud.futurechain.constant.Constant.SMS_REGISTER_KEY;
import static com.xdaocloud.futurechain.util.HttpUtils.getRemortIP;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.base.utils.RedisUtils;
import com.xdaocloud.futurechain.config.GeetestConfig;
import com.xdaocloud.futurechain.dto.req.sms.SMS_CodeRequest;
import com.xdaocloud.futurechain.dto.req.sms.SendSMSRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.service.SMSService;
import com.xdaocloud.futurechain.util.GeetestLib;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 短信平台控制器
 *
 * @author WangPengHua
 */
@RestController("AppSMSController")
@Api(value = "SMSController", description="短信平台控制器")
@RequestMapping("/api/app/")
public class SMSController {

    private static Logger LOG = LoggerFactory.getLogger(SMSController.class);

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发送短信服务类
     */
    @Autowired
    private SMSService smsService;

    /**
     * 发送手机验证码
     *

     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2017年11月2日
     * @author WangPengHua
     */
    @ApiOperation(value = "发送手机验证码")
    @PostMapping("v2/sms/register/send")
    public ResultInfo<?> sendSMS(HttpServletRequest request)
            throws Exception {
        //parseBindingResult(bindingResult);
        /*Object object = redisUtils.get("futurechain:verify:code:"+smsRequest.getVerifyCode().toUpperCase());
        LOG.info("====redis--verify:code====="+object);
        LOG.info("=====客户端传输上来VerifyCode===="+smsRequest.getVerifyCode().toUpperCase());
        if (object == null || !smsRequest.getVerifyCode().toUpperCase().equals(String.valueOf(object))) {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码错误");
        }
        redisUtils.remove("futurechain:verify:code:");*/
        LOG.info("====v2/sms/register/send=====");

        int gtResult = smsService.checkGeetest(request);
        
        if(gtResult == 1) {
            return smsService.sendSmsCode(request.getParameter("mobileNumber"));
        }else {
            return new ResultInfo<>(ResultInfo.VERIFICATION_FAILURE, ResultInfo.MSG_VERIFICATION_FAILURE);
        }
    }

    /**
     * 找回密码发送手机验证码
     *
     * @param smsRequest    入参
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2017年11月2日
     * @author WangPengHua
     */
    @ApiOperation(value = "找回密码验证码")
    @PostMapping("v2/sms/backPass/send")
    public ResultInfo<?> sendBackPassSMS(@Valid @RequestBody SendSMSRequest smsRequest)
            throws Exception {
        return smsService.sendBackPassSMS(smsRequest.getMobileNumber());
    }

    /**
     * 找回交易密码发送手机验证码
     *
     * @param userIdRequest  入参
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2017年11月2日
     * @author WangPengHua
     */
    @ApiOperation(value = "修改交易密码")
    @PostMapping("v2/sms/changeTransactionPassWord/send")
    public ResultInfo<?> sendChangeTransactionPassSMS(@Valid @RequestBody UserIdRequest userIdRequest)
            throws Exception {
        return smsService.sendChangeTransactionPassSMS(userIdRequest.getUserid());
    }

    /**
     * 校验手机验证码
     *
     * @param smsRequest    入参
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "校验手机验证码")
    @PostMapping("v2/sms/check/code")
    public ResultInfo<?> checkSMSCode(@Valid @RequestBody SMS_CodeRequest smsRequest)
            throws Exception {
        return smsService.checkSMSCode(smsRequest.getPhone(), smsRequest.getCode(), SMS_REGISTER_KEY);
    }




    /**
     * 客户端手动发短信邀请添加记录
     * @param map
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2017年11月2日
     * @author WangPengHua
     */
    @ApiOperation(value = "客户端手动发短信邀请添加记录")
    @PostMapping("v2/sms/addInvitationLog")
    public ResultInfo<?> addInvitationLog(@RequestBody Map<String,Object> map) throws Exception {
        return smsService.addInvitationLog(map);
    }


    /********************************************************************************************************************************************/
    /**
     * 发送手机验证码
     *
     * @param smsRequest    入参
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2017年11月2日
     * @author WangPengHua
     */
    @ApiOperation(value = "发送手机验证码")
    @PostMapping("v1/sms/send")
    public ResultInfo<?> sendSMSV1(@Valid @RequestBody SendSMSRequest smsRequest)
            throws Exception {
        /*Object object = redisUtils.get("futurechain:verify:code:"+smsRequest.getVerifyCode().toUpperCase());
        LOG.info("====redis--verify:code====="+object);
        LOG.info("=====客户端传输上来VerifyCode===="+smsRequest.getVerifyCode().toUpperCase());
        if (object == null || !smsRequest.getVerifyCode().toUpperCase().equals(String.valueOf(object))) {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码错误");
        }
        redisUtils.remove("futurechain:verify:code:");*/
        return smsService.sendSmsCodeV1(smsRequest.getMobileNumber());
    }

    /**
     * 校验手机验证码
     *
     * @param smsRequest    入参
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "校验手机验证码")
    @PostMapping("v1/sms/check/code")
    public ResultInfo<?> checkSMS_CodeV1(@Valid @RequestBody SMS_CodeRequest smsRequest)
            throws Exception {
        return smsService.checkSMSCodeV1(smsRequest.getPhone(), smsRequest.getCode());
    }


/*    @GetMapping("v1/sms/get_erify_code")
    public void getVerifyCodeV1(HttpServletResponse response)
            throws Exception {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        LOG.info("==验证码==" + verifyCode);
        redisUtils.set("futurechain:verify:code:"+verifyCode, verifyCode, 300L);
        // 生成图片
        int w = 100, h = 40;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }*/

    /**
     * 
     * 极验接口，客户端初始化时先调用这个接口，滑动完后再调SMSService.checkGeetest()进行校验合法性
     * @param request
     * @param response
     * @return
     * @date 2018年9月4日
     * @author patrick
     */
    @ApiOperation(value = "极验滑动验证获取gt，challenge参数的api接口")
    @GetMapping("v1/sms/gt_register")
    public Object register(HttpServletRequest request, HttpServletResponse response) {

        String ipAddress = getRemortIP(request);

        LOG.info("====终端IP地址--getRemortIP====" + ipAddress);

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());

        String resStr = "{}";

        // 自定义userid
        String userid = "test";

        // 自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); // 网站用户id
        param.put("client_type", "native"); // web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", ipAddress); // 传输用户请求验证时所携带的IP

        // 进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

//        // 将服务器状态设置到session中
//        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
//        // 将userid设置到session中
//        request.getSession().setAttribute("userid", userid);
        
        redisUtils.set(gtSdk.gtServerStatusSessionKey, gtServerStatus, 300L);

        resStr = gtSdk.getResponseStr();
        return JSON.toJSON(resStr);
    }
}

