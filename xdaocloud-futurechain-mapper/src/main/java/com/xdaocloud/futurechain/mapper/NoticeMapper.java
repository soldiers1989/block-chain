package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.notice.NoticeDTO;
import com.xdaocloud.futurechain.model.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    /**
     * 获取公告列表
     *
     * @return
     */
    List<NoticeDTO> selectNoticeList();
}