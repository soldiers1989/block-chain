package com.xdaocloud.futurechain.service;

import static com.xdaocloud.futurechain.constant.Constant.SMS_BACK_PASS_KEY;
import static com.xdaocloud.futurechain.constant.Constant.SMS_BACK_TRANSACTION_PASS_KEY;
import static com.xdaocloud.futurechain.constant.Constant.SMS_REGISTER_KEY;
import static com.xdaocloud.futurechain.util.DateUtils.getSurplusTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.github.pagehelper.util.StringUtil;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.base.utils.RedisUtils;
import com.xdaocloud.futurechain.config.GeetestConfig;
import com.xdaocloud.futurechain.mapper.FriendPhoneMapper;
import com.xdaocloud.futurechain.mapper.UserMapper;
import com.xdaocloud.futurechain.model.FriendPhone;
import com.xdaocloud.futurechain.model.User;
import com.xdaocloud.futurechain.util.GeetestLib;

@Component
public class SMSService {

    private static final Log logger = LogFactory.getLog(SMSService.class);

    @Resource(name = "http-client")
    public RestTemplate restTemplate;

    /**
     * redisUtils
     */
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendPhoneMapper friendPhoneMapper;


    /**
     * 发送注册短信验证码
     *
     * @param phone
     * @return
     * @date 2017年11月11日
     * @author WangPengHua
     */
    @SuppressWarnings("unchecked")
    public ResultInfo<?> sendSmsCode(String phone) {
        return sendSMSCode(phone, SMS_REGISTER_KEY);

    }

    /**
     * 找回密码发送短信验证码
     *
     * @param phone
     * @return
     * @date 2017年11月11日
     * @author WangPengHua
     */
    @SuppressWarnings("unchecked")
    public ResultInfo<?> sendBackPassSMS(String phone) {
        return sendSMSCode(phone, SMS_BACK_PASS_KEY);
    }

    /**
     * 找回交易密码 短信验证码
     *
     * @param userid 身份证
     * @return
     */
    public ResultInfo<?> sendChangeTransactionPassSMS(String userid) {
        User user = userMapper.selectByPrimaryKey(Long.valueOf(userid));
        if (!StringUtils.isEmpty(user.getMobileNumber())) {
            return sendSMSCode(user.getMobileNumber(), SMS_BACK_TRANSACTION_PASS_KEY);
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, "身份证信息有误");
        }

    }

    private ResultInfo<?> sendSMSCode(String phone, String redisKey) {
        //Object object = redisUtils.get(redisKey + phone);
       /* if (object != null) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }*/
        Object countObj = redisUtils.get(redisKey + "count:" + phone);
        Integer count = 0;
        if (countObj != null) {
            count = Integer.parseInt(countObj.toString());
        }
        logger.info("======" + count);
        if (count > 10) {
            return new ResultInfo<>(ResultInfo.FAILURE, "发送验证码次数过多,请明天再尝试");
        }
        String url = "http://yunpian.com/v1/sms/send.json";
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        redisUtils.set(redisKey + phone, code, 300L);
        redisUtils.set(redisKey + "count:" + phone, count + 1, getSurplusTime());
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("apikey", "b5a3d71fd2b234d1f8c3b815b15e2d41");
        params.add("mobile", phone);
        params.add("text", "【脉链星球】您的验证码是" + code + "，有效期5分钟。如非本人操作，请忽略本短信！");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params);
        Map<String, Object> map = restTemplate.postForEntity(url, httpEntity, Map.class).getBody();
        if (null == map || map.size() == 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        int success = (Integer) map.get("code");
        if (success == 0) {
            logger.info("Send message :" + "mobile:" + phone + "  code:" + code + "    Result:" + map);
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else {
            logger.info("Send message :" + "mobile:" + phone + "  code:" + code + "    Result:" + map);
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
    }

    /**
     * 校验短信验证码
     *
     * @param smsCode
     * @return
     * @date 2017年11月11日
     * @author WangPengHua
     */
    public ResultInfo<?> checkSMSCode(String phone, String smsCode, String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        logger.info("Check Code sessionId=" + session.getId());
        Object object = redisUtils.get(key + phone);
        logger.info("===短信验证码==" + object.toString());
        if (object == null || !smsCode.equalsIgnoreCase(String.valueOf(object))) {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码错误");
        }
        //redisUtils.remove("futurechain:register:sms:code:" + phone);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }
    

    /**
     * 
     * 校验极验滑动验证码
     * @return 验证结果,1表示验证成功0表示验证失败
     * @date 2018年9月4日
     * @author patrick
     */
    public int checkGeetest(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        int gt_server_status_code = (Integer)redisUtils.get(gtSdk.gtServerStatusSessionKey);

        // 自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
//        param.put("user_id", userid); // 网站用户id
        param.put("client_type", "native"); // web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); // 传输用户请求验证时所携带的IP

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            // gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }
            return gtResult;
    }


    /**
     * 校验短信验证码
     *
     * @param smsCode
     * @return
     * @date 2017年11月11日
     * @author WangPengHua
     */
    public Boolean checkSMS_Code(String phone, String smsCode, String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        logger.info("Check Code sessionId=" + session.getId());
        Object object = redisUtils.get(key + phone);
        if (object == null || !smsCode.equalsIgnoreCase(String.valueOf(object))) {
            return false;
        }
        //redisUtils.remove("futurechain:register:sms:code:" + phone);
        return true;
    }


    /**
     * 校验短信验证码
     *
     * @param usercode
     * @return
     * @date 2017年11月11日
     * @author WangPengHua
     */
    public ResultInfo<?> checkSMSCode(String phone, String usercode) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        System.err.println("Check Code sessionId=" + session.getId());
        Object object = redisUtils.get("futurechain:register:sms:code:" + phone);
        if (object == null || !usercode.equalsIgnoreCase(String.valueOf(object))) {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码错误");
        }
        //redisUtils.remove("futurechain:register:sms:code:" + phone);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }



    /********************************************************************************************************************************************************/




    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     * @date 2017年11月11日
     * @author WangPengHua
     */
    @SuppressWarnings("unchecked")
    public ResultInfo<?> sendSmsCodeV1(String phone) {

        Object object = redisUtils.get("futurechain:register:sms:code:" + phone);
        if (object != null) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }

        String url = "http://yunpian.com/v1/sms/send.json";
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        redisUtils.set("futurechain:register:sms:code:" + phone, code, 300L);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("apikey", "b5a3d71fd2b234d1f8c3b815b15e2d41");
        params.add("mobile", phone);
        params.add("text", "【脉链星球】您的验证码是" + code + "，有效期5分钟。如非本人操作，请忽略本短信！");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params);
        Map<String, Object> map = restTemplate.postForEntity(url, httpEntity, Map.class).getBody();
        if (null == map || map.size() == 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        int success = (Integer) map.get("code");
        if (success == 0) {
            logger.info("Send message :" + "mobile:" + phone + "  code:" + code + "    Result:" + map);
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else {
            logger.info("Send message :" + "mobile:" + phone + "  code:" + code + "    Result:" + map);
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }

    }

    /**
     * 校验短信验证码
     *
     * @param usercode
     * @return
     * @date 2017年11月11日
     * @author WangPengHua
     */
    public ResultInfo<?> checkSMSCodeV1(String phone, String usercode) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        System.err.println("Check Code sessionId=" + session.getId());
        Object object = redisUtils.get("futurechain:register:sms:code:" + phone);
        if (object == null || !usercode.equalsIgnoreCase(String.valueOf(object))) {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码错误");
        }
        //redisUtils.remove("futurechain:register:sms:code:" + phone);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    public ResultInfo<?> addInvitationLog(Map<String, Object> map) {
        try {
            if (StringUtil.isEmpty(map.get("userId").toString())) {
                new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
            }
            if (StringUtil.isEmpty(map.get("mobileList").toString())) {
                new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
            }
            List<String> mobileList = (List<String>) map.get("mobileList");
            for (String mobile : mobileList){
                FriendPhone friendPhone = new FriendPhone();
                friendPhone.setPhone(mobile);
                friendPhone.setState(false);
                friendPhone.setUserId(Long.parseLong(map.get("userId").toString()));
                friendPhone.setIsDeleted(false);
                friendPhone.setGmtModified(new Date());
                friendPhone.setGmtCreate(new Date());

                friendPhoneMapper.insert(friendPhone);
            }


            return  new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }
}
