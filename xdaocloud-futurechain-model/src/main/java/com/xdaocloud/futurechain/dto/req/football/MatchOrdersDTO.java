package com.xdaocloud.futurechain.dto.req.football;

import org.hibernate.validator.constraints.NotBlank;

public class MatchOrdersDTO {

    @NotBlank(message = "比赛id不能为空")
    private String matchId;

    @NotBlank(message = "购买麦钻数量不能为空")
    private String eosSum;

    /**
     * 胜：1、负：-1、平：0
     */
    @NotBlank
    private String vorl;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getEosSum() {
        return eosSum;
    }

    public void setEosSum(String eosSum) {
        this.eosSum = eosSum;
    }

    public String getVorl() {
        return vorl;
    }

    public void setVorl(String vorl) {
        this.vorl = vorl;
    }
}


