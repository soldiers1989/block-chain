package com.xdaocloud.futurechain.dto.req.community;

import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 删除社群
 *
 * @author LuoFuMin
 * @data 2018/9/6
 */
public class DeleteCommunityRequest extends UserIdRequest implements Serializable {
    private static final long serialVersionUID = -6201236301773974696L;

    @NotNull
    private Long communityId;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}

