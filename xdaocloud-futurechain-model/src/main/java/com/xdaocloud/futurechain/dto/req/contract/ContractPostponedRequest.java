package com.xdaocloud.futurechain.dto.req.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/24.
 */
public class ContractPostponedRequest implements IBaseRequest,Serializable {
    private static final long serialVersionUID = 4040853080561135383L;

    @NotNull(message = "合约id不能为空")
    private long contractId;

    //延期还款原因不能为空
    @NotBlank(message = "原因不能为空")
    private String cause;

    //延期还款时间
    private String deferTime;

    @NotBlank(message = "用户ID不能为空")
    private String userId;

    @NotNull(message = "类型不能为空")
    private int type; //1  延期还款、2 取消合约


    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDeferTime() {
        return deferTime;
    }

    public void setDeferTime(String deferTime) {
        this.deferTime = deferTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
