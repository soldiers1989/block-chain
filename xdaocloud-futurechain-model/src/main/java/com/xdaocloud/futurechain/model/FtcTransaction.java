package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * FTC交易记录
 */
public class FtcTransaction implements Serializable{

    private static final long serialVersionUID = 5790784811603160144L;

    private Long id;

    private Long userId;

    /**
     * 转出钱包地址
     */
    private String fromWallet;

    /**
     * 转入钱包地址
     */
    private String toWallet;

    /**
     * 数量
     */
    private BigInteger amount;

    /**
     *以太坊交易燃料费用
     */
    private BigInteger gas;

    /**
     * 燃料单价
     */
    private BigInteger gasPrice;

    /**
     * 交易状态订单状态（0：支付失败，1支付成功，3 支付中...,支付中无意外是可以交易成功的）
     */
    private Byte tranState;

    /**
     * 返回错误信息(error = null 说明交易成功,result = null 说明交易失败)
     */
    private String tranMsg;

    /**
     * 交易成功会累加nonce数量
     */
    private BigInteger tranNonce;

    /**
     * 返回哈希值表示交易中，等待矿工处理
     */
    private String tranHash;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDelete;

    public FtcTransaction() {
    }

    public FtcTransaction(Long userId, String fromWallet, String toWallet, BigInteger amount, BigInteger gas, BigInteger gasPrice, Byte tranState, String tranMsg, BigInteger tranNonce, Boolean isDelete) {
        this.userId = userId;
        this.fromWallet = fromWallet;
        this.toWallet = toWallet;
        this.amount = amount;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.tranState = tranState;
        this.tranMsg = tranMsg;
        this.tranNonce = tranNonce;
        this.isDelete = isDelete;
    }

    public FtcTransaction(Long userId, String fromWallet, String toWallet, BigInteger amount, BigInteger gas, BigInteger gasPrice, Byte tranState, BigInteger tranNonce, String tranHash, Boolean isDelete) {
        this.userId = userId;
        this.fromWallet = fromWallet;
        this.toWallet = toWallet;
        this.amount = amount;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.tranState = tranState;
        this.tranNonce = tranNonce;
        this.tranHash = tranHash;
        this.isDelete = isDelete;
    }

    public FtcTransaction(Long id, Long userId, String fromWallet, String toWallet, BigInteger amount, BigInteger gas, BigInteger gasPrice, Byte tranState, String tranMsg, BigInteger tranNonce, String tranHash, Date gmtCreate, Date gmtModified, Boolean isDelete) {
        this.id = id;
        this.userId = userId;
        this.fromWallet = fromWallet;
        this.toWallet = toWallet;
        this.amount = amount;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.tranState = tranState;
        this.tranMsg = tranMsg;
        this.tranNonce = tranNonce;
        this.tranHash = tranHash;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.isDelete = isDelete;
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
        this.fromWallet = fromWallet;
    }

    public String getToWallet() {
        return toWallet;
    }

    public void setToWallet(String toWallet) {
        this.toWallet = toWallet;
    }

    public BigInteger getamount() {
        return amount;
    }

    public void setamount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getGas() {
        return gas;
    }

    public void setGas(BigInteger gas) {
        this.gas = gas;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
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
        this.tranMsg = tranMsg;
    }

    public BigInteger getTranNonce() {
        return tranNonce;
    }

    public void setTranNonce(BigInteger tranNonce) {
        this.tranNonce = tranNonce;
    }

    public String getTranHash() {
        return tranHash;
    }

    public void setTranHash(String tranHash) {
        this.tranHash = tranHash;
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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}