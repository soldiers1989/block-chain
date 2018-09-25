package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Report;

public interface ReportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
}