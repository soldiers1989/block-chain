package com.xdaocloud.futurechain.dto.user;

import java.io.Serializable;
import java.math.BigDecimal;

public class AssetsDTO implements Serializable{
    private static final long serialVersionUID = 5645799654962683363L;

    /**
     * 可用eos
     */
    private BigDecimal freeEos;


    /**
     * 锁定eos
     */
    private BigDecimal lockEos;

    /**
     * 总和eos
     */
    private BigDecimal sumEos;



    /**
     * 赞赏总金额
     */
    private BigDecimal buySumRmb;


    /**
     * 赞赏eos总数量
     */
    private BigDecimal buySumEos;

    public BigDecimal getFreeEos() {
        return freeEos;
    }

    public void setFreeEos(BigDecimal freeEos) {
        this.freeEos = freeEos;
    }

    public BigDecimal getLockEos() {
        return lockEos;
    }

    public void setLockEos(BigDecimal lockEos) {
        this.lockEos = lockEos;
    }

    public BigDecimal getSumEos() {
        return sumEos;
    }

    public void setSumEos(BigDecimal sumEos) {
        this.sumEos = sumEos;
    }

    public BigDecimal getBuySumRmb() {
        return buySumRmb;
    }

    public void setBuySumRmb(BigDecimal buySumRmb) {
        this.buySumRmb = buySumRmb;
    }

    public BigDecimal getBuySumEos() {
        return buySumEos;
    }

    public void setBuySumEos(BigDecimal buySumEos) {
        this.buySumEos = buySumEos;
    }
}
