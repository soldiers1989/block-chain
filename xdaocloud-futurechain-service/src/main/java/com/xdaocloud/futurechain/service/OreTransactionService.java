package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;

public interface OreTransactionService {

    /**
     * 添加交易记录
     * @param userid 用户id
     * @param amount 数量
     * @param way
     * @return
     */
    ResultInfo<?> addOreTransactionRecord(Long userid,Long amount,String way) throws Exception;


    /**
     * 查询一周内矿石 前10名的排行
     *
     * @return
     * @throws Exception
     */
    ResultInfo<?> findOreRanking() throws Exception;
}
