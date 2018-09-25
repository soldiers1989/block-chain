package com.xdaocloud.futurechain.dto.req.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ContractIdRequest implements IBaseRequest,Serializable {

    private static final long serialVersionUID = 5152963369639528772L;

    @NotNull(message = "合约id不能为空")
    private Long contractId;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
}
