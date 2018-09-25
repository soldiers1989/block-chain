package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 特殊用户(一级用户，不需要别人的邀请码)
 */
public class SpecialUser implements Serializable {
    private static final long serialVersionUID = -3924868908502575962L;
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     *手机号
     */
    private String mobileNumber;

    /**
     *用于邀请其它用户注册的邀请码
     */
    private String inviteCode;

    /**
     *真实姓名
     */
    private String name;

    /**
     *身份证
     */
    private String idcard;

    /**
     *性别
     */
    private Boolean sex;

    /**
     *推荐人(公司销售)
     */
    private String peferee;

    /**
     *推荐人编号'
     */
    private String pefereeNo;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public SpecialUser() {
    }

    public SpecialUser(String mobileNumber, String inviteCode) {
        this.mobileNumber = mobileNumber;
        this.inviteCode = inviteCode;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber == null ? null : mobileNumber.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPeferee() {
        return peferee;
    }

    public void setPeferee(String peferee) {
        this.peferee = peferee == null ? null : peferee.trim();
    }

    public String getPefereeNo() {
        return pefereeNo;
    }

    public void setPefereeNo(String pefereeNo) {
        this.pefereeNo = pefereeNo == null ? null : pefereeNo.trim();
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