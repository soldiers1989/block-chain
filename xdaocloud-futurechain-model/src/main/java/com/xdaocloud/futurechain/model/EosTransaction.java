package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * eos 交易记录
 */
public class EosTransaction implements Serializable {

    private static final long serialVersionUID = -5168929429115329437L;

    private Long id;

    private Long userId;

    /**
     * 转出账户
     */
    private String fromWallet;

    /**
     * 转入账户
     */
    private String toWallet;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 交易状态订单状态（0：支付失败，1支付成功，3 支付中...，4 平台补贴失败，5 迁移eos交易失败,6 迁移成功,7 锁仓交易（假交易））
     */
    private Byte tranState;

    /**
     * 交易信息
     */
    private String tranMsg;

    /**
     * 区块链交易哈希值
     */
    private String tranHash;

    /**
     * 备注信息
     */
    private String remarks;

    private String gmtCreate;

    private String gmtModified;

    private Boolean isDeleted;

    public EosTransaction() {
    }

    public EosTransaction(Long userId) {
        this.userId = userId;
    }

    public EosTransaction(String fromWallet, String toWallet) {
        this.fromWallet = fromWallet;
        this.toWallet = toWallet;
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

    public String getFromWallet() {
        return fromWallet;
    }

    public void setFromWallet(String fromWallet) {
        this.fromWallet = fromWallet == null ? null : fromWallet.trim();
    }

    public String getToWallet() {
        return toWallet;
    }

    public void setToWallet(String toWallet) {
        this.toWallet = toWallet == null ? null : toWallet.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Byte getTranState() {
        return tranState;
    }

    public void setTranState(Byte tranState) {
        this.tranState = tranState;
    }

    public String getTranMsg() {
        return tranMsg;
    }

    public void setTranMsg(String tranMsg) {
        this.tranMsg = tranMsg == null ? null : tranMsg.trim();
    }

    public String getTranHash() {
        return tranHash;
    }

    public void setTranHash(String tranHash) {
        this.tranHash = tranHash == null ? null : tranHash.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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