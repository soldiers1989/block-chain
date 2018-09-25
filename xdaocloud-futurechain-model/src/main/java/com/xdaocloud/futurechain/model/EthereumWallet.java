package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 以太坊钱包
 */
public class EthereumWallet implements Serializable{
    private static final long serialVersionUID = -5543617643840242183L;

    private Long id;

    private Long userId;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 钱包私钥
     */
    private String walletPrivateKey;

    /**
     * 钱包公钥
     */
    private String walletPublicKey;

    /**
     * 钱包文件物理路径
     */
    private String walletFilePath;

    /**
     * 钱包密码
     */
    private String passPhrase;

    /**
     * 备注
     */
    private String remark;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;



    public EthereumWallet() {
    }

    public EthereumWallet(Long userId, String walletAddress, String passPhrase, String remark) {
        this.userId = userId;
        this.walletAddress = walletAddress;
        this.passPhrase = passPhrase;
        this.remark = remark;
    }

    public EthereumWallet(Long userId, String walletAddress, String walletPrivateKey, String walletPublicKey, String walletFilePath, String passPhrase, Boolean isDeleted) {
        this.userId = userId;
        this.walletAddress = walletAddress;
        this.walletPrivateKey = walletPrivateKey;
        this.walletPublicKey = walletPublicKey;
        this.walletFilePath = walletFilePath;
        this.passPhrase = passPhrase;
        this.isDeleted = isDeleted;
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

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress == null ? null : walletAddress.trim();
    }

    public String getWalletPrivateKey() {
        return walletPrivateKey;
    }

    public void setWalletPrivateKey(String walletPrivateKey) {
        this.walletPrivateKey = walletPrivateKey == null ? null : walletPrivateKey.trim();
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey == null ? null : walletPublicKey.trim();
    }

    public String getWalletFilePath() {
        return walletFilePath;
    }

    public void setWalletFilePath(String walletFilePath) {
        this.walletFilePath = walletFilePath == null ? null : walletFilePath.trim();
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase == null ? null : passPhrase.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }




}