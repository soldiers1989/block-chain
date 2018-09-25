package com.xdaocloud.futurechain.dto.req.ethereum;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigInteger;

public class TransactionRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 700716945514828095L;

    @NotBlank(message = "用户Id不能为空")
    private String userid;

    @NotBlank(message = "账户不能为空")
    private String from;

    @NotBlank(message = "账户不能为空")
    private String to;

    //该交易消耗的总gas数量, 它将返回未使用的gas,（推荐30400）
    @DecimalMin(value = "1",message = "gas不能小于1")
    private BigInteger gas;

    //该交易中gas的单价（用以太币计算，推荐=10000000000000wei，交易费用 = gas*gasPrice）
    @DecimalMin(value = "1",message = "gasPrice不能小于1")
    private  BigInteger  gasPrice;

    @DecimalMin(value = "0",message = "交易金额必须大于0")
    private BigInteger value;

    /**
     * 已编译的合同代码或被调用的方法签名和编码参数的散列???
     */
    private String data;

    @NotBlank(message = "钱包不能为空")
    private String passphrase;

    public TransactionRequest() {
    }

    public TransactionRequest(String userid, String from, String to, BigInteger gas, BigInteger gasPrice, BigInteger value, String data, String passphrase) {
        this.userid = userid;
        this.from = from;
        this.to = to;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.value = value;
        this.data = data;
        this.passphrase = passphrase;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }
}
