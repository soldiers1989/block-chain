package com.xdaocloud.futurechain.controller;

import com.alibaba.fastjson.JSONArray;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.group.*;
import com.xdaocloud.futurechain.service.GroupService;
import com.xdaocloud.futurechain.util.ImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 群管理
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "GroupController", description = "群")
@RequestMapping("/api/app/")
public class GroupController{

    @Autowired
    private GroupService groupService;

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
     * @return
     * @throws Exception
     * @date 2018年9月13日
     * @author LuoFuMin
     */
    @ApiOperation(value = "创建群", notes = "groupAvatar：群头像（非）、userid ：用户id、groupName ：群名字（非）、desc ：邀请入群信息（非）、" +
            "members ： 邀请群成员、groupIds 子群id数组（非）、restrictNum ： 限制人数（非）、" +
            "magree ：0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群（非）、parentId ：父id（非）、groupType ：群类型 0 -普通群， 1-社群（非）")
    @PostMapping("v2/group")
    public ResultInfo<?> create(@RequestParam(name = "groupAvatar", required = false) MultipartFile groupAvatarFile,
                                @RequestParam(name = "userid") Long userid,
                                @RequestParam(name = "groupName", required = false) String groupName,
                                @RequestParam(name = "desc", required = false) String desc,
                                @RequestParam(name = "members", required = false) JSONArray members,
                                @RequestParam(name = "restrictNum", required = false) Integer restrictNum,
                                @RequestParam(name = "magree", required = false, defaultValue = "0") int magree,
                                @RequestParam(name = "parentId", required = false, defaultValue = "0") Long parentId,
                                HttpServletRequest httpServletRequest
    ) throws Exception {
        if (groupAvatarFile != null) {
            ResultInfo<?> x = ImageUtils.checkImage(groupAvatarFile);
            if (x != null) {
                return x;
            }
        }
        if (members != null && members.isEmpty()) {
            return new ResultInfo<>(ResultInfo.FAILURE, "群成员不能为空");
        }
        return groupService.create(groupAvatarFile, userid, groupName, desc, members, magree, parentId, restrictNum, httpServletRequest);
    }

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
    @ApiOperation(value = "修改群信息", notes = "groupAvatar：群头像（非）、userid ：用户id、groupName ：群名字（非）、desc ：邀请入群信息（非）、" +
            "members ： 邀请群成员、restrictNum ： 限制人数（非）、magree ：0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群（非）、parentId ：父id（非）")
    @PutMapping("v2/group")
    public ResultInfo<?> update(@RequestParam(name = "groupAvatar", required = false) MultipartFile groupAvatarFile,
                                @RequestParam(name = "userid") Long userid,
                                @RequestParam(name = "groupId") String groupId,
                                @RequestParam(name = "industryId", defaultValue = "-1") Long industryId,
                                @RequestParam(name = "groupName", required = false) String groupName,
                                @RequestParam(name = "restrictNum", required = false) Integer restrictNum,
                                @RequestParam(name = "magree", required = false, defaultValue = "-1") int magree,
                                @RequestParam(name = "parentId", required = false) Long parentId,
                                @RequestParam(name = "announcement", required = false) String announcement,
                                @RequestParam(name = "intro", required = false) String intro,
                                @RequestParam(name = "beinvitemode", required = false, defaultValue = "-1") int beinvitemode,
                                @RequestParam(name = "invitemode", required = false, defaultValue = "-1") int invitemode,
                                @RequestParam(name = "uptinfomode", required = false, defaultValue = "-1") int uptinfomode,
                                @RequestParam(name = "upcustommode", required = false, defaultValue = "-1") int upcustommode,
                                HttpServletRequest httpServletRequest
    ) throws Exception {
        if (groupAvatarFile != null) {
            ResultInfo<?> x = ImageUtils.checkImage(groupAvatarFile);
            if (x != null) {
                return x;
            }
        }
        return groupService.update(groupAvatarFile, userid, groupId, industryId, groupName, restrictNum, magree, parentId, announcement, intro, beinvitemode, invitemode, uptinfomode, upcustommode, httpServletRequest);
    }


    /**
     * 添加管理员
     *
     * @param addManagerRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    @ApiOperation(value = "添加群管理员", notes = "userid ：用户id、groupId ： 群id、 members（JSONArray） ：管理员")
    @PostMapping("v2/group/add-manager")
    public ResultInfo<?> addManager(@Valid @RequestBody AddManagerRequest addManagerRequest) throws Exception {
        return groupService.addManager(addManagerRequest);
    }

    /**
     * 移除管理员
     *
     * @param addManagerRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    @ApiOperation(value = "添加群管理员", notes = "userid ：用户id、groupId ： 群id、 members（JSONArray） ：管理员")
    @DeleteMapping("v2/group/remove-manager")
    public ResultInfo<?> removeManager(@Valid @RequestBody AddManagerRequest addManagerRequest) throws Exception {
        return groupService.removeManager(addManagerRequest);
    }

    /**
     * 禁言
     *
     * @param muteTlistAllRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    @ApiOperation(value = "禁言", notes = "userid ：用户id、groupId ： 群id、 members（JSONArray） ：管理员")
    @PostMapping("v2/group/mute-tlist-all")
    public ResultInfo<?> muteTlistAll(@Valid @RequestBody MuteTlistAllRequest muteTlistAllRequest) throws Exception {
        return groupService.muteTlistAll(muteTlistAllRequest);
    }

    /**
     * 拉人入群
     *
     * @param joinGroupRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "加入群", notes = "members ：群成员、userid ：用户id、groupId ：群id、msg ：邀请信息")
    @PostMapping("v2/group/add")
    public ResultInfo<?> add(@Valid @RequestBody JoinGroupRequest joinGroupRequest) throws Exception {
        return groupService.add(joinGroupRequest);
    }

    /**
     * 踢人出群
     *
     * @param kickMembersRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "踢人出群", notes = "members ：群成员、userid ：用户id、groupId ：群id")
    @PostMapping("v2/group/kick")
    public ResultInfo<?> kick(@Valid @RequestBody KickMembersRequest kickMembersRequest) throws Exception {
        return groupService.kick(kickMembersRequest);
    }


    /**
     * 获取群成员
     *
     * @param groupId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取群成员")
    @PostMapping("v2/group/get/groupMembers")
    public ResultInfo<?> getGroupMembers(@Valid @RequestBody GroupIdDTO groupId) {
        return groupService.getGroupMembers(groupId.getGroupId());
    }

    /**
     * 获取群列表
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取群列表")
    @PostMapping("v2/group/get/groupList")
    public ResultInfo<?> groupList(@Valid @RequestBody UserNoRequest request) {
        return groupService.groupList(request);
    }


    /**
     * 删除群
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "删除群", notes = "userid ：用户id、 groupId ：群id")
    @DeleteMapping("v2/group/delete")
    public ResultInfo<?> delete(@Valid @RequestBody DeleteGroupRequest request) throws Exception {
        return groupService.delete(request);
    }


    /**
     * 退群
     *
     * @param leaveGroupRequest
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    @ApiOperation(value = "退群", notes = "userid ：用户id、 groupId ：群id")
    @PostMapping("v2/group/quit")
    public ResultInfo<?> quit(@Valid @RequestBody LeaveGroupRequest leaveGroupRequest) throws Exception {
        return groupService.quit(leaveGroupRequest);
    }

    /**
     * 修改消息提醒开关
     *
     * @param muteTeamRequest
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    @ApiOperation(value = "修改消息提醒开关", notes = "userid ：用户id、 groupId ：群id、ope ：1-关闭消息提醒，2-打开消息提醒，其他值无效")
    @PostMapping("v2/group/mute-team")
    public ResultInfo<?> muteTeam(@Valid @RequestBody MuteTeamRequest muteTeamRequest) throws Exception {
        return groupService.muteTeam(muteTeamRequest);
    }
}
