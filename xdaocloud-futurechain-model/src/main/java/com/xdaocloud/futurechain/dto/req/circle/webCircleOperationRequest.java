package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/13.
 */
public class webCircleOperationRequest implements IBaseRequest ,Serializable {


    private static final long serialVersionUID = -8038880010709758504L;

    @NotNull(message = "文章id不能为空")
    private long circleId;
    @NotNull(message = "状态不能为空")
    private int type;

    public long getCircleId() {
        return circleId;
    }

    public void setCircleId(long circleId) {
        this.circleId = circleId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
