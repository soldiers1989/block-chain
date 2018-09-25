package com.xdaocloud.futurechain.dto.resp.friend;


import com.xdaocloud.futurechain.common.IBaseRequest;

public class FriendResponse implements IBaseRequest {

    private Long userid;

    private String userNo;

    private String nickname;

    private String avatar;

    private String remark;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
