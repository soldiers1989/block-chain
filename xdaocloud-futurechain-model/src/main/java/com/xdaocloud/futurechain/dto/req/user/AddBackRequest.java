package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class AddBackRequest implements IBaseRequest,Serializable {

    private static final long serialVersionUID = -6233986030788909196L;


    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank(message = "银行名不能为空")
    private String backName;

    @NotBlank(message = "卡号不能为空")
    @Length(message = "卡号必须是{min}~{max}位字符", min = 16, max = 19)
    private String bankNumber;

    @NotBlank
    private String bankType;

    @Pattern(message = "姓名必须为2~8位汉字", regexp = "[\u4e00-\u9fa5]{2,8}")
    private String cardholder;

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }
}
