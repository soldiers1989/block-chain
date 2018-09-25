package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.FootballMatch;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FootballMatchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FootballMatch record);

    int insertSelective(FootballMatch record);

    FootballMatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FootballMatch record);

    int updateByPrimaryKey(FootballMatch record);

    List<FootballMatch> findListByType(@Param("type") int type);

    List<FootballMatch> findListByTypeAndStartTime(@Param("type") int type, @Param("startTime") Date startTime);

    void addFootballMatch(@Param("map") Map<String, Object> map);

    List<Map<String,Object>> footballMatchList();

    void updateByFootballMatchId(@Param("footballMatchId")long footballMatchId,@Param("win") int win,@Param("endTime")String endTime,@Param("scoreA") int scoreA,@Param("scoreB") int scoreB);
    void updateConcede(@Param("concede")int concede,@Param("teamMemberId")long teamMemberId);
}