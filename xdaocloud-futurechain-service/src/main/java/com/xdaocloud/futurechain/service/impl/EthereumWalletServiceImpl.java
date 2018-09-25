package com.xdaocloud.futurechain.service.impl;

import com.xdaocloud.futurechain.mapper.EthereumWalletMapper;
import com.xdaocloud.futurechain.service.EthereumWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EthereumWalletServiceImpl implements EthereumWalletService {

    @Autowired
    private EthereumWalletMapper ethereumWalletMapper;


    /**
     * 根据用户id查询钱包文件
     *
     * @param userid 用户id
     * @return
     */
    @Override
    public List<String> findWalletFilePathByUserId(Long userid) throws Exception {
        List<String> stringList = ethereumWalletMapper.findWalletFilePathByUserId(userid);
        return stringList;
    }

    /**
     * 根据以太坊钱包地址查询钱包文件
     *
     * @param walletAddress 钱包地址
     * @return
     */
    @Override
    public String findWalletFilePathByWalletAddress(String walletAddress) throws Exception {
        return ethereumWalletMapper.findWalletFilePathByWalletAddress(walletAddress);
    }

    /**
     * 根据用户id查询 用户所拥有以太坊钱包数量
     *
     * @param userid 用户id
     * @return
     */
    @Override
    public int findCountByUserid(Long userid) throws Exception {
        return ethereumWalletMapper.findCountByUserid(userid);
    }


    /**
     * 查询用户拥有钱包数量
     *
     * @param userid 用户id
     * @return
     */
    @Override
    public String findWalletAddressByUserid(Long userid) throws Exception {
        return ethereumWalletMapper.findWalletAddressByUserid(userid);
    }
}
