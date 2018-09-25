package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 大V排行榜请求
 * @author dql
 */

public class RankingListRequest implements IBaseRequest,Serializable {

    private static final long serialVersionUID = 1153007005272780983L;

    @NotBlank(message = "用户Id不能为空")
    private String userId;        //用户id
    
    private Integer type;          //0=周阅读排行 1=月阅读排行  2=周粉丝排行 3=月粉丝排行
    
    private Long[] industryId;  //用户行业类型数组(id)
    
    @Min(value = 0)
    private Integer page;       //显示页数
    
    @Min(value = 1)
    private Integer size;       //显示数量

    
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
     * @return Returns the type.
     */
    public Integer getType() {
        return type;
    }

    
    /**
     * @param type The type to set.
     */
    public void setType(Integer type) {
        this.type = type;
    }

    
    /**
     * @return Returns the industryId.
     */
    public Long[] getIndustryId() {
        return industryId;
    }

    
    /**
     * @param industryId The industryId to set.
     */
    public void setIndustryId(Long[] industryId) {
        this.industryId = industryId;
    }

    
    /**
     * @return Returns the page.
     */
    public Integer getPage() {
        return page;
    }

    
    /**
     * @param page The page to set.
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    
    /**
     * @return Returns the size.
     */
    public Integer getSize() {
        return size;
    }

    
    /**
     * @param size The size to set.
     */
    public void setSize(Integer size) {
        this.size = size;
    }
}
