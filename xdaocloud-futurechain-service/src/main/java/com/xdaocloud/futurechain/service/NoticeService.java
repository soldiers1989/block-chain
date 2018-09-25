package com.xdaocloud.futurechain.service;

import com.xdaocloud.futurechain.dto.resp.notice.NoticeDTO;

import java.util.List;

/**
 * 公告接口
 *
 * @author lixuzhou@foxmail.com
 * @version 0.1 2018/3/7
 */
public interface NoticeService {

    /**
     * 获取公告列表
     *
     * @return
     */
    List<NoticeDTO> getNoticeList();
}
