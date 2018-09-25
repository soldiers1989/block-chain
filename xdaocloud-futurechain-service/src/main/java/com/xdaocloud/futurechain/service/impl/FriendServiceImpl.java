package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.friend.AddFriendRequest;
import com.xdaocloud.futurechain.dto.req.friend.FindFriendRemarkRequest;
import com.xdaocloud.futurechain.dto.req.friend.FindFriendRequest;
import com.xdaocloud.futurechain.dto.req.friend.HandleFriendApplyRequest;
import com.xdaocloud.futurechain.dto.req.huanxin.Message;
import com.xdaocloud.futurechain.dto.req.huanxin.SendMessageRequest;
import com.xdaocloud.futurechain.dto.req.user.UpdateFriendRemarkRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.dto.resp.friend.FriendResponse;
import com.xdaocloud.futurechain.dto.resp.friend.FriendApplyResponse;
import com.xdaocloud.futurechain.dto.resp.user.UserInfoResponse;
import com.xdaocloud.futurechain.huanxin.HuanxinMessage;
import com.xdaocloud.futurechain.huanxin.HuanxinUser;
import com.xdaocloud.futurechain.mapper.AddFriendMapper;
import com.xdaocloud.futurechain.mapper.FriendMapper;
import com.xdaocloud.futurechain.mapper.UserMapper;
import com.xdaocloud.futurechain.model.AddFriend;
import com.xdaocloud.futurechain.model.Friend;
import com.xdaocloud.futurechain.model.User;
import com.xdaocloud.futurechain.service.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendServiceImpl implements FriendService {

    private static Logger LOG = LoggerFactory.getLogger(FriendServiceImpl.class);
    /**
     * 环信用户业务类
     */
    @Autowired
    private HuanxinUser huanxinUser;

    /**
     * 用户操作
     */
    @Autowired
    private UserMapper userMapper;


    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private AddFriendMapper addFriendMapper;

    @Autowired
    private HuanxinMessage huanxinMessage;


    /**
     * 申请添加好友
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public void addFriend(AddFriendRequest request) {
        Long friendId = userMapper.findIdByPhone(request.getAccid());
        Friend fri = friendMapper.findOneByUserIdAndFriendId(request.getUserid(), friendId);
        if (fri ==null){
            Friend friend = new Friend();
            friend.setUserId(request.getUserid());
            friend.setFriendId(friendId);
            int i = friendMapper.insertSelective(friend);
            LOG.info("》》》 保存好友关系==" + i);
        }
    }

    /**
     * 获取好友申请列表
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    public ResultInfo<?> getFriendApply(UserIdRequest request) {
        List<FriendApplyResponse> addFriendList = addFriendMapper.getFriendApplyByUserId(Long.valueOf(request.getUserid()));
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, addFriendList);
    }

    /**
     * 是否同意加好友
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> handleFriendApplyRequest(HandleFriendApplyRequest request) {
        AddFriend addFriend = addFriendMapper.selectByPrimaryKey(Long.valueOf(request.getAddFriendId()));
        if (addFriend.getIsDeleted()) {
            return new ResultInfo<>(ResultInfo.FAILURE, "操作已过期");
        }
        AddFriend addFriendUpdate = new AddFriend();
        addFriendUpdate.setId(Long.valueOf(request.getAddFriendId()));
        addFriendUpdate.setIsAgree(Byte.valueOf(request.getAgree()));
        addFriendUpdate.setIsDeleted(true);
        addFriendMapper.updateByPrimaryKeySelective(addFriendUpdate);

        Friend f = friendMapper.findOneByUserIdAndFriendId(addFriend.getUserId(), addFriend.getFriendId());
        if (f == null) {
            f = friendMapper.findOneByUserIdAndFriendId(addFriend.getFriendId(), addFriend.getUserId());
        }
        if (f == null) {
            Friend friend = new Friend();
            friend.setUserId(addFriend.getUserId());
            friend.setFriendId(addFriend.getFriendId());
            friend.setFriendRemark(request.getRemark());
            friendMapper.insertSelective(friend);
            try {
                User user = userMapper.selectByPrimaryKey(addFriend.getUserId());
                User user2 = userMapper.selectByPrimaryKey(addFriend.getFriendId());
            } catch (Exception e) {
                LOG.error("==环信加好友失败==");
                e.printStackTrace();
            }
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, "已经添加过好友，不要重复添加");
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 查询好友列表
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    public ResultInfo<?> findFriendList(UserIdRequest request) {
     /*   List<FriendResponse> list1 = friendMapper.findListByUserId(Long.valueOf(request.getUserid()));
        List<FriendResponse> list2 = friendMapper.findListByFriendId(Long.valueOf(request.getUserid()));
        if (!list2.isEmpty()) {
            list1.addAll(list2);
        }*/
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 修改好友备注
     *
     * @param updateFriendRemarkRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Transactional
    @Override
    public ResultInfo<?> updateRemark(UpdateFriendRemarkRequest updateFriendRemarkRequest) {
        Long userId = Long.valueOf(updateFriendRemarkRequest.getUserid());
        Long friendId = Long.valueOf(updateFriendRemarkRequest.getFriendId());
        Friend friend = friendMapper.findOneByUserIdAndFriendId(userId, friendId);
        if (friend != null) {
            friend.setFriendRemark(updateFriendRemarkRequest.getRemark());
            friendMapper.updateByPrimaryKey(friend);
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else {
            friend = friendMapper.findOneByUserIdAndFriendId(friendId, userId);
            if (friend != null) {
                friend.setUserRemark(updateFriendRemarkRequest.getRemark());
                friendMapper.updateByPrimaryKey(friend);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
            } else {
                return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
            }
        }
    }

    /**
     * 查询好友
     *
     * @param findFriendRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    public ResultInfo<?> findFriend(FindFriendRequest findFriendRequest) {

        List<UserInfoResponse> list = userMapper.findLikeByMobileMumber(findFriendRequest.getMobileNumber());

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 模糊查询
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    public ResultInfo<?> findFriendLike(Page_Request request) {
        Long userId = Long.valueOf(request.getUserid());
        List<Long> ids = friendMapper.findFriendsByUserId(userId);
        ids.add(userId);
        List<Long> ids2 = friendMapper.findFriendsByFriendId(userId);
        if (!ids2.isEmpty()) {
            ids.addAll(ids2);
        }
        Integer page = 0;
        if (request.getPage() != 0) {
            page = request.getSize() * request.getPage() + 1;
        }
        List<UserInfoResponse> list = userMapper.findFriendLike(request.getCondition(), ids, page, request.getSize());
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 查询好友信息
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    public ResultInfo<?> findFriendRemark(FindFriendRemarkRequest request) {
        Long userid = Long.valueOf(request.getUserid());
        Long friendId = Long.valueOf(request.getFriendId());
        User user = userMapper.selectByPrimaryKey(userid);
        User friend = userMapper.selectByPrimaryKey(friendId);
        String nickname = "";
        String remark = "";
        String avatar = "";
        String userNo = "";
        Friend friend_z = friendMapper.findOneByUserIdAndFriendId(userid, friendId);
        if (friend_z == null) {
            Friend friend_b = friendMapper.findOneByUserIdAndFriendId(friendId, userid);
            if (friend_b == null) {
                return new ResultInfo<>(ResultInfo.DATA_NOT_FOUND, ResultInfo.MSG_DATA_NOT_FOUND);
            }
            nickname = friend.getNickname();
            avatar = friend.getAvatar();
            remark = friend_b.getUserRemark();
        } else {
            nickname = friend.getNickname();
            avatar = friend.getAvatar();
            remark = friend_z.getFriendRemark();
        }

        Map<String, String> map = new HashMap();
        map.put("nickname", nickname);
        map.put("remark", remark);
        map.put("avatar", avatar);
        map.put("userNo", userNo);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
    }
}
