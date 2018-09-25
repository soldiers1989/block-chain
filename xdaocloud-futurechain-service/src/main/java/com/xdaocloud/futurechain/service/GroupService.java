package com.xdaocloud.futurechain.service;

import com.alibaba.fastjson.JSONArray;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.group.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface GroupService {


    /**
     * 创建群
     *
     * @param groupAvatarFile 群头像
     * @param userid          群主id
     * @param groupName       群名称
     * @param desc            群描述
     * @param members         群成员
     * @param restrictNum     限制人数
     * @param magree          0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群
     * @param parentId        父id
     * @return ResultInfo
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> create(MultipartFile groupAvatarFile, Long userid, String groupName, String desc, JSONArray members,
                         int magree, Long parentId, Integer restrictNum, HttpServletRequest httpServletRequest) throws IOException;


    /**
     * 修改群
     *
     * @param groupAvatarFile 群头像
     * @param userid          群主id
     * @param groupId         群id
     * @param industryId      行业id
     * @param groupName       群名称
     * @param restrictNum     限制人数
     * @param magree          0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群
     * @param parentId        父id
     * @param announcement    群公告
     * @param intro           群描述，最大长度512字符
     * @param beinvitemode    被邀请人同意方式，0-需要同意(默认),1-不需要同意
     * @param invitemode      谁可以邀请他人入群，0-管理员(默认),1-所有人
     * @param uptinfomode     谁可以修改群资料，0-管理员(默认),1-所有人
     * @param upcustommode    谁可以更新群自定义属性，0-管理员(默认),1-所有人
     * @return
     * @throws Exception
     * @date 2018年9月14日
     * @author LuoFuMin
     */
    ResultInfo<?> update(MultipartFile groupAvatarFile, Long userid, String groupId, Long industryId, String groupName, Integer restrictNum,
                         int magree, Long parentId, String announcement, String intro,
                         int beinvitemode, int invitemode, int uptinfomode, int upcustommode, HttpServletRequest httpServletRequest
    ) throws Exception;


    /**
     * 加入群
     *
     * @param joinGroupRequest
     * @return
     */
    ResultInfo<?> add(JoinGroupRequest joinGroupRequest) throws Exception;

    /**
     * 获取群成员
     *
     * @param groupId
     * @return
     */
    ResultInfo<?> getGroupMembers(String groupId);

    /**
     * 获取群列表
     *
     * @param request
     * @return
     */
    ResultInfo<?> groupList(UserNoRequest request);


    /**
     * 删除群
     *
     * @param request
     * @return
     */
    ResultInfo<?> delete(DeleteGroupRequest request) throws Exception;


    /**
     * 退群
     *
     * @param leaveGroupRequest
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    ResultInfo<?>  quit(LeaveGroupRequest leaveGroupRequest) throws Exception;


    /**
     * 添加管理员
     *
     * @param addManagerRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    ResultInfo<?> addManager(AddManagerRequest addManagerRequest) throws Exception;

    /**
     * 移除管理员
     *
     * @param addManagerRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    ResultInfo<?> removeManager(AddManagerRequest addManagerRequest) throws Exception;

    /**
     * 禁言
     *
     * @param muteTlistAllRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    ResultInfo<?> muteTlistAll(MuteTlistAllRequest muteTlistAllRequest) throws Exception;

    /**
     * 修改消息提醒开关
     *
     * @param muteTeamRequest
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    ResultInfo<?> muteTeam(MuteTeamRequest muteTeamRequest)  throws Exception;

    /**
     * 踢人出群
     *
     * @param kickMembersRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> kick(KickMembersRequest kickMembersRequest) throws Exception ;
}
