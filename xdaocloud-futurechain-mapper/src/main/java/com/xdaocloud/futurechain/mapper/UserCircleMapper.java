package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.UserCircle;
import org.apache.ibatis.annotations.Param;

public interface UserCircleMapper {
    int insert(UserCircle record);

    int insertSelective(UserCircle record);

    int findByUserIdAndCircleId(@Param("userId") Long userId, @Param("circleId") Long circleId);

    int deleteByUserIdAndCircleId(@Param("userId") Long userId, @Param("circleId") Long circleId);
}