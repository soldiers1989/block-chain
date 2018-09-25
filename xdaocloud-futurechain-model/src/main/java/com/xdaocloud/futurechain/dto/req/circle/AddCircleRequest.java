package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class AddCircleRequest implements IBaseRequest, Serializable{
    private static final long serialVersionUID = -734043190487252876L;

    @NotBlank(message = "用户Id不能为空")
    private String userId;


    @NotBlank(message = "脉圈Id不能为空")
    private String circleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }
}
