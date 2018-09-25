package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Address;
import org.apache.ibatis.annotations.Param;
import retrofit2.http.POST;

import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);


    int updateAllChoiceByUserIdAndAppId(@Param("userId") Long userId, @Param("appId") String appId);

    List<Address> findListByUserIdAndAppId(@Param("userId") Long userId, @Param("appId") String appId);

    Address findDefault(Long userid);
}