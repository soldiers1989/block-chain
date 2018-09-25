package com.xdaocloud.futurechain.dto.req.address;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 删除地址
 *
 * @author LuoFuMin
 * @data 2018/9/12
 */
public class DeleteAddressRequest implements IBaseRequest,Serializable{

    @NotNull
    private Long userid;

    @NotNull
    private Long[] ids;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
