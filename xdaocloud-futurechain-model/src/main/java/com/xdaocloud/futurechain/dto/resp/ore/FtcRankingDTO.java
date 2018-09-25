package com.xdaocloud.futurechain.dto.resp.ore;

import java.io.Serializable;
import java.math.BigDecimal;

public class FtcRankingDTO implements Serializable {

    private static final long serialVersionUID = 3412729538077611094L;

    private String  mobileMumber;

    private String nickname;

    private BigDecimal amount;

    public FtcRankingDTO(String mobileMumber, String nickname, BigDecimal amount) {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
