package com.xdaocloud.futurechain.dto.req.user;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 记录用户
 *
 * @author LuoFuMin
 * @data 2018/9/10
 */
public class RecordRequest implements Serializable {

    private static final long serialVersionUID = 4368899967467527215L;

    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;

    @NotBlank(message = "用户名不能为空")
    private String mobileNumber;


}
