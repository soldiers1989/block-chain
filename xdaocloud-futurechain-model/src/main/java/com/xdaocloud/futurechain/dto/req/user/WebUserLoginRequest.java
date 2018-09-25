package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class WebUserLoginRequest implements IBaseRequest,Serializable {
    private static final long serialVersionUID = -8604283261954882027L;

    @NotBlank(message = "用户名不能为空")
    private String  userName;

    @NotBlank(message = "密码不能为空")
    private String  password;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
