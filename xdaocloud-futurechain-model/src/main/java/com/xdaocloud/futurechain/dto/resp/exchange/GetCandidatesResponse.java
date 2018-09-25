package com.xdaocloud.futurechain.dto.resp.exchange;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;
import java.math.BigDecimal;

public class GetCandidatesResponse implements IBaseRequest,Serializable{


    private static final long serialVersionUID = 1106127071984641051L;

    private Long userId;

    private String mobileNumber;

    private String name;

    private String nickname;

    private String agreeTime;

    private BigDecimal sumEos;

    private BigDecimal sumRmb;

    private BigDecimal sumEosAB;

    private BigDecimal sumRmbAB;

    private Integer peopleA;

    private Integer peopleB;

    private Integer agent;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(String agreeTime) {
        this.agreeTime = agreeTime;
    }

    public BigDecimal getSumEos() {
        return sumEos;
    }

    public void setSumEos(BigDecimal sumEos) {
        this.sumEos = sumEos;
    }

    public BigDecimal getSumRmb() {
        return sumRmb;
    }

    public void setSumRmb(BigDecimal sumRmb) {
        this.sumRmb = sumRmb;
    }

    public BigDecimal getSumEosAB() {
        return sumEosAB;
    }

    public void setSumEosAB(BigDecimal sumEosAB) {
        this.sumEosAB = sumEosAB;
    }

    public BigDecimal getSumRmbAB() {
        return sumRmbAB;
    }

    public void setSumRmbAB(BigDecimal sumRmbAB) {
        this.sumRmbAB = sumRmbAB;
    }

    public Integer getPeopleA() {
        return peopleA;
    }

    public void setPeopleA(Integer peopleA) {
        this.peopleA = peopleA;
    }

    public Integer getPeopleB() {
        return peopleB;
    }

    public void setPeopleB(Integer peopleB) {
        this.peopleB = peopleB;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getAgent() {
        return agent;
    }

    public void setAgent(Integer agent) {
        this.agent = agent;
    }
}
