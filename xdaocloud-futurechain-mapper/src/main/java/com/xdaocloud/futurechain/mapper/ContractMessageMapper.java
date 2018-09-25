package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.ContractMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContractMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractMessage record);

    int insertSelective(ContractMessage record);

    ContractMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractMessage record);

    int updateByPrimaryKey(ContractMessage record);

    int selectByContractIdCount(long contractId);

    List<Map<String,Object>> selectByContractId(@Param("contractId") long contractId, @Param("page") int pageNums, @Param("size") int pageSize);
}