package com.xdaocloud.futurechain.dto.resp.user;

import com.xdaocloud.futurechain.common.IBaseRequest;

import java.io.Serializable;

public class PropertyResponse implements IBaseRequest, Serializable{

    private static final long serialVersionUID = -6355852153074583827L;

    private String ore;

    private String mai;

    private String price;

    private String lock_mai;

    private String sum_mai;

    public PropertyResponse(String ore, String mai) {
        this.ore = ore;
        this.mai = mai;
    }

    public PropertyResponse(String ore, String mai, String price) {
        this.ore = ore;
        this.mai = mai;
        this.price = price;
    }

    public PropertyResponse(String ore, String mai, String price, String lock_mai) {
        this.ore = ore;
        this.mai = mai;
        this.price = price;
        this.lock_mai = lock_mai;
    }

    public PropertyResponse(String ore, String mai, String price, String lock_mai, String sum_mai) {
        this.ore = ore;
        this.mai = mai;
        this.price = price;
        this.lock_mai = lock_mai;
        this.sum_mai = sum_mai;
    }

    public String getSum_mai() {
        return sum_mai;
    }

    public void setSum_mai(String sum_mai) {
        this.sum_mai = sum_mai;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOre() {
        return ore;
    }

    public void setOre(String ore) {
        this.ore = ore;
    }

    public String getMai() {
        return mai;
    }

    public void setMai(String mai) {
        this.mai = mai;
    }

    public String getLock_mai() {
        return lock_mai;
    }

    public void setLock_mai(String lock_mai) {
        this.lock_mai = lock_mai;
    }
}
