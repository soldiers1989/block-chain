package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *用户免密金额
 */
public class Quota implements Serializable {
    private static final long serialVersionUID = -8979103595091908739L;
    /**
     *用户id
     */
    private Long userId;

    /**
     * 免密eos数量
     */
    private BigDecimal amount;

    /**
     *是否激活
     */
    private Boolean activate;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Quota() {
    }

    public Quota(Long userId, BigDecimal amount, Boolean activate) {
        this.userId = userId;
        this.amount = amount;
        this.activate = activate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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