package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.IndustryType;

import java.util.List;
import java.util.Map;

public interface IndustryTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IndustryType record);

    int insertSelective(IndustryType record);

    IndustryType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IndustryType record);

    int updateByPrimaryKey(IndustryType record);

    List<IndustryType> selectAll();
    
    /**
     * 
     * 获取朋友圈类型列表
     * @return 
     * @date 2018年6月28日
     * @author dql
     */
    List<Map<Object, Object>> getIndustryList();

}