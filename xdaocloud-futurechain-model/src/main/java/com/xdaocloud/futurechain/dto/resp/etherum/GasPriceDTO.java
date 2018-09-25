package com.xdaocloud.futurechain.dto.resp.etherum;

import java.io.Serializable;
import java.math.BigInteger;

public class GasPriceDTO implements Serializable{

    private static final long serialVersionUID = 5533929843869596250L;
    /**
     * gas 数量
     */
    private BigInteger gas;

    /**
     * gas 单价
     */
    private BigInteger gasPrice;


    public GasPriceDTO(BigInteger gas, BigInteger gasPrice) {
        this.gas = gas;
        this.gasPrice = gasPrice;
    }

    public BigInteger getGas() {
        return gas;
    }

    public void setGas(BigInteger gas) {
        this.gas = gas;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }
}
