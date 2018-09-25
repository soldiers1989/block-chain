package com.xdaocloud.futurechain.dto.resp.eos;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;
import java.math.BigDecimal;

public class UnlockEosInfo implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -6770632755772309940L;

    private Long id;

    private Long userId;

    private String name;

    private String nickname;

    private String mobileNumber;

    private String unlockTime;

    private BigDecimal unlockAmount;

    private BigDecimal lockAmount;


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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(String unlockTime) {
        this.unlockTime = unlockTime;
    }

    public BigDecimal getUnlockAmount() {
        return unlockAmount;
    }

    public void setUnlockAmount(BigDecimal unlockAmount) {
        this.unlockAmount = unlockAmount;
    }

    public BigDecimal getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(BigDecimal lockAmount) {
        this.lockAmount = lockAmount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
