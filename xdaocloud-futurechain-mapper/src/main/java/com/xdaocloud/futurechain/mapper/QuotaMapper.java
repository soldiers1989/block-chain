package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Quota;

public interface QuotaMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(Quota record);

    int insertSelective(Quota record);

    Quota selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(Quota record);

    int updateByPrimaryKey(Quota record);

}