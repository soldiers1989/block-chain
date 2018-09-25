package com.xdaocloud.futurechain.model;

import java.io.Serializable;

public class ErrorLog implements Serializable {
    private static final long serialVersionUID = -9092303905526339981L;
    private Long id;

    private Long userId;

    private String filePath;

    private String appVersion;

    private String phoneSystemVersion;

    private String phoneModel;

    private String phoneCompany;

    private String gmtCreate;

    private String gmtModified;

    private Boolean isDeleted;

    public ErrorLog() {
    }

    public ErrorLog(Long userId, String filePath, String appVersion, String phoneSystemVersion, String phoneModel, String phoneCompany) {
        this.userId = userId;
        this.filePath = filePath;
        this.appVersion = appVersion;
        this.phoneSystemVersion = phoneSystemVersion;
        this.phoneModel = phoneModel;
        this.phoneCompany = phoneCompany;
    }

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    public String getPhoneSystemVersion() {
        return phoneSystemVersion;
    }

    public void setPhoneSystemVersion(String phoneSystemVersion) {
        this.phoneSystemVersion = phoneSystemVersion == null ? null : phoneSystemVersion.trim();
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel == null ? null : phoneModel.trim();
    }

    public String getPhoneCompany() {
        return phoneCompany;
    }

    public void setPhoneCompany(String phoneCompany) {
        this.phoneCompany = phoneCompany == null ? null : phoneCompany.trim();
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}