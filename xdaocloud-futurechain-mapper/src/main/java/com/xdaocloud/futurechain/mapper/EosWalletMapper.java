package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.EosWallet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EosWalletMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EosWallet record);

    int insertSelective(EosWallet record);

    EosWallet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EosWallet record);

    int updateByPrimaryKeyWithBLOBs(EosWallet record);

    int updateByPrimaryKey(EosWallet record);

    Integer findCountByParam(EosWallet record);

    EosWallet findOneByParam(EosWallet record);

    String findWalletNameByUserId(@Param("userId") Long userId);

    EosWallet findOneByUserId(@Param("userId") Long userId);


    String findWalletName(@Param("walletName") String walletName);

    List<EosWallet> findAll();


    List<EosWallet> findMovenWalletByIsDelete(Boolean bool);

    EosWallet findOneByWalletName(@Param("walletName") String walletName);
}