package com.xdaocloud.futurechain.dto.req.football;

public class MatchOrderDTO {

    //比赛场次Id
    private Long matchId;

    //竞猜结果状态（1：胜，0：平，-1：负）
    private String gameStatus;

    //球队 竞猜金额
    private String subtotal;


    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
