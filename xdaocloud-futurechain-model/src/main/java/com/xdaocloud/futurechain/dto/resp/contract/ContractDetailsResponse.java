package com.xdaocloud.futurechain.dto.resp.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合约详情
 * @author LuoFuMin
 * @data 2018/7/26
 */
public class ContractDetailsResponse implements IBaseRequest,Serializable {

    private static final long serialVersionUID = -5354167185820546969L;

    /**
     * 合约id
     */
    private Long id;


    /**
     * 合约id（区块链返回）
     */
    private String contractHashId;

    /**
     * 发布合约者id
     */
    private Long userId;

    /**
     * 发布合约者昵称
     */
    private String userNickName;

    /**
     * 发布合约者头像
     */
    private String userAvatar;


    /**
     * 合约类型身份：1-放贷，2-借贷
     */
    private Integer role;

    /**
     * 签约者id
     */
    private Long signId;

    /**
     * 签约者昵称
     */
    private String  signNickName;

    /**
     * 签约者头像
     */
    private String  signAvatar;


    /**
     * 签约时间
     */
    private String signTime;


    /**
     * 合约名称
     */
    private String conName;

    /**
     * 合约类型
     */
    private String contractTypeId;

    /**
     * 合约场地
     */
    private String conAddress;

    /**
     * 合约海报
     */
    private String portrait;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 借款方式
     */
    private Integer borrowWay;

    /**
     * 利息
     */
    private String accrual;

    /**
     * 还款时间
     */
    private String backTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 富文本
     */
    private String richText;

    /**
     * 状态 0：合约取消 1:合约不通过（拒绝签约）
     */
    private Integer state;

    /**
     * 违约金
     */
    private BigDecimal penalty;


    /**
     * 签约状态
     */
    private Boolean signState;

    /**
     * 收款状态
     */
    private Boolean collectState;

    /**
     * 还款状态
     */
    private Boolean stillState;

    /**
     * 合约状态 0 已取消 1 未开始 2 已结束 3 进行中
     */
    private Integer contractState;

    /**
     * 创建时间
     */
    private String gmtCreate;

    public Integer getContractState() {
        return contractState;
    }

    public void setContractState(Integer contractState) {
        this.contractState = contractState;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractHashId() {
        return contractHashId;
    }

    public void setContractHashId(String contractHashId) {
        this.contractHashId = contractHashId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public String getSignNickName() {
        return signNickName;
    }

    public void setSignNickName(String signNickName) {
        this.signNickName = signNickName;
    }

    public String getSignAvatar() {
        return signAvatar;
    }

    public void setSignAvatar(String signAvatar) {
        this.signAvatar = signAvatar;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getConAddress() {
        return conAddress;
    }

    public void setConAddress(String conAddress) {
        this.conAddress = conAddress;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getBorrowWay() {
        return borrowWay;
    }

    public void setBorrowWay(Integer borrowWay) {
        this.borrowWay = borrowWay;
    }

    public String getAccrual() {
        return accrual;
    }

    public void setAccrual(String accrual) {
        this.accrual = accrual;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
    }

    public Boolean getSignState() {
        return signState;
    }

    public void setSignState(Boolean signState) {
        this.signState = signState;
    }

    public Boolean getCollectState() {
        return collectState;
    }

    public void setCollectState(Boolean collectState) {
        this.collectState = collectState;
    }

    public Boolean getStillState() {
        return stillState;
    }

    public void setStillState(Boolean stillState) {
        this.stillState = stillState;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
