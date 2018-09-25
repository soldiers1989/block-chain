package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.FriendPhone;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FriendPhoneMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FriendPhone record);

    int insertSelective(FriendPhone record);

    FriendPhone selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FriendPhone record);

    int updateByPrimaryKey(FriendPhone record);

    List<String> selectByPhoneList(@Param("mobileList") List<Map<String, Object>> mobileList);
}