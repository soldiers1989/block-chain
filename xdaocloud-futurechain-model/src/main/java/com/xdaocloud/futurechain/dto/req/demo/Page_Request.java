package com.xdaocloud.futurechain.dto.req.demo;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;

public class Page_Request implements IBaseRequest,Serializable{

    private static final long serialVersionUID = 9023052078564982932L;


    @NotBlank(message = "用户id不能为空")
    private String userid;

    @Min(value = 0)
    private Integer page = 0;

    @Min(value = 1)
    private Integer size = 20;

    private String condition;

    public Page_Request() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
