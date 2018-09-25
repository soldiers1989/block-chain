package com.xdaocloud.futurechain.dto.req.demo;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Null 限制只能为null
 * @NotNull 限制必须不为null
 * @AssertFalse 限制必须为false
 * @AssertTrue 限制必须为true
 * @DecimalMax(value) 限制必须为一个不大于指定值的数字
 * @DecimalMin(value) 限制必须为一个不小于指定值的数字
 * @Digits(integer,fraction) 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
 * @Future 限制必须是一个将来的日期
 * @Max(value) 限制必须为一个不大于指定值的数字
 * @Min(value) 限制必须为一个不小于指定值的数字
 * @Pattern(value) 限制必须符合指定的正则表达式
 * @Size(max,min) 限制字符长度必须在min到max之间
 * @Past 验证注解的元素值（日期类型）比当前时间早
 * @NotEmpty 验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
 * @NotBlank 验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
 * @Email 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式
 */
public class SaveUserRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 1L;

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

    public SaveUserRequest() {}

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
