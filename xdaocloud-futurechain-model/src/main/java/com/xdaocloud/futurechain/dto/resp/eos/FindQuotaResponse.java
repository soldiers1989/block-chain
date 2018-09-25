package com.xdaocloud.futurechain.dto.resp.eos;


import com.xdaocloud.futurechain.common.IBaseRequest;

import java.math.BigDecimal;

public class FindQuotaResponse implements IBaseRequest {


    private BigDecimal amount;

    private Boolean activate;

    private String defaultValue;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
