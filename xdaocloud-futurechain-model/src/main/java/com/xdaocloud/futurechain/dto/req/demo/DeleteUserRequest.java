package com.xdaocloud.futurechain.dto.req.demo;


import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DeleteUserRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户Ids不能为空")
    private String[] ids;

    public DeleteUserRequest() {}

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
