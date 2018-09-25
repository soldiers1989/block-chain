package com.xdaocloud.futurechain.dto.resp.user;

import java.io.Serializable;
import java.util.Date;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = -2658225821757926985L;

    private Long userid;

    private String token;

    private String username;

    /**
     * 手机号码
     */
    private String mobileNumber;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idcard;


    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * eos钱包
     */
    private Boolean existEosWallet;

    /**
     * 云信token
     */
    private String yxToken;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYxToken() {
        return yxToken;
    }

    public void setYxToken(String yxToken) {
        this.yxToken = yxToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LoginResponse() {
        super();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Boolean getExistEosWallet() {
        return existEosWallet;
    }

    public void setExistEosWallet(Boolean existEosWallet) {
        this.existEosWallet = existEosWallet;
    }

}