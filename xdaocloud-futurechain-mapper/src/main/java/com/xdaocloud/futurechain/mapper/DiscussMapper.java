package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Discuss;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DiscussMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Discuss record);

    int insertSelective(Discuss record);

    Discuss selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Discuss record);

    int updateByPrimaryKey(Discuss record);

    int selectByCircleIdCount(@Param("circleId") long circleId);

    List<Map<String,Object>> selectByCircleIdList(@Param("circleId")long circleId, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

}