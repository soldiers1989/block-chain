package com.xdaocloud.futurechain.dto.req.eos;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class SetQuotaRequest implements IBaseRequest {

    /**
     * 金额
     */
    private String amount;

    @NotBlank(message = "用户id不能为空")
    private String userid;

    /**
     * 是否激活
     */
    private Integer activate;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getActivate() {
        return activate;
    }

    public void setActivate(Integer activate) {
        this.activate = activate;
    }
}
