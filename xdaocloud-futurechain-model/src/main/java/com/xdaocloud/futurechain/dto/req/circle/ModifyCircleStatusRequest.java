/**
 * 
 */
package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 上下架朋友圈
 * @author dql
 */

public class ModifyCircleStatusRequest implements IBaseRequest,Serializable{
    
    @NotBlank(message = "用户Id不能为空")
    private String userId;    //用户id
    
    @NotBlank(message = "朋友圈Id不能为空")
    private String circleId;    //朋友圈id
    
    private boolean status;    //false=上架  true=下架
    
    
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
     * @return 获取上下架状态  0/false:上架  1/true:下架
     */
    public boolean getStatus() {
        return status;
    }

    
    /**
     *  设置上下架状态  0/false:上架  1/true:下架
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
