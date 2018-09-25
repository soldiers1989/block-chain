package com.xdaocloud.futurechain.feignapi;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.request.uc.*;
import com.xdaocloud.futurechain.response.uc.UCLoginResponse;
import com.xdaocloud.futurechain.response.uc.UCUserDetailResponse;
import com.xdaocloud.futurechain.response.uc.UCUserResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;


/**
 * 用户 接口
 *
 * @author LuoFuMin
 * @data 2018/9/3
 */
@FeignClient(value = "uc", fallback = FeignConfig.class)
public interface UserCenterService {

    /**
     * 获取个人信息
     *
     * @param id 用户id
     * @return UCUserDetailResponse
     * @author LuoFuMin
     * @date 2018/9/10
     */
    @RequestLine("GET /api/auth/v1/user/{id}")
    ResultInfo<UCUserDetailResponse> getUserInfo(@Param(value = "id") Long id);

    /**
     * 注册
     *
     * @param registerRequest
     * @return UCUserResponse
     * @author LuoFuMin
     * @date 2018/9/10
     */

    @RequestLine("POST /api/v1/user/register")
    @Headers("Content-Type: application/json")
    ResultInfo<UCUserResponse> userRegister(UCRegisterRequest registerRequest);

    /**
     * 登录
     *
     * @param loginRequest
     * @return UCLoginResponse
     * @author LuoFuMin
     * @date 2018/9/10
     */
    @RequestLine("POST api/v1/user/login")
    @Headers("Content-Type: application/json")
    ResultInfo<UCLoginResponse> login(UCLoginRequest loginRequest);

    /**
     * 修改用户信息
     *
     * @param ucUpdateUserRequest
     * @return ?
     * @author LuoFuMin
     * @date 2018/9/10
     */
    @RequestLine("POST api/auth/v1/user/profile")
    @Headers("Content-Type: application/json")
    ResultInfo<?> profile(UCUpdateUserRequest ucUpdateUserRequest);

    /**
     * 上传头像
     *
     * @param ucPushAvatarRequest
     * @return ?
     * @author LuoFuMin
     * @date 2018/9/10
     */
    @RequestLine("POST api/auth/v1/user/push-avatar")
    @Headers("Content-Type: application/json")
    ResultInfo<?> pushAvatar(UCPushAvatarRequest ucPushAvatarRequest);


    /**
     * 重置密码
     *
     * @param ucResetUserPwdRequest
     * @return ?
     * @author LuoFuMin
     * @date 2018/9/10
     */
    @RequestLine("POST api/auth/v1/user/resetpwd")
    @Headers("Content-Type: application/json")
    ResultInfo<?> resetpwd(UCResetUserPwdRequest ucResetUserPwdRequest);

    /**
     * 修改密码
     *
     * @param ucUpdatePwdRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/10
     */
    @RequestLine("POST  api/auth/v1/user/pwd")
    @Headers("Content-Type: application/json")
    ResultInfo<?> pwd(UCUpdatePwdRequest ucUpdatePwdRequest);


}
