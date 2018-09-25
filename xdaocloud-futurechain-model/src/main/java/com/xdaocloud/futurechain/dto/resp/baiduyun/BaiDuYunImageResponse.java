package com.xdaocloud.futurechain.dto.resp.baiduyun;

import java.util.List;

/**
 * Created by Administrator on 2018/7/5.
 */
public class BaiDuYunImageResponse {

    public String conclusion;
    public Long log_id;
    public Integer conclusionType;
    public List<ImageResponseData> data;

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public int getConclusionType() {
        return conclusionType;
    }

    public void setConclusionType(Integer conclusionType) {
        this.conclusionType = conclusionType;
    }

    public List<ImageResponseData> getData() {
        return data;
    }

    public void setData(List<ImageResponseData> data) {
        this.data = data;
    }
}
