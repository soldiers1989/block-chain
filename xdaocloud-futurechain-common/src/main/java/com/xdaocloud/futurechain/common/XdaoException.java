package com.xdaocloud.futurechain.common;

/**
 * 继承RuntimeException 异常，Spring对运行时异常进行事务回滚
 * <p>
 */
public class XdaoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private Object data;

    public XdaoException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public XdaoException(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
