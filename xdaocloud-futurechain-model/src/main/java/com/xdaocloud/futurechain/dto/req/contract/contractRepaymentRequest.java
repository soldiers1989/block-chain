package com.xdaocloud.futurechain.dto.req.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/25.
 */
public class contractRepaymentRequest implements IBaseRequest,Serializable {
    private static final long serialVersionUID = 2969973371514868129L;

    @NotNull(message = "合约ID不能为空")
    private long contractId;

    @NotNull(message = "类型不能为空：1 还款 2 收款")
    private int type;

    @NotBlank(message = "用户ID不能为空")
    private String userId;

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
