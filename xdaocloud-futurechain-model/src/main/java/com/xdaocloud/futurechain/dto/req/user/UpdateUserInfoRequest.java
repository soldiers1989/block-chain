package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

public class UpdateUserInfoRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 5974900703771201638L;

    @Pattern(message = "身份证号码必须为18位数，不能有特殊字符", regexp = "[A-Za-z0-9]{18,18}")
    private String idcard;

    @Pattern(message = "姓名必须为2~10位汉字", regexp = "[\u4e00-\u9fa5]{2,10}")
    private String name;

    @NotBlank(message = "用户id不能为空")
    private String userid;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 电话
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;


    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 行业
     */
    private String industryId;

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
