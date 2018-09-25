package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告
 */
public class Notice implements Serializable {

    private static final long serialVersionUID = -3589022197894979496L;

    private Long id;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 公告内容
     */
    private String content;
    /**
     * 跳转链接
     */
    private String url;

    /**
     * 优先级（0~255），数值越小优先级越高
     */
    private Byte priority;
    /**
     * 生效时间
     */
    private Date enableTime;
    /**
     * 失效时间
     */
    private Date unableTime;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public Date getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public Date getUnableTime() {
        return unableTime;
    }

    public void setUnableTime(Date unableTime) {
        this.unableTime = unableTime;
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