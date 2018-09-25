package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/13.
 */
public class WebCircleRequest implements IBaseRequest,Serializable {

    private static final long serialVersionUID = 2611759864115993602L;


    @NotNull(message = "文章id不能为空")
    private long circleId;

    public long getCircleId() {
        return circleId;
    }

    public void setCircleId(long circleId) {
        this.circleId = circleId;
    }


}
