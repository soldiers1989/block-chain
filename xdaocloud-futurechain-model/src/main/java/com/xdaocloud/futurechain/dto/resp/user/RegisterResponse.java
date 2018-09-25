package com.xdaocloud.futurechain.dto.resp.user;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
    private static final long serialVersionUID = -8243079591418155050L;

    /**
     * 用户id
     */
    private String sysid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户手机号码
     */
    private String username;

    /**
     * token令牌
     */
    private String token;
    /**
     * 环信账号
     */
    private String hx_username;
    /**
     * 环信密码
     */
    private String hx_password;

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHx_username() {
        return hx_username;
    }

    public void setHx_username(String hx_username) {
        this.hx_username = hx_username;
    }

    public String getHx_password() {
        return hx_password;
    }

    public void setHx_password(String hx_password) {
        this.hx_password = hx_password;
    }
}
