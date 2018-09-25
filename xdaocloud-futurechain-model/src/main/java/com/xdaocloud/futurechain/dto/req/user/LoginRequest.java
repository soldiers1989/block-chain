package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class LoginRequest implements IBaseRequest, Serializable {
    private static final long serialVersionUID = 1587149399655258843L;

    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String mobileNumber;

    @NotBlank(message = "密码不能为空")
    @Length(message = "密码必须是{min}~{max}位字符", min = 6, max = 20)
    private String password;

    /**
     * ip地址
     */
    private String spbillCreateIp;


    /**
     * mac 地址
     */
    //@Pattern(message = "mac地址格式不正确", regexp = "([A-Fa-f0-9]{2}-){5}[A-Fa-f0-9]{2}")
    @NotBlank
    private String mac;

    /**
     * App版本
     */
    @NotBlank
    private String appVersion;

    /**
     * 手机系统版本
     */
    @NotBlank
    private String phoneSystemVersion;

    /**
     * 手机型号
     */
    @NotBlank
    private String phoneModel;

    /**
     * 手机所属公司
     */
    @NotBlank
    private String phoneCompany;


    public LoginRequest() {
    }


    public LoginRequest(String mobileNumber, String password) {
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getPhoneSystemVersion() {
        return phoneSystemVersion;
    }

    public void setPhoneSystemVersion(String phoneSystemVersion) {
        this.phoneSystemVersion = phoneSystemVersion;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneCompany() {
        return phoneCompany;
    }

    public void setPhoneCompany(String phoneCompany) {
        this.phoneCompany = phoneCompany;
    }
}
