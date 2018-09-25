package com.xdaocloud.futurechain.model;


import java.io.Serializable;

/**
 * 签到
 */
public class SignIn implements Serializable {
    private static final long serialVersionUID = -7050567737634689489L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 连续 签到
     */
    private Integer continuity;

    /**
     * 奖励麦粒数量
     */
    private Integer reward;

    private String gmtCreate;

    private String gmtModified;

    private Boolean isDeleted;

    public SignIn() {
    }

    public SignIn(Long userId, Integer continuity, Integer reward) {
        this.userId = userId;
        this.continuity = continuity;
        this.reward = reward;
    }

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

    public Integer getContinuity() {
        return continuity;
    }

    public void setContinuity(Integer continuity) {
        this.continuity = continuity;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
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