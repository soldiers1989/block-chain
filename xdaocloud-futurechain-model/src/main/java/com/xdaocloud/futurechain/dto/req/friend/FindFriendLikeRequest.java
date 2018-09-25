package com.xdaocloud.futurechain.dto.req.friend;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class FindFriendLikeRequest implements IBaseRequest{

    @NotBlank(message = "用户id不能为空")
    private String userid;

    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
