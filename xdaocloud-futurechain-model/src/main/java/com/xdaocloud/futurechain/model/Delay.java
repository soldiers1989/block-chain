package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 延期还款
 *
 * @author LuoFuMin
 * @date 2018/7/20
 */

public class Delay implements Serializable {

    private static final long serialVersionUID = -2707307815159502104L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 合约id
     */
    private Long contractId;

    /**
     * 具体原因
     */
    private String cause;

    /**
     * 延期时间
     */
    private String deferTime;

    /**
     * 状态 ：0-拒绝，1-同意
     */
    private Integer state;

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

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public String getDeferTime() {
        return deferTime;
    }

    public void setDeferTime(String deferTime) {
        this.deferTime = deferTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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