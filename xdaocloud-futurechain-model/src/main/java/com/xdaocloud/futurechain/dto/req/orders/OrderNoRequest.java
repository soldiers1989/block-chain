package com.xdaocloud.futurechain.dto.req.orders;


import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.Pattern;

public class OrderNoRequest implements IBaseRequest {
    
    @Pattern(message = "不能有特殊字符", regexp = "[A-Za-z0-9]{0,16}")
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }
}
