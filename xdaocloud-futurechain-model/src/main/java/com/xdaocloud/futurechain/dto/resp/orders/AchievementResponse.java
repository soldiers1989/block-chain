package com.xdaocloud.futurechain.dto.resp.orders;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;
import java.math.BigDecimal;

public class AchievementResponse implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 2711389757727253917L;
    /**
     * 自己充值
     */
    private BigDecimal rmb;

    /**
     * 下线充值总数
     */
    private BigDecimal rmbSum;

    /**
     * 下线购买eos总数
     */
    private BigDecimal eosSum;

    /**
     * 已经提现
     */
    private BigDecimal presented;

    /**
     * 剩余
     */
    private BigDecimal staging;


    /**
     * 成为代理候选人金额（单位元）
     */
    private Integer becomeAgent;

    /**
     * 成为代理商金额（单位元）
     */
    private Integer candidateAgent;

    /**
     * 0-代理商申请拒绝,1-成为代理商,2-审核中,3-初始默认状态,4-成为代理候选人
     */
    private Byte agent;

    /**
     * 下线人数
     */
    private Integer people;

    public Integer getBecomeAgent() {
        return becomeAgent;
    }

    public void setBecomeAgent(Integer becomeAgent) {
        this.becomeAgent = becomeAgent;
    }

    public Integer getCandidateAgent() {
        return candidateAgent;
    }

    public void setCandidateAgent(Integer candidateAgent) {
        this.candidateAgent = candidateAgent;
    }

    public BigDecimal getRmb() {
        return rmb;
    }

    public void setRmb(BigDecimal rmb) {
        this.rmb = rmb;
    }

    public BigDecimal getRmbSum() {
        return rmbSum;
    }

    public void setRmbSum(BigDecimal rmbSum) {
        this.rmbSum = rmbSum;
    }

    public BigDecimal getEosSum() {
        return eosSum;
    }

    public void setEosSum(BigDecimal eosSum) {
        this.eosSum = eosSum;
    }

    public BigDecimal getPresented() {
        return presented;
    }

    public void setPresented(BigDecimal presented) {
        this.presented = presented;
    }

    public BigDecimal getStaging() {
        return staging;
    }

    public void setStaging(BigDecimal staging) {
        this.staging = staging;
    }


    public Byte getAgent() {
        return agent;
    }

    public void setAgent(Byte agent) {
        this.agent = agent;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }
}
