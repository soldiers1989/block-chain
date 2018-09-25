package com.xdaocloud.futurechain.dto.req.demo;


import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.Min;

public class PageBase_Request  implements IBaseRequest {


    @Min(value = 0)
    private Integer page = 0;

    @Min(value = 1)
    private Integer size = 20;

    private String condition;


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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
