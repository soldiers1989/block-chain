package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.SystemSetup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemSetupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemSetup record);

    int insertSelective(SystemSetup record);

    SystemSetup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemSetup record);

    int updateByPrimaryKey(SystemSetup record);

    int selectByKeyName(@Param("auditSwitch") String auditSwitch);

    List<Map<String,Object>> selectSystemSetup();
}