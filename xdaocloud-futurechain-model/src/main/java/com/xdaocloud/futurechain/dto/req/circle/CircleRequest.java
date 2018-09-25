package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 基本朋友圈类型
 * @author dql
 */
public class CircleRequest implements IBaseRequest,Serializable{

    private static final long serialVersionUID = 2611759864115993602L;

    @NotBlank(message = "用户id不能为空")
    private String userId;
    
    @NotBlank(message = "朋友圈id不能为空")
    private String circleId;

    /**
     * @return Returns the userId.
     */
    public String getUserId() {
        return userId;
    }


    
    /**
     * @param userId The userId to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * @return Returns the circleId.
     */
    public String getCircleId() {
        return circleId;
    }

    
    /**
     * @param circleId The circleId to set.
     */
    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }
    
    
}
