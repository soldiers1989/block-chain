package com.xdaocloud.futurechain.dto.req.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/24.
 */
public class ContractResRequest implements IBaseRequest,Serializable {
    private static final long serialVersionUID = 1976480807441918923L;

    @NotNull(message = "消息ID不能为空")
    private long contractMessageId;

    @NotNull(message = "同意或者不同意不能为空")
    private Boolean state;

    @NotBlank(message = "用户ID不能为空")
    private String userId;


    @NotNull(message = "类型不能为空")
    private int type; //1  延期还款、2 取消合约


    public long getContractMessageId() {
        return contractMessageId;
    }

    public void setContractMessageId(long contractMessageId) {
        this.contractMessageId = contractMessageId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
