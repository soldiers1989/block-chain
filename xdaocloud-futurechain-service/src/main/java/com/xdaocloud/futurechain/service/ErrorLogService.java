package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ErrorLogService {


    /**
     * 上传错误日志
     *
     * @param logFile            log文件
     * @param userId             用户id
     * @param appVersion         App 版本
     * @param phoneSystemVersion 手机系统版本
     * @param phoneModel         手机版本
     * @param phoneCompany       手机制造商
     * @return
     */
    ResultInfo<?> pushErrorLog(MultipartFile logFile, Long userId, String appVersion, String phoneSystemVersion, String phoneModel, String phoneCompany) throws IOException;

    /**
     * 查询错误日志
     *
     * @param request
     * @return
     */
    ResultInfo<?> getErrorLog(PageBase_Request request);
}
