package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class UpdateFriendRemarkRequest implements IBaseRequest {

    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank(message = "朋友id不能为空")
    private String friendId;

    private String remark;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
