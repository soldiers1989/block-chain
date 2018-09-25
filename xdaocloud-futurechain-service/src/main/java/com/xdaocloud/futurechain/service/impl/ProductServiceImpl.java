package com.xdaocloud.futurechain.service.impl;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.mapper.ProductMapper;
import com.xdaocloud.futurechain.mapper.RewardMapper;
import com.xdaocloud.futurechain.model.Product;
import com.xdaocloud.futurechain.model.Reward;
import com.xdaocloud.futurechain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品业务类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RewardMapper rewardMapper;

    /**
     * 获取商品
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findProducts() throws Exception {
        List<Product> products = productMapper.findProducts();
        if (null == products || products.size() == 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_DATA_NOT_FOUND);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, products);
    }

    /**
     * 获取麦粒奖励数目
     *
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reward> getReward() {
        List<Reward> list = rewardMapper.findAllList();
        return list;
    }
}
