package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.user.BankResponse;
import com.xdaocloud.futurechain.model.BankCard;

import java.util.List;

public interface BankCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BankCard record);

    int insertSelective(BankCard record);

    BankCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankCard record);

    int updateByPrimaryKey(BankCard record);


    BankCard findOneByUserId(Long userId);



    List<BankResponse> findListByUserId(Long userId);
}