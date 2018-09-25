package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.FootballTeamMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FootballTeamMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FootballTeamMember record);

    int insertSelective(FootballTeamMember record);

    FootballTeamMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FootballTeamMember record);

    int updateByPrimaryKey(FootballTeamMember record);

    List<FootballTeamMember> findListByTeamId(@Param("teamId") Long teamId);

    List<Map<String,Object>> footballTeamMemberList(@Param("footballTeamId") Long id);

   void deleteTeamMember(@Param("teamMemberId")long teamMemberId, @Param("footballTeamId")long footballTeamId);


}