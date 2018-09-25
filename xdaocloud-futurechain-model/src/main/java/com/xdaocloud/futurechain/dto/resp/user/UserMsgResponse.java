package com.xdaocloud.futurechain.dto.resp.user;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.util.Date;

public class UserMsgResponse extends JdkSerializationRedisSerializer implements IBaseRequest,Serializable {

    private static final long serialVersionUID = 1064561038575061232L;
    /**
     * 用户id
     */
    public Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobileNumber;

    /**
     * 别名
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;


    /**
     * 注册时使用的邀请码
     */
    private String registerInviteCode;

    /**
     * 用于邀请其它用户注册的邀请码
     */
    private String inviteCode;

    /**
     * 用户所拥有的矿石总额
     */
    private Long ore;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 行业id
     */
    private String profession;

    /**
     * 行业名称
     */
    private String professionName;


    /**
     * 邮箱
     */
    private String email;


    /**
     * 是否签到
     */
    public boolean signIn;

    /**
     * 好友数量
     */
    public Integer friend;

    /**
     * 关注数量
     */
    public Integer follow;

    /**
     * 粉丝数量
     */
    public Integer fans;

    /**
     * 等级
     */
    private int vipRank;

    /**
     * 代理候选人状态
     */
    private byte agent;

    /**
     * 用户地址
     */
    private String address;

    /**
     * eos 钱包名
     */
    private String eosWalletName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRegisterInviteCode() {
        return registerInviteCode;
    }

    public void setRegisterInviteCode(String registerInviteCode) {
        this.registerInviteCode = registerInviteCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Long getOre() {
        return ore;
    }

    public void setOre(Long ore) {
        this.ore = ore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSignIn() {
        return signIn;
    }

    public void setSignIn(boolean signIn) {
        this.signIn = signIn;
    }

    public Integer getFriend() {
        return friend;
    }

    public void setFriend(Integer friend) {
        this.friend = friend;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public int getVipRank() {
        return vipRank;
    }

    public void setVipRank(int vipRank) {
        this.vipRank = vipRank;
    }

    public byte getAgent() {
        return agent;
    }

    public void setAgent(byte agent) {
        this.agent = agent;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getEosWalletName() {
        return eosWalletName;
    }

    public void setEosWalletName(String eosWalletName) {
        this.eosWalletName = eosWalletName;
    }
}
