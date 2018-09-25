package com.xdaocloud.futurechain.dto.resp.circle;

import java.util.Date;

/**
 * 朋友圈列表展示数据
 * @author dql
 */
public class CircleDisplayResponse {
    
    private Long id;                    //朋友圈id
    
    private Long userId;                //用户id

    private String userName;            //用户名
    
    private boolean readed;             //用户是否已阅读该文章-
    
    private boolean isForwarding;       //是否为转发文章

    private boolean collect;       //是否收藏
    
    private String tagName;             //文章标签
    
    private String content;             //正文文本
    
    private String forwardingContent;   //转发感想
    
    private String userHead;            //发表,转发用户的头像
    
    private String[] images;            //图片
    
    private String  fileAddress;        //图片原地址
    
    private String price;               //阅读价格
    
    private Long forwarding;            //转发数
    
    private Long discuss;               //评论数
    
    private Long good;                  //点赞数
    
    private Long readNum;               //阅读数-月
    
    private Long readFriendNum;         //阅读本文的好友数-
    
    private Integer userLevel;           //用户等级-
    
    private Integer status;              //发布状态:0:待审核 1:审核通过 2:审核不通过
    
    private Long originalId;             //原文朋友圈id

    private Date gmtModified;           //文章更新时间

    private Integer rewardsUserNumber; //奖励用户
    
    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    
    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * @return Returns the userId.
     */
    public Long getUserId() {
        return userId;
    }

    
    /**
     * @param userId The userId to set.
     */
    public void setUserid(Long userId) {
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
     * @return Returns the readed.
     */
    public boolean getReaded() {
        return readed;
    }

    
    /**
     * @param readed The readed to set.
     */
    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    
    /**
     * @return Returns the isForwarding.
     */
    public boolean getIsForwarding() {
        return isForwarding;
    }

    
    /**
     * @param isForwarding The isForwarding to set.
     */
    public void setIsForwarding(boolean isForwarding) {
        this.isForwarding = isForwarding;
    }

    
    /**
     * @return Returns the tagName.
     */
    public String getTagName() {
        return tagName;
    }

    
    /**
     * @param tagName The tagName to set.
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    
    /**
     * @return Returns the content.
     */
    public String getContent() {
        return content;
    }

    
    /**
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    
    /**
     * @return Returns the forwardingContent.
     */
    public String getForwardingContent() {
        return forwardingContent;
    }

    
    /**
     * @param forwardingContent The forwardingContent to set.
     */
    public void setForwardingContent(String forwardingContent) {
        this.forwardingContent = forwardingContent;
    }

    
    /**
     * @return Returns the userHead.
     */
    public String getUserHead() {
        return userHead;
    }

    
    /**
     * @param userHead The userHead to set.
     */
    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    
    /**
     * @return Returns the images.
     */
    public String[] getImages() {
        return images;
    }

    
    /**
     * @param images The images to set.
     */
    public void setImages(String[] images) {
        this.images = images;
    }

    
    /**
     * @return Returns the price.
     */
    public String getPrice() {
        return price;
    }

    
    /**
     * @param price The price to set.
     */
    public void setPrice(String price) {
        this.price = price;
    }

    
    /**
     * @return Returns the forwarding.
     */
    public Long getForwarding() {
        return forwarding;
    }

    
    /**
     * @param forwarding The forwarding to set.
     */
    public void setForwarding(Long forwarding) {
        this.forwarding = forwarding;
    }

    
    /**
     * @return Returns the discuss.
     */
    public Long getDiscuss() {
        return discuss;
    }

    
    /**
     * @param discuss The discuss to set.
     */
    public void setDiscuss(Long discuss) {
        this.discuss = discuss;
    }

    
    /**
     * @return Returns the good.
     */
    public Long getGood() {
        return good;
    }

    
    /**
     * @param good The good to set.
     */
    public void setGood(Long good) {
        this.good = good;
    }

    
    /**
     * @return Returns the readNum.
     */
    public Long getReadNum() {
        return readNum;
    }

    
    /**
     * @param readNum The readNum to set.
     */
    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    
    /**
     * @return Returns the readFriendNum.
     */
    public Long getReadFriendNum() {
        return readFriendNum;
    }

    
    /**
     * @param readFriendNum The readFriendNum to set.
     */
    public void setReadFriendNum(Long readFriendNum) {
        this.readFriendNum = readFriendNum;
    }

    
    /**
     * @return Returns the userLevel.
     */
    public Integer getUserLevel() {
        return userLevel;
    }

    
    /**
     * @param userLevel The userLevel to set.
     */
    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }


    
    /**
     * @return 获取朋友圈状态:0:待审核 1:审核通过 2:审核不通过
     */
    public Integer getStatus() {
        return status;
    }


    
    /**
     * @param :0:待审核 1:审核通过 2:审核不通过
     */
    public void setStatus(Integer status) {
        this.status = status;
    }


    
    /**
     * @return Returns the originalId.
     */
    public Long getOriginalId() {
        return originalId;
    }


    
    /**
     * @param originalId The originalId to set.
     */
    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }


    
    /**
     * @return Returns the fileAddress.
     */
    public String getFileAddress() {
        return fileAddress;
    }


    
    /**
     * @param fileAddress The fileAddress to set.
     */
    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }


    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getRewardsUserNumber() {
        return rewardsUserNumber;
    }

    public void setRewardsUserNumber(Integer rewardsUserNumber) {
        this.rewardsUserNumber = rewardsUserNumber;
    }
}
