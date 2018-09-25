package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 奖励机制
 */
public class Reward implements Serializable {
    private static final long serialVersionUID = -7137137118779227394L;

    private Long id;

    /**
     * 根据类型判断（1-注册奖励；2-邀请好友奖励；3-签到奖励：5-充值奖励；6-分享奖励）
     */
    private Short type;

    /**
     * 奖励麦粒数量
     */
    private Long oreAmount;

    /**
     * 奖励eos数量
     */
    private BigDecimal eosAmount;

    /**
     * 备注
     */
    private String remarks;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getOreAmount() {
        return oreAmount;
    }

    public void setOreAmount(Long oreAmount) {
        this.oreAmount = oreAmount;
    }

    public BigDecimal getEosAmount() {
        return eosAmount;
    }

    public void setEosAmount(BigDecimal eosAmount) {
        this.eosAmount = eosAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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