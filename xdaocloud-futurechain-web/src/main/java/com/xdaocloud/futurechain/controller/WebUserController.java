package com.xdaocloud.futurechain.controller;


import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.user.ToExamineRequest;
import com.xdaocloud.futurechain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 用户管理
 *
 * @author LuoFuMin
 * @date 2018年6月28日
 */

@RestController
@RequestMapping("/api/web")
@Api(value = "WebUserController", description = "用户管理")
public class WebUserController {


    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取用户信息")
    @PostMapping("v2/user/get/users")
    @RequiresPermissions("maichain_user_getlist")
    public ResultInfo<?> getUsers(@Valid @RequestBody PageBase_Request pageBaseRequest)
            throws Exception {
        if (pageBaseRequest.getPage() >= 1) {
            pageBaseRequest.setPage(pageBaseRequest.getPage() - 1);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userService.getUsers(pageBaseRequest));
    }

    /**
     * 获取代理商信息
     *
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取代理商信息")
    @PostMapping("v2/user/get/usersByAgent")
    @RequiresPermissions("maichain_user_agent")
    public ResultInfo<?> getUsersByAgent(@Valid @RequestBody PageBase_Request pageBaseRequest)
            throws Exception {
        if (pageBaseRequest.getPage() >= 1) {
            pageBaseRequest.setPage(pageBaseRequest.getPage() - 1);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userService.getUsersByAgent(pageBaseRequest));
    }

    /**
     * 审核代理商
     * 0-代理商申请拒绝,1-成为代理商,2-审核中,3-初始默认状态,4-成为代理候选人
     *
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @ApiOperation(value = "审核代理商")
    @PostMapping("v2/user/toExamine")
    @RequiresPermissions("maichain_user_toexamine")
    public ResultInfo<?> toExamine(@Valid @RequestBody ToExamineRequest request)
            throws Exception {
        if (request.getExecute() != 1 && request.getExecute() != 0) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, ResultInfo.MSG_INVALID_PARAM);
        }
        Boolean bool = userService.toExamine(request);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


}
