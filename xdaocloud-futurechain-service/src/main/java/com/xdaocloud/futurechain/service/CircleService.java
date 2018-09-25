package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.circle.*;
import com.xdaocloud.futurechain.dto.resp.circle.CircleDisplayResponse;
import com.xdaocloud.futurechain.dto.resp.circle.FollowResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface CircleService {
    
    /**
     * 获取朋友圈类型列表
     * @return 
     * @date 2018年6月20日
     * @author dql
     */
    List<Map<Object, Object>> getIndustryList(HttpServletRequest httpServletRequest);

    /**
     * 收藏朋友圈
     * @param entity
     * @return 
     * @date 2018年6月20日
     * @author dql
     */
    long collectCircle(CollectRequest entity);

    /**
     * 
     * 朋友圈列表
     * @param entity
     * @return 
     * @date 2018年6月25日
     * @author dql
     */
    Map<Object, Object> getCircleList(CircleListRequest entity,HttpServletRequest httpServletRequest);

    /**
     * 设置为不感兴趣
     * @param entity
     * @return 
     * @date 2018年6月20日
     * @author dql
     */
    long loseInterest(CircleRequest entity);

    /**
     * 举报朋友圈
     * @param entity
     * @return 
     * @date 2018年6月20日
     * @author dql
     */
    long report(ReportRequest entity);

    /**
     * 上下架朋友圈动态
     * @param entity
     * @return 
     * @date 2018年6月20日
     * @author dql
     */
    long modifyCircleStatus(ModifyCircleStatusRequest entity) throws Exception;


    /**
     * 麦圈详情--扣费
     * @param entity
     * @return
     */
    ResultInfo<?> buckleMoney(Map<String, Object> entity);

    /**
     * 麦圈详情
     * @param entity
     * @return
     * @date 2018年6月21日
     * @author lmd
     */
    ResultInfo<?> circleDetails(Map<String,Object>  entity) throws Exception;

    /**
     * 麦圈详情评论列表
     * @param map
     * @return
     * @date 2018年6月21日
     * @author lmd
     */
    ResultInfo<?> detailsComment(Map<String, Object> map);

    /**
     * 评论文章
     * @param map
     * @return
     * @date 2018年6月22日
     * @author lmd
     */
    ResultInfo<?> setCircleComment(Map<String, Object> map);
    /**
     * 点赞文章或者取消点赞
     * @param map
     * @return
     * @date 2018年6月22日
     * @author lmd
     */
    ResultInfo<?> likeCircle(Map<String, Object> map);

    /**
     * 关注或取消关注用户
     * @param map
     * @return
     * @date 2018年6月22日
     * @author lmd
     */
    ResultInfo<?> follow(Map<String, Object> map);

    /**
     * 转发文章
     * @param map
     * @return
     * @date 2018年6月22日
     * @author lmd
     */
    ResultInfo<?> forwarded(Map<String, Object> map,HttpServletRequest httpServletRequest);

    /**
     * 我的发布列表
     * @param userId
     * @param size
     * @param page
     * @return
     * @date 2018年6月22日
     * @author dql
     * @param type
     * @param searchContent 
     */
    List<CircleDisplayResponse> myCircleList(String userId, Integer type, String searchContent, Integer page, Integer size);

    /**
     * 我的关注好友列表
     * @param userId
     * @param page
     * @param size
     * @return
     * @date 2018年6月22日
     * @author dql
     */
    List<FollowResponse> getFollowList(String userId, Integer page, Integer size);

   /**
    *
    * @param userId 用户ID
    * @param circleId 文章ID
    * @param content 文章内容
    * @param industryId 行业类型ID
    * @param articleType 文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)
    * @param userNumber 总共奖励用户数
    * @param number 扣除阅读者用户麦钻数量或者单个用户奖励麦钻数量
    * @param fileType 0:没有文件  1:只有图片  2:只有视频  3:有视频有图片
    * @param fileUrl 文件路径
    * @param type 0:新发布  1:编辑发布
    *@param password 交易密码
    * @return
    */
   ResultInfo<?> circleRelease(Long userId ,Long circleId , String content, long industryId, int articleType, int userNumber, String number, int fileType, String fileUrl,int type,String password,HttpServletRequest httpServletRequest)throws Exception;


    /**
     * 获取行业类型列表
     * @return
     */
    List<Map<Object,Object>> getIndustryTypeList();

    /**
     * 获取用户行业列表
     * @return 
     * @date 2018年6月25日
     * @author dql
     */
    List<Map<Object, Object>> getUserIndustryList();

    /**
     * 获取用户的发布文章列表
     * @param userId
     * @param page
     * @param size
     * @return 
     * @date 2018年6月25日
     * @author dql
     * @param searchContent 
     */
    List<CircleDisplayResponse> getUserCircleList(String userId, String searchContent, Integer page, Integer size);

    /**
     * 
     * 获取大V排行榜
     * @param entity
     * @return 
     * @date 2018年6月25日
     * @author dql
     */
    Map<Object, Object> getRankingList(RankingListRequest entity);

    /**
     * 获取大V页面推荐用户列表
     * @param userId
     * @param page
     * @param size
     * @return 
     * @date 2018年6月26日
     * @author dql
     */
    PageResponse getRecommendUserList(String userId, Integer page, Integer size);

    /**
     * 删除朋友圈
     * @param entity
     * @return 
     * @date 2018年6月27日
     * @author dql
     */
    long deleteCircle(CircleRequest entity);

    /**
     * 文章发布---文件上传
     * @param request
     * @return
     */
    ResultInfo<?> circleUploadImage(HttpServletRequest request);

    /**
     * 文章编辑--获取文章详细信息
     * @param map circleId:被转发文章ID
     * @return
     */
    ResultInfo<?> getCircle(Map<String, Object> map);


    /**
     * web管理界面--文章列表
     * @param webCircleListRequest
     * @return
     */
    ResultInfo<?> webGetCircleList(WebCircleListRequest webCircleListRequest);

    /**
     * web管理界面--文章详情
     * @param map
     * @return
     */
    ResultInfo<?> getCircleDetails(WebCircleRequest map);

    /**
     * web麦圈详情评论列表
     * @param map
     * @return
     * @date 2018年6月21日
     * @author lmd
     */
    ResultInfo<?> webDetailsComment(webCircleCommentRequest map);

    /**
     * web管理界面--文章编辑
     * @param map
     * @return
     */
    ResultInfo<?> updateCircle(webCircleOperationRequest map) throws Exception;

    /**
     * web管理界面--审核开关列表
     * @return
     */
    List<Map<String, Object>> getSystemSetup();

    /**
     * web管理界面--更改审核开关
     * @param map
     * @return
     */
    int updateSystemSetup(WebSystemSetupRequest map);
}
