package com.xdaocloud.futurechain.dto.req.exchange;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

public class PutMoneyRequest  implements IBaseRequest {


    @NotBlank(message = "用户id不能为空")
    private String userid;

    private BigDecimal a_menoy;

    private BigDecimal b_menoy;

    private Integer type;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getA_menoy() {
        return a_menoy;
    }

    public void setA_menoy(BigDecimal a_menoy) {
        this.a_menoy = a_menoy;
    }

    public BigDecimal getB_menoy() {
        return b_menoy;
    }

    public void setB_menoy(BigDecimal b_menoy) {
        this.b_menoy = b_menoy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
