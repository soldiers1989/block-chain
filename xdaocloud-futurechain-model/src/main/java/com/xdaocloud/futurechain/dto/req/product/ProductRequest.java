package com.xdaocloud.futurechain.dto.req.product;


import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -4215138573869423197L;
    /**
     * 商品编码
     */
    @NotBlank(message = "商品编码不能为空")
    private String productCode;

    /**
     * 购买数量
     */
    private BigDecimal amount;

    private String money;
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank(message = "付款方式不能为空")
    private String payment;

    private String spbillCreateIp;

    private String redirect_url;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }
}
