package com.xdaocloud.futurechain.dto.orders;

import java.io.Serializable;
import java.math.BigDecimal;

public class BuyRecordDTO implements Serializable{
    private static final long serialVersionUID = -1282693641680247068L;

    /**
     * 购买记录id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 购买eos的数量
     */
    private BigDecimal eosSum;

    /**
     * 实际金额
     */
    private BigDecimal moneySum;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String mobileNumber;

    /**
     * 购买时间
     */
    private String buyTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }

    public BigDecimal getEosSum() {
        return eosSum;
    }

    public void setEosSum(BigDecimal eosSum) {
        this.eosSum = eosSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
