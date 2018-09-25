package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * eos 钱包
 *
 * @author LuoFuMin
 * @date 2018/8/20
 */

public class EosWallet implements Serializable {

    private static final long serialVersionUID = 9051055882457790254L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 钱包名也是账户名，钱包管理-钱包私钥，账户管理-钱包
     */
    private String walletName;

    /**
     * 交易密码 （废弃）
     */
    private String passPhrase;

    /**
     * 钱包 私钥（废弃）
     */
    private String walletPass;

    /**
     * 数字钱包地址（废弃）
     */
    private String walletAddress;

    /**
     * active 私钥（加密）
     */
    private String activePrivateKey;

    /**
     * active 公钥（不加密）
     */
    private String activePublicKey;

    /**
     * owner 私钥（加密）
     */
    private String ownerPrivateKey;

    /**
     * owner 公钥（不加密）
     */
    private String ownerPublicKey;

    /**
     * 冷钱包文件地址（废弃）
     */
    private String walletFilePath;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    /**
     * 备注
     */
    private String remark;

    public EosWallet() {
    }


    public EosWallet(Long userId) {
        this.userId = userId;
    }

    public EosWallet(Long userId, Boolean isDeleted) {
        this.userId = userId;
        this.isDeleted = isDeleted;
    }

    public EosWallet(String walletName) {
        this.walletName = walletName;
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

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName == null ? null : walletName.trim();
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase == null ? null : passPhrase.trim();
    }

    public String getWalletPass() {
        return walletPass;
    }

    public void setWalletPass(String walletPass) {
        this.walletPass = walletPass == null ? null : walletPass.trim();
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress == null ? null : walletAddress.trim();
    }

    public String getActivePrivateKey() {
        return activePrivateKey;
    }

    public void setActivePrivateKey(String activePrivateKey) {
        this.activePrivateKey = activePrivateKey == null ? null : activePrivateKey.trim();
    }

    public String getActivePublicKey() {
        return activePublicKey;
    }

    public void setActivePublicKey(String activePublicKey) {
        this.activePublicKey = activePublicKey == null ? null : activePublicKey.trim();
    }

    public String getOwnerPrivateKey() {
        return ownerPrivateKey;
    }

    public void setOwnerPrivateKey(String ownerPrivateKey) {
        this.ownerPrivateKey = ownerPrivateKey == null ? null : ownerPrivateKey.trim();
    }

    public String getOwnerPublicKey() {
        return ownerPublicKey;
    }

    public void setOwnerPublicKey(String ownerPublicKey) {
        this.ownerPublicKey = ownerPublicKey == null ? null : ownerPublicKey.trim();
    }

    public String getWalletFilePath() {
        return walletFilePath;
    }

    public void setWalletFilePath(String walletFilePath) {
        this.walletFilePath = walletFilePath == null ? null : walletFilePath.trim();
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