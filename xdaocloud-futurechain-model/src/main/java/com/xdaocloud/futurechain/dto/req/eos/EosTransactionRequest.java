package com.xdaocloud.futurechain.dto.req.eos;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class EosTransactionRequest implements IBaseRequest{

    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank(message = "转入账户不能为空")
    private String toAccount;

    @NotBlank(message = "转移数量不能为空")
    private String num;

    private String transactionPassword;

    private String desc;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getTransactionPassword() {
        return transactionPassword;
    }

    public void setTransactionPassword(String transactionPassword) {
        this.transactionPassword = transactionPassword;
    }
}
