package com.xdaocloud.futurechain.dto.req.exchange;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class GetAchievementABRequest implements Serializable {

    private static final long serialVersionUID = -7843707131526540167L;

    @NotBlank(message = "用户id不能为空")
    private String userid;

    /**
     * 1代表一级，2代表二级
     */
    private Integer type;

    public GetAchievementABRequest() {
    }

    public GetAchievementABRequest(String userid, Integer type) {
        this.userid = userid;
        this.type = type;
    }

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
}
