package com.xdaocloud.futurechain.dto.req.sms;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class SendSMSRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "手机号码不能为空")
    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}

