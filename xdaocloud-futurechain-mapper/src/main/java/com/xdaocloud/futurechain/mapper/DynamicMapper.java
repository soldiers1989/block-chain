package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Dynamic;

public interface DynamicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dynamic record);

    int insertSelective(Dynamic record);

    Dynamic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dynamic record);

    int updateByPrimaryKeyWithBLOBs(Dynamic record);

    int updateByPrimaryKey(Dynamic record);
}