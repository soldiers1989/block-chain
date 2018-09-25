package com.xdaocloud.futurechain.model;

import java.io.Serializable;

public class UserCircle implements Serializable {

    private static final long serialVersionUID = 2411269546379451863L;

    private Long userId;

    private Long circleId;

    public UserCircle() {
    }

    public UserCircle(Long userId, Long circleId) {
        this.userId = userId;
        this.circleId = circleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }
}