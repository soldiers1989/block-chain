package com.xdaocloud.futurechain.dto.user;


import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class MobileNumberDTO implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -5301330110429028162L;
    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
