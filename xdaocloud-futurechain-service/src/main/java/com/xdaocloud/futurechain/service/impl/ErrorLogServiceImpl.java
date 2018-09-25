package com.xdaocloud.futurechain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdaocloud.futurechain.common.QiniuConfig;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.mapper.ErrorLogMapper;
import com.xdaocloud.futurechain.model.ErrorLog;
import com.xdaocloud.futurechain.service.ErrorLogService;
import com.xdaocloud.futurechain.util.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    /**
     * 七牛云储存配置
     */
    @Autowired
    private QiniuConfig qiniuConfig;


    @Autowired
    private ErrorLogMapper errorLogMapper;

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
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> pushErrorLog(MultipartFile logFile, Long userId, String appVersion, String phoneSystemVersion, String phoneModel, String phoneCompany) throws IOException {
        String path = QiNiuUtils.pushFile(logFile, qiniuConfig);
        errorLogMapper.insertSelective(new ErrorLog(userId, path, appVersion, phoneSystemVersion, phoneModel, phoneCompany));
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 查询错误日志
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    public ResultInfo<?> getErrorLog(PageBase_Request request) {

        PageHelper.startPage(request.getPage(), request.getSize(), "id DESC");
        List<ErrorLog> list = errorLogMapper.findListByParam(request.getCondition());
        PageInfo<ErrorLog> pageInfo = new PageInfo<ErrorLog>(list);
        PageResponse response = new PageResponse(pageInfo);
        return new ResultInfo<>(ResultInfo.SUCCESS,ResultInfo.MSG_SUCCESS,response);
    }
}
