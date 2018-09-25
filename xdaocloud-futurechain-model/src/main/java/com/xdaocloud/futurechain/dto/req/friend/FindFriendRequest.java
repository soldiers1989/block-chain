package com.xdaocloud.futurechain.dto.req.friend;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class FindFriendRequest implements IBaseRequest {

    @NotBlank(message = "手机号码不能为空")
    private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
