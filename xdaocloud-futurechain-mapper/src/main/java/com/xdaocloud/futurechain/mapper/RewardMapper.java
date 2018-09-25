package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Reward;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RewardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Reward record);

    int insertSelective(Reward record);

    Reward selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Reward record);

    int updateByPrimaryKey(Reward record);

    Reward findOneByType(@Param("type") Integer type);

    List<Reward> findAllList();
}