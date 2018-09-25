package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Feedback;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);
}