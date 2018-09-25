package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.Follow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Follow record);

    int insertSelective(Follow record);

    Follow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Follow record);

    int updateByPrimaryKey(Follow record);

    int  findCountByParam(Follow record);

    List<Follow> findListByParam(Follow record);

    int selectByUserIdAndPassiveUserId(@Param("userId") Long userId,@Param("passiveUserId")  Long passiveUserId);

    void updateByUserIdAndFollowUserId(@Param("userId")long userId,@Param("followUserId") long followUserId);
    
    /**
     * 是否已关注  0=未关注
     */
    long selectIsFollow(@Param("userId")Long userId,@Param("passiveUserId")Long passiveUserId);
    
    /**
     * 阅读总数
     */
    long selectFollowCountAll(@Param("userId")Long userId);
    
    /**
     * 好友关注数
     */
    long selectFriendFollowNum(@Param("userId")Long userId, @Param("passiveUserId")Long passiveUserId);
}