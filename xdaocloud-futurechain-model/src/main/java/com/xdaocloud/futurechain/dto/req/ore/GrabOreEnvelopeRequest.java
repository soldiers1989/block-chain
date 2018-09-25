package com.xdaocloud.futurechain.dto.req.ore;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class GrabOreEnvelopeRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 959097557672256689L;

    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank(message = "矿包id不能为空")
    private String oreEnvelopeId;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOreEnvelopeId() {
        return oreEnvelopeId;
    }

    public void setOreEnvelopeId(String oreEnvelopeId) {
        this.oreEnvelopeId = oreEnvelopeId;
    }
}
