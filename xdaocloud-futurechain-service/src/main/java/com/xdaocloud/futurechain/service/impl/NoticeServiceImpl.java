package com.xdaocloud.futurechain.service.impl;

import com.xdaocloud.futurechain.dto.resp.notice.NoticeDTO;
import com.xdaocloud.futurechain.mapper.NoticeMapper;
import com.xdaocloud.futurechain.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 公告接口实现
 *
 * @author lixuzhou@foxmail.com
 * @version 0.1 2018/3/7
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @SuppressWarnings("unused")
    private static Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 获取公告列表
     *
     * @return
     */
    @Override
    public List<NoticeDTO> getNoticeList() {
        List<NoticeDTO> list = noticeMapper.selectNoticeList();
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }
}
