package com.xdaocloud.futurechain.dto.req.address;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 更新地址
 *
 * @author LuoFuMin
 * @data 2018/9/12
 */
public class UpdateAddressRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -5121344257342424181L;

    @NotNull
    private Long id;

    @NotNull
    private Long userid;

    /**
     * 名字
     */
    private String name;

    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区/县
     */
    private String county;
    /**
     * 详细门牌号
     */
    private String detailed;
    /**
     * 邮政编码
     */
    @Size(min = 6, max = 6, message = "邮政编码必须是6位数字")
    @Pattern(regexp = "^[0-9]+$", message = "邮政编码必须是6位数字")
    private String postcode;
    /**
     * 手机号码
     */
    @Pattern(message = "手机号码格式不正确", regexp = "^[1][3,4,5,7,8][0-9]{9}$")
    private String phone;
    /**
     * 是否选中
     */
    private Byte choice;



    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getChoice() {
        return choice;
    }

    public void setChoice(Byte choice) {
        this.choice = choice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
