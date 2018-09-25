package com.xdaocloud.futurechain.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.mapper.IndustryMapper;
import com.xdaocloud.futurechain.model.Industry;
import com.xdaocloud.futurechain.service.IndustryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/15.
 */

@Service
public class IndustryServiceImpl implements IndustryService {


    private static Logger LOG = LoggerFactory.getLogger(IndustryServiceImpl.class);

    @Autowired
    private IndustryMapper industryMapper;

    /**
     * 行业列表（根据行业类型默认选中）
     *
     * @param map
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getIndustryList(Map<String, Object> map) {
        List<Map<String,Object>> list =  new ArrayList<>();
        try {

           boolean isIndustryType = true;
            //修改添加好友记录表
            List<Industry> industries = industryMapper.getIndustryList();
            if(industries != null ){
                if (StringUtil.isEmpty(map.get("industryType").toString())) {
                    isIndustryType = false;
                }
                for (Industry industry : industries){
                    Map<String,Object> stringMap = new HashMap<>();
                    stringMap.put("industryId",industry.getId());
                    stringMap.put("industryType",industry.getIndustryType());
                    stringMap.put("industryName",industry.getIndustryName());
                    stringMap.put("industryColor",industry.getIndustryColor());
                    if (isIndustryType && industry.getIndustryType().equals(map.get("industryType").toString())){
                        stringMap.put("isOnclick",true);
                    }else {
                        stringMap.put("isOnclick",false);
                    }
                    list.add(stringMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS,list);
    }
}
