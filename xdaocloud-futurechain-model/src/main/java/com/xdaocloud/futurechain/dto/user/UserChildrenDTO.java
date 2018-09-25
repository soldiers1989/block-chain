package com.xdaocloud.futurechain.dto.user;


import java.io.Serializable;
import java.util.List;

public class UserChildrenDTO implements Serializable {

    private static final long serialVersionUID = 5778360439957394166L;

    private Long id;

    private String inviteCode;

    private String name;

    private String mobileNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    private List<UserChildrenDTO> childrenUserIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserChildrenDTO> getChildrenUserIds() {
        return childrenUserIds;
    }

    public void setChildrenUserIds(List<UserChildrenDTO> childrenUserIds) {
        this.childrenUserIds = childrenUserIds;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
