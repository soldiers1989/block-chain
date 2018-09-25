package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户好友手机号
 *
 * @author LiMaoDao
 * @date 2018/8/20
 */

public class FriendPhone implements Serializable {
    private static final long serialVersionUID = 903841703860759841L;
    private Long id;

    private Long userId;

    /**
     * 用户通讯录号码
     */
    private String phone;

    /**
     * 状态 1-是未发送，0-是已经发送过了
     */
    private Boolean state;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
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