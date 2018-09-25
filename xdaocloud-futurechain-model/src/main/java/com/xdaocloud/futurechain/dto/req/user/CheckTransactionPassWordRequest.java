package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 验证支付密码
 * @author LuoFuMin
 * @data 2018/9/13
 */
public class CheckTransactionPassWordRequest implements IBaseRequest,Serializable{
    private static final long serialVersionUID = -4744260387449528862L;

    @NotNull
    private Long userid;

    @NotBlank
    private String transactionPassWord;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getTransactionPassWord() {
        return transactionPassWord;
    }

    public void setTransactionPassWord(String transactionPassWord) {
        this.transactionPassWord = transactionPassWord;
    }
}
