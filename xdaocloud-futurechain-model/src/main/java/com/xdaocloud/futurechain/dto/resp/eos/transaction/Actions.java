package com.xdaocloud.futurechain.dto.resp.eos.transaction;

import java.util.List;

public class Actions {

    private String account;
    private String name;
    private List<Authorization> authorization;
    private String data;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Authorization> getAuthorization() {
        return authorization;
    }

    public void setAuthorization(List<Authorization> authorization) {
        this.authorization = authorization;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
