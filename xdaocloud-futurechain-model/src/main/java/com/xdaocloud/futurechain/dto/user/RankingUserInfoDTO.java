package com.xdaocloud.futurechain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户排行榜的用户信息
 *
 * @author lixuzhou@foxmail.com
 * @version 0.1 2018/3/7
 */
public class RankingUserInfoDTO implements Serializable {

    private static final long serialVersionUID = 5028876958990146900L;

    /**
     * 用户ID
     */
    @JsonIgnore
    Long id;

    /**
     * 用户中心ID
     */
    @JsonIgnore
    Long userCenterId;

    /**
     * 用户昵称
     */
    String nickname;

    /**
     * 蓝宝石数量
     */
    BigDecimal sapphireAmount;

    /**
     * 未来力数量
     */
    BigDecimal futurePowerAmount;

    /**
     * 步行挖矿获得的蓝宝石数量
     */
    BigDecimal walkingMiningSapphireAmount;

    /**
     * @return Returns the id
     **/
    public Long getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Returns the userCenterId
     **/
    public Long getUserCenterId() {
        return userCenterId;
    }

    /**
     * @param userCenterId The userCenterId to set.
     */
    public void setUserCenterId(Long userCenterId) {
        this.userCenterId = userCenterId;
    }

    /**
     * @return Returns the nickname
     **/
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname The nickname to set.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return Returns the sapphireAmount
     **/
    public BigDecimal getSapphireAmount() {
        return sapphireAmount;
    }

    /**
     * @param sapphireAmount The sapphireAmount to set.
     */
    public void setSapphireAmount(BigDecimal sapphireAmount) {
        this.sapphireAmount = sapphireAmount;
    }

    /**
     * @return Returns the futurePowerAmount
     **/
    public BigDecimal getFuturePowerAmount() {
        return futurePowerAmount;
    }

    /**
     * @param futurePowerAmount The futurePowerAmount to set.
     */
    public void setFuturePowerAmount(BigDecimal futurePowerAmount) {
        this.futurePowerAmount = futurePowerAmount;
    }

    /**
     * @return Returns the walkingMiningSapphireAmount
     **/
    public BigDecimal getWalkingMiningSapphireAmount() {
        return walkingMiningSapphireAmount;
    }

    /**
     * @param walkingMiningSapphireAmount The walkingMiningSapphireAmount to set.
     */
    public void setWalkingMiningSapphireAmount(BigDecimal walkingMiningSapphireAmount) {
        this.walkingMiningSapphireAmount = walkingMiningSapphireAmount;
    }

    @Override
    public String toString() {
        return "RankingUserInfoDTO{" + "id=" + id + ", userCenterId=" + userCenterId + ", nickname='" + nickname + '\''
               + ", sapphireAmount=" + sapphireAmount + ", futurePowerAmount=" + futurePowerAmount
               + ", walkingMiningSapphireAmount=" + walkingMiningSapphireAmount + '}';
    }
}
