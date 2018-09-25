package com.xdaocloud.futurechain.dto.resp;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;

public class VersionResponse implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -2979382871108090190L;

    private String versionCode;

    private String versionName;

    private String upgradeMsg;

    private String downloadUrl;

    private String clientType;

    private Boolean isForce;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpgradeMsg() {
        return upgradeMsg;
    }

    public void setUpgradeMsg(String upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Boolean getIsForce() {
        return isForce;
    }

    public void setForce(Boolean force) {
        isForce = force;
    }
}
