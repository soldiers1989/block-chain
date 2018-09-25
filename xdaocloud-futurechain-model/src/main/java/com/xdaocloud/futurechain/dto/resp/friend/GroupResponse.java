package com.xdaocloud.futurechain.dto.resp.friend;

import java.io.Serializable;

public class GroupResponse implements Serializable{

    private static final long serialVersionUID = 2459555774228605234L;

    private Long id;

    private String mobileNumber;

    private String nickname;

    private String avatar;

    private String inviteCode;

    private String userNo;

    private String remark;

   public GroupResponse() {
    }

    public GroupResponse(Long id, String mobileNumber, String nickname, String avatar, String inviteCode) {
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.nickname = nickname;
        this.avatar = avatar;
        this.inviteCode = inviteCode;
    }

    public GroupResponse(Long id, String mobileNumber, String nickname, String avatar, String inviteCode, String userNo, String remark) {
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.nickname = nickname;
        this.avatar = avatar;
        this.inviteCode = inviteCode;
        this.userNo = userNo;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
