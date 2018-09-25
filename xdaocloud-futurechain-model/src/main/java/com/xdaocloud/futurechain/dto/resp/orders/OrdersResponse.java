package com.xdaocloud.futurechain.dto.resp.orders;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrdersResponse implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 1717715439402187515L;


    private Long id;

    private Long userId;

    private String nickname;

    /**
     * 内部订单号
     */
    private String orderNo;

    /**
     * 第三方订单号
     */
    private String tradeNo;

    /**
     * 付款方式
     */
    private String paymentType;

    /**
     * 买入价格
     */
    private BigDecimal buyPrice;

    /**
     * 购买数量
     */
    private BigDecimal amount;

    /**
     * 订单金额
     */
    private BigDecimal fee;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 订单状态（0：未支付，1支付成功，2支付失败，3退款成功，4：订单关闭，5：支付错误，6：订单金额与实际付款金额不匹配）
     */
    private Byte state;

    /**
     * 订单交易信息
     */
    private String tradeDesc;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
