package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.ore.OreRankingDTO;
import com.xdaocloud.futurechain.model.OreTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OreTransactionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OreTransaction record);

    int insertSelective(OreTransaction record);

    OreTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OreTransaction record);

    int updateByPrimaryKey(OreTransaction record);

    /**
     * 查询一周内矿石 前10名的排行
     *
     * @return
     */
    List<OreRankingDTO> findOreRanking();


    List<OreTransaction> findByUserId(@Param("userId") Long userId);

}