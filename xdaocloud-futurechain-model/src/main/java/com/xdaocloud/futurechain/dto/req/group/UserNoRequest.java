package com.xdaocloud.futurechain.dto.req.group;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class UserNoRequest implements IBaseRequest {
    @NotBlank
    private String userNo;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
