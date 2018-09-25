package com.xdaocloud.futurechain.kafka;

import java.io.Serializable;

/**
 * 接收推送者
 * 
 * @author LiDong
 */
public class ReceiveUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id，个推时必填
     */
    private String toUserId;

    /**
     * 手机号，短信时必填
     */
    private Long mobile;

    /**
     * 收件人email，发邮件时必填
     */
    private String email;

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
