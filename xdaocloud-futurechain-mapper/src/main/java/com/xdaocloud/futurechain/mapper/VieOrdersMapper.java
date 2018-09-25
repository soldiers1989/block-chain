package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.VieOrders;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface VieOrdersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VieOrders record);

    int insertSelective(VieOrders record);

    VieOrders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VieOrders record);

    int updateByPrimaryKey(VieOrders record);

    Integer findCountById(Long matchId);

    Integer findCountByIds(@Param("friends") List<Long> friends, @Param("matchId") Long matchId);

    BigDecimal findSumByMatchId(@Param("matchId") Long matchId);

    List<VieOrders> findListByUserId(@Param("userId") Long userId);

    List<VieOrders> findListByState(@Param("state") int state);

    List<VieOrders> findListByMatchIdAndVorl(@Param("matchId") Long matchId, @Param("vorl") int vorl);

    BigDecimal findSumByMatchIdAndVorl(@Param("matchId") Long matchId, @Param("vorl") int vorl);

    List<VieOrders> findListByFootballMatchId(@Param("footballMatchId")long footballMatchId);
}