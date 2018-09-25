package com.xdaocloud.futurechain.dto.req.group;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class UpdateGroupNameRequest implements IBaseRequest {

    @NotBlank(message = "群号不能为空")
    private String groupId;
    @NotBlank(message = "用户id不能为空")
    private String userid;
    @NotBlank(message = "群名不能为空")
    private String groupName;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
