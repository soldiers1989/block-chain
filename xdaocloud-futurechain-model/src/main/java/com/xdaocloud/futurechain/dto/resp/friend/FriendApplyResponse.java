package com.xdaocloud.futurechain.dto.resp.friend;


import com.xdaocloud.futurechain.common.IBaseRequest;

public class FriendApplyResponse implements IBaseRequest {

    private Long addFriendId;

    /**
     * 用户唯一编号
     */
    public Integer friendNo;

    private Long friendId;

    /**
     * 别名
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;


    private String addType;

    private Byte isAgree;

    private String remark;

    private String gmtCreate;

    private Boolean isDeleted;

    public Long getAddFriendId() {
        return addFriendId;
    }

    public void setAddFriendId(Long addFriendId) {
        this.addFriendId = addFriendId;
    }

    public Integer getFriendNo() {
        return friendNo;
    }

    public void setFriendNo(Integer friendNo) {
        this.friendNo = friendNo;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
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

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public Byte getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(Byte isAgree) {
        this.isAgree = isAgree;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
