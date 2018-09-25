package com.xdaocloud.futurechain.dto.resp.circle;


/**
 * 大V排行榜
 * @author dql
 */

public class RankingResponse {
    
    private Long userId;                //用户id

    private String userName;            //用户名
    
    private String headUrl;             //用户头像
    
    private Integer level;               //用户等级
    
    private Long fansNumWeek;           //粉丝周增加数
    
    private Long fansNumMonth;          //粉丝月增加数
    
    private Long fansNum;               //粉丝总数
    
    private Long firendFansNum;         //好友关注数
    
    private Long readCountWeek;         //阅读周增加数
    
    private Long readCountMonth;        //阅读月增加数
    
    private Long readCount;             //阅读总数
    
    private Long articleCount;          //发布文章总数
    
    private boolean attentioned;        //是否已关注

    
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
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }

    
    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    /**
     * @return Returns the headUrl.
     */
    public String getHeadUrl() {
        return headUrl;
    }

    
    /**
     * @param headUrl The headUrl to set.
     */
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
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

    
    /**
     * @return Returns the fansNumWeek.
     */
    public Long getFansNumWeek() {
        return fansNumWeek;
    }

    
    /**
     * @param fansNumWeek The fansNumWeek to set.
     */
    public void setFansNumWeek(Long fansNumWeek) {
        this.fansNumWeek = fansNumWeek;
    }

    
    /**
     * @return Returns the fansNumMonth.
     */
    public Long getFansNumMonth() {
        return fansNumMonth;
    }

    
    /**
     * @param fansNumMonth The fansNumMonth to set.
     */
    public void setFansNumMonth(Long fansNumMonth) {
        this.fansNumMonth = fansNumMonth;
    }

    
    /**
     * @return Returns the fansNum.
     */
    public Long getFansNum() {
        return fansNum;
    }

    
    /**
     * @param fansNum The fansNum to set.
     */
    public void setFansNum(Long fansNum) {
        this.fansNum = fansNum;
    }

    
    /**
     * @return Returns the firendFansNum.
     */
    public Long getFirendFansNum() {
        return firendFansNum;
    }

    
    /**
     * @param firendFansNum The firendFansNum to set.
     */
    public void setFirendFansNum(Long firendFansNum) {
        this.firendFansNum = firendFansNum;
    }

    
    /**
     * @return Returns the readCountWeek.
     */
    public Long getReadCountWeek() {
        return readCountWeek;
    }

    
    /**
     * @param readCountWeek The readCountWeek to set.
     */
    public void setReadCountWeek(Long readCountWeek) {
        this.readCountWeek = readCountWeek;
    }

    
    /**
     * @return Returns the readCountMonth.
     */
    public Long getReadCountMonth() {
        return readCountMonth;
    }

    
    /**
     * @param readCountMonth The readCountMonth to set.
     */
    public void setReadCountMonth(Long readCountMonth) {
        this.readCountMonth = readCountMonth;
    }

    
    /**
     * @return Returns the readCount.
     */
    public Long getReadCount() {
        return readCount;
    }

    
    /**
     * @param readCount The readCount to set.
     */
    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

    
    /**
     * @return Returns the articleCount.
     */
    public Long getArticleCount() {
        return articleCount;
    }

    
    /**
     * @param articleCount The articleCount to set.
     */
    public void setArticleCount(Long articleCount) {
        this.articleCount = articleCount;
    }

    
    /**
     * @return Returns the attentioned.
     */
    public boolean getAttentioned() {
        return attentioned;
    }

    
    /**
     * @param attentioned The attentioned to set.
     */
    public void setAttentioned(boolean attentioned) {
        this.attentioned = attentioned;
    }
    
    
}
