package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 代理商兑换记录
 */
public class Exchange implements Serializable {

    private static final long serialVersionUID = 1546324507486003406L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 1：一级下线，2：二级下线
     */
    private Integer downline;


    /**
     * 兑换eos数量
     */
    private BigDecimal devoteEos;

    /**
     * 得到eos数量
     */
    private BigDecimal eos;

    /**
     * eos转移是否成功
     */
    private Boolean tranEos;

/*    *//**
     * 兑换人民币数量
     *//*
    private BigDecimal devoteRmb;

    *//**
     * 得到人民币数量
     *//*
    private BigDecimal rmb;

    *//**
     * rmb转移是否成功
     *//*
    private Boolean tranRmb;*/

    /**
     * 备注
     */
    private String remark;

    private String gmtCreate;

    private String gmtModified;

    private Boolean isDeleted;

    public Exchange() {
    }

    public Exchange(Long userId, BigDecimal eos) {
        this.userId = userId;
        this.eos = eos;
    }

    public Exchange(Long userId, Integer downline, BigDecimal eos) {
        this.userId = userId;
        this.downline = downline;
        this.eos = eos;
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

    public Integer getDownline() {
        return downline;
    }

    public void setDownline(Integer downline) {
        this.downline = downline;
    }

    public BigDecimal getEos() {
        return eos;
    }

    public void setEos(BigDecimal eos) {
        this.eos = eos;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getDevoteEos() {
        return devoteEos;
    }

    public void setDevoteEos(BigDecimal devoteEos) {
        this.devoteEos = devoteEos;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getTranEos() {
        return tranEos;
    }

    public void setTranEos(Boolean tranEos) {
        this.tranEos = tranEos;
    }


}