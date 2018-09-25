package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.friend.AddFriendRequest;
import com.xdaocloud.futurechain.dto.req.friend.FindFriendRemarkRequest;
import com.xdaocloud.futurechain.dto.req.friend.FindFriendRequest;
import com.xdaocloud.futurechain.dto.req.friend.HandleFriendApplyRequest;
import com.xdaocloud.futurechain.dto.req.user.UpdateFriendRemarkRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.service.FriendService;
import com.xdaocloud.futurechain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 好友关系
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "FriendController", description = "好友")
@RequestMapping("/api/app/")
public class FriendController {

    @Autowired
    private FriendService friendService;


    /**
     * 好友记录
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "添加好友申请")
    @PostMapping("v2/friend")
    public void addFriend(@Valid @RequestBody AddFriendRequest request) throws Exception {
        friendService.addFriend(request);
    }


}
