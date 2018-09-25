package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论记录表
 */
public class Discuss implements Serializable {

    private static final long serialVersionUID = 4365160603639139752L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 朋友圈id(评论的朋友圈id)
     */
    private Long circleId;

    /**
     * 父评论id(第一级评论为null)
     */
    private Long fatherDiscussId;

    /**
     * 评论内容
     */
    private String discussContent;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDelete;

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

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public Long getFatherDiscussId() {
        return fatherDiscussId;
    }

    public void setFatherDiscussId(Long fatherDiscussId) {
        this.fatherDiscussId = fatherDiscussId;
    }

    public String getDiscussContent() {
        return discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent == null ? null : discussContent.trim();
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