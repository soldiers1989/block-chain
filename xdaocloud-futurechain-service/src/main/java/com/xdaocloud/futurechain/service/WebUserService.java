package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.resp.user.WebUserLoginResponse;

/**
 * web用户管理
 *
 * @author LuoFuMin
 * @data 2018/7/13
 */
public interface WebUserService {

    /**
     * 登录
     *
     * @param username 用户名，password 密码
     * @return
     * @author LuoFuMIn
     * @date 2018/7/19
     */
    WebUserLoginResponse doLoin(String username, String password);

    /**
     * 修改密码
     *
     * @param request
     * @return ResultInfo
     * @author LuoFuMIn
     * @date 2018/7/19
     */
    ResultInfo<?> updatePwd(String userId, String password, String oldPassword,String token);
}
