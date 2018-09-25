package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 举报
 * @author dql
 */

public class ReportRequest implements IBaseRequest,Serializable{

    private static final long serialVersionUID = -8362080095521486417L;
    
    @NotBlank(message = "用户Id不能为空")
    private String userId;            //用户id
    
    @NotBlank(message = "朋友圈Id不能为空")
    private String circleId;          //朋友圈id
    
    private String repContent;      //举报内容
  
    
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
     * @return Returns the repContent.
     */
    public String getRepContent() {
        return repContent;
    }

    
    /**
     * @param repContent The repContent to set.
     */
    public void setRepContent(String repContent) {
        this.repContent = repContent;
    }

}
