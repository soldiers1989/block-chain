package com.xdaocloud.futurechain.common;

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class PageRequest implements IBaseRequest, Serializable {
    private static final long serialVersionUID = 1L;
    @ApiParam(
            value = "页码",
            defaultValue = "1"
    )
    @Pattern(
            message = "页码必须为大于0的整数",
            regexp = "[1-9]\\d*"
    )
    public String page = "1";
    @ApiParam(
            value = "每页大小",
            defaultValue = "20"
    )
    @Pattern(
            message = "每页显示条数必须为大于0的整数",
            regexp = "[1-9]\\d*"
    )
    public String size = "20";

    public PageRequest() {
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}

