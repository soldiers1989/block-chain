package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VieOrders implements Serializable {
    private static final long serialVersionUID = 7437402105908384442L;
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 比赛id
     */
    private Long matchId;

    /**
     * 胜：1、负：-1、平：0
     */
    private Integer vorl;

    /**
     * 购买数量
     */
    private BigDecimal amount;


    /**
     * 赢得eos
     */
    private BigDecimal winEos;

    /**
     * 交易信息
     */
    private String tradeDesc;

    /**
     * 订单状态（0：未支付，1支付成功，2支付失败，3退款成功，4：订单关闭，5：支付错误，6.取消下注并成功退款，比赛开始前5分钟不能取消或修改），7.没买中，8.买中并已获得奖金，9.结款错误,需要重新结款
     */
    private Byte state;

    private Date gmtCreate;

    private Date gmtModified;

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

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getVorl() {
        return vorl;
    }

    public void setVorl(Integer vorl) {
        this.vorl = vorl;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc == null ? null : tradeDesc.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public BigDecimal getWinEos() {
        return winEos;
    }

    public void setWinEos(BigDecimal winEos) {
        this.winEos = winEos;
    }
}