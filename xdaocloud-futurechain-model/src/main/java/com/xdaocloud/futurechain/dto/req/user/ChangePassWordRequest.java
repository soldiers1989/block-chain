package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class ChangePassWordRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -1359264187097703613L;

    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank(message = "新密码不能为空")
    @Pattern(message = "用户名必须是6~20位数字字母的组合", regexp = "[A-Za-z0-9]{6,20}")
    private String newPass;

    @NotBlank(message = "旧密码不能为空")
    @Pattern(message = "用户名必须是6~20位数字字母的组合", regexp = "[A-Za-z0-9]{6,20}")
    private String oldPass;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }
}
