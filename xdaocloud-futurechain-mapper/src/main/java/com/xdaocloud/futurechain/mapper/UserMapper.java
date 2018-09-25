package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.circle.FollowResponse;
import com.xdaocloud.futurechain.dto.resp.friend.GroupResponse;
import com.xdaocloud.futurechain.dto.resp.user.UserInfoResponse;
import com.xdaocloud.futurechain.dto.resp.user.UserOreRankingResponse;
import com.xdaocloud.futurechain.dto.resp.user.UserOreResponse;
import com.xdaocloud.futurechain.dto.user.UserChildrenDTO;
import com.xdaocloud.futurechain.model.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int save(User record);

    /**
     * 根据手机号码查询 user 表信息
     *
     * @param mobileNumber
     * @return User
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    User findByMobileMumber(@Param("mobileNumber") String mobileNumber);


    /**
     * 根据手机号码搜索用户信息
     *
     * @param mobileNumber
     * @return User
     * @date 2018年3月2日
     * @author LuoFuMin
     */
    List<UserInfoResponse> findLikeByMobileMumber(@Param("mobileNumber") String mobileNumber);

    /**
     * 根据邀请码 user 表信息
     *
     * @param inviteCode
     * @return User
     * @date 2018年3月5日
     * @author LuoFuMin
     */
    User findByInviteCode(@Param("inviteCode") String inviteCode);


    String findInviteCodeByUserId(@Param("userId") Long userId);

    /**
     * 根据邀请码查询所有下线用户id数组
     *
     * @param inviteCode 邀请码
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<UserChildrenDTO> findChildrenUserByInviteCode(@Param("inviteCode") String inviteCode);


    /**
     * 根据邀请码查询一级下线用户id数组
     *
     * @param inviteCode 邀请码
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<UserChildrenDTO> findUserIdsByInviteCode(@Param("inviteCode") String inviteCode);


    /**
     * 检索某个用户已邀请的用户数，根据用户注册时所使用的邀请码
     *
     * @param userid 邀请的用户ID
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    int selectInvitedUserCount(Long userid);

    /**
     * 根据 userid 添加矿石
     *
     * @param userid
     * @param ore
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    int addOreByUserid(@Param("userid") Long userid, @Param("ore") Long ore);

    /**
     * 查询用户所拥有的矿石数量
     *
     * @param userid
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    Long findOreByUserId(@Param("userid") Long userid);


    /**
     * 根据用户id 查询群成员
     *
     * @param userid 用户id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<GroupResponse> findGroupByUserId(@Param("userid") Long userid);


    /**
     * 获取群成员信息
     *
     * @param userIds
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<GroupResponse> findOpenMsgByUserIds(@Param("userIds") List<Long> userIds);


    /**
     * 查询单个用户
     *
     * @param user
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    User findOneByParam(User user);

    /**
     * 查询用户数组
     *
     * @param user
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<User> findListByParam(User user);

    /**
     * 查询用户数量
     *
     * @param user
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    int findCountByParam(User user);

    /**
     * 查询全部用户麦粒排行
     *
     * @param size
     * @param page
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<UserOreResponse> findUOreRankingAll(@Param("page") Integer page, @Param("size") Integer size);

    /**
     * 查询朋友圈麦粒排行
     *
     * @param list
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<UserOreResponse> findOreRankingFriends(@Param("list") List<Long> list, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 查询全部用户中此用户的排行
     *
     * @param userId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    UserOreRankingResponse findOreRankingByUserId(@Param("userId") Long userId);


    /**
     * 查询朋友圈中此用户的排行
     *
     * @param userId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    UserOreRankingResponse findOreRankingInFriendsByUserId(@Param("userId") Long userId, @Param("list") List<Long> list);


    /**
     * 查询用户所邀请的好友
     *
     * @param inviteCode
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<UserInfoResponse> getInvitationFriends(@Param("userId") Long userId, @Param("inviteCode") String inviteCode, @Param("page") Integer page, @Param("size") Integer size);


    String findMobileNumberByIdCard(@Param("idcard") String idcard);

    /**
     * 模糊查询
     *
     * @param condition
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<UserInfoResponse> findFriendLike(@Param("condition") String condition, @Param("ids") List<Long> ids, @Param("page") int page, @Param("size") int size);

    void updateSignature(@Param("userId") long userId, @Param("signature") String signature);

    void updateNickname(@Param("userId") long userId, @Param("nickname") String nickname);

    void updateIdcardOrName(@Param("userId") long userId, @Param("name") String name, @Param("idcard") String idcard);

    void updateIndustry(@Param("userId") long userId, @Param("industryId") String industryId);

    /*****************************************************************************************************/

    List<UserOreResponse> findUserOreList();

    List<String> selectByPhoneList(@Param("mobileList") List<Map<String, Object>> mobileList);

    List<Map<String, Object>> selectByPhoneUserList(@Param("mobileList") List<Map<String, Object>> mobileList, @Param("userId") long userId);

    /**
     * 查询关注好友列表
     *
     * @param userId
     * @param size
     * @param page
     * @return
     * @date 2018年6月22日
     * @author dql
     */
    List<FollowResponse> getFollowList(@Param("userId") Long userId, @Param("page") Integer page, @Param("size") Integer size);


    /**
     * 查询用户
     *
     * @param page
     * @param size
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    List<UserInfoResponse> getUsers(@Param("page") Integer page, @Param("size") Integer size);


    /**
     * 查询用户
     *
     * @param page
     * @param size
     * @return
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    List<UserInfoResponse> getUsersByAgent(@Param("list") int[] list, @Param("page") Integer page, @Param("size") Integer size);


    /**
     * 查询用户
     *
     * @param phone
     * @param page
     * @param size
     * @return
     * @date 2018年月4日
     * @author LuoFuMin
     */
    List<UserInfoResponse> getUsersByPhone(@Param("phone") String phone, @Param("page") Integer page, @Param("size") Integer size);


    /**
     * 查询用户数量
     *
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    int findCount();


    /**
     * 查询用户数量
     *
     * @param list
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    int findCountByAgent(@Param("list") int[] list);


    /**
     * 查询用户
     *
     * @param agent
     * @return
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    List<UserInfoResponse> findListByAgent(@Param("agent") int agent);


    /**
     * 查询用户
     *
     * @param agents
     * @return
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    List<UserInfoResponse> findListByAgents(@Param("agents") List<Integer> agents);


    /**
     * 查询用户
     *
     * @param agents
     * @param phone
     * @return
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    List<UserInfoResponse> findListByAgentsAndPhone(@Param("agents") List<Integer> agents, @Param("phone") String phone);

    /**
     * 查询用户
     *
     * @param agent
     * @param phone
     * @return
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    List<UserInfoResponse> findListByAgentAndPhone(@Param("agent") int agent, @Param("phone") String phone);


    /**
     * 查询手机号码
     *
     * @param userid
     * @return String
     * @author LuoFuMin
     * @date 2018/9/13
     */
    String findPhoneById(Long userid);


    /**
     * 查询id
     *
     * @param phone
     * @return String
     * @author LuoFuMin
     * @date 2018/9/13
     */
    Long findIdByPhone(String phone);

    /**
     * 查询交易密码
     *
     * @param userid
     * @return String
     * @author LuoFuMin
     * @date 2018/9/13
     */
    String findTransactionPassWord(Long userid);
}