package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.friend.GroupResponse;
import com.xdaocloud.futurechain.model.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    List<Group> findListByUserId(@Param("userNo") String userNo);

    List<Group> findListByGroupIds(@Param("list") List<String> list);

    List<String> findUserIdsByGroupId(@Param("groupId") String groupId);

    List<String> findGroupIdsByUserNo(@Param("userNo") String userNo);


    int findCount(@Param("userNo") String userNo, @Param("groupId") String groupId);

    List<GroupResponse> findUsersByGroupId(@Param("groupId") String groupId);

    Group findOneByGroupId(@Param("groupId") String groupId);

    int deleteMembersByGroupId(@Param("groupId") String groupId);

    int quit(@Param("groupId") String groupId,@Param("accid") String accid);

    int addMembers(@Param("groupId")String groupId, @Param("accid")String accid);

    int removeMembers(@Param("groupId")String groupId, @Param("accid")String accid);
}