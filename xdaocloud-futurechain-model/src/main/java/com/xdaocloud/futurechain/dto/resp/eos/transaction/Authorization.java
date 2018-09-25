package com.xdaocloud.futurechain.dto.resp.eos.transaction;

public class Authorization {

    private String actor;

    private String permission;


    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
