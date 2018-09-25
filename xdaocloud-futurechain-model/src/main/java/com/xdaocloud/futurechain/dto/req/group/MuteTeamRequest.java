package com.xdaocloud.futurechain.dto.req.group;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改消息提醒开关
 *
 * @author LuoFuMin
 * @data 2018/9/14
 */
public class MuteTeamRequest implements IBaseRequest, Serializable {
    private static final long serialVersionUID = 5219337731016881636L;

    @NotNull
    private Long userid;

    @NotBlank
    private String groupId;
    /**
     * 1：关闭消息提醒，2：打开消息提醒，其他值无效
     */
    @NotNull
    private Integer ope;

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

    public Integer getOpe() {
        return ope;
    }

    public void setOpe(Integer ope) {
        this.ope = ope;
    }
}
