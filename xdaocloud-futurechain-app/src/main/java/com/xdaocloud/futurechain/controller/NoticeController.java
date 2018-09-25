package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.resp.notice.NoticeDTO;
import com.xdaocloud.futurechain.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公告控制类
 *
 * @author lixuzhou@foxmail.com
 * @version 0.1 2018/3/7
 */
@RestController
@Api(value = "NoticeController", description="公告控制类")
@RequestMapping("/api/app/")
public class NoticeController {

    @SuppressWarnings("unused")
    private static Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    /**
     * 获取公告列表
     *
     * @return
     */
    @ApiOperation(value = "获取公告列表")
    @GetMapping("v2/notice/")
    public ResultInfo<?> getNoticeList() {
        List<NoticeDTO> list = noticeService.getNoticeList();
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /************************************************************************************************************************************/


    /**
     * 获取公告列表
     *
     * @return
     */
    @ApiOperation(value = "获取公告列表")
    @GetMapping("v1/notice/")
    public ResultInfo<?> getNoticeListV1() {
        List<NoticeDTO> list = noticeService.getNoticeList();
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }
}
