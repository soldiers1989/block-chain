package com.xdaocloud.futurechain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.ethereum.TransactionRecordRequest;
import com.xdaocloud.futurechain.mapper.FtcTransactionMapper;
import com.xdaocloud.futurechain.model.FtcTransaction;
import com.xdaocloud.futurechain.service.FtcTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FtcTransactionServiceImpl implements FtcTransactionService {

    @Autowired
    private FtcTransactionMapper ftcTransactionMapper;

    /**
     * 保存交易记录
     *
     * @param ftcTransaction
     * @return
     */
    @Override
    @Transactional
    public Boolean saveFtcTransaction(FtcTransaction ftcTransaction) {
        int i = ftcTransactionMapper.insertSelective(ftcTransaction);
        if (i == 0) return false;
        return true;
    }

    /**
     * 查询交易记录
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<PageResponse> findByAddress(TransactionRecordRequest request) {

        System.out.println("==getPage=="+request.getPage()+",getSize"+request.getSize());
        PageHelper.startPage(request.getPage(),request.getSize(),"id DESC");
        List<FtcTransaction> ftcTransactions = ftcTransactionMapper.findByAddress(request.getAddress());
        PageInfo<FtcTransaction> pageInfo = new PageInfo<FtcTransaction>(ftcTransactions);
        PageResponse response = new PageResponse(pageInfo);
        return new ResultInfo<>(ResultInfo.SUCCESS,ResultInfo.MSG_SUCCESS,response);
    }

}
