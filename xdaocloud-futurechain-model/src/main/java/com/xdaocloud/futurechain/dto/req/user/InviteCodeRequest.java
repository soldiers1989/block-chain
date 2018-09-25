package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;

public class InviteCodeRequest implements IBaseRequest,Serializable{

    private static final long serialVersionUID = 1037862871545581736L;

    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;


    @Min(value = 0)
    private Integer page;

    @Min(value = 1)
    private Integer size;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
