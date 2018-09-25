package com.xdaocloud.futurechain.request.uc;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author LuoFuMin
 * @data 2018/9/10
 */
public class UCRegisterRequest implements Serializable, IBaseRequest {

    private static final long serialVersionUID = 1L;

    @Pattern(message = "姓名必须为2~10位汉字", regexp = "[\u4e00-\u9fa5]{2,10}")
    private String name;

    @Pattern(message = "用户名必须是6~20位数字字母的组合", regexp = "[A-Za-z0-9]{6,20}")
    private String username;

    @Length(message = "密码必须是{min}~{max}位字符", min = 6, max = 20)
    private String password;

    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 昵称
     */
    private String nickname;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
