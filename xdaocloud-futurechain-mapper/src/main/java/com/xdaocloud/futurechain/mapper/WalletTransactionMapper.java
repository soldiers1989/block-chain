package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.WalletTransaction;

public interface WalletTransactionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WalletTransaction record);

    int insertSelective(WalletTransaction record);

    WalletTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WalletTransaction record);

    int updateByPrimaryKey(WalletTransaction record);
}