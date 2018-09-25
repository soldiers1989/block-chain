package com.xdaocloud.futurechain.dto.resp.football;


import com.xdaocloud.futurechain.common.IBaseRequest;

public class SubstitutionDTO implements IBaseRequest {

    private String name;

    private String avatar;

    private String ballNumber;

    private String courtPosition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(String ballNumber) {
        this.ballNumber = ballNumber;
    }

    public String getCourtPosition() {
        return courtPosition;
    }

    public void setCourtPosition(String courtPosition) {
        this.courtPosition = courtPosition;
    }
}
