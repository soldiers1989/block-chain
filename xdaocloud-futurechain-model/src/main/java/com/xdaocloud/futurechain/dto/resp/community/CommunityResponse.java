package com.xdaocloud.futurechain.dto.resp.community;

import com.xdaocloud.futurechain.model.Group;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建社群返回
 *
 * @author LuoFuMin
 * @data 2018/9/5
 */
public class CommunityResponse implements Serializable {

    private static final long serialVersionUID = 4012535159188418656L;

    private Long id;

    /**
     * 应用id
     */
    private String appIp;

    /**
     * 社群头像
     */
    private String communityAvatar;

    /**
     * 社区编号
     */
    private Integer communityNo;

    /**
     * 社群公告
     */
    private String communityNotice;

    /**
     * 创建者id
     */
    private Long userId;

    /**
     * 社区最高管理者id，可以转让
     */
    private Long proprieterId;

    /**
     * 社区名称
     */
    private String communityName;

    /**
     * 限制人数（预留字段）
     */
    private Integer restrictNum;

    /**
     * 存在人数（预留字段）
     */
    private Integer number;

    /**
     * 真实人数（预留字段）
     */
    private Integer realNumber;

    /**
     * 备注信息
     */
    private String remarks;


    private List<Group> groups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCommunityAvatar() {
        return communityAvatar;
    }

    public void setCommunityAvatar(String communityAvatar) {
        this.communityAvatar = communityAvatar;
    }

    public Integer getCommunityNo() {
        return communityNo;
    }

    public void setCommunityNo(Integer communityNo) {
        this.communityNo = communityNo;
    }

    public String getCommunityNotice() {
        return communityNotice;
    }

    public void setCommunityNotice(String communityNotice) {
        this.communityNotice = communityNotice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProprieterId() {
        return proprieterId;
    }

    public void setProprieterId(Long proprieterId) {
        this.proprieterId = proprieterId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Integer getRestrictNum() {
        return restrictNum;
    }

    public void setRestrictNum(Integer restrictNum) {
        this.restrictNum = restrictNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAppIp() {
        return appIp;
    }

    public void setAppIp(String appIp) {
        this.appIp = appIp;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getRealNumber() {
        return realNumber;
    }

    public void setRealNumber(Integer realNumber) {
        this.realNumber = realNumber;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
