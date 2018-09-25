package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡
 */
public class BankCard implements Serializable {

    private static final long serialVersionUID = -2850102828206099957L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     *银行名称（招商银行）
     */
    private String bankName;

    /**
     *银行卡 卡号
     */
    private String bankNumber;

    /**
     *银行类型（xx支行）
     */
    private String bankType;

    /**
     * 持卡人
     */
    private String cardholder;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber == null ? null : bankNumber.trim();
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }
}