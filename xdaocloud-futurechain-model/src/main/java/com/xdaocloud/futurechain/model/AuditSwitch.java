package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 开关
 *
 * @author LiMaoDao
 * @date 2018/8/20
 */

public class AuditSwitch implements Serializable {
    private static final long serialVersionUID = -8106384150614215100L;
    private Long id;

    /**
     * 类型(0:文字  1:图片  2:视频)
     */
    private Boolean type;

    /**
     * 审核模式(0:自动审核   1:人工审核)
     */
    private Boolean mode;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean mode) {
        this.mode = mode;
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