package com.xdaocloud.futurechain.controller;

import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.user.*;
import com.xdaocloud.futurechain.dto.resp.user.BankResponse;
import com.xdaocloud.futurechain.mapper.SpecialUserMapper;
import com.xdaocloud.futurechain.mapper.UserMapper;
import com.xdaocloud.futurechain.model.SpecialUser;
import com.xdaocloud.futurechain.model.User;
import com.xdaocloud.futurechain.service.IndustryService;
import com.xdaocloud.futurechain.service.UserService;
import com.xdaocloud.futurechain.util.ImageUtils;
import com.xdaocloud.futurechain.util.QRCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.xdaocloud.futurechain.util.HttpUtils.getRemortIP;

/**
 * 用户管理
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "UserController", description = "用户管理")
@RequestMapping("/api/app/")
public class UserController {

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户操作类
     */
    @Autowired
    private UserService userService;

    @Autowired
    private IndustryService industryService;

    /**
     * 平台邀请码
     */
    @Value("${platform.InviteCode}")
    private String platformInviteCode;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SpecialUserMapper specialUserMapper;


    /**
     * 用户通过手机号注册
     *
     * @param registerRequest 入参
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "用户通过手机号注册")
    @PostMapping("v2/user/register")
    public ResultInfo<?> userRegister(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest httpServletRequest)
            throws Exception {
<<<<<<< HEAD
        // 校验短信验证码
      /*  Object object = redisUtils.get(SMS_REGISTER_KEY + registerRequest.getMobileNumber());
        if (object == null || !registerRequest.getSmsCode().equalsIgnoreCase(String.valueOf(object))) {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码错误");
        }
        LOG.info("》》》验证码==" + object.toString());*/
        Boolean boolCode = platformInviteCode.equals(registerRequest.getInviteCode());
        //根用户如果是自己保存为 0
        Long rootId = (long) 0;
        Long inviteUserId = (long) 0;
        //邀请码转大写
        String registerInviteCode = registerRequest.getInviteCode().toUpperCase();
        //检查邀请码是否有效
        if (!boolCode) {
            User userInvite = userMapper.findByInviteCode(registerInviteCode);
            if (userInvite == null) {
                return new ResultInfo<>(ResultInfo.INVALID_PARAM, "无效邀请码");
            }
            rootId = userInvite.getFriendChainRootUserId();
            inviteUserId = userInvite.getId();
        } else {//查询是否存在特定用户
            SpecialUser specialUser = specialUserMapper.findOneByParam(new SpecialUser(registerRequest.getMobileNumber(), registerRequest.getInviteCode()));
            if (specialUser == null) {
                return new ResultInfo<>(ResultInfo.INVALID_PARAM, "无效邀请码");
            }
        }
        //查询用户是否注册成功
        User user = userMapper.findByMobileMumber(registerRequest.getMobileNumber());
        if (user != null && user.getRegisterStatus() == true) {
            LOG.error("》》》error====不可以重复注册");
            return new ResultInfo<>(ResultInfo.FAILURE, "用户已存在");
        }
        return userService.userRegister(registerRequest, rootId, inviteUserId, httpServletRequest);
=======
        parseBindingResult(bindingResult);
        LOG.info("》》》终端IP地址P==" + getRemortIP(httpServletRequest));
        registerRequest.setSpbillCreateIp(getRemortIP(httpServletRequest));
        //return userService.userRegister(registerRequest);
        return new ResultInfo<>(ResultInfo.FAILURE, "系统维护，暂停注册");
>>>>>>> 5a98d1b... 屏蔽注册功能
    }


    /**
     * 找回密码
     *
     * @param backPass
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "找回密码")
    @PostMapping("v2/user/back_password")
    public ResultInfo<?> backPassWord(@Valid @RequestBody GetBackPassRequest backPass, HttpServletRequest httpServletRequest) throws Exception {
        return userService.backPassWord(backPass, httpServletRequest);
    }

    /**
     * 修改交易密码
     *
     * @param backTransactionPassWordRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "修改交易密码")
    @PostMapping("v2/user/changeTransactionPassWord")
    public ResultInfo<?> changeTransactionPassWord(@Valid @RequestBody BackTransactionPassWordRequest backTransactionPassWordRequest) throws Exception {
        return userService.changeTransactionPassWord(backTransactionPassWordRequest);
    }

    /**
     *  验证支付密码
     *
     * @param checkTransactionPassWordRequest
     * @param 
     * @return
     * @throws Exception
     * @date 2018年9月13日
     * @author LuoFuMin
     */
    @ApiOperation(value = "验证支付密码",notes = "transactionPassWord ：交易密码、userid ： 用户id")
    @PostMapping("v2/user/checkTransactionPassWord")
    public ResultInfo<?> checkTransactionPassWord(@Valid @RequestBody CheckTransactionPassWordRequest checkTransactionPassWordRequest) throws Exception {
        return userService.checkTransactionPassWord(checkTransactionPassWordRequest);
    }

    /**
     * 修改密码
     *
     * @param request
     * @param 
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "修改密码")
    @PostMapping("v2/user/change_password")
    public ResultInfo<?> changePassWord(@Valid @RequestBody ChangePassWordRequest changePassWord, HttpServletRequest request ) throws Exception {
       
        return userService.changePassWord(changePassWord, request);
    }

    /**
     * 用户登录
     *
     * @param loginRequest  入参
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("v2/user/login")
    public ResultInfo<?> userLogin(@Valid @RequestBody LoginRequest loginRequest,  HttpServletRequest httpServletRequest)
            throws Exception {
       
        //LOG.info("====终端IP地址--getRemortIP====" + getRemortIP(httpServletRequest));
        loginRequest.setSpbillCreateIp(getRemortIP(httpServletRequest));
        return userService.userLogin(loginRequest, httpServletRequest);
    }


    /**
     * 获取用户信息
     *
     * @param userIdRequest
     * @param 
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取用户信息")
    @PostMapping("v2/user/get_msg")
    public ResultInfo<?> getUserInfo(@Valid @RequestBody UserIdRequest userIdRequest, HttpServletRequest httpServletRequest )
            throws Exception {
       
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userService.getUserMsg(Long.valueOf(userIdRequest.getUserid()), httpServletRequest));
    }

    /**
     * 上传头像
     *
     * @param userid 用户id
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年3月7日
     * @author LuoFuMin
     */
    @ApiOperation(value = "上传头像")
    @PostMapping("v2/user/pull/avatar")
    public ResultInfo<?> avatarUpload(@RequestParam(name = "file") MultipartFile multipartFile, @RequestParam(name = "userid") Long userid, HttpServletRequest httpServletRequest)
            throws Exception {
        ResultInfo<?> x = ImageUtils.checkImage(multipartFile);
        if (x != null) {
            return x;
        }
        return userService.avatarUpload(multipartFile, userid, httpServletRequest);
    }

    /**
     * 修改用户信息
     *
     * @param updateUserInfoRequest
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "修改用户信息")
    @PostMapping("v2/user/update/info")
    public ResultInfo<?> updateUserInfo(@Valid @RequestBody UpdateUserInfoRequest updateUserInfoRequest, HttpServletRequest httpServletRequest )
            throws Exception {
       
        return userService.UpdateUserInfo(updateUserInfoRequest, httpServletRequest);
    }

    /**
     * 获取用户资产
     *
     * @param userIdRequest 用户id
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取用户资产")
    @PostMapping("v2/user/property")
    public ResultInfo<?> findUserProperty(@Valid @RequestBody UserIdRequest userIdRequest ) throws Exception {
       
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userService.findUserProperty(Long.valueOf(userIdRequest.getUserid())));
    }

    /**
     * 获得用户的邀请码等参数
     *
     * @param userIdRequest 用户ID
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获得用户的邀请码等参数")
    @PostMapping("v2/user/invitation")
    public ResultInfo<?> getInviteCodeInfo(@Valid @RequestBody UserIdRequest userIdRequest ) throws Exception {
       
        return userService.getInviteCodeInfo(Long.valueOf(userIdRequest.getUserid()));
    }

    /**
     * 获取全部麦粒排行
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取全部麦粒排行", notes = "获取麦粒排行")
    @PostMapping("v2/user/oreRankingAll")
    public ResultInfo<?> findUserOreList(@Valid @RequestBody Page_Request request) throws Exception {
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userService.findUserOreList(request));
    }

    /**
     * 获取朋友圈麦粒排行
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取朋友圈麦粒排行", notes = "获取麦粒排行")
    @PostMapping("v2/user/oreRankingFriends")
    public ResultInfo<?> findOreRankingFriends(@Valid @RequestBody Page_Request request) throws Exception {
        return userService.findOreRankingFriends(request);
    }

    /**
     * 一键签到
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "一键签到", notes = "userid:用户id")
    @PostMapping("v2/user/click/signIn")
    public ResultInfo<?> clickSignIn(@Valid @RequestBody UserIdRequest request) throws Exception {
       
        return userService.clickSignIn(request);
    }

    /**
     * 麦粒收支明细
     *
     * @param pageRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "麦粒收支明细", notes = "page:起始页, size: 一页的数量, userid:用户id")
    @PostMapping("v2/user/get/hxl/transaction")
    public ResultInfo<?> findByUserId(@Valid @RequestBody Page_Request pageRequest ) throws Exception {
       
        return userService.getHxlTransaction(pageRequest);
    }


    /**
     * 查询邀请的好友
     *
     * @param inviteCodeRequest
     * @param 
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询邀请的好友", notes = "page:起始页, size: 一页的数量")
    @PostMapping("v2/user/get/invitation/friends")
    public ResultInfo<?> getInvitationFriends(@Valid @RequestBody InviteCodeRequest inviteCodeRequest ) throws Exception {
       
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userService.getInvitationFriends(inviteCodeRequest));
    }


    /**
     * 获取用户二维码
     *
     * @param userid   用户id
     * @param response
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取用户二维码")
    @GetMapping("v2/user/QR_code/{userid}")
    public void QRCode(@PathVariable(name = "userid") Long userid, HttpServletResponse response) throws Exception {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        User user = userService.findUserByUserId(userid);
        JSONObject jsonData = new JSONObject();
        if (user != null) {
            jsonData.put("inviteCode", user.getInviteCode());
        }
        QRCodeUtils.writeToStream(jsonData.toJSONString(), "jpg", response.getOutputStream());
        //QRCodeUtils.encode(data,300,300, user.getAvatar(), response.getOutputStream());
    }


    /**
     * 查询可以邀请的通讯录列表
     *
     * @param map
     * @param 
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询可以邀请的通讯录列表")
    @PostMapping("v2/user/getInvitationList")
    public ResultInfo<?> getInvitationList(@RequestBody Map<String, Object> map) throws Exception {
       
        return userService.getInvitationList(map);
    }


    /**
     * 根据邀请码添加好友
     *
     * @param map
     * @param 
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "根据邀请码添加好友")
    @PostMapping("v2/user/invitationAddFriends")
    public ResultInfo<?> invitationAddFriends(@RequestBody Map<String, Object> map ) throws Exception {
       
        return userService.invitationAddFriends(map);
    }


    /**
     * 添加或者拒绝好友添加用户
     *
     * @param map
     * @param 
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询可以邀请的通讯录列表")
    @PostMapping("v2/user/addFriends")
    public ResultInfo<?> addFriends(@RequestBody Map<String, Object> map) throws Exception {
       
        return userService.addFriends(map);
    }

    /**
     * 行业类型列表
     *
     * @param map
     * @param 
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "行业类型列表")
    @PostMapping("v2/user/getIndustryList")
    public ResultInfo<?> getIndustryList(@RequestBody Map<String, Object> map ) throws Exception {
       
        return industryService.getIndustryList(map);
    }


    /**
     * 查询邀请的所有好友
     *
     * @param inviteCode
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询邀请的所有好友")
    @PostMapping("v2/user/findChildrens")
    public ResultInfo<?> findChildrens(String inviteCode) throws Exception {
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userService.findChildrens(inviteCode));
    }

    /**
     * 添加银行卡
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年7月5日
     * @author LuoFuMin
     */
    @ApiOperation(value = "添加银行卡")
    @PostMapping("v2/user/addBank")
    public ResultInfo<?> addBack(@Valid @RequestBody AddBackRequest request) throws Exception {
       
        Boolean bool = userService.addBack(request);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 获取银行卡
     *
     * @return
     * @throws Exception
     * @date 2018年7月5日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取银行卡")
    @PostMapping("v2/user/getBank")
    public ResultInfo<?> getBack(@Valid @RequestBody UserIdRequest userIdRequest) throws Exception {
        List<BankResponse> list = userService.getBank(Long.valueOf(userIdRequest.getUserid()));
<<<<<<< HEAD
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
=======
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS,list);
    }


/*****************************************************************************************************************************************************/


    /**
     * 用户通过手机号注册
     *
     * @param registerRequest 入参
     * @param bindingResult   参数校验结果
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "用户通过手机号注册")
    @PostMapping("v1/user/register")
    public ResultInfo<?> userRegisterV1(@Valid @RequestBody RegisterRequest_v1 registerRequest, BindingResult bindingResult)
            throws Exception {
        parseBindingResult(bindingResult);
        //return userService.userRegisterV1(registerRequest);
        return new ResultInfo<>(ResultInfo.FAILURE, "系统维护，暂停注册");
>>>>>>> 5a98d1b... 屏蔽注册功能
    }



}
