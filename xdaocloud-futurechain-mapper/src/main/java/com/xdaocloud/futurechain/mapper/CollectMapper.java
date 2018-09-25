package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Collect;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);
    
    /**
     * 
     * 查询数量
     * @param collect
     * @return 
     * @date 2018年6月28日
     * @author dql
     */
    Long selectCount(Collect collect);
    
    /**
     * 
     * 查询id
     * @param collect
     * @return 
     * @date 2018年6月28日
     * @author dql
     */
    Long selectCollectId(Collect collect);

    int selectCountByUserIdAndCircleId(@Param("userId") Long userIds, @Param("circleId") Long circleId);
}