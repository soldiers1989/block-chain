package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 收藏
 * 
 * @author dql
 */

public class CollectRequest implements IBaseRequest,Serializable{

    private static final long serialVersionUID = -8209376535959510375L;

    @NotBlank(message = "用户id不能为空")
    private String userId;

    @NotBlank(message = "朋友圈id不能为空")
    private String circleId;
    
    private boolean isCollect;
    
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


    /**
     * @return Returns the isCollect.
     */
    public boolean getIsCollect() {
        return isCollect;
    }

    
    /**
     * @param isCollect The isCollect to set.
     */
    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }
    
    
}
