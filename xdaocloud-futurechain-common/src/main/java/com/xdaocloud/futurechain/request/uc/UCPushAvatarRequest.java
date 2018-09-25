package com.xdaocloud.futurechain.request.uc;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LuoFuMin
 * @data 2018/9/10
 */
public class UCPushAvatarRequest implements IBaseRequest,Serializable {

    private static final long serialVersionUID = -4795212847150267025L;

    @NotNull(message = "用户Id不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank(message = "头像不能为空")
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UCPushAvatarRequest(Long id, String avatar) {
        this.id = id;
        this.avatar = avatar;
    }
}
