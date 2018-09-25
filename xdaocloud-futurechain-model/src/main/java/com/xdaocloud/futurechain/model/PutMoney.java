package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录（暂时不用）
 */
public class PutMoney implements Serializable {

    private static final long serialVersionUID = -7263864433523144682L;

    private Long id;

    private Long userId;

    private Integer downline;

    private BigDecimal devoteRmb;

    private BigDecimal rmb;

    private String remark;

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

    public BigDecimal getDevoteRmb() {
        return devoteRmb;
    }

    public void setDevoteRmb(BigDecimal devoteRmb) {
        this.devoteRmb = devoteRmb;
    }

    public BigDecimal getRmb() {
        return rmb;
    }

    public void setRmb(BigDecimal rmb) {
        this.rmb = rmb;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getDownline() {
        return downline;
    }

    public void setDownline(Integer downline) {
        this.downline = downline;
    }
}