package com.xdaocloud.futurechain.dto.req.eos;


import com.xdaocloud.futurechain.common.IBaseRequest;

public class EOS_Account implements IBaseRequest {

    private String accountName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
