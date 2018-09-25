package com.xdaocloud.futurechain.dto.req.demo;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UpdateUserRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户Id不能为空")
    private String id;

    @NotBlank(message = "姓名不能为空")
    @Pattern(message = "姓名必须为2~10位汉字", regexp = "[\u4e00-\u9fa5]{2,10}")
    private String name;

    @NotBlank(message = "用户名不能为空")
    @Pattern(message = "用户名必须是6~20位数字字母的组合", regexp = "[A-Za-z0-9]{6,20}")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(message = "密码必须是{min}~{max}位字符", min = 6, max = 20)
    private String password;

    private String avatar;

    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    public UpdateUserRequest() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
