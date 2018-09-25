package com.xdaocloud.futurechain.dto.req.group;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class CreateGroupRequest implements IBaseRequest {

    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank(message = "群名称不能为空")
    private String groupName;

    /**
     * 群头像
     */
    private String groupAvatar;


}
