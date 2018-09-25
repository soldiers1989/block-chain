package com.xdaocloud.futurechain.dto.req.community;

import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author LuoFuMin
 * @data 2018/9/6
 */
public class AddCommunityManagerRequest extends UserIdRequest implements Serializable {

    private static final long serialVersionUID = -4729536953450179518L;

    @NotNull
    private Long communityId;

    @NotNull
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}
