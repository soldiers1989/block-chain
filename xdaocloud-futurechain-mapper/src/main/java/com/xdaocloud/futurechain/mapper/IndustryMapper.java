package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Industry;

import java.util.List;
import java.util.Map;

public interface IndustryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Industry record);

    int insertSelective(Industry record);

    Industry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Industry record);

    int updateByPrimaryKey(Industry record);

    List<Industry> getIndustryList();

    List<Map<Object, Object>> getUserIndustryList();
}