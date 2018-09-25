package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.util.Date;

 /**
  * 举报记录表
  *@author  LiMaoDao
  *@date  2018/8/20
  */
public class Report implements Serializable {

    private static final long serialVersionUID = 1361677047999173428L;

    private Long id;

     /**
      * 用户id
      */
    private Long userId;

     /**
      * 朋友圈id
      */
    private Long circleId;

     /**
      * 举报内容(涉黄、反党、反国家)
      */
    private String repContent;

     /**
      * 审核结果(0:待审核 1:成功  2:失败-失败会恢复文章)
      */
    private Byte auditResults;

     /**
      * 审核用户id
      */
    private Long auditUserId;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDelete;

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

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public String getRepContent() {
        return repContent;
    }

    public void setRepContent(String repContent) {
        this.repContent = repContent == null ? null : repContent.trim();
    }

    public Byte getAuditResults() {
        return auditResults;
    }

    public void setAuditResults(Byte auditResults) {
        this.auditResults = auditResults;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}