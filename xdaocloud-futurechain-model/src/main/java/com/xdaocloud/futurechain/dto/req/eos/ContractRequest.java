package com.xdaocloud.futurechain.dto.req.eos;

import java.io.Serializable;

/**
 * 合约信息发布 给dapp
 *
 * @author LuoFuMin
 * @data 2018/8/14
 */
public class ContractRequest implements Serializable {

    private static final long serialVersionUID = -4777513213434295947L;
    /**
     * 合约id
     */
    private String id;

    /**
     * 创建合约者id
     */
    private String userId;

    /**
     * 创建合约者身份：1-放贷，2-借款
     */
    private String role;

    /**
     * 签约者id
     */
    private String signId;

    /**
     * 合约哈希值
     */
    private String contractHashId;

    /**
     * 发合约账户
     */
    private String userEosAccount;

    /**
     * 发合约者私钥
     */
    private String userKey;

    /**
     * 签约者账户
     */
    private String signEosAccount;

    /**
     * 签合约者私钥
     */
    private String signKey;

    /**
     * 签约时间
     */
    private String signTime;

    /**
     * 合约名字
     */
    private String conName;

    /**
     * 合约类型
     */
    private String contractTypeId;

    /**
     * 合约地址
     */
    private String conAddress;

    /**
     * 图片地址
     */
    private String portrait;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 借款金额
     */
    private String money;

    /**
     * 借款方式
     */
    private String borrowWay;

    /**
     * 利息
     */
    private String accrual;

    /**
     * 还款时间
     */
    private String backTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 富文本内容
     */
    private String richText;

    /**
     * 违约金
     */
    private String penalty;


    /**
     * 创建时间戳
     */
    private String gmtCreate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getContractHashId() {
        return contractHashId;
    }

    public void setContractHashId(String contractHashId) {
        this.contractHashId = contractHashId;
    }

    public String getUserEosAccount() {
        return userEosAccount;
    }

    public void setUserEosAccount(String userEosAccount) {
        this.userEosAccount = userEosAccount;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getSignEosAccount() {
        return signEosAccount;
    }

    public void setSignEosAccount(String signEosAccount) {
        this.signEosAccount = signEosAccount;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getConAddress() {
        return conAddress;
    }

    public void setConAddress(String conAddress) {
        this.conAddress = conAddress;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBorrowWay() {
        return borrowWay;
    }

    public void setBorrowWay(String borrowWay) {
        this.borrowWay = borrowWay;
    }

    public String getAccrual() {
        return accrual;
    }

    public void setAccrual(String accrual) {
        this.accrual = accrual;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userId=" + userId +
                ", role=" + role +
                ", signId=" + signId +
                ", contractHashId='" + contractHashId + '\'' +
                ", userEosAccount='" + userEosAccount + '\'' +
                ", userKey='" + userKey + '\'' +
                ", signEosAccount='" + signEosAccount + '\'' +
                ", signKey='" + signKey + '\'' +
                ", signTime='" + signTime + '\'' +
                ", conName='" + conName + '\'' +
                ", contractTypeId='" + contractTypeId + '\'' +
                ", conAddress='" + conAddress + '\'' +
                ", portrait='" + portrait + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", money='" + money + '\'' +
                ", borrowWay='" + borrowWay + '\'' +
                ", accrual='" + accrual + '\'' +
                ", backTime='" + backTime + '\'' +
                ", remark='" + remark + '\'' +
                ", richText='" + richText + '\'' +
                ", penalty='" + penalty + '\'' +
                ", gmtCreate='" + gmtCreate + '\'' +
                '}';
    }
}
