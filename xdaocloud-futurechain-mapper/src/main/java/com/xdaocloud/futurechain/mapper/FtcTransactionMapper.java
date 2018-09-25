package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.ore.FtcRankingDTO;
import com.xdaocloud.futurechain.model.FtcTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FtcTransactionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FtcTransaction record);

    int insertSelective(FtcTransaction record);

    FtcTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FtcTransaction record);

    int updateByPrimaryKey(FtcTransaction record);

    /**
     * 查询交易记录
     *
     * @param address 钱包地址

     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<FtcTransaction> findByAddress(@Param("address") String address);


    /**
     * 查询一周内ftc 前10名的排行
     *
     * @return
     */
    List<FtcRankingDTO> findFtcRanking();



}