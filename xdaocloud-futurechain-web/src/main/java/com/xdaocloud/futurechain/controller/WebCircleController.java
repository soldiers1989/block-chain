package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.circle.*;
import com.xdaocloud.futurechain.service.CircleService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Administrator on 2018/7/10.
 */

@RestController("WebCircle")
@RequestMapping("/api/web/")
public class WebCircleController {

    private static Logger LOG = LoggerFactory.getLogger(WebCircleController.class);

    @Autowired
    private CircleService circleService;


    @ApiOperation(value = "文章列表")
    @PostMapping("v2/circle/getCircleList")
    @RequiresPermissions("maichain_circle_circlelist")
    public ResultInfo<?> getCircleList(@Valid @RequestBody WebCircleListRequest webCircleListRequest ) throws Exception {
        return circleService.webGetCircleList(webCircleListRequest);
    }

    @ApiOperation(value = "文章详情")
    @PostMapping("v2/circle/getWebCircleDetails")
    @RequiresPermissions("maichain_circle_webcircle_details")
    public ResultInfo<?> getWebCircleDetails(@Valid @RequestBody WebCircleRequest map) throws Exception {
        return circleService.getCircleDetails(map);
    }


    @ApiOperation(value = "文章详情--评论")
    @PostMapping("v2/circle/getWebCircleComment")
    @RequiresPermissions("maichain_circle_webcircle_comment")
    public ResultInfo<?> getWebCircleComment(@Valid @RequestBody webCircleCommentRequest map) throws Exception {
        return circleService.webDetailsComment(map);
    }


    @ApiOperation(value = "文章操作")
    @PostMapping("v2/circle/updateWebCircle")
    @RequiresPermissions("maichain_circle_update_webcircle")
    public ResultInfo<?> updateWebCircle(@Valid@RequestBody webCircleOperationRequest map) throws Exception {
        return circleService.updateCircle(map);
    }


    @ApiOperation(value = "审核开关列表")
    @GetMapping("v2/circle/getSystemSetup")
    @RequiresPermissions("maichain_circle_system_setup")
    public ResultInfo<?> getSystemSetup() throws Exception {
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS,circleService.getSystemSetup());
    }

    @ApiOperation(value = "修改审核开关")
    @PostMapping("v2/circle/updateSystemSetup")
    @RequiresPermissions("maichain_circle_update_system_setup")
    public ResultInfo<?> updateSystemSetup(@Valid @RequestBody WebSystemSetupRequest map) throws Exception {
        int st = circleService.updateSystemSetup(map);
        if(st == 1 ){
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }
        return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_INVALID_PARAM);
    }
}
