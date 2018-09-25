package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/** web页面管理文章列表
 * Created by Administrator on 2018/7/10.
 */
public class WebCircleListRequest implements IBaseRequest,Serializable {

    private int auditStatus = 0;//审核状态

    private int stateType = 0; //上下架状态

    private int reportType = 0;//举报状态

    private String content;//文章内容

    private String phoneNumber; //手机号
    @NotNull(message = "页码不能为空")
    private int page = 1;           //页数(默认是1)
    @NotNull(message = "数量不能为空")
    private int size= 10;           //每页显示数量


    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getStateType() {
        return stateType;
    }

    public void setStateType(Integer stateType) {
        this.stateType = stateType;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
