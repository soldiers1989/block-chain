package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 申请记录
 */
public class AddFriend  implements Serializable {
    private static final long serialVersionUID = -5816054959459463961L;

    private Long id;

    /**
     * 申请人id
     */
    private Long userId;

    /**
     * 添加好友id
     */
    private Long friendId;

    /**
     * 加好友类型
     */
    private String addType;

    /**
     * 是否同意
     */
    private Byte isAgree;

    /**
     * 备注信息
     */
    private String remark;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 是否已经处理 此次请求
     */
    private Boolean isDeleted;

    public AddFriend() {
    }

   public AddFriend(Long friendId) {
        this.friendId = friendId;
    }

    public AddFriend(Long userId, Long friendId, String addType,String remark) {
        this.userId = userId;
        this.friendId = friendId;
        this.addType = addType;
        this.remark = remark;
    }

    public AddFriend(Long id, Byte isAgree) {
        this.id = id;
        this.isAgree = isAgree;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType == null ? null : addType.trim();
    }

    public Byte getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(Byte isAgree) {
        this.isAgree = isAgree;
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