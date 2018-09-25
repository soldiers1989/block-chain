package com.xdaocloud.futurechain.dto.req.group;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class GroupIdDTO implements IBaseRequest {

    @NotBlank(message = "群号不能为空")
    private String  groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
