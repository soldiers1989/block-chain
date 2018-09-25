package com.xdaocloud.futurechain.controller;


import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.feignapi.UserCenterService;
import com.xdaocloud.futurechain.dto.resp.VersionResponse;
import com.xdaocloud.futurechain.mapper.VersionMapper;
import com.xdaocloud.futurechain.model.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 版本升级
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "VersionController", description = "版本升级")
@RequestMapping("/api/app/")
public class VersionController {

    @Autowired
    VersionMapper versionMapper;
    @Autowired
    UserCenterService userCenterService;


    /**
     * 查询最新版本
     *
     * @param clientType 客户端类型（ios ，android）
     * @param response
     * @return
     * @throws IOException
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询最新版本")
    @GetMapping("v2/version/update/{clientType}")
    public ResultInfo<?> checkVersion(@PathVariable(name = "clientType") String clientType, HttpServletResponse response) throws IOException, InterruptedException {
        VersionResponse versionResponse = getVersionResponse(clientType);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, versionResponse);
    }


    /**
     * 查询最新版本
     *
     * @param clientType（ios ，android）
     * @param response
     * @return
     * @throws IOException
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询最新版本")
    @GetMapping("v1/version/update/{clientType}")
    public ResultInfo<?> checkVersionV1(@PathVariable(name = "clientType") String clientType, HttpServletResponse response) throws IOException {
        VersionResponse versionResponse = getVersionResponse(clientType);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, versionResponse);
    }


    /**
     * 获取版本信息
     *
     * @param clientType
     * @return
     */
    private VersionResponse getVersionResponse(String clientType) {
        Version version = versionMapper.findByClientType(clientType);
        VersionResponse versionResponse = new VersionResponse();
        versionResponse.setVersionCode(version.getVersionCode());
        versionResponse.setVersionName(version.getVersionName());
        versionResponse.setClientType(version.getClientType());
        versionResponse.setDownloadUrl(version.getDownloadUrl());
        versionResponse.setUpgradeMsg(version.getUpgradeMsg());
        versionResponse.setForce(version.getIsForce());
        return versionResponse;
    }

}
