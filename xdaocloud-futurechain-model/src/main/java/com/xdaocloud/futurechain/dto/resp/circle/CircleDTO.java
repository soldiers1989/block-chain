package com.xdaocloud.futurechain.dto.resp.circle;

import java.io.Serializable;

public class CircleDTO implements Serializable{

    private static final long serialVersionUID = -5272394533612691494L;

    private Long id;

    private String circleName;

    private String circleNumber;

    private Boolean hold;

    public CircleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getCircleNumber() {
        return circleNumber;
    }

    public void setCircleNumber(String circleNumber) {
        this.circleNumber = circleNumber;
    }

    public Boolean getHold() {
        return hold;
    }

    public void setHold(Boolean hold) {
        this.hold = hold;
    }
}
