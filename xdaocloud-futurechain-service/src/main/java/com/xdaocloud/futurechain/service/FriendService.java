package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.friend.AddFriendRequest;
import com.xdaocloud.futurechain.dto.req.friend.FindFriendRemarkRequest;
import com.xdaocloud.futurechain.dto.req.friend.FindFriendRequest;
import com.xdaocloud.futurechain.dto.req.friend.HandleFriendApplyRequest;
import com.xdaocloud.futurechain.dto.req.user.UpdateFriendRemarkRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;

public interface FriendService {


    void addFriend(AddFriendRequest request);

    ResultInfo<?> getFriendApply(UserIdRequest request);

    ResultInfo<?> handleFriendApplyRequest(HandleFriendApplyRequest request);

    ResultInfo<?> findFriendList(UserIdRequest request);

    ResultInfo<?> updateRemark(UpdateFriendRemarkRequest updateFriendRemarkRequest);

    ResultInfo<?> findFriend(FindFriendRequest findFriendRequest);

    ResultInfo<?>  findFriendLike(Page_Request request);

    /**
     * 查询好友信息
     * @param request
     * @return
     */
    ResultInfo<?> findFriendRemark(FindFriendRemarkRequest request);
}
