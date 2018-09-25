package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Wallet;

public interface WalletMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);
}