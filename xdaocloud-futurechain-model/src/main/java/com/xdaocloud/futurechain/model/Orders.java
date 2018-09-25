package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 赞赏订单
 */
public class Orders implements Serializable {

    private static final long serialVersionUID = -635097573779926409L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

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

    public Orders() {
    }

    public Orders(Long userId, String orderNo, BigDecimal amount, BigDecimal fee, String productCode, Byte state) {
        this.userId = userId;
        this.orderNo = orderNo;
        this.amount = amount;
        this.fee = fee;
        this.productCode = productCode;
        this.state = state;
    }

    public Orders(Long userId, String orderNo, String paymentType, BigDecimal buyPrice, BigDecimal amount, BigDecimal fee, String productCode, Byte state) {
        this.userId = userId;
        this.orderNo = orderNo;
        this.paymentType = paymentType;
        this.buyPrice = buyPrice;
        this.amount = amount;
        this.fee = fee;
        this.productCode = productCode;
        this.state = state;
    }

    public Orders(Long userId, String orderNo, String paymentType, BigDecimal buyPrice, BigDecimal amount, BigDecimal fee, String productCode, Byte state, String tradeDesc) {
        this.userId = userId;
        this.orderNo = orderNo;
        this.paymentType = paymentType;
        this.buyPrice = buyPrice;
        this.amount = amount;
        this.fee = fee;
        this.productCode = productCode;
        this.state = state;
        this.tradeDesc = tradeDesc;
    }

    public Orders(Long id, Long userId, String orderNo, String tradeNo, String paymentType, BigDecimal amount, BigDecimal fee, String productCode, Byte state, Date gmtCreate, Date gmtModified, Boolean isDeleted) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.tradeNo = tradeNo;
        this.paymentType = paymentType;
        this.amount = amount;
        this.fee = fee;
        this.productCode = productCode;
        this.state = state;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.isDeleted = isDeleted;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
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
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
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

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }
}