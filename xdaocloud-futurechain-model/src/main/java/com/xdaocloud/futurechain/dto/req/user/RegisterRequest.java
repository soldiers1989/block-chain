package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class RegisterRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 1L;

    //@Pattern(message = "用户名必须是6~20位数字字母的组合", regexp = "[A-Za-z0-9]{6,20}")
    private String username;

    @NotBlank(message = "用户名不能为空")
    private String mobileNumber;

    @NotBlank(message = "密码不能为空")
    @Length(message = "密码必须是{min}~{max}位字符", min = 6, max = 20)
    private String password;

    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;

    //@NotBlank(message = "姓名不能为空")
    private String name;

    //@NotBlank(message = "身份证号码不能为空")
    private String idcard;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;


    /**
     * ip地址
     */
    private String spbillCreateIp;


    /**
     * mac 地址
     */
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
    private String phoneModel;

    /**
     * 手机所属公司
     */
    private String phoneCompany;


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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

