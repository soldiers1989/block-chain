package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.futurechain.common.QiniuConfig;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.constant.Constant;
import com.xdaocloud.futurechain.dto.req.group.*;
import com.xdaocloud.futurechain.dto.req.huanxin.CreateGroupRequest;
import com.xdaocloud.futurechain.dto.resp.friend.GroupResponse;
import com.xdaocloud.futurechain.httpapi.IMTeamService;
import com.xdaocloud.futurechain.httpapi.ResultMsg;
import com.xdaocloud.futurechain.huanxin.HuanxinGroup;
import com.xdaocloud.futurechain.mapper.GroupMapper;
import com.xdaocloud.futurechain.mapper.UserMapper;
import com.xdaocloud.futurechain.model.Group;
import com.xdaocloud.futurechain.model.User;
import com.xdaocloud.futurechain.response.yunxin.AddTeamResponse;
import com.xdaocloud.futurechain.response.yunxin.CreateTeamResponse;
import com.xdaocloud.futurechain.response.yunxin.TeamQueryResponse;
import com.xdaocloud.futurechain.service.GroupService;
import com.xdaocloud.futurechain.util.JSONUtils;
import com.xdaocloud.futurechain.util.QiNiuUtils;
import com.xdaocloud.futurechain.util.YunXinUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {

    private static Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);

    /**
     * 七牛云储存配置
     */
    @Autowired
    private QiniuConfig qiniuConfig;


    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private HuanxinGroup huanxinGroup;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    IMTeamService imTeamService;

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
    @Transactional
    @Override
    public ResultInfo<?> create(MultipartFile groupAvatarFile, Long userid, String groupName, String desc, JSONArray members,
                                int magree, Long parentId, Integer restrictNum, HttpServletRequest httpServletRequest) throws IOException {
        String appId = httpServletRequest.getHeader(Constant.APPID_KEY);
        String path = "";
        String groupId = "";
        if (groupAvatarFile != null) {
            path = QiNiuUtils.pushFile(groupAvatarFile, qiniuConfig);
            if (path == null) {
                return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
            }
        }
        LOG.info("》》》path====" + path);
        String phone = userMapper.findPhoneById(userid);
        Map<String, Object> param = make_param(path);
        //不填邀请信息，必须有默认值
        if (StringUtils.isBlank(desc)) {
            desc = "群聊";
        }
       /* if (members == null) {
            members = new JSONArray();
            members.add("hello");
        }*/
        if (StringUtils.isBlank(groupName)) {
            groupName = "群聊";
        }
        CreateTeamResponse createTeamResponse = imTeamService.create(YunXinUtils.getheaders(), groupName, phone, members.toJSONString(), desc, magree, 0, param).execute().body();
        LOG.info("==code==" + createTeamResponse.getCode());
        if (createTeamResponse.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, createTeamResponse.getDesc());
        }
        LOG.info("==groupid==" + createTeamResponse.getTid());
        groupId = createTeamResponse.getTid();
        Group group = new Group();
        group.setGroupId(groupId);
        group.setParentId(parentId);
        group.setAppId(appId);
        group.setGroupName(groupName);
        group.setGroupAvatar(path);
        group.setOwner(phone);
        groupMapper.insertSelective(group);

        Map<String, String> map = new HashMap<>();
        map.put("groupId", groupId);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
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
    @Override
    @Transactional
    public ResultInfo<?> update(MultipartFile groupAvatarFile, Long userid, String groupId, Long industryId, String groupName, Integer restrictNum, int magree, Long parentId,
                                String announcement, String intro, int beinvitemode, int invitemode, int uptinfomode, int upcustommode, HttpServletRequest httpServletRequest) throws Exception {
        String path = "";
        if (groupAvatarFile != null) {
            path = QiNiuUtils.pushFile(groupAvatarFile, qiniuConfig);
            if (path == null) {
                return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
            }
        }
        String phone = userMapper.findPhoneById(userid);
        Group group = groupMapper.findOneByGroupId(groupId);
        //判断是否是群主
        if (StringUtils.isNoneBlank(phone) && phone.equals(group.getOwner())) {

        }
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNoneBlank(path)) {
            params.put("icon", path);
            group.setGroupAvatar(path);
        }
        if (StringUtils.isNoneBlank(groupName)) {
            params.put("tname", groupName);
            group.setGroupName(groupName);
        }
        if (StringUtils.isNoneBlank(announcement)) {
            params.put("announcement", announcement);
            group.setGroupNotice(announcement);
        }
        if (StringUtils.isNoneBlank(intro)) {
            params.put("intro", intro);
            group.setGroupIntro(intro);
        }
        if (beinvitemode != -1) {
            params.put("beinvitemode", beinvitemode);
        }
        if (invitemode != -1) {
            params.put("invitemode", invitemode);
        }
        if (uptinfomode != -1) {
            params.put("uptinfomode", uptinfomode);
        }
        if (upcustommode != -1) {
            params.put("upcustommode", upcustommode);
        }
        if (industryId != -1) {
            group.setIndustryId(industryId);
        }
        ResultMsg resultMsg = imTeamService.update(YunXinUtils.getheaders(), group.getGroupId(), group.getOwner(), params).execute().body();
        LOG.info("==修改群信息==" + resultMsg.getCode());
        if (resultMsg.getCode().intValue() == 200) {
            groupMapper.updateByPrimaryKey(group);
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 组装可选参数
     *
     * @param path
     * @return
     */
    public Map<String, Object> make_param(String path) {
        Map<String, Object> param = new HashMap<>();
        if (StringUtils.isNoneBlank(path)) {
            param.put("icon", path);
        }
        //被邀请人同意方式，0-需要同意(默认),1-不需要同意
        //param.put("beinvitemode", 1);
        //谁可以邀请他人入群，0-管理员(默认),1-所有人
        param.put("invitemode", 1);
        //谁可以修改群资料，0-管理员(默认),1-所有人
        param.put("uptinfomode", 1);

        return param;
    }


    /**
     * 添加管理员
     *
     * @param addManagerRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    @Override
    @Transactional
    public ResultInfo<?> addManager(AddManagerRequest addManagerRequest) throws Exception {
        String phone = userMapper.findPhoneById(addManagerRequest.getUserid());
        Group group = groupMapper.findOneByGroupId(addManagerRequest.getGroupId());
        //判断是否是群主
        if (StringUtils.isBlank(phone) && group == null && !phone.equals(group.getOwner())) {

        }
        JSONArray jsonArray = addManagerRequest.getMembers();
        ResultMsg resultMsg = imTeamService.addManager(YunXinUtils.getheaders(), group.getGroupId(), phone, jsonArray.toJSONString()).execute().body();

        if (resultMsg.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }
        String manager = group.getManager();
        JSONArray managerJSONArray = JSONArray.parseArray(manager);
        managerJSONArray.addAll(jsonArray);
        group.setManager(managerJSONArray.toJSONString());
        groupMapper.updateByPrimaryKeySelective(group);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 移除管理员
     *
     * @param addManagerRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    @Override
    @Transactional
    public ResultInfo<?> removeManager(AddManagerRequest addManagerRequest) throws Exception {
        String phone = userMapper.findPhoneById(addManagerRequest.getUserid());
        Group group = groupMapper.findOneByGroupId(addManagerRequest.getGroupId());
        //判断是否是群主
        if (StringUtils.isBlank(phone) && group == null && !phone.equals(group.getOwner())) {

        }
        JSONArray jsonArray = addManagerRequest.getMembers();
        ResultMsg resultMsg = imTeamService.removeManager(YunXinUtils.getheaders(), group.getGroupId(), phone, jsonArray.toJSONString()).execute().body();
        if (resultMsg.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }
        String manager = group.getManager();
        JSONArray managerJSONArray = JSONArray.parseArray(manager);
        for (int i = 0; i < jsonArray.size(); i++) {
            String accid = jsonArray.get(i).toString();
            managerJSONArray.remove(accid);
        }
        group.setManager(managerJSONArray.toJSONString());
        groupMapper.updateByPrimaryKeySelective(group);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 禁言
     *
     * @param muteTlistAllRequest
     * @return
     * @author LuoFuMin
     * @date 2018/9/14
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> muteTlistAll(MuteTlistAllRequest muteTlistAllRequest) throws Exception {
        String phone = userMapper.findPhoneById(muteTlistAllRequest.getUserid());
        Group group = groupMapper.findOneByGroupId(muteTlistAllRequest.getGroupId());
        //判断是否是群主
        if (StringUtils.isBlank(phone) && group == null && !phone.equals(group.getOwner())) {

        }
        ResultMsg resultMsg = imTeamService.muteTlistAll(YunXinUtils.getheaders(), group.getGroupId(), phone, muteTlistAllRequest.getMuteType()).execute().body();
        if (resultMsg.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 修改消息提醒开关
     *
     * @param muteTeamRequest
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> muteTeam(MuteTeamRequest muteTeamRequest) throws Exception {
        String phone = userMapper.findPhoneById(muteTeamRequest.getUserid());
        ResultMsg resultMsg = imTeamService.muteTeam(YunXinUtils.getheaders(), muteTeamRequest.getGroupId(), phone, muteTeamRequest.getOpe()).execute().body();
        if (resultMsg.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 踢人出群
     *
     * @param kickMembersRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> kick(KickMembersRequest kickMembersRequest) throws Exception {
        String phone = userMapper.findPhoneById(kickMembersRequest.getUserid());
        ResultMsg resultMsg = imTeamService.kicks(YunXinUtils.getheaders(), kickMembersRequest.getGroupId(), phone, kickMembersRequest.getMembers().toJSONString()).execute().body();
        if (resultMsg.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }
        for (int i = 0; i < kickMembersRequest.getMembers().size(); i++) {
            groupMapper.quit(kickMembersRequest.getGroupId(), kickMembersRequest.getMembers().get(i).toString());
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 加入群
     *
     * @param joinGroupRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> add(JoinGroupRequest joinGroupRequest) throws Exception {
        String msg = "";
        String groupId = joinGroupRequest.getGroupId();
        String phone = userMapper.findPhoneById(joinGroupRequest.getUserid());
        Group group = groupMapper.findOneByGroupId(joinGroupRequest.getGroupId());
        //判断是否是群主
        if (StringUtils.isBlank(phone) && group == null && !phone.equals(group.getOwner())) {

        }
        if (StringUtils.isBlank(joinGroupRequest.getMsg())) {
            msg = "邀请进群";
        } else {
            msg = joinGroupRequest.getMsg();
        }
        ResultMsg resultMsg = imTeamService.add(YunXinUtils.getheaders(), groupId, phone, joinGroupRequest.getMembers().toJSONString(), msg, joinGroupRequest.getMagree()).execute().body();
        if (resultMsg.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }

        for (int i = 0; i < joinGroupRequest.getMembers().size(); i++) {
            String accid = joinGroupRequest.getMembers().get(i).toString();
            groupMapper.addMembers(groupId, accid);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 退群
     *
     * @param leaveGroupRequest
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> quit(LeaveGroupRequest leaveGroupRequest) throws Exception {
        String phone = userMapper.findPhoneById(leaveGroupRequest.getUserid());
        ResultMsg resultMsg = imTeamService.leave(YunXinUtils.getheaders(), leaveGroupRequest.getGroupId(), phone).execute().body();
        if (resultMsg.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }
        groupMapper.quit(leaveGroupRequest.getGroupId(), phone);

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 获取群成员列表
     *
     * @param groupId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getGroupMembers(String groupId) {
        List<GroupResponse> responseList = groupMapper.findUsersByGroupId(groupId);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, responseList);
    }

    /**
     * 查找群
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    public ResultInfo<?> groupList(UserNoRequest request) {
        List<String> groupIds = groupMapper.findGroupIdsByUserNo(request.getUserNo());
        List<Group> groups = null;
        if (groupIds != null && !groupIds.isEmpty()) {
            groups = groupMapper.findListByGroupIds(groupIds);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, groups);
    }


    /**
     * 删除群
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> delete(DeleteGroupRequest request) throws Exception {
        String phone = userMapper.findPhoneById(request.getUserid());
        Group group = groupMapper.findOneByGroupId(request.getGroupId());
        //判断是否是群主
        if (StringUtils.isBlank(phone) && group == null && !phone.equals(group.getOwner())) {

        }
        ResultMsg resultMsg = imTeamService.remove(YunXinUtils.getheaders(), group.getGroupId(), phone).execute().body();
        if (resultMsg.getCode().intValue() != 200) {
            return new ResultInfo<>(ResultInfo.FAILURE, resultMsg.getDesc());
        }
        groupMapper.deleteByPrimaryKey(group.getId());
        groupMapper.deleteMembersByGroupId(group.getGroupId());
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


}
