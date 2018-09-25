package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/13.
 */
public class WebSystemSetupRequest implements IBaseRequest,Serializable {

    private static final long serialVersionUID = -1829312453691897224L;

    @NotNull(message = "开关id不能为空")
    private int id;
    @NotNull(message = "开关状态不能为空")
    private int keyValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(int keyValue) {
        this.keyValue = keyValue;
    }
}
