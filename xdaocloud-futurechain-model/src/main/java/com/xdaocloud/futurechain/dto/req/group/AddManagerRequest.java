package com.xdaocloud.futurechain.dto.req.group;

import com.alibaba.fastjson.JSONArray;
import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加管理员
 *
 * @author LuoFuMin
 * @data 2018/9/14
 */
public class AddManagerRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -8479939042336500208L;

    @NotNull
    private Long userid;

    @NotBlank
    private String groupId;


    @NotNull
    private JSONArray members;

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

    public JSONArray getMembers() {
        return members;
    }

    public void setMembers(JSONArray members) {
        this.members = members;
    }
}
