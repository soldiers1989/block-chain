package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包交易记录 （废弃）
 */
public class WalletTransaction implements Serializable {
    private static final long serialVersionUID = 3074711853173193354L;
    private Long id;

    private Long userId;

    private String way;

    private BigDecimal digitalCurrency;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public WalletTransaction() {
    }

    public WalletTransaction(Long userId, String way, BigDecimal digitalCurrency) {
        this.userId = userId;
        this.way = way;
        this.digitalCurrency = digitalCurrency;
    }

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

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way == null ? null : way.trim();
    }

    public BigDecimal getDigitalCurrency() {
        return digitalCurrency;
    }

    public void setDigitalCurrency(BigDecimal digitalCurrency) {
        this.digitalCurrency = digitalCurrency;
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