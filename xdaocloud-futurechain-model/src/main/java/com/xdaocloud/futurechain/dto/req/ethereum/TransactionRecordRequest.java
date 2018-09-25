package com.xdaocloud.futurechain.dto.req.ethereum;


import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;

public class TransactionRecordRequest implements IBaseRequest, Serializable{
    private static final long serialVersionUID = -1211532149551068890L;

    private String address;

    private Integer page;

    private Integer size;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
