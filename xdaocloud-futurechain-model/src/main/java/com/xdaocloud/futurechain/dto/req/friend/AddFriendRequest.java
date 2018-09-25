package com.xdaocloud.futurechain.dto.req.friend;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AddFriendRequest implements IBaseRequest, Serializable {
    private static final long serialVersionUID = 5529494190489549188L;

    @NotNull
    private Long userid;

    @NotBlank(message = "accid不能为空")
    private String accid;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }
}
