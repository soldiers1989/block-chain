package com.xdaocloud.futurechain.dto.resp.football;

import java.math.BigDecimal;

public class MatchDTO {

    /**
     * 比赛id
     */
    private Long matchId;


    /**
     * 队名
     */
    private String teamNameA;

    /**
     * 队id
     */
    private String teamIdA;

    /**
     * 国旗
     */
    private String nationalFlagA;

    /**
     * 队名
     */
    private String teamNameB;

    /**
     * 队id
     */
    private String teamIdB;

    /**
     * 国旗
     */
    private String nationalFlagB;

    /**
     * 让球（-1表示a队让一球，+1表示b队让一球）
     */
    private Integer concede;

    /**
     * 场地
     */
    private String field;

    /**
     * 自定义竞猜金额
     */
    private BigDecimal customSum;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 订单状态（0：未支付，1支付成功，2支付失败，3退款成功，4：订单关闭，5：支付错误，6.取消下注并成功退款，比赛开始前5分钟不能取消或修改），7没买中，8，买中并已获得奖金
     */
    private Byte state;


    /**
     * 赢得eos
     */
    private BigDecimal winEos;


    /**
     * 比赛金额总数
     */
    private BigDecimal total;

    /**
     * 结束时间戳
     */
    private String endTimestamp;

    /**
     * 参与竞猜人总数
     */
    private Integer sumPeople;

    /**
     * 参与竞猜朋友数量
     */
    private Integer sumFriends;

    /**
     * 每场比赛 用户已支付的金额
     */
    private BigDecimal subtotal;

    /**
     * 每场比赛的状态（1：进行中，2：已完成）
     */
    private Integer status;

    /**
     *订单Id
     */
    private Long orderId;

    /**
     * 竞猜结果状态（1：胜，0：平，-1：负）
     */
    private Integer gameStatus;

    public BigDecimal getWinEos() {
        return winEos;
    }

    public void setWinEos(BigDecimal winEos) {
        this.winEos = winEos;
    }

    public Integer getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(Integer gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getTeamNameA() {
        return teamNameA;
    }

    public void setTeamNameA(String teamNameA) {
        this.teamNameA = teamNameA;
    }

    public String getTeamNameB() {
        return teamNameB;
    }

    public void setTeamNameB(String teamNameB) {
        this.teamNameB = teamNameB;
    }

    public Integer getConcede() {
        return concede;
    }

    public void setConcede(Integer concede) {
        this.concede = concede;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public BigDecimal getCustomSum() {
        return customSum;
    }

    public void setCustomSum(BigDecimal customSum) {
        this.customSum = customSum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNationalFlagA() {
        return nationalFlagA;
    }

    public void setNationalFlagA(String nationalFlagA) {
        this.nationalFlagA = nationalFlagA;
    }

    public String getNationalFlagB() {
        return nationalFlagB;
    }

    public void setNationalFlagB(String nationalFlagB) {
        this.nationalFlagB = nationalFlagB;
    }

    public Integer getSumPeople() {
        return sumPeople;
    }

    public void setSumPeople(Integer sumPeople) {
        this.sumPeople = sumPeople;
    }

    public Integer getSumFriends() {
        return sumFriends;
    }

    public void setSumFriends(Integer sumFriends) {
        this.sumFriends = sumFriends;
    }

    public String getTeamIdA() {
        return teamIdA;
    }

    public void setTeamIdA(String teamIdA) {
        this.teamIdA = teamIdA;
    }

    public String getTeamIdB() {
        return teamIdB;
    }

    public void setTeamIdB(String teamIdB) {
        this.teamIdB = teamIdB;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}
