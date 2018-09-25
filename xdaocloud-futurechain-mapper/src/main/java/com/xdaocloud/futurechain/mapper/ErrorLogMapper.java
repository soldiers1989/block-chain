package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.ErrorLog;

import java.util.List;

public interface ErrorLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ErrorLog record);

    int insertSelective(ErrorLog record);

    ErrorLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErrorLog record);

    int updateByPrimaryKey(ErrorLog record);

    List<ErrorLog> findListByParam(String condition);
}