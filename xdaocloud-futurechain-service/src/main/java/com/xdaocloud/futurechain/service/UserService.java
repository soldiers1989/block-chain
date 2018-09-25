package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.friend.AddFriendRequest;
import com.xdaocloud.futurechain.dto.req.friend.HandleFriendApplyRequest;
import com.xdaocloud.futurechain.dto.req.user.*;
import com.xdaocloud.futurechain.dto.resp.friend.InvitationFriendsResponse;
import com.xdaocloud.futurechain.dto.resp.user.BankResponse;
import com.xdaocloud.futurechain.dto.resp.user.PropertyResponse;
import com.xdaocloud.futurechain.dto.resp.user.UserMsgResponse;
import com.xdaocloud.futurechain.dto.resp.user.UserOreRankingResponse;
import com.xdaocloud.futurechain.model.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {


    /**
     * 同步用户
     *
     * @param user
     * @return
     * @date 2018年8月28日
     * @author LuoFuMin
     */
    Boolean syncUser(User user);


    /**
     * 注册
     *
     * @param registerRequest 注册信息
     * @param rootId          根用户id
     * @param inviteUserId    邀请用户id
     * @return ResultInfo
     * @throws Exception
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    ResultInfo<?> userRegister(RegisterRequest registerRequest, Long rootId, Long inviteUserId, HttpServletRequest httpServletRequest) throws Exception;

    /**
     * 根据手机号码查询用户信息
     *
     * @param mobileMumber 手机号码
     * @return ResultInfo
     * @throws Exception
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    ResultInfo<?> findByMobileMumber(String mobileMumber)
            throws Exception;

    /**
     * 根据手机号码查询用户信息
     *
     * @param loginRequest
     * @return ResultInfo
     * @throws Exception
     * @date 2018年3月6日
     * @author LuoFuMin
     */
    ResultInfo<?> userLogin(LoginRequest loginRequest, HttpServletRequest httpServletRequest)
            throws Exception;



    /**
     * 获取用户信息
     *
     * @param userid
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    UserMsgResponse getUserMsg(Long userid, HttpServletRequest httpServletRequest)
            throws Exception;


    /**
     * 修改用户信息
     *
     * @param updateUserInfoRequest
     * @return
     * @throws Exception
     * @date 2018年3月7日
     * @author LuoFuMin
     */
    ResultInfo<?> UpdateUserInfo(UpdateUserInfoRequest updateUserInfoRequest, HttpServletRequest httpServletRequest)
            throws Exception;


    /**
     * 上传头像
     *
     * @param multipartFile 上传文件
     * @return
     * @throws Exception
     */
    ResultInfo<?> avatarUpload(MultipartFile multipartFile, Long userid, HttpServletRequest httpServletRequest) throws Exception;

    /**
     * 查询我的资产
     *
     * @param userid
     * @return
     * @throws Exception
     */
    PropertyResponse findUserProperty(Long userid) throws Exception;

    /**
     * 获得用户的邀请码等参数
     *
     * @param userid 用户ID
     * @return
     */
    ResultInfo<?> getInviteCodeInfo(Long userid) throws Exception;

    /**
     * 一键签到
     *
     * @param request
     * @return
     */
    ResultInfo<?> clickSignIn(UserIdRequest request) throws Exception;

    /**
     * 查询全部用户排行
     *
     * @param request
     * @return
     * @throws Exception
     */
    UserOreRankingResponse findUserOreList(Page_Request request) throws Exception;


    /**
     * 查询朋友圈用户排行
     *
     * @param request
     * @return
     * @throws Exception
     */
    ResultInfo<?> findOreRankingFriends(Page_Request request) throws Exception;


    /**
     * 根据用户id 查询群成员
     *
     * @param userid 用户id
     * @return
     */
    ResultInfo<?> findGroupByUserId(Long userid) throws Exception;


    /**
     * 麦粒收支明细
     *
     * @param page_request
     * @return
     * @throws Exception
     */
    ResultInfo<?> getHxlTransaction(Page_Request page_request) throws Exception;


    /**
     * 找回密码
     *
     * @param backPass
     * @return
     */
    ResultInfo<?> backPassWord(GetBackPassRequest backPass, HttpServletRequest httpServletRequest);


    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    ResultInfo<?> changePassWord(ChangePassWordRequest changePassWord, HttpServletRequest request);

    /**
     * 根据id查询用户
     *
     * @param userId
     * @return
     */
    User findUserByUserId(Long userId);

    /**
     * 查询邀请的好友
     *
     * @param inviteCodeRequest
     * @return
     */
    InvitationFriendsResponse getInvitationFriends(InviteCodeRequest inviteCodeRequest);


    /**
     * 修改交易密码
     *
     * @param backTransactionPassWordRequest
     * @return
     * @throws Exception
     */
    ResultInfo<?> changeTransactionPassWord(BackTransactionPassWordRequest backTransactionPassWordRequest);



    /**
     * 查询可以邀请的通讯录列表
     *
     * @param map
     * @return
     */
    ResultInfo<?> getInvitationList(Map<String, Object> map);

    /**
     * 根据邀请码添加好友
     *
     * @param map
     * @return
     */
    ResultInfo<?> invitationAddFriends(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    ResultInfo<?> addFriends(Map<String, Object> map);

    /**
     * 查询下线
     *
     * @param inviteCode
     * @return
     */
    Object findChildrens(String inviteCode);


    /**
     * 获取用户列表
     *
     * @return
     * @throws Exception
     * @date 2018年7月3日
     * @author LuoFuMin
     */
    PageResponse getUsers(PageBase_Request pageBaseRequest) throws Exception;

    /**
     * 审核代理商
     *
     * @param request
     * @return
     * @date 2018年6月29日
     * @author LuoFuMin
     */
    Boolean toExamine(ToExamineRequest request);


    /**
     * 获取代理商列表
     *
     * @return
     * @throws Exception
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    PageResponse getUsersByAgent(PageBase_Request pageBaseRequest) throws Exception;


    /**
     * 添加银行卡
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年7月5日
     * @author LuoFuMin
     */
    Boolean addBack(AddBackRequest request);


    /**
     * 查询银行卡
     *
     * @param aLong
     * @return
     * @date 2018年7月6日
     * @author LuoFuMin
     */
    List<BankResponse> getBank(Long aLong);

    /**
     *  验证支付密码
     *
     * @param checkTransactionPassWordRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> checkTransactionPassWord(CheckTransactionPassWordRequest checkTransactionPassWordRequest) throws Exception;
}
