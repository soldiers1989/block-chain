package com.xdaocloud.futurechain.dto.req.community;

import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 加入社群
 *
 * @author LuoFuMin
 * @data 2018/9/6
 */
public class AddCommunityRequest extends UserIdRequest implements Serializable {
    private static final long serialVersionUID = 1109363881500922861L;

    @NotNull
    private Long communityId;

    @NotNull
    private List<String> groupIds;

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}
