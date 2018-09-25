package com.xdaocloud.futurechain.dto.resp.exchange;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;
import java.math.BigDecimal;

public class AllForwardRecordResponse implements IBaseRequest,Serializable {

    private static final long serialVersionUID = -5662187251141301994L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 1：一级下线，2：二级下线
     */
    private Integer downline;

    private String name;

    /**
     * 别名
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String mobileNumber;


    /**
     * 兑换eos数量
     */
    private BigDecimal devoteEos;

    /**
     * 得到eos数量
     */
    private BigDecimal eos;

    private String gmtCreate;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public BigDecimal getDevoteEos() {
        return devoteEos;
    }

    public void setDevoteEos(BigDecimal devoteEos) {
        this.devoteEos = devoteEos;
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
}
