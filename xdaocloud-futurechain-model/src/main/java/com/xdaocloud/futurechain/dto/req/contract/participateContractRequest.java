package com.xdaocloud.futurechain.dto.req.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/25.
 */
public class participateContractRequest implements IBaseRequest,Serializable {
    private static final long serialVersionUID = 3880606142873107621L;

    @NotNull(message = "合约ID不能为空")
    private long contractId;

    @NotNull(message = "同意或者不同意不能为空")
    private Boolean state;

    @NotBlank(message = "用户ID不能为空")
    private String userId;

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
