package com.xdaocloud.futurechain.dto.req.group;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 禁言
 *
 * @author LuoFuMin
 * @data 2018/9/14
 */
public class MuteTlistAllRequest implements IBaseRequest, Serializable {

    private Long userid;

    private String groupId;

    /**
     * 禁言类型 0:解除禁言，1:禁言普通成员 3:禁言整个群(包括群主)
     */
    @NotNull
    private Integer muteType;


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

    public Integer getMuteType() {
        return muteType;
    }

    public void setMuteType(Integer muteType) {
        this.muteType = muteType;
    }
}
