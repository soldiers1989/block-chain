package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

 /**
  * 关注类
  *@author  LuoFuMin
  *@date  2018/8/20
  */

public class Follow implements Serializable {
    private static final long serialVersionUID = 7218954395772602894L;
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 被关注的用户id
     */
    private Long passiveUserId;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDelete;

    public Follow() {
    }

    public Follow(Long userId) {
        this.userId = userId;
    }

    public Follow(Long userId, Long passiveUserId) {
        this.userId = userId;
        this.passiveUserId = passiveUserId;
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

    public Long getPassiveUserId() {
        return passiveUserId;
    }

    public void setPassiveUserId(Long passiveUserId) {
        this.passiveUserId = passiveUserId;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}