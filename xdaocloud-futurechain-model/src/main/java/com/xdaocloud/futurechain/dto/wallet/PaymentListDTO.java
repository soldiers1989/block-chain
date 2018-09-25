package com.xdaocloud.futurechain.dto.wallet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 收支记录返回数据
 *
 * @author lixuzhou@foxmail.com
 * @version 0.1 2018/3/7
 */
public class PaymentListDTO implements Serializable {

    private static final long serialVersionUID = -4821302432743346677L;

    /**
     * 总余量
     */
    private BigDecimal total;

    /**
     * 收支记录总数
     */
    private Integer count;

    /**
     * 收支记录（分页）
     */
    private List<PaymentDTO> payment;

    /**
     * @return Returns the total
     **/
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total The total to set.
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return Returns the count
     **/
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count to set.
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return Returns the payment
     **/
    public List<PaymentDTO> getPayment() {
        return payment;
    }

    /**
     * @param payment The payment to set.
     */
    public void setPayment(List<PaymentDTO> payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "PaymentListDTO{" + "total=" + total + ", count=" + count + ", payment=" + payment + '}';
    }
}
