package com.xdaocloud.futurechain.mapper;


import com.xdaocloud.futurechain.dto.req.contract.DappContractRequest;
import com.xdaocloud.futurechain.model.UserContract;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserContractMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserContract record);

    int insertSelective(UserContract record);

    UserContract selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserContract record);

    int updateByPrimaryKey(UserContract record);

    /**
     * DAPP首页根据合约名称及用户ID查询合约列表 总数
     * @param dappContractRequest
     * @return
     */
    int findContractByUserIdOrContractNameCount(DappContractRequest dappContractRequest);

    /*
        <if test="searchContract != null and searchContract != ''">
      and  c.con_name LIKE CONCAT(CONCAT('%',#{searchContract},'%'))
    </if>
    and (c.back_time > SYSDATE() OR c.collect_state = true Or c.state = 0 )


        <if test="searchContract != null and searchContract != ''">
      and  c.con_name LIKE CONCAT(CONCAT('%',#{searchContract},'%'))
    </if>
    and (c.back_time > SYSDATE() OR c.collect_state = true Or c.state = 0 )
     */

    /**
     * DAPP首页根据合约名称及用户ID查询合约列表
     * @param dappContractRequest
     * @return
     */
    List<Map<String,Object>> findContractByUserIdOrContractName(DappContractRequest dappContractRequest);

    /**
     *
     * @param userId
     * @param contractId
     * @return
     */
    UserContract selectByUserIdAndContractId(@Param("userId") long userId,@Param("contractId") long contractId);
}