package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包 （废弃）
 */
public class Wallet implements Serializable {
    private static final long serialVersionUID = -6747383794447394161L;
    private Long userId;

    private BigDecimal moneySum;

    private BigDecimal digitalCurrency;

    private String walletAddress;

    private String walletPrivateKey;

    private String walletPublicKey;

    private String remark;

    private String passPhrase;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Wallet() {
    }

    public Wallet(Long userId, BigDecimal moneySum, BigDecimal digitalCurrency) {
        this.userId = userId;
        this.moneySum = moneySum;
        this.digitalCurrency = digitalCurrency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }

    public BigDecimal getDigitalCurrency() {
        return digitalCurrency;
    }

    public void setDigitalCurrency(BigDecimal digitalCurrency) {
        this.digitalCurrency = digitalCurrency;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress == null ? null : walletAddress.trim();
    }

    public String getWalletPrivateKey() {
        return walletPrivateKey;
    }

    public void setWalletPrivateKey(String walletPrivateKey) {
        this.walletPrivateKey = walletPrivateKey == null ? null : walletPrivateKey.trim();
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey == null ? null : walletPublicKey.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase == null ? null : passPhrase.trim();
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
}