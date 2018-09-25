package com.xdaocloud.futurechain.dto.req.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/18.
 */
public class DappContractRequest implements IBaseRequest,Serializable  {
    private static final long serialVersionUID = 5894830515584665669L;

    @NotBlank(message = "用户id不能为空")
    private String userId;            //用户id

    private String searchContract;   //搜索内容

    @Min(value = 1)
    private int page;           //页数(默认是1)

    @Min(value = 1)
    private int  size;           //每页显示数量

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSearchContract() {
        return searchContract;
    }

    public void setSearchContract(String searchContract) {
        this.searchContract = searchContract;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
