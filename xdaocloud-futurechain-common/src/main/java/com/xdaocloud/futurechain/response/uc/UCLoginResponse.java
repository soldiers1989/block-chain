package com.xdaocloud.futurechain.response.uc;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LuoFuMin
 * @data 2018/9/10
 */
public class UCLoginResponse implements Serializable {

    private Long id;

    private String userToken;

    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 电话
     */
    private String phone;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idcard;


    /**
     * 出生日期
     */
    private Date birthday;


    public UCLoginResponse() {
        super();
    }

    public UCLoginResponse(Long id, String userToken) {
        super();
        this.id = id;
        this.userToken = userToken;
    }

    public UCLoginResponse(Long id, String userToken, String nickname, String avatar, String phone, String name, String idcard) {
        this.id = id;
        this.userToken = userToken;
        this.nickname = nickname;
        this.avatar = avatar;
        this.phone = phone;
        this.name = name;
        this.idcard = idcard;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
