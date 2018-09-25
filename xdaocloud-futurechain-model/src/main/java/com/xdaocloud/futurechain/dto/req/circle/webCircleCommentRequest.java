package com.xdaocloud.futurechain.dto.req.circle;

import com.xdaocloud.futurechain.common.IBaseRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/13.
 */
public class webCircleCommentRequest implements IBaseRequest,Serializable {

    private static final long serialVersionUID = 2611759864115993602L;


    @NotNull(message = "文章id不能为空")
    private long circleId;


    private int page = 1;           //页数(默认是1)

    private int size = 10;           //每页显示数量

    public long getCircleId() {
        return circleId;
    }

    public void setCircleId(long circleId) {
        this.circleId = circleId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
