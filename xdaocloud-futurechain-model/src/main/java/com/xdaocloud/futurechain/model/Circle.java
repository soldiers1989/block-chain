package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 朋友圈
 *
 * @author LiMaoDao
 * @date 2018/8/20
 */

public class Circle implements Serializable {
    private static final long serialVersionUID = 4381525502160064346L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    private String appId;

    /**
     * 内容
     */
    private String content;

    /**
     * 行业类别id
     */
    private Long industryId;

    /**
     * json对象{"address1":"文件地址","address2":"文件地址''}
     */
    private String fileAddress;

    /**
     * 总共奖励用户数
     */
    private Integer rewardsUserNumber;

    /**
     * 单个用户奖励数量
     */
    private BigDecimal rewardsNumber;

    /**
     * 剩余奖励个数
     */
    private Integer surplusRewardsNumber;

    /**
     * 扣除阅读者用户麦钻数量
     */
    private BigDecimal deductionNumber;

    /**
     * 文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)
     */
    private Byte articleType;

    /**
     * 文章类型(0:原文  1:转发)
     */
    private Boolean articleCategory;

    /**
     * 原文朋友圈id(原文情况下为null)
     */
    private Long circleId;

    /**
     * 转发感想(原文为null)
     */
    private String feelings;

    /**
     * 检查结果(0:待审核 1:审核通过 2:审核不通过)
     */
    private Byte auditResult;

    /**
     * 朋友圈状态(0:上架  1:下架)
     */
    private Boolean articleStatus;

    /**
     * 总转发数
     */
    private Long totalRetransmission;

    /**
     * 总评论数
     */
    private Long totalDiscuss;

    /**
     * 总点赞数
     */
    private Long totalPraise;

    /**
     * 总阅读数
     */
    private Long totalReading;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress == null ? null : fileAddress.trim();
    }

    public Integer getRewardsUserNumber() {
        return rewardsUserNumber;
    }

    public void setRewardsUserNumber(Integer rewardsUserNumber) {
        this.rewardsUserNumber = rewardsUserNumber;
    }

    public BigDecimal getRewardsNumber() {
        return rewardsNumber;
    }

    public void setRewardsNumber(BigDecimal rewardsNumber) {
        this.rewardsNumber = rewardsNumber;
    }

    public Integer getSurplusRewardsNumber() {
        return surplusRewardsNumber;
    }

    public void setSurplusRewardsNumber(Integer surplusRewardsNumber) {
        this.surplusRewardsNumber = surplusRewardsNumber;
    }

    public BigDecimal getDeductionNumber() {
        return deductionNumber;
    }

    public void setDeductionNumber(BigDecimal deductionNumber) {
        this.deductionNumber = deductionNumber;
    }

    public Byte getArticleType() {
        return articleType;
    }

    public void setArticleType(Byte articleType) {
        this.articleType = articleType;
    }

    public Boolean getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(Boolean articleCategory) {
        this.articleCategory = articleCategory;
    }

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public String getFeelings() {
        return feelings;
    }

    public void setFeelings(String feelings) {
        this.feelings = feelings == null ? null : feelings.trim();
    }

    public Byte getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(Byte auditResult) {
        this.auditResult = auditResult;
    }

    public Boolean getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Boolean articleStatus) {
        this.articleStatus = articleStatus;
    }

    public Long getTotalRetransmission() {
        return totalRetransmission;
    }

    public void setTotalRetransmission(Long totalRetransmission) {
        this.totalRetransmission = totalRetransmission;
    }

    public Long getTotalDiscuss() {
        return totalDiscuss;
    }

    public void setTotalDiscuss(Long totalDiscuss) {
        this.totalDiscuss = totalDiscuss;
    }

    public Long getTotalPraise() {
        return totalPraise;
    }

    public void setTotalPraise(Long totalPraise) {
        this.totalPraise = totalPraise;
    }

    public Long getTotalReading() {
        return totalReading;
    }

    public void setTotalReading(Long totalReading) {
        this.totalReading = totalReading;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}