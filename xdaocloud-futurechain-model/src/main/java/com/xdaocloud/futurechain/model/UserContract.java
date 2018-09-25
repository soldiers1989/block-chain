package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

public class UserContract implements Serializable{
    private static final long serialVersionUID = -5540709409506035247L;
    private Long id;

    /**
     *用户id
     */
    private Long userId;

    /**
     *合约ID
     */
    private Long contractId;

    /**
     *更新时间
     */
    private Date gmtModified;

    /**
     *  true 自己发布的   false 参与/签约
     */
    private Boolean type;

    /**
     * 是否置顶（备用）
     */
    private Boolean isHot;

    private Boolean isDeleted;

    public UserContract() {
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

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}