package com.xdaocloud.futurechain.dto.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class InviteCodeDTO implements IBaseRequest {

    @NotBlank
    private String inviteCode;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
