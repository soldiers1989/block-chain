package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.FootballTeam;

import java.util.List;
import java.util.Map;

public interface FootballTeamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FootballTeam record);

    int insertSelective(FootballTeam record);

    FootballTeam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FootballTeam record);

    int updateByPrimaryKey(FootballTeam record);

    List<Map<String,Object>> footballTeamList();
}