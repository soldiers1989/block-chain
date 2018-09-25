package com.xdaocloud.futurechain.service.impl;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.resp.ore.OreRankingDTO;
import com.xdaocloud.futurechain.mapper.OreTransactionMapper;
import com.xdaocloud.futurechain.model.OreTransaction;
import com.xdaocloud.futurechain.service.OreTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OreTransactionServiceImpl implements OreTransactionService {

    @Autowired
    private OreTransactionMapper oreTransactionMapper;

    /**
     * 添加交易记录
     *
     * @param userid 用户id
     * @param amount 数量
     * @param way
     * @return
     */
    @Transactional
    @Override
    public ResultInfo<?> addOreTransactionRecord(Long userid, Long amount, String way) throws Exception {
        OreTransaction oreTransaction = new OreTransaction(userid, amount, way);
        int i = oreTransactionMapper.insertSelective(oreTransaction);
        if (i > 0) return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }

    /**
     * 查询一周内矿石 前10名的排行
     *
     * @return
     * @throws Exception
     */
    @Override
    public ResultInfo<?> findOreRanking() throws Exception {
        List<OreRankingDTO> oreRankingDTOs = oreTransactionMapper.findOreRanking();
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS,oreRankingDTOs);
    }
}
