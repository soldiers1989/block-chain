package com.xdaocloud.futurechain.dto.resp.user;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;

public class BankResponse implements IBaseRequest,Serializable {

    private static final long serialVersionUID = 5268202628831159819L;

    private String bankName;

    private String bankNumber;

    private String bankType;

    private String cardholder;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }
}
