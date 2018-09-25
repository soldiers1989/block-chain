package com.xdaocloud.futurechain.dto.req.ore;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

public class CreateOreEnvelopeRequest implements IBaseRequest,Serializable{
    private static final long serialVersionUID = 2605573921543012884L;

    @NotBlank(message = "用户id不能为空")
    private String userid;

    /**
     * 矿包数量
     */
    @Min(value = 1,message = "不能小于1")
    @Max(value = 100,message = "不能大于100")
    private Integer count;

    /**
     * 矿包标题
     */
    @NotBlank(message = "标题不能为空")
    private String ore_title;

    /**
     * 矿包金额
     */
    @Min(value = 1,message = "不能小于1")
    private String amount;


    private String transactionPassword;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOre_title() {
        return ore_title;
    }

    public void setOre_title(String ore_title) {
        this.ore_title = ore_title;
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public void setTransactionPassword(String transactionPassword) {
        this.transactionPassword = transactionPassword;
    }
}
