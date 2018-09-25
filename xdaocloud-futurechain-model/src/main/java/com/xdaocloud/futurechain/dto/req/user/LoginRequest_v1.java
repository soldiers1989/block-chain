package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class LoginRequest_v1 implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 4758199947030344971L;

    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String mobileNumber;

    @NotBlank(message = "密码不能为空")
    @Length(message = "密码必须是{min}~{max}位字符", min = 6, max = 20)
    private String password;

    public LoginRequest_v1(String mobileNumber, String password) {
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public LoginRequest_v1() {
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
}
