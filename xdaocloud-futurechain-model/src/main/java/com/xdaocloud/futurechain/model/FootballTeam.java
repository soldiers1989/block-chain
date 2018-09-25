package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 足球队
 *
 * @author LuoFuMin
 * @date 2018/8/20
 */

public class FootballTeam implements Serializable {
    private static final long serialVersionUID = 3727371280429071560L;
    private Long id;

    /**
     * 队名
     */
    private String teamName;

    /**
     * 国家
     */
    private String country;

    /**
     * 教练
     */
    private String coach;

    /**
     * 教练头像
     */
    private String coachAvatar;

    /**
     * 队长
     */
    private String captain;

    /**
     * 队员
     */
    private String teamMember;

    /**
     * 替补
     */
    private String substitute;

    /**
     * 守门员
     */
    private String goalkeeper;

    /**
     * 国旗
     */
    private String nationalFlag;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach == null ? null : coach.trim();
    }

    public String getCoachAvatar() {
        return coachAvatar;
    }

    public void setCoachAvatar(String coachAvatar) {
        this.coachAvatar = coachAvatar;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain == null ? null : captain.trim();
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember == null ? null : teamMember.trim();
    }

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String substitute) {
        this.substitute = substitute == null ? null : substitute.trim();
    }

    public String getGoalkeeper() {
        return goalkeeper;
    }

    public void setGoalkeeper(String goalkeeper) {
        this.goalkeeper = goalkeeper == null ? null : goalkeeper.trim();
    }

    public String getNationalFlag() {
        return nationalFlag;
    }

    public void setNationalFlag(String nationalFlag) {
        this.nationalFlag = nationalFlag == null ? null : nationalFlag.trim();
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