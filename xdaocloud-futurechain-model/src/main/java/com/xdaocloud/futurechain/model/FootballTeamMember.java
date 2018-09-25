package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 足球队成员
 *
 * @author LuoFuMin
 * @date 2018/8/20
 */

public class FootballTeamMember implements Serializable {
    private static final long serialVersionUID = -6658055951470045074L;
    private Long id;

    private Long footballTeamId;

    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 号码
     */
    private Integer ballNumber;

    /**
     * 位置
     */
    private String courtPosition;

    /**
     * 10-队长，1-队员，0-替补
     */
    private String identity;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFootballTeamId() {
        return footballTeamId;
    }

    public void setFootballTeamId(Long footballTeamId) {
        this.footballTeamId = footballTeamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Integer getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(Integer ballNumber) {
        this.ballNumber = ballNumber;
    }

    public String getCourtPosition() {
        return courtPosition;
    }

    public void setCourtPosition(String courtPosition) {
        this.courtPosition = courtPosition == null ? null : courtPosition.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
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