package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.service.ErrorLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * 错误日志控制类
 */
@RestController
@RequestMapping("/api/app/")
public class ErrorLogController {


    @Autowired
    private ErrorLogService errorLogService;


    /**
     * 上传错误日志
     *
     * @param logFile            log文件
     * @param userid             用户id
     * @param appVersion         App 版本
     * @param phoneSystemVersion 手机系统版本
     * @param phoneModel         手机版本
     * @param phoneCompany       手机制造商
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "创建群")
    @PostMapping("v2/errorLog/push")
    public ResultInfo<?> pushErrorLog(@RequestParam(name = "logFile", required = false) MultipartFile logFile, @RequestParam(name = "userid") Long userid, String appVersion, String phoneSystemVersion, String phoneModel, String phoneCompany) throws IOException {
        return errorLogService.pushErrorLog(logFile, userid, appVersion, phoneSystemVersion, phoneModel, phoneCompany);
    }


    /**
     * 查询错误日志
     *
     * @param request
     * @return
     * @throws IOException
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询错误日志")
    @PostMapping("v2/errorLog/getList")
    public ResultInfo<?> getErrorLog(@Valid @RequestBody PageBase_Request request) throws IOException {
        return errorLogService.getErrorLog(request);
    }

}
