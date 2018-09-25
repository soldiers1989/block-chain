package com.xdaocloud.futurechain.dto.wallet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 具体某一条收支记录
 *
 * @author lixuzhou@foxmail.com
 * @version 0.1 2018/3/7
 */
public class PaymentDTO implements Serializable {

    private static final long serialVersionUID = -5805613295526177045L;

    /**
     * 收支记录ID
     */
    @JsonIgnore
    private Long id;

    /**
     * 收支渠道
     */
    private String way;

    /**
     * 收支日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date date;

    /**
     * 收支数量
     */
    private BigDecimal amount;

    /**
     * @return Returns the id
     **/
    public Long getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Returns the way
     **/
    public String getWay() {
        return way;
    }

    /**
     * @param way The way to set.
     */
    public void setWay(String way) {
        this.way = way;
    }

    /**
     * @return Returns the date
     **/
    public Date getDate() {
        return date;
    }

    /**
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Returns the amount
     **/
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" + "id=" + id + ", way='" + way + '\'' + ", date=" + date + ", amount=" + amount + '}';
    }
}
