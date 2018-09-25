package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Delay;

public interface DelayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Delay record);

    int insertSelective(Delay record);

    Delay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Delay record);

    int updateByPrimaryKey(Delay record);
}