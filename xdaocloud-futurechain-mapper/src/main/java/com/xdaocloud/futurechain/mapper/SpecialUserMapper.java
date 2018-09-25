package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.SpecialUser;

public interface SpecialUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpecialUser record);

    int insertSelective(SpecialUser record);

    SpecialUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpecialUser record);

    int updateByPrimaryKey(SpecialUser record);

    SpecialUser findOneByParam(SpecialUser specialUser);
}