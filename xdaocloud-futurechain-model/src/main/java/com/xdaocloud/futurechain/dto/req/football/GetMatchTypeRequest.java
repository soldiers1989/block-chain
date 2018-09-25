package com.xdaocloud.futurechain.dto.req.football;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class GetMatchTypeRequest implements IBaseRequest {

    @NotBlank(message = "用户id不能为空")
    private String userid;

    private Integer type;

    private Integer status;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
