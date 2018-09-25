package com.xdaocloud.futurechain.dto.resp.user;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;

/**
 * web 登录 返回结果
 *
 * @author LuoFuMin
 * @data 2018/7/13
 */
public class WebUserLoginResponse<T> implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -784129438252588505L;

    private Long id;

    private String name;

    private String token;

    private String userToken;

    private String errorMsg;

    private T authority;

    public WebUserLoginResponse() {
    }

    public WebUserLoginResponse(Long id, String name, String userToken) {
        this.id = id;
        this.name = name;
        this.userToken = userToken;
    }

    public WebUserLoginResponse(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getAuthority() {
        return authority;
    }

    public void setAuthority(T authority) {
        this.authority = authority;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
