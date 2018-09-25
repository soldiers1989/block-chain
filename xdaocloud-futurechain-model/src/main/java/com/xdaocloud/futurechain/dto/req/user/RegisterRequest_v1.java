package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class RegisterRequest_v1 implements IBaseRequest {


    @NotBlank(message = "用户名不能为空")
    private String mobileNumber;

    @NotBlank(message = "密码不能为空")
    @Pattern(message = "密码必须是6~20位数字字母的组合", regexp = "[A-Za-z0-9]{6,20}")
    private String password;

    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "身份证号码不能为空")
    private String idcard;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;


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
}
