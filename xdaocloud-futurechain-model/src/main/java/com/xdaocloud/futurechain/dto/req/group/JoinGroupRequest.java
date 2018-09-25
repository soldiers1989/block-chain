package com.xdaocloud.futurechain.dto.req.group;

import com.alibaba.fastjson.JSONArray;
import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class JoinGroupRequest implements IBaseRequest,Serializable {

    private static final long serialVersionUID = 7927522904556682245L;

    @NotNull
    private Long userid;

    @NotBlank(message = "群号不能为空")
    private String groupId;

    private String msg;

    @NotNull
    private JSONArray members;

    /**
     * 管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群
     */
    private int magree = 1;


    public JSONArray getMembers() {
        return members;
    }

    public void setMembers(JSONArray members) {
        this.members = members;
    }

    public int getMagree() {
        return magree;
    }

    public void setMagree(int magree) {
        this.magree = magree;
    }

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
