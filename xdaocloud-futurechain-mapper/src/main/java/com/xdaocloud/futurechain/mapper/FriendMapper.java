package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.friend.FriendResponse;
import com.xdaocloud.futurechain.model.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);

    Friend findOneByParam(Friend friend);

    List<Friend>  findListByParam(Friend friend);

    List<FriendResponse> findListByUserId(Long userId);

    /**
     * 主动加的好友
     * @param userId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<Long> findFriendsByUserId(Long userId);

    /**
     * 被动加的好友
     * @param friendId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<Long> findFriendsByFriendId(Long friendId);

    List<FriendResponse> findListByFriendId(Long userId);

    /**
     * 查询好友数量
     * @param userId 用户id
     * @param friendId 朋友id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    int findCountByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    /**
     * 查询好友
     * @param userId
     * @param friendId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    Friend findOneByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    int countByUserIdOfMyFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    /**
     * 查询用户全部好友ID
     * @param userId
     * @return
     */
    List<Long> selectFriendIdListBuUserId(@Param("userId")Long userId);
}