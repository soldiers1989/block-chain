package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.contract.HotTypeResponse;
import com.xdaocloud.futurechain.model.ContractType;

import java.util.List;
import java.util.Map;

public interface ContractTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractType record);

    int insertSelective(ContractType record);

    ContractType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractType record);

    int updateByPrimaryKey(ContractType record);

    List<ContractType> getAll();

    List<HotTypeResponse> getHotTypeList();

    /**
     * 根据二级ID查询一级名称跟二级名称
     * @param contractTypeId
     * @return
     */
    Map<String,Object> selectParentIdById(Long contractTypeId);
}