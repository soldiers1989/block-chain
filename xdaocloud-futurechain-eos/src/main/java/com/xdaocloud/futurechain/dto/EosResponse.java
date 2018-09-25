package com.xdaocloud.futurechain.dto;

import com.xdaocloud.futurechain.common.IBaseRequest;
import com.xdaocloud.futurechain.dto.resp.eos.KeyDTO;

public class EosResponse<T> implements IBaseRequest {

    /**
     * eos返回错误 例子
     * {"code":500,"message":"Internal Service Error","error":{"code":3120001,"name":"wallet_exist_exception","what":"Wallet already exists","details":[{"message":"Wallet with name: 'cmd' already exists at /root/.local/share/eosio/nodeos/data/./cmd.wallet","file":"wallet_manager.cpp","line_number":41,"method":"create"}]}}
     */

    /**
     * 1 表示success；
     * 0  和 500 表示fail；
     * -1 表示异常；
     * java中Integer类型对于-128-127之间的数是缓冲区取的，所以用等号比较是一致的。但对于不在这区间的数字是在堆中new出来的。所以地址空间不一样，也就不相等。
     * 所以，以后碰到Integer比较值是否相等需要用intValue()
     * Double没有缓冲区。
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    private KeyDTO keyDTO;

    /**
     * 数据
     */
    private T data;

    /**
     * 错误
     */
    private Error error;

    public EosResponse() {
    }

    public EosResponse(Integer code) {
        this.code = code;
    }

    public EosResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public EosResponse(Integer code, KeyDTO keyDTO) {
        this.code = code;
        this.keyDTO = keyDTO;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public KeyDTO getKeyDTO() {
        return keyDTO;
    }

    public void setKeyDTO(KeyDTO keyDTO) {
        this.keyDTO = keyDTO;
    }
}
