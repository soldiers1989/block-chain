package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 群
 */
public class Group implements Serializable {

    private static final long serialVersionUID = 2647509083458394304L;

    private Long id;

    /**
     * 行业
     */
    private Long industryId;

    private String appId;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 没有子群的 默认为 0
     */
    private Long parentId;

    /**
     * 群类型：0 社群，1普通群
     */
    private Byte groupType;
    /**
     * 群id
     */
    private String groupId;

    /**
     * 群名
     */
    private String groupName;

    /**
     * 群公告
     */
    private String groupNotice;

    /**
     * 群简介
     */
    private String groupIntro;

    /**
     * 群主
     */
    private String owner;

    /**
     * 管理员（json数组）
     */
    private String manager;

    /**
     * 群头像
     */
    private String groupAvatar;

    /**
     * 限制人数
     */
    private Integer restrictNum;

    private String gmtCreate;

    private String gmtModified;

    private Boolean isDeleted;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupNotice() {
        return groupNotice;
    }

    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice == null ? null : groupNotice.trim();
    }

    public Byte getGroupType() {
        return groupType;
    }

    public void setGroupType(Byte groupType) {
        this.groupType = groupType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getGroupAvatar() {
        return groupAvatar;
    }

    public void setGroupAvatar(String groupAvatar) {
        this.groupAvatar = groupAvatar;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Integer getRestrictNum() {
        return restrictNum;
    }

    public void setRestrictNum(Integer restrictNum) {
        this.restrictNum = restrictNum;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public String getGroupIntro() {
        return groupIntro;
    }

    public void setGroupIntro(String groupIntro) {
        this.groupIntro = groupIntro;
    }
}