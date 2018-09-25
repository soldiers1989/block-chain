package com.xdaocloud.futurechain.dto.resp.eos;


import com.xdaocloud.futurechain.common.IBaseRequest;

public class GetBalanceResponse implements IBaseRequest {

    private String balance;

    public GetBalanceResponse(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
