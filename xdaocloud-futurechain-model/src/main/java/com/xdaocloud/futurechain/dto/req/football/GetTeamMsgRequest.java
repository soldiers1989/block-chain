package com.xdaocloud.futurechain.dto.req.football;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class GetTeamMsgRequest implements IBaseRequest{

    @NotBlank
    private String teamId;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
