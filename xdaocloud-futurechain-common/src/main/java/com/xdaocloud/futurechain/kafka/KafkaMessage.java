package com.xdaocloud.futurechain.kafka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 推送
 * 
 * @author LiDong
 */
public class KafkaMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 发送人id
     */
    private String fromUserId;

    /**
     * 系统标识
     */
    private String sysCode;

    /**
     * APP名称
     */
    private String appCode;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 标题
     */
    private String title;

    /**
     * 填充模板的参数
     */
    private ArrayList<HashMap<String, Object>> param;

    /**
     * 详情链接地址
     */
    private String msgDetailLinkUrl;

    /**
     * 扩展字段
     */
    private String msgExtendField;

    /**
     * 即时：0 定时:1
     */
    private String msgTimeLiness;

    /**
     * 计划推送时间，cron格式
     */
    private String sendTimePlan;

    /**
     * 需要发送的用户列表
     */
    private List<ReceiveUser> userList;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<HashMap<String, Object>> getParam() {
        return param;
    }

    public void setParam(ArrayList<HashMap<String, Object>> param) {
        this.param = param;
    }

    public String getMsgDetailLinkUrl() {
        return msgDetailLinkUrl;
    }

    public void setMsgDetailLinkUrl(String msgDetailLinkUrl) {
        this.msgDetailLinkUrl = msgDetailLinkUrl;
    }

    public String getMsgExtendField() {
        return msgExtendField;
    }

    public void setMsgExtendField(String msgExtendField) {
        this.msgExtendField = msgExtendField;
    }

    public String getMsgTimeLiness() {
        return msgTimeLiness;
    }

    public void setMsgTimeLiness(String msgTimeLiness) {
        this.msgTimeLiness = msgTimeLiness;
    }

    public String getSendTimePlan() {
        return sendTimePlan;
    }

    public void setSendTimePlan(String sendTimePlan) {
        this.sendTimePlan = sendTimePlan;
    }

    public List<ReceiveUser> getUserList() {
        return userList;
    }

    public void setUserList(List<ReceiveUser> userList) {
        this.userList = userList;
    }

}
