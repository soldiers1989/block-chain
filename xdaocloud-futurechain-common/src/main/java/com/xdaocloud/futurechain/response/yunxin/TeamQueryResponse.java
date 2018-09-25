package com.xdaocloud.futurechain.response.yunxin;

import com.xdaocloud.futurechain.response.yunxin.entity.Tinfos;

import java.util.List;

/**
 * @author LuoFuMin
 * @data 2018/9/12
 */
public class TeamQueryResponse {

    private Integer code;

    private List<Tinfos> tinfos;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Tinfos> getTinfos() {
        return tinfos;
    }

    public void setTinfos(List<Tinfos> tinfos) {
        this.tinfos = tinfos;
    }
}
