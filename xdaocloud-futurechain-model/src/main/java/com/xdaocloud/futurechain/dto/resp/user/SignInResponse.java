package com.xdaocloud.futurechain.dto.resp.user;

import java.io.Serializable;

public class SignInResponse implements Serializable{

    private static final long serialVersionUID = 4563141132036350193L;

    private Integer weekSignIn;

    private Boolean todaySignIn;

    public SignInResponse(Integer weekSignIn, Boolean todaySignIn) {
        this.weekSignIn = weekSignIn;
        this.todaySignIn = todaySignIn;
    }

    public Integer getWeekSignIn() {
        return weekSignIn;
    }

    public void setWeekSignIn(Integer weekSignIn) {
        this.weekSignIn = weekSignIn;
    }

    public Boolean getTodaySignIn() {
        return todaySignIn;
    }

    public void setTodaySignIn(Boolean todaySignIn) {
        this.todaySignIn = todaySignIn;
    }
}
