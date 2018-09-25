package com.xdaocloud.futurechain.dto.resp.eos;


import com.xdaocloud.futurechain.common.IBaseRequest;

public class ExistResponse implements IBaseRequest {

    public ExistResponse() {
    }

    public ExistResponse(Boolean exist, String walletName) {
        this.exist = exist;
        this.walletName = walletName;
    }

    private Boolean exist;

    private String walletName;

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        exist = exist;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
}
