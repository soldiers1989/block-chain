package com.xdaocloud.futurechain.dto.resp.user;

import java.io.Serializable;
import java.util.List;

public class UserOreRankingResponse implements Serializable {

    private static final long serialVersionUID = 6458363299726236086L;

    public Long id;

    private String nickname;

    private Long ore;

    private String avatar;

    private Integer ranking;


    private List<UserOreResponse> userOreResponseList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getOre() {
        return ore;
    }

    public void setOre(Long ore) {
        this.ore = ore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public List<UserOreResponse> getUserOreResponseList() {
        return userOreResponseList;
    }

    public void setUserOreResponseList(List<UserOreResponse> userOreResponseList) {
        this.userOreResponseList = userOreResponseList;
    }
}
