package com.xdaocloud.futurechain.dto.req.friend;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class FindFriendRemarkRequest implements IBaseRequest {

    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank(message = "用户id不能为空")
    private String friendId;

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
}
