package com.xdaocloud.futurechain.dto.resp.circle;


/**
 * 关注好友信息
 * @author dql
 */

public class FollowResponse {
    
    private Long userId;
    
    private String headurl;
    
    private String name;
    
    private String motto;

    private Integer level;

    
    /**
     * @return Returns the userId.
     */
    public Long getUserId() {
        return userId;
    }

    
    /**
     * @param userId The userId to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    /**
     * @return Returns the headurl.
     */
    public String getHeadurl() {
        return headurl;
    }

    
    /**
     * @param headurl The headurl to set.
     */
    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * @return Returns the motto.
     */
    public String getMotto() {
        return motto;
    }

    
    /**
     * @param motto The motto to set.
     */
    public void setMotto(String motto) {
        this.motto = motto;
    }

    
    /**
     * @return Returns the level.
     */
    public Integer getLevel() {
        return level;
    }

    
    /**
     * @param level The level to set.
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    
}
