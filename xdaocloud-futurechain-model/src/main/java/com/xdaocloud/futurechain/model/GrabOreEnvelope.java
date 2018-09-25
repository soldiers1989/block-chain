package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 抢矿包记录
 */
public class GrabOreEnvelope implements Serializable {

    private static final long serialVersionUID = 5590793586470062360L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 矿包id
     */
    private Long oreEnvelopeId;

    /**
     * 得到的随机数
     */
    private BigDecimal randomNumber;

    /**
     * 状态是否可抢
     */
    private Boolean state;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public GrabOreEnvelope() {
    }

    public GrabOreEnvelope(Long id, Long userId, Long oreEnvelopeId, BigDecimal randomNumber, Date gmtCreate, Date gmtModified, Boolean isDeleted) {
        this.id = id;
        this.userId = userId;
        this.oreEnvelopeId = oreEnvelopeId;
        this.randomNumber = randomNumber;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.isDeleted = isDeleted;
    }

    public GrabOreEnvelope(Long userId, Long oreEnvelopeId, BigDecimal randomNumber, Boolean state) {
        this.userId = userId;
        this.oreEnvelopeId = oreEnvelopeId;
        this.randomNumber = randomNumber;
        this.state = state;
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

    public Long getOreEnvelopeId() {
        return oreEnvelopeId;
    }

    public void setOreEnvelopeId(Long oreEnvelopeId) {
        this.oreEnvelopeId = oreEnvelopeId;
    }

    public BigDecimal getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(BigDecimal randomNumber) {
        this.randomNumber = randomNumber;
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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }


}