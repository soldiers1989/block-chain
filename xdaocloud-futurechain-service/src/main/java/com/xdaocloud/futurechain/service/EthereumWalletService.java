package com.xdaocloud.futurechain.service;

import java.util.List;

public interface EthereumWalletService {
    /**
     * 根据用户id查询钱包文件
     *
     * @param userid 用户id
     * @return
     */
    List<String> findWalletFilePathByUserId(Long userid) throws Exception;


    /**
     * 根据以太坊钱包地址查询钱包文件
     *
     * @param walletAddress 钱包地址
     * @return
     */
    String findWalletFilePathByWalletAddress(String walletAddress) throws Exception;

    /**
     * 根据用户id查询 用户所拥有以太坊钱包数量
     *
     * @param userid 用户id
     * @return
     */
    int findCountByUserid(Long userid) throws Exception;

    /**
     * 查询用户拥有钱包数量
     *
     * @param userid 用户id
     * @return
     */
    String findWalletAddressByUserid(Long userid) throws Exception;

}
