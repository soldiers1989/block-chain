package com.xdaocloud.futurechain.dto.req.eos;

import com.xdaocloud.futurechain.common.IBaseRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author LuoFuMin
 * @data 2018/7/18
 */
public class GetPrivateKeyRequest extends UserIdRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = 3597966813690117901L;

    @NotBlank(message = "交易密码不能为空！")
    private String transactionPassword;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public void setTransactionPassword(String transactionPassword) {
        this.transactionPassword = transactionPassword;
    }
}
