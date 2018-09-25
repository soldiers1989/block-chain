package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.EthereumWallet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EthereumWalletMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EthereumWallet record);

    int insertSelective(EthereumWallet record);

    EthereumWallet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EthereumWallet record);

    int updateByPrimaryKey(EthereumWallet record);

    /**
     * 根据用户id 查询钱包文件保存路径
     * @param userid 用户id
     * @return
     */
    List<String> findWalletFilePathByUserId(@Param("userid") Long userid);

    /**
     * 根据钱包地址查询 钱包文件路径
     * @param walletAddress 钱包地址
     * @return
     */
    String findWalletFilePathByWalletAddress(@Param("walletAddress") String walletAddress);

    /**
     * 根据用户id查询 钱包地址
     * @param userid
     * @return
     */
    String findWalletAddressByUserid(@Param("userid") Long userid);

    /**
     * 查询用户拥有钱包数量
     * @param userid 用户id
     * @return
     */
    int findCountByUserid(@Param("userid") Long userid);
}

