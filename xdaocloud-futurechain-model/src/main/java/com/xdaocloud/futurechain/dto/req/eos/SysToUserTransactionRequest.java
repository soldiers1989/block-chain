package com.xdaocloud.futurechain.dto.req.eos;

import com.xdaocloud.futurechain.common.IBaseRequest;

public class SysToUserTransactionRequest implements IBaseRequest {

    /**
     * 手机
     */
    private String mobileNumber;

    /**
     * 钱包名
     */
    private String eosWallet;

    /**
     * 金额
     */
    private String money;

    /**
     * 赞赏方式
     */
    private String desc;

    /**
     * 单价
     */
    private String price;

    /**
     * 是否锁仓
     */
    private Boolean lock;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEosWallet() {
        return eosWallet;
    }

    public void setEosWallet(String eosWallet) {
        this.eosWallet = eosWallet;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }
}
