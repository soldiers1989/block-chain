package com.xdaocloud.futurechain.dto.req.eos;

import com.xdaocloud.futurechain.common.IBaseRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 导入eos钱包
 *
 * @author LuoFuMin
 * @data 2018/7/18
 */
public class ImportAccountRequest extends UserIdRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -5373919627769339336L;

    private String walletName;

    @NotBlank(message = "私钥不能为空")
    @Length(min = 51, max = 51, message = "钱包密匙错误！")
    private String privateKey;

    /**
     * 钱包交易密码
     */
    @NotBlank(message = "交易密码不能为空")
    private String passPhrase;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }
}
