package com.xdaocloud.futurechain.dto.req.eos;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class EosCreateWalletRequest implements IBaseRequest {

    @NotBlank(message = "用户id不能为空")
    private String userid;

    /**
     * 钱包交易密码
     */
    @NotBlank(message = "交易密码不能为空")
    private String passPhrase;

    /**
     * 备注
     */
    private String  remarks;

    @NotBlank
    private String  walletName;


    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
