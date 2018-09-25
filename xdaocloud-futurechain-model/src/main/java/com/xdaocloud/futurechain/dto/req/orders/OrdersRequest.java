package com.xdaocloud.futurechain.dto.req.orders;


import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class OrdersRequest implements IBaseRequest,Serializable{
    private static final long serialVersionUID = 2025581262036940116L;

    private String payment;

    @Pattern(message = "不能有特殊字符", regexp = "[A-Za-z0-9]{0,16}")
    private String  orderNo;

    private String redirect_url;

    private String spbillCreateIp;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }
}
