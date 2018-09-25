package com.xdaocloud.futurechain.dto.req.contract;

import com.xdaocloud.futurechain.common.IBaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LuoFuMin
 * @data 2018/7/24
 */
public class AddRichTextRequest implements IBaseRequest, Serializable {

    private static final long serialVersionUID = -3825306254207127747L;

    /**
     * 富文本
     */
    @NotBlank
    private String richText;

    /**
     * 合约id
     */
    @NotNull
    private Long contractId;

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
}
