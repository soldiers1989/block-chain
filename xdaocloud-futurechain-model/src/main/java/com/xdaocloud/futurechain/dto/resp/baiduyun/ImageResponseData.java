package com.xdaocloud.futurechain.dto.resp.baiduyun;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/5.
 */
public class ImageResponseData implements Serializable{

    private String msg;
    private Double probability;
    private Integer type;
    private  Object stars;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getStars() {
        return stars;
    }

    public void setStars(Object stars) {
        this.stars = stars;
    }
}
