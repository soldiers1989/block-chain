package com.xdaocloud.futurechain.response.yunxin;

/**
 * 创建 云信 accid
 * @author LuoFuMin
 * @data 2018/9/12
 */
public class CreateAccIdResponse {

    private Integer code;


    private Info info;

    /**
     * 错误信息
     */
    private String desc;

    public class Info {
        public String token;
        public String accid;
        public String name;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
