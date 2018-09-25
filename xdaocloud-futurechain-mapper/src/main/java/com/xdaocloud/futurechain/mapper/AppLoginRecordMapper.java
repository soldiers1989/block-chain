package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.AppLoginRecord;

public interface AppLoginRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppLoginRecord record);

    int insertSelective(AppLoginRecord record);

    AppLoginRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppLoginRecord record);

    int updateByPrimaryKey(AppLoginRecord record);
}