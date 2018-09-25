package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户行业
 *
 * @author LiMaoDao
 * @date 2018/8/20
 */

public class Industry implements Serializable {
    private static final long serialVersionUID = -2416796784790979828L;

    private Long id;

    /**
     * 行业类型
     */
    private String industryType;

    /**
     * 行业名称
     */
    private String industryName;

    /**
     * 颜色
     */
    private String industryColor;

    /**
     * 上级ID
     */
    private Long parentId;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType == null ? null : industryType.trim();
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName == null ? null : industryName.trim();
    }

    public String getIndustryColor() {
        return industryColor;
    }

    public void setIndustryColor(String industryColor) {
        this.industryColor = industryColor == null ? null : industryColor.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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