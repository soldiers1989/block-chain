package com.xdaocloud.futurechain.dto.req.football;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public class AddOrdersRequest implements IBaseRequest {

    @NotBlank(message = "用户id不能为空")
    private String userid;


    private String transactionPassword;


    private List<MatchOrderDTO> order;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public void setTransactionPassword(String transactionPassword) {
        this.transactionPassword = transactionPassword;
    }

    public List<MatchOrderDTO> getOrder() {
        return order;
    }

    public void setOrder(List<MatchOrderDTO> order) {
        this.order = order;
    }
}
