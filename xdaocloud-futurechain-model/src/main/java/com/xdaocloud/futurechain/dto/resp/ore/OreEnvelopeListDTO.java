package com.xdaocloud.futurechain.dto.resp.ore;

import java.math.BigInteger;

public class OreEnvelopeListDTO {

    private BigInteger id;

    private Long userId;

    /**
     * 矿包数量
     */
    private Integer count;

    /**
     * 矿包标题
     */
    private String oreTitle;

    /**
     * 矿包状态
     */
    private Boolean oreState;

    /**
     * 此用户是否已经抢过这个矿包
     */
    private Boolean userIsOpen;

    private String avatar;

    /**
     * 别名
     */
    private String nickname;

    private String gmtCreate;

    public OreEnvelopeListDTO() {
    }


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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOreTitle() {
        return oreTitle;
    }

    public void setOreTitle(String oreTitle) {
        this.oreTitle = oreTitle;
    }

    public Boolean getOreState() {
        return oreState;
    }

    public void setOreState(Boolean oreState) {
        this.oreState = oreState;
    }

    public Boolean getUserIsOpen() {
        return userIsOpen;
    }

    public void setUserIsOpen(Boolean userIsOpen) {
        this.userIsOpen = userIsOpen;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
