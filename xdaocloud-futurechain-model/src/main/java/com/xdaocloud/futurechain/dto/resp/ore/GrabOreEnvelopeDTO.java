package com.xdaocloud.futurechain.dto.resp.ore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class GrabOreEnvelopeDTO implements Serializable {

    private static final long serialVersionUID = -2925349133628980505L;

    private BigInteger id;

    private Long userId;

    private Long oreEnvelopeId;

    private BigDecimal randomNumber;

    /**
     * 别名
     */
    private String nickname;


    /**
     * 头像地址
     */
    private String avatar;

    private String gmtCreate;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOreEnvelopeId() {
        return oreEnvelopeId;
    }

    public void setOreEnvelopeId(Long oreEnvelopeId) {
        this.oreEnvelopeId = oreEnvelopeId;
    }

    public BigDecimal getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(BigDecimal randomNumber) {
        this.randomNumber = randomNumber;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
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
}
