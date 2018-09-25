package com.xdaocloud.futurechain.dto.req.feedback;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

public class AddFeedbackRequest implements IBaseRequest {

    @NotBlank(message = "用户id不能为空")
    private String userid;

    @NotBlank
    private String content;

    @NotBlank
    private String contact;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
