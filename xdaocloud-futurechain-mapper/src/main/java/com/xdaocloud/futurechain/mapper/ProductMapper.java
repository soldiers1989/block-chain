package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> findProducts();

    /**
     * 根据编码查询商品
     * @param code
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    Product findByProductCode(String code);
}