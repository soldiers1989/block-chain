package com.xdaocloud.futurechain.dto.resp.ore;


import java.io.Serializable;
import java.util.List;

public class GrabOreEnvelopeResponse implements Serializable {

    private static final long serialVersionUID = -6741786734128619675L;


    private Long userId;

    private Long oreEnvelopeId;

    /**
     * 随机数
     */
    private String randomNumber;

    /**
     * 矿包标题
     */
    private String oreTitle;

    /**
     * 矿包总数
     */
    private Integer sum;

    /**
     * 矿包剩余数
     */
    private Integer count;

    /**
     * 1 抢到了，0没抢到
     */
    private Integer vie;

    /**
     * 别名
     */
    private String nickname;


    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 已抢矿包记录
     */
    private List<GrabOreEnvelopeDTO> grabOreEnvelopeList;


    public GrabOreEnvelopeResponse(String oreTitle, Long userId, Long oreEnvelopeId, String randomNumber, Integer sum, Integer count, Integer vie, String nickname, String avatar, List<GrabOreEnvelopeDTO> grabOreEnvelopeList) {
        this.oreTitle = oreTitle;
        this.userId = userId;
        this.oreEnvelopeId = oreEnvelopeId;
        this.randomNumber = randomNumber;
        this.sum = sum;
        this.count = count;
        this.vie = vie;
        this.nickname = nickname;
        this.avatar = avatar;
        this.grabOreEnvelopeList = grabOreEnvelopeList;
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

    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<GrabOreEnvelopeDTO> getGrabOreEnvelopeList() {
        return grabOreEnvelopeList;
    }

    public void setGrabOreEnvelopeList(List<GrabOreEnvelopeDTO> grabOreEnvelopeList) {
        this.grabOreEnvelopeList = grabOreEnvelopeList;
    }

    public Integer getVie() {
        return vie;
    }

    public void setVie(Integer vie) {
        this.vie = vie;
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

    public String getOreTitle() {
        return oreTitle;
    }

    public void setOreTitle(String oreTitle) {
        this.oreTitle = oreTitle;
    }
}
