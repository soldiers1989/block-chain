package com.xdaocloud.futurechain.dto.req.ethereum;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class SolidityRequest implements Serializable{

    private static final long serialVersionUID = 2744785963760151779L;

    @NotBlank(message = "合约代码不能为空")
    private String code;

    @NotBlank(message = "用户Id不能为空")
    private String userid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
