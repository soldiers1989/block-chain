package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class ToExamineRequest implements IBaseRequest{

    @NotBlank(message = "用户id不能为空")
    private String userid;

    /**
     * 操作人
     */
    private String manager;

    /**
     * 执行 1-同意，0-拒绝
     */
    private byte execute;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public byte getExecute() {
        return execute;
    }

    public void setExecute(byte execute) {
        this.execute = execute;
    }
}
