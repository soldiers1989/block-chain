package com.xdaocloud.futurechain.dto.resp.ore;

import java.io.Serializable;
import java.math.BigDecimal;

public class OreRankingDTO implements Serializable{

    private static final long serialVersionUID = 806929030377951922L;

    private String  mobileMumber;

    private String nickname;

    private BigDecimal amount;

    public OreRankingDTO(String mobileMumber, String nickname, BigDecimal amount) {
        this.mobileMumber = mobileMumber;
        this.nickname = nickname;
        this.amount = amount;
    }

    public String getMobileMumber() {
        return mobileMumber;
    }

    public void setMobileMumber(String mobileMumber) {
        this.mobileMumber = mobileMumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
