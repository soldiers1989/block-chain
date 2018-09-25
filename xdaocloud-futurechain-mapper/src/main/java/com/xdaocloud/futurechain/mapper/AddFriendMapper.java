package com.xdaocloud.futurechain.mapper;


import com.xdaocloud.futurechain.dto.resp.friend.FriendApplyResponse;
import com.xdaocloud.futurechain.model.AddFriend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddFriendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddFriend record);

    int insertSelective(AddFriend record);

    AddFriend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AddFriend record);

    int updateByPrimaryKey(AddFriend record);

    List<FriendApplyResponse> getFriendApplyByUserId(@Param("friendId") Long friendId);

    /**
     * 同意加好友
     *
     * @param userId
     * @param friendUserId
     * @param type
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    void updateIsAgree(@Param("userId") Long userId, @Param("friendUserId") Long friendUserId, @Param("type") byte type);


    /**
     * 查询添加记录
     *
     * @param userId   用户id
     * @param friendId 朋友id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    AddFriend findOneByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);


    /**
     * 查询添加记录
     *
     * @param userId   用户id
     * @param friendId 朋友id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    AddFriend findOneByUserIdOrFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    Integer selectByuserIdAndfriendUserId(@Param("userId") Long userId, @Param("friendUserId") Long friendUserId);

    void updateByuserIdAndfriendUserId(@Param("userId") Long userId, @Param("friendUserId") Long friendUserId);
}