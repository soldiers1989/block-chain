package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.req.contract.DappContractRequest;
import com.xdaocloud.futurechain.dto.resp.contract.ContractDetailsResponse;
import com.xdaocloud.futurechain.model.Contract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);

    List<Contract> findContract(@Param("userid") Long userid);

    int findMyReleaseListByUserIdOrContractNameCount(DappContractRequest dappContractRequest);

    List<Contract> findMyReleaseListByUserIdOrContractName(DappContractRequest dappContractRequest);

    int findMyContractListListByUserIdOrContractNameCount(DappContractRequest dappContractRequest);

    List<Contract> findMyContractListListByUserIdOrContractName(DappContractRequest dappContractRequest);

    Contract findMyReleaseByUserIdOrContractId(@Param("contractId") long contractId, @Param("userId") long userId, @Param("type") int type);

    ContractDetailsResponse findContractDetails(@Param("contractId") Long contractId, @Param("isDeleted") Boolean isDeleted);

    /**
     * 删除富文本
     * @param contractId
     */
    int delRichText(Long contractId);

    /*Contract findMyReleaseByUserIdOrContractId(@Param("userid")ContractManageRequest contractManageRequest,@Param("userid")int type);*/
}