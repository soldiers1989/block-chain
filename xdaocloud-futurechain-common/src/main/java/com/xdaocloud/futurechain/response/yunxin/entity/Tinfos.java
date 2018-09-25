package com.xdaocloud.futurechain.response.yunxin.entity;

import com.alibaba.fastjson.JSON;
import com.xdaocloud.futurechain.response.yunxin.TeamQueryResponse;


/**
 * 获取群信息
 *
 * @author LuoFuMin
 * @data 2018/9/12
 */
public class Tinfos {

    private String icon;
    private String tname;
    private String announcement;
    private String owner;
    private Integer maxusers;
    private Integer joinmode;
    private Integer tid;
    private String intro;
    public Integer size;
    private String custom;
    private String clientCustom;
    private Boolean mute;
    private Long createtime;
    private Long updatetime;
    private String[] admins;
    private String[] members;
    private Integer muteType;
    private Integer uptinfomode;
    private Integer upcustommode;
    private Integer beinvitemode;
    private Integer invitemode;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    public Integer getJoinmode() {
        return joinmode;
    }

    public void setJoinmode(Integer joinmode) {
        this.joinmode = joinmode;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getClientCustom() {
        return clientCustom;
    }

    public void setClientCustom(String clientCustom) {
        this.clientCustom = clientCustom;
    }

    public Boolean getMute() {
        return mute;
    }

    public void setMute(Boolean mute) {
        this.mute = mute;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public String[] getAdmins() {
        return admins;
    }

    public void setAdmins(String[] admins) {
        this.admins = admins;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public Integer getMuteType() {
        return muteType;
    }

    public void setMuteType(Integer muteType) {
        this.muteType = muteType;
    }

    public Integer getUptinfomode() {
        return uptinfomode;
    }

    public void setUptinfomode(Integer uptinfomode) {
        this.uptinfomode = uptinfomode;
    }

    public Integer getUpcustommode() {
        return upcustommode;
    }

    public void setUpcustommode(Integer upcustommode) {
        this.upcustommode = upcustommode;
    }

    public Integer getBeinvitemode() {
        return beinvitemode;
    }

    public void setBeinvitemode(Integer beinvitemode) {
        this.beinvitemode = beinvitemode;
    }

    public Integer getInvitemode() {
        return invitemode;
    }

    public void setInvitemode(Integer invitemode) {
        this.invitemode = invitemode;
    }

    public static void main(String[] args) {
        String str = "{\"tinfos\":[{\"icon\":null,\"announcement\":null,\"updatetime\":1536742405067,\"muteType\":0,\"uptinfomode\":0,\"maxusers\":200,\"intro\":null,\"size\":2,\"createtime\":1536742405067,\"upcustommode\":0,\"owner\":\"13808962301\",\"tname\":\"13808962301\",\"beinvitemode\":0,\"joinmode\":0,\"tid\":1386609010,\"invitemode\":0,\"mute\":false}],\"code\":200}";

        TeamQueryResponse teamQueryResponse = JSON.parseObject(str, TeamQueryResponse.class);

        System.out.println(teamQueryResponse.getCode());
        System.out.println(teamQueryResponse.getTinfos());

        Integer i  = 1386609010;

        System.out.println(i);

    }
}
