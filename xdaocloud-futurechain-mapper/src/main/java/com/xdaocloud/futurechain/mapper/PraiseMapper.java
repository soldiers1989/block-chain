package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Praise;
import org.apache.ibatis.annotations.Param;

public interface PraiseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Praise record);

    int insertSelective(Praise record);

    Praise selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Praise record);

    int updateByPrimaryKey(Praise record);

    int selectByUserIdAndCircleId(@Param("userId") Long userId, @Param("circleId") Long circleId);

    void updateByUserIdAndCircleId(@Param("userId") Long userId, @Param("circleId") Long circleId);

    /**
     * 根据文章ID获取总点赞数
     * @param circleId
     * @return
     */
    int selectCountByCircleId(@Param("circleId")Long circleId);
}