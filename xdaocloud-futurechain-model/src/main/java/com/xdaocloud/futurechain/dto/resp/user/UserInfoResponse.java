package com.xdaocloud.futurechain.dto.resp.user;


import java.io.Serializable;

public class UserInfoResponse implements Serializable {
    private static final long serialVersionUID = 3664823060905086489L;

    private String userid;

    private String mobileNumber;

    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 用户唯一编号
     */
    private Integer userNo;


    /**
     * 注册时间
     */
    private String gmtCreate;


    /**
     * 是否是好友
     */
    private Integer is_friend;

    /**
     * 代理候选人状态
     */
    private byte agent;

    /**
     * 注册时间
     */
    private String registerTime;

    /**
     * 代理申请时间
     */
    private String agreeTime;

    /**
     * 真实姓名
     */
    private String name;

    private String inviteCode;


    private String walletName;


    private Boolean signIn;

    private String idcard;

    private String ranking;

    private String imageUrl;




    public UserInfoResponse(String mobileNumber, String nickname, String idcard, String name, String ranking, String imageUrl, String inviteCode, Boolean signIn) {
        this.mobileNumber = mobileNumber;
        this.nickname = nickname;
        this.idcard = idcard;
        this.name = name;
        this.ranking = ranking;
        this.imageUrl = imageUrl;
        this.inviteCode = inviteCode;
        this.signIn = signIn;
    }


    public UserInfoResponse() {
    }

    public UserInfoResponse(String userid, String mobileNumber, String nickname, String avatar, Integer userNo, String registerTime, byte agent, String agreeTime, String name, String inviteCode) {
        this.userid = userid;
        this.mobileNumber = mobileNumber;
        this.nickname = nickname;
        this.avatar = avatar;
        this.userNo = userNo;
        this.agent = agent;
        this.agreeTime = agreeTime;
        this.name = name;
        this.registerTime = registerTime;
        this.inviteCode = inviteCode;
    }



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(Integer is_friend) {
        this.is_friend = is_friend;
    }

    public byte getAgent() {
        return agent;
    }

    public void setAgent(byte agent) {
        this.agent = agent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(String agreeTime) {
        this.agreeTime = agreeTime;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
}
