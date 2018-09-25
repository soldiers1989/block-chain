package com.xdaocloud.futurechain.dto.req.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class IdCardRequest implements IBaseRequest, Serializable{

    private static final long serialVersionUID = 8583238700914334104L;

    @Pattern(message = "身份证号码必须为18位数，不能有特殊字符", regexp = "[A-Za-z0-9]{18,18}")
    private String  idcard;

    @NotBlank(message = "姓名不能为空")
    @Pattern(message = "姓名必须为2~10位汉字", regexp = "[\u4e00-\u9fa5]{2,10}")
    private String name;

    @NotBlank(message = "用户id不能为空")
    private String userid;

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
