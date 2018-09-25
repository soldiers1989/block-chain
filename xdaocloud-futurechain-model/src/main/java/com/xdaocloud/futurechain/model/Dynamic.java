package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 动态
 */
public class Dynamic implements Serializable {

    private static final long serialVersionUID = 6588686180212863875L;

    private Long id;

    /**
     * 圈子id
     */
    private Long circleId;

    /**
     * 用户id
     */
    private Long userId;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    /**
     * 动态内容
     */
    private String dyContent;

    /**
     * 点赞（json对象{01:"用户id",02:"用户id''}）！！弃用
     */
    private String dyLike;

    /**
     * 图片（json对象{01:"图片地址",02:"图片地址''}）
     */
    private String dyImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getDyContent() {
        return dyContent;
    }

    public void setDyContent(String dyContent) {
        this.dyContent = dyContent == null ? null : dyContent.trim();
    }

    public String getDyImage() {
        return dyImage;
    }

    public void setDyImage(String dyImage) {
        this.dyImage = dyImage == null ? null : dyImage.trim();
    }

    public String getDyLike() {
        return dyLike;
    }

    public void setDyLike(String dyLike) {
        this.dyLike = dyLike == null ? null : dyLike.trim();
    }

}