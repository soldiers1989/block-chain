package com.xdaocloud.futurechain.dto.req.friend;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class HandleFriendApplyRequest implements IBaseRequest {

    @NotBlank
    private String addFriendId;
    @NotBlank
    private String agree;

    private String remark;

    public String getAddFriendId() {
        return addFriendId;
    }

    public void setAddFriendId(String addFriendId) {
        this.addFriendId = addFriendId;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
