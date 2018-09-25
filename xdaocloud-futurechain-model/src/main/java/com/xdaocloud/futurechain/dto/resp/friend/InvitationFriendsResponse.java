package com.xdaocloud.futurechain.dto.resp.friend;

import com.xdaocloud.futurechain.common.IBaseRequest;
import com.xdaocloud.futurechain.dto.resp.user.UserInfoResponse;

import java.io.Serializable;
import java.util.List;

public class InvitationFriendsResponse implements IBaseRequest,Serializable {

    private static final long serialVersionUID = 380466215391171758L;
    private Integer count;

    private List<UserInfoResponse> userInfoResponseList;

    public InvitationFriendsResponse(Integer count, List<UserInfoResponse> userInfoResponseList) {
        this.count = count;
        this.userInfoResponseList = userInfoResponseList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserInfoResponse> getUserInfoResponseList() {
        return userInfoResponseList;
    }

    public void setUserInfoResponseList(List<UserInfoResponse> userInfoResponseList) {
        this.userInfoResponseList = userInfoResponseList;
    }
}
