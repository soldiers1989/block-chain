package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

public class SystemSetup implements Serializable {

    private static final long serialVersionUID = 2459835763554025272L;

    private Integer id;

    /**
     * 开关名称
     */
    private String keyname;

    /**
     *开关状态
     */
    private Integer keyvalue;

    /**
     *开关说明
     */
    private String explain;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname == null ? null : keyname.trim();
    }

    public Integer getKeyvalue() {
        return keyvalue;
    }

    public void setKeyvalue(Integer keyvalue) {
        this.keyvalue = keyvalue;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}