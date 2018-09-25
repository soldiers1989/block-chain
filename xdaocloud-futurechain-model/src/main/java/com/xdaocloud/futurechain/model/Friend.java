package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

 /**
  * 好友关系
  * userId=自己的id，主动加好友；friendId=自己id，被动加好友；
  *@author  LuoFuMin
  *@date  2018/8/20
  */

public class Friend implements Serializable {

    private static final long serialVersionUID = 5718500872783628506L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 好友id
     */
    private Long friendId;

    /**
     * 是否屏蔽
     */
    private Boolean userReject;

    /**
     * 是否屏蔽
     */
    private Boolean friendReject;

    /**
     * 好友备注（关系相反）
     */
    private String userRemark;

    /**
     * 用户备注（关系相反）
     */
    private String friendRemark;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Friend() {
    }

    public Friend(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public Friend(Long userId, Long friendId, String userRemark) {
        this.userId = userId;
        this.friendId = friendId;
        this.userRemark = userRemark;
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

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Boolean getUserReject() {
        return userReject;
    }

    public void setUserReject(Boolean userReject) {
        this.userReject = userReject;
    }

    public Boolean getFriendReject() {
        return friendReject;
    }

    public void setFriendReject(Boolean friendReject) {
        this.friendReject = friendReject;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark == null ? null : userRemark.trim();
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark == null ? null : friendRemark.trim();
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