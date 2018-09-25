package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.req.circle.WebCircleListRequest;
import com.xdaocloud.futurechain.dto.resp.circle.CircleDisplayResponse;
import com.xdaocloud.futurechain.dto.resp.circle.RankingResponse;
import com.xdaocloud.futurechain.model.Circle;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CircleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Circle record);

    int insertSelective(Circle record);

    Circle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Circle record);

    int updateByPrimaryKey(Circle record);

    /**
     * 热门 按阅读量倒序-一个月
     * <p>
     * ORDER BY (select COUNT(1) from t_reading_record rr  WHERE (rr.gmt_create <![CDATA[ >= ]]> (select DATE_ADD(curdate(),interval -day(curdate())+1 day)) AND rr.gmt_create <![CDATA[ <= ]]> (select last_day(curdate()))) AND rr.circle_id = c.id) DESC
     */
    List<CircleDisplayResponse> getCircleListByPopular(@Param("userId") Long userId, @Param("industryId") Long[] industryId, @Param("searchContent") String searchContent, @Param("appId") String appId, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 好友 按创建时间倒序
     */
    List<CircleDisplayResponse> getCircleListByFriend(@Param("userId") Long userId, @Param("userIDList") List<Long> userIDList, @Param("industryId") Long[] industryId, @Param("searchContent") String searchContent, @Param("appId") String appId, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 关注 按创建时间倒序
     */
    List<CircleDisplayResponse> getCircleListByAttention(@Param("userId") Long userId, @Param("industryId") Long[] industryId, @Param("searchContent") String searchContent, @Param("appId") String appId, @Param("page") Integer page, @Param("size") Integer size);

    Map<String, Object> selectCircleDetails(@Param("circleId") Long circleId);

    /**
     * @param circleId
     * @param type     0 取消点赞-1   1 点赞+1  2 评论+1  3 文章总转发数+1
     */
    void updatePraiseOrDiscussByCcircleId(@Param("circleId") long circleId, @Param("type") int type);

    /**
     * 我的发布列表
     */
    List<CircleDisplayResponse> findMyCircleList(@Param("userId") Long userId, @Param("searchContent") String searchContent, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 查询阅读朋友数
     */
    long selectReadFriendNum(@Param("userIDList") List<Long> userIDList, @Param("circleId") Long circleId);

    /**
     * 查询原文
     */
    CircleDisplayResponse findOriginalCircle(@Param("circleId") Long circleId, @Param("userId") Long userId);

    /**
     * 我的收藏列表
     */
    List<CircleDisplayResponse> findMyCollectList(@Param("userId") Long userId, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 我的下架列表
     */
    List<CircleDisplayResponse> findMyDropCircleList(@Param("userId") Long userId, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 获取阅读排行榜  type=0(月排行) type=1(周排行)
     */
    List<RankingResponse> getReadRankingList(@Param("type") Integer type, @Param("industryId") Long[] industryId, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 发布文章总数
     */
    long selectReleaseTotal(@Param("userId") Long userId);

    /**
     * 获取粉丝排行榜  type=0(月排行) type=1(周排行)
     */
    List<RankingResponse> getFansRankingList(@Param("type") Long type, @Param("industryId") Long[] industryId, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 查询用户发布列表
     */
    List<CircleDisplayResponse> findUserCircleList(@Param("userId") Long userId, @Param("searchContent") String searchContent, @Param("page") Integer page, @Param("size") Integer size);


    Circle selectById(@Param("circleId") Long circleId);

    int selectWebCircleListCount(WebCircleListRequest webCircleListRequest);

    List<Map<String, Object>> selectWebCircleList(WebCircleListRequest webCircleListRequest);

    /**
     * 根据文章ID  删除状态为false  审核状态为 审核通过
     *
     * @param circleId
     * @return
     */
    Circle selectByArticleStatus(@Param("circleId") long circleId);

    /**
     * 根据文章ID  删除状态为false
     *
     * @param circleId
     * @return
     */
    Circle selectByArticle(@Param("circleId") long circleId);

    Map<String, Object> selectWebCircleDetails(@Param("circleId") Long circleId);

    Circle selectWebByPrimaryKey(@Param("circleId") long circleId);
}