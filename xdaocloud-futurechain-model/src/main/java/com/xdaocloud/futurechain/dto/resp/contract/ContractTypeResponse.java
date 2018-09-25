package com.xdaocloud.futurechain.dto.resp.contract;


import com.xdaocloud.futurechain.common.IBaseRequest;
import com.xdaocloud.futurechain.util.tree.TreeData;

import java.util.List;

/**
 * @author LuoFuMin
 * @data 2018/7/23
 */
public class ContractTypeResponse extends TreeData implements IBaseRequest {


    private String name;

    private Boolean isHot;

    public ContractTypeResponse() {
        super();
    }

    public ContractTypeResponse(Long id, String name, Long parentId, List<TreeData> children) {
        super();
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }
}
