package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.ethereum.TransactionRecordRequest;
import com.xdaocloud.futurechain.model.FtcTransaction;


public interface FtcTransactionService {

    /**
     * 保存交易记录
     * @param ftcTransaction
     * @return
     */
    Boolean saveFtcTransaction(FtcTransaction ftcTransaction);


    /**
     * 查询交易记录
     * @param request
     * @return
     */
    ResultInfo<?> findByAddress(TransactionRecordRequest request);


}
