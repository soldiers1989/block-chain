package com.xdaocloud.futurechain.dto.req.eos;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.math.BigDecimal;

public class UnlockEosRequest implements IBaseRequest {

    /**
     * 手机
     */
    private String mobileNumber;


    /**
     * 钱包名
     */
    private String eosWallet;


    /**
     * 解锁数量
     */
    private BigDecimal amount;


    /**
     * 原因
     */
    private String reason;


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEosWallet() {
        return eosWallet;
    }

    public void setEosWallet(String eosWallet) {
        this.eosWallet = eosWallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
