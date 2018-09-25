package com.xdaocloud.futurechain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合约
 *
 * @author LuoFuMIn
 * @date 2018/7/20
 */

public class Contract implements Serializable {

    private static final long serialVersionUID = -6553655971101447142L;

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
     * 合约类型身份：1-放贷，2-借贷
     */
    private Integer role;

    /**
     * 签约者id
     */
    private Long signId;


    /**
     * 签约时间
     */
    private String signTime;


    /**
     * 合约名称
     */
    private String conName;

    /**
     * 合约类型id
     */
    private Long contractTypeId;

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


    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName == null ? null : conName.trim();
    }

    public Long getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(Long contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getConAddress() {
        return conAddress;
    }

    public void setConAddress(String conAddress) {
        this.conAddress = conAddress == null ? null : conAddress.trim();
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
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
        this.accrual = accrual == null ? null : accrual.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText == null ? null : richText.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
    }

    public String getContractHashId() {
        return contractHashId;
    }

    public void setContractHashId(String contractHashId) {
        this.contractHashId = contractHashId;
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
}