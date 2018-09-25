package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Version;
import org.apache.ibatis.annotations.Param;

public interface VersionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKey(Version record);

    Version findByClientType(@Param("clientType") String clientType);
}