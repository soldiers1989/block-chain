package com.xdaocloud.futurechain.httpapi;



/**
 * 云信返回信息
 *
 * @author LuoFuMin
 * @data 2018/9/10
 */
public class ResultMsg<T> {
    private Integer code;
    /**
     * 错误信息
     */
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
