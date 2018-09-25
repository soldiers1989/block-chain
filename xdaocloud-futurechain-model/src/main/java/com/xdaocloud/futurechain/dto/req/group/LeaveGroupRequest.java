package com.xdaocloud.futurechain.dto.req.group;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 退群
 * @author LuoFuMin
 * @data 2018/9/14
 */
public class LeaveGroupRequest implements IBaseRequest,Serializable{
    private static final long serialVersionUID = 8141429548405790522L;

    @NotNull
    private Long userid;

    @NotBlank
    private String groupId;


    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
