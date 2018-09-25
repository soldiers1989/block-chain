package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.model.Reward;

import java.util.List;

public interface ProductService {


    /**
     * 获取商品
     * @return
     * @throws Exception
     */
    ResultInfo<?> findProducts() throws Exception;

    /**
     * 获取麦粒奖励数目
     *
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    List<Reward> getReward();
}
