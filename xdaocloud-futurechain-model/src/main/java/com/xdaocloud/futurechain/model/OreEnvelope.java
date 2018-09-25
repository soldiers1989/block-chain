package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 矿包
 */
public class OreEnvelope implements Serializable{

    private static final long serialVersionUID = 4476352152890171962L;

    private Long id;

    private Long userId;

    /**
     * 矿包数量
     */
    private Integer count;

    /**
     * 矿包金额
     */
    private BigDecimal amount;

    /**
     * 有效时间（分钟）
     */
    private Integer validTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 矿包标题
     */
    private String oreTitle;

    /**
     * 矿包状态
     */
    private Boolean oreState;

    /**
     * 奖励发放状态
     */
    private Boolean reward;

    /**
     * 领奖记录,json字符串
     */
    private String awardFtc;

    /**
     * 红包金额随机数
     */
    private String random;

    /**
     * 剩余未领红包数量
     */
    private Integer residue;


    private String gmtCreate;

    private String gmtModified;

    private Boolean isDeleted;


    public OreEnvelope() {
    }

    public OreEnvelope(Long id, Integer residue) {
        this.id = id;
        this.residue = residue;
    }

    public OreEnvelope(Long userId, Integer count, Integer validTime, String oreTitle) {
        this.userId = userId;
        this.count = count;
        this.validTime = validTime;
        this.oreTitle = oreTitle;
    }

    public OreEnvelope(Long userId, Integer count, Integer validTime, Date endTime, String oreTitle) {
        this.userId = userId;
        this.count = count;
        this.validTime = validTime;
        this.endTime = endTime;
        this.oreTitle = oreTitle;
    }

    public OreEnvelope(Long userId, Integer count, BigDecimal amount, String oreTitle) {
        this.userId = userId;
        this.count = count;
        this.amount = amount;
        this.oreTitle = oreTitle;
    }

    public OreEnvelope(Long userId, Integer count, BigDecimal amount, String oreTitle, String random, Integer residue) {
        this.userId = userId;
        this.count = count;
        this.amount = amount;
        this.oreTitle = oreTitle;
        this.random = random;
        this.residue = residue;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public Integer getResidue() {
        return residue;
    }

    public void setResidue(Integer residue) {
        this.residue = residue;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOreTitle() {
        return oreTitle;
    }

    public void setOreTitle(String oreTitle) {
        this.oreTitle = oreTitle == null ? null : oreTitle.trim();
    }

    public Boolean getOreState() {
        return oreState;
    }

    public void setOreState(Boolean oreState) {
        this.oreState = oreState;
    }

    public Boolean getReward() {
        return reward;
    }

    public void setReward(Boolean reward) {
        this.reward = reward;
    }

    public String getAwardFtc() {
        return awardFtc;
    }

    public void setAwardFtc(String awardFtc) {
        this.awardFtc = awardFtc;
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