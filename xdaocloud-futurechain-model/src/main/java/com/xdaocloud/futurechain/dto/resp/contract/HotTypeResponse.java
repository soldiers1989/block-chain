package com.xdaocloud.futurechain.dto.resp.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;

/**
 * @author LuoFuMin
 * @data 2018/7/23
 */
public class HotTypeResponse implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -5677372026887406034L;

    private Long id;

    private String name;

    public HotTypeResponse() {
    }

    public HotTypeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
