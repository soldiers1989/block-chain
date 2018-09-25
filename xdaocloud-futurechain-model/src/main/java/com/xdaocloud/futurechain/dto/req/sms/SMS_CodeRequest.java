package com.xdaocloud.futurechain.dto.req.sms;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class SMS_CodeRequest  implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "验证码不能为空")
    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

