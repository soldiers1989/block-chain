package com.xdaocloud.futurechain.dto.req.ethereum;


import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class WalletRequest implements IBaseRequest, Serializable {

    @Pattern(message = "钱包地址必须是42位,不包含特殊字符", regexp = "0x[A-Za-z0-9]{40}")
    private String walletAddress;

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
