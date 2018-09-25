package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 朋友圈首页 (热门/关注/好友)
 * @author dql
 */

public class CircleListRequest implements IBaseRequest,Serializable{

    private static final long serialVersionUID = 6480693430568708103L;

    @NotBlank(message = "用户id不能为空")
    private String userId;            //用户id
    
    private Integer type;           //类型:0=热门朋友圈  1=好友朋友圈  2=关注朋友圈
    
    private Long [] industryId;     //朋友圈类型数组
    
    private String searchContent;   //搜索内容
    
    @Min(value = 0)
    private Integer page;           //页数(默认是1)
    
    @Min(value = 1)
    private Integer size;           //每页显示数量

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
     * @return Returns the searchContent.
     */
    public String getSearchContent() {
        return searchContent;
    }

    
    /**
     * @param searchContent The searchContent to set.
     */
    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
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
