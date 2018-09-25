package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.LoseInterested;
import org.apache.ibatis.annotations.Param;

public interface LoseInterestedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoseInterested record);

    int insertSelective(LoseInterested record);

    LoseInterested selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoseInterested record);

    int updateByPrimaryKey(LoseInterested record);

    int selectByUserIdAndCircleId(@Param("userId") long userId, @Param("circleId")long circleId);
}