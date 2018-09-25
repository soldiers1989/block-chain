package com.xdaocloud.futurechain.response.yunxin;

import com.xdaocloud.futurechain.response.yunxin.entity.Faccid;

/**
 * @author LuoFuMin
 * @data 2018/9/12
 */
public class AddTeamResponse {

    private Integer code;


    private Faccid faccid;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Faccid getFaccid() {
        return faccid;
    }

    public void setFaccid(Faccid faccid) {
        this.faccid = faccid;
    }
}
