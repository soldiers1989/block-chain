package com.xdaocloud.futurechain.dto.req.exchange;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

public class PutForwardRequest implements IBaseRequest {


    @NotBlank(message = "用户id不能为空")
    private String userid;

    private BigDecimal eosAmount;

    private Integer type;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getEosAmount() {
        return eosAmount;
    }

    public void setEosAmount(BigDecimal eosAmount) {
        this.eosAmount = eosAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
