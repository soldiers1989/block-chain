package com.xdaocloud.futurechain.request.uc;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LuoFuMin
 * @data 2018/9/10
 */
public class UCResetUserPwdRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 6342278839703396206L;
    @NotNull(message = "用户Ids不能为空")
    private Long[] ids;

    @NotBlank(message = "密码不能为空")
    private String password;


    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
