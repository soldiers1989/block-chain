package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class ChangePassRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -1855811310154173831L;


    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String mobileNumber;

    @NotBlank(message = "密码不能为空")
    @Pattern(message = "用户名必须是6~20位数字字母的组合", regexp = "[A-Za-z0-9]{6,20}")
    private String newPass;

    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
