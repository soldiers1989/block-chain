package com.xdaocloud.futurechain.model;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 8820069420766697012L;

    /**
     * 用户id
     */
    public Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String passwordMd5;

    /**
     * 手机号码
     */
    private String mobileNumber;

    /**
     * 交易密码
     */
    private String transactionPassword;


    /**
     * 别名
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;



    /**
     * 本地注册状态
     */
    private Boolean registerStatus;

    /**
     * 朋友链根用户ID（冗余字段，根用户该字段值为自己的ID）
     */
    private Long friendChainRootUserId;

    /**
     * 注册邀请用户ID（冗余字段，无邀请用户时该字段值为自己ID）
     */
    private Long registerInviteUserId;

    /**
     * 注册时使用的邀请码
     */
    private String registerInviteCode;

    /**
     * 用于邀请其它用户注册的邀请码
     */
    private String inviteCode;

    /**
     * 用户所拥有的矿石总额
     */
    private Long ore;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 用户状态
     */
    private Boolean status;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 身份证正面照片
     */
    private String idcardPositive;

    /**
     * 身份证反面照片
     */
    private String idcardNegative;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 所属行业
     */
    private String profession;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 0-代理商申请拒绝,1-成为代理商,2-审核中,3-初始默认状态,4-成为代理候选人
     */
    private Byte agent;


    /**
     * 代理商同意时间
     */
    private String agreeTime;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String avatar) {
        this.id = id;
        this.avatar = avatar;
    }

    public User(Long id, Byte agent) {
        this.id = id;
        this.agent = agent;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5 == null ? null : passwordMd5.trim();
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber == null ? null : mobileNumber.trim();
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public void setTransactionPassword(String transactionPassword) {
        this.transactionPassword = transactionPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Boolean getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

    public Long getFriendChainRootUserId() {
        return friendChainRootUserId;
    }

    public void setFriendChainRootUserId(Long friendChainRootUserId) {
        this.friendChainRootUserId = friendChainRootUserId;
    }

    public Long getRegisterInviteUserId() {
        return registerInviteUserId;
    }

    public void setRegisterInviteUserId(Long registerInviteUserId) {
        this.registerInviteUserId = registerInviteUserId;
    }

    public String getRegisterInviteCode() {
        return registerInviteCode;
    }

    public void setRegisterInviteCode(String registerInviteCode) {
        this.registerInviteCode = registerInviteCode == null ? null : registerInviteCode.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public Long getOre() {
        return ore;
    }

    public void setOre(Long ore) {
        this.ore = ore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getIdcardPositive() {
        return idcardPositive;
    }

    public void setIdcardPositive(String idcardPositive) {
        this.idcardPositive = idcardPositive == null ? null : idcardPositive.trim();
    }

    public String getIdcardNegative() {
        return idcardNegative;
    }

    public void setIdcardNegative(String idcardNegative) {
        this.idcardNegative = idcardNegative == null ? null : idcardNegative.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Byte getAgent() {
        return agent;
    }

    public void setAgent(Byte agent) {
        this.agent = agent;
    }

    public String getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(String agreeTime) {
        this.agreeTime = agreeTime;
    }
}