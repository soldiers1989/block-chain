package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.PutMoney;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface PutMoneyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PutMoney record);

    int insertSelective(PutMoney record);

    PutMoney selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PutMoney record);

    int updateByPrimaryKey(PutMoney record);

    BigDecimal findSumDevoteRmb(@Param("userId") Long userId,@Param("downline") int downline);
}