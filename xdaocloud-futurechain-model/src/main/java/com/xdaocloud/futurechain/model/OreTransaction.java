package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 哈希粒
 */
public class OreTransaction implements Serializable {
    private static final long serialVersionUID = -3804974310785698326L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 交易数量
     */
    private Long amount;

    /**
     * 交易方式
     */
    private String way;

    private String gmtCreate;

    private String gmtModified;

    private Boolean isDeleted;

    public OreTransaction() {
    }

    public OreTransaction(Long userId, Long amount, String way) {
        this.userId = userId;
        this.amount = amount;
        this.way = way;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way == null ? null : way.trim();
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}