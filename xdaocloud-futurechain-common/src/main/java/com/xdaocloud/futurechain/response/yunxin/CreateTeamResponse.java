package com.xdaocloud.futurechain.response.yunxin;

/**
 * 创建群
 *
 * @author LuoFuMin
 * @data 2018/9/12
 */
public class CreateTeamResponse {

    private Integer code;

    private String tid;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
