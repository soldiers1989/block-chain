package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 比赛类
 *
 * @author LuoFuMin
 * @date 2018/8/20
 */

public class FootballMatch implements Serializable {
    private static final long serialVersionUID = -4932239976802346359L;
    private Long id;

    private Long userId;

    /**
     * A 队 id
     */
    private Long teamA;
    /**
     * B 队 id
     */
    private Long teamB;

    /**
     * A 队 队名
     */
    private String teamNameA;

    /**
     * B 队 队名
     */
    private String teamNameB;

    /**
     * 让球（-1表示a队让一球，+1表示b队让一球）
     */
    private Integer concede;

    /**
     * a球队进球数
     */
    private Integer scoreA;

    /**
     * b球队进球数
     */
    private Integer scoreB;

    /**
     * 常规  1：球队a赢球，2：球队b赢球，0：平局
     */
    private Integer win;

    /**
     * 让球  1：球队a赢球，2：球队b赢球，0：平局
     */
    private Integer concedeWin;

    /**
     * 1麦链杯，2链友杯
     */
    private Integer type;

    /**
     * 场地
     */
    private String field;

    /**
     * 自定义竞猜金额
     */
    private BigDecimal customSum;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    private String gmtCreate;

    private String gmtModified;

    private Boolean isDeleted;

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

    public Long getTeamA() {
        return teamA;
    }

    public void setTeamA(Long teamA) {
        this.teamA = teamA;
    }

    public Long getTeamB() {
        return teamB;
    }

    public void setTeamB(Long teamB) {
        this.teamB = teamB;
    }

    public String getTeamNameA() {
        return teamNameA;
    }

    public void setTeamNameA(String teamNameA) {
        this.teamNameA = teamNameA == null ? null : teamNameA.trim();
    }

    public String getTeamNameB() {
        return teamNameB;
    }

    public void setTeamNameB(String teamNameB) {
        this.teamNameB = teamNameB == null ? null : teamNameB.trim();
    }

    public Integer getConcede() {
        return concede;
    }

    public void setConcede(Integer concede) {
        this.concede = concede;
    }

    public Integer getScoreA() {
        return scoreA;
    }

    public void setScoreA(Integer scoreA) {
        this.scoreA = scoreA;
    }

    public Integer getScoreB() {
        return scoreB;
    }

    public void setScoreB(Integer scoreB) {
        this.scoreB = scoreB;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getConcedeWin() {
        return concedeWin;
    }

    public void setConcedeWin(Integer concedeWin) {
        this.concedeWin = concedeWin;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public BigDecimal getCustomSum() {
        return customSum;
    }

    public void setCustomSum(BigDecimal customSum) {
        this.customSum = customSum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
}