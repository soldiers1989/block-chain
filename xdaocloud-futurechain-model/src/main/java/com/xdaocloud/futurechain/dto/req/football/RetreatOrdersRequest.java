package com.xdaocloud.futurechain.dto.req.football;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class RetreatOrdersRequest implements IBaseRequest {

    @NotBlank(message = "用户id不能为空")
    private String userid;
    @NotBlank
    private String orderId;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
