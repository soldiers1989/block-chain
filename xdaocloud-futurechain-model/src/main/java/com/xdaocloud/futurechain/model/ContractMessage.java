package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 合约信息
 *
 * @author LuoFuMin
 * @date 2018/8/20
 */

public class ContractMessage implements Serializable {

    private static final long serialVersionUID = -7921260098622853160L;

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
     * /1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款
     */
    private Integer causeType;

    /**
     * 原因
     */
    private String cause;

    /**
     * 延期时间
     */
    private Date deferTime;

    /**
     * 0-拒绝，1-同意
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

    public Integer getCauseType() {
        return causeType;
    }

    public void setCauseType(Integer causeType) {
        this.causeType = causeType;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public Date getDeferTime() {
        return deferTime;
    }

    public void setDeferTime(Date deferTime) {
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