package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.AuditSwitch;

public interface AuditSwitchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuditSwitch record);

    int insertSelective(AuditSwitch record);

    AuditSwitch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuditSwitch record);

    int updateByPrimaryKey(AuditSwitch record);
}