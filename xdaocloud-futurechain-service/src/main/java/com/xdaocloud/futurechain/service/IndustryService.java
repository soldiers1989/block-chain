package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/15.
 */
public interface IndustryService {
    /**
     * 行业列表（根据行业类型默认选中）
     * @param map
     * @return
     */
    ResultInfo<?> getIndustryList(Map<String, Object> map);
}
