package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class CheckCodeRequest implements IBaseRequest, Serializable {
    private static final long serialVersionUID = 6330611938546941158L;

    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String mobileNumber;

    @NotBlank(message = "验证码不能为空")
    private String uuidcode;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUuidcode() {
        return uuidcode;
    }

    public void setUuidcode(String uuidcode) {
        this.uuidcode = uuidcode;
    }
}
