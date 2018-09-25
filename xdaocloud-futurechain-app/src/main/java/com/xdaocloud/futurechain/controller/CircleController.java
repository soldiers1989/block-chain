package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.circle.*;
import com.xdaocloud.futurechain.dto.resp.circle.CircleDisplayResponse;
import com.xdaocloud.futurechain.dto.resp.circle.FollowResponse;
import com.xdaocloud.futurechain.service.CircleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 麦圈控制类
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "CircleController", description = "麦圈")
@RequestMapping("/api/app/")
public class CircleController {

    @Autowired
    private CircleService circleService;

    /**
     * 获取朋友圈类型列表
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     * @date 2018年6月20日
     * @author dql
     */
    @ApiOperation(value = "获取朋友圈类型列表", notes = "获取朋友圈类型列表")
    @GetMapping("v2/circle/get/industry/list")
    public ResultInfo<?> getIndustryList(HttpServletRequest httpServletRequest) throws Exception {
        List<Map<Object, Object>> list = circleService.getIndustryList(httpServletRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 获取朋友圈列表
     *
     * @param entity
     * @return
     * @throws Exception
     * @date 2018年6月25日
     * @author dql
     */
    @ApiOperation(value = "获取朋友圈列表", notes = "获取朋友圈列表")
    @PostMapping("v2/circle/get/list")
    public ResultInfo<?> getCircleList(@Valid @ApiParam("userId:用户ID  type:类型(0=热门朋友圈  1=好友朋友圈  2=关注朋友圈)  industryId:朋友圈类型数组(id) searchContent:搜索文本    page:页数(默认0) size:每页显示数量") @RequestBody CircleListRequest entity, HttpServletRequest httpServletRequest) throws Exception {


        Map<Object, Object> result = circleService.getCircleList(entity, httpServletRequest);
        Integer status = (Integer) result.get("status");
        if (status == 0) {
            List<CircleDisplayResponse> list = (List<CircleDisplayResponse>) result.get("list");
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
        } else if (status == 1) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }

    /**
     * 收藏朋友圈
     *
     * @param entity
     * @return
     * @throws Exception
     * @date 2018年6月20日
     * @author dql
     */
    @ApiOperation(value = "收藏/取消收藏 朋友圈", notes = "userId:用户id circleId:朋友圈id isCollect:true(收藏) false(取消收藏)")
    @PostMapping("v2/circle/collect")
    public ResultInfo<?> collectCircle(@Valid @ApiParam("userId:用户id circleId:朋友圈id isCollect:true(收藏) false(取消收藏)") @RequestBody CollectRequest entity) throws Exception {

        long result = circleService.collectCircle(entity);
        if (result == 0) {
            return new ResultInfo<>(ResultInfo.SUCCESS, "收藏成功");
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }

    /**
     * 不感兴趣
     *
     * @param entity
     * @return
     * @throws Exception
     * @date 2018年6月20日
     * @author dql
     */
    @ApiOperation(value = "不感兴趣", notes = "userId:用户id circleId:朋友圈id")
    @PostMapping("v2/circle/lose/interest")
    public ResultInfo<?> loseInterest(@Valid @ApiParam("userId:用户id circleId:朋友圈id") @RequestBody CircleRequest entity) throws Exception {
        long result = circleService.loseInterest(entity);
        if (result == 0) {
            return new ResultInfo<>(ResultInfo.SUCCESS, "");
        }

        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }

    /**
     * 举报朋友圈
     *
     * @param entity
     * @return
     * @throws Exception
     * @date 2018年6月21日
     * @author dql
     */
    @ApiOperation(value = "举报朋友圈", notes = "userId:用户id circleId:朋友圈id")
    @PostMapping("v2/circle/report")
    public ResultInfo<?> report(@Valid @ApiParam("userId:用户id circleId:朋友圈id") @RequestBody ReportRequest entity) throws Exception {

        long result = circleService.report(entity);
        if (result == 0) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }

        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }

    /**
     * 上下架朋友圈
     *
     * @param entity
     * @return
     * @throws Exception
     * @date 2018年6月21日
     * @author dql
     */
    @ApiOperation(value = "上下架朋友圈", notes = "userId:用户id circleId:朋友圈id  status: false(默认)=上架  true=下架")
    @PostMapping("v2/circle/modify/status")
    public ResultInfo<?> modifyCircleStatus(@Valid @ApiParam("userId:用户id circleId:朋友圈id  status: false(默认)=上架  true=下架") @RequestBody ModifyCircleStatusRequest entity) throws Exception {
        long result = circleService.modifyCircleStatus(entity);
        if (result == 0) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }

        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }


    /**
     * 麦圈--扣费
     *
     * @param entity
     * @return
     * @throws Exception
     * @date 2018年6月21日
     * @author lmd
     */
    @ApiOperation(value = "麦圈--扣费", notes = "userId:用户id  circleId:文章ID originalId 被转发文章ID type 1转发 2 原文   zUserId 转发用户ID  password 交易密码（默认为空字符串）")
    @PostMapping("v2/modify/circle/buckleMoney")
    public ResultInfo<?> buckleMoney(@RequestBody Map<String, Object> entity) throws Exception {

        return circleService.buckleMoney(entity);
    }


    /**
     * 麦圈详情
     *
     * @param entity
     * @throws Exception
     * @date 2018年6月21日
     * @author lmd
     */
    @ApiOperation(value = "麦圈详情", notes = "userId:用户id circleId:朋友圈id  ")
    @PostMapping("v2/modify/circle/details")
    public ResultInfo<?> circleDetails(@RequestBody Map<String, Object> entity) throws Exception {
        return circleService.circleDetails(entity);
    }

    /**
     * 麦圈详情评论列表
     *
     * @param map
     * @return
     * @throws Exception
     * @date 2018年6月21日
     * @author lmd
     */
    @ApiOperation(value = "麦圈详情评论列表", notes = " circleId:朋友圈id  ")
    @PostMapping("v2/modify/circle/detailsComment")
    public ResultInfo<?> detailsComment(@RequestBody Map<String, Object> map) throws Exception {
        return circleService.detailsComment(map);
    }

    /**
     * 评论文章
     *
     * @param map
     * @return
     * @throws Exception
     * @date 2018年6月22日
     * @author lmd
     */
    @ApiOperation(value = "评论文章", notes = " circleId:朋友圈id  userId:用户ID  discussContent:评论内容")
    @PostMapping("v2/modify/circle/comment")
    public ResultInfo<?> setCircleComment(@RequestBody Map<String, Object> map) throws Exception {
        return circleService.setCircleComment(map);
    }

    /**
     * 点赞文章或者取消点赞
     *
     * @param map
     * @return
     * @throws Exception
     * @date 2018年6月22日
     * @author lmd
     */
    @ApiOperation(value = "点赞文章", notes = " circleId:朋友圈id  userId:用户ID ")
    @PostMapping("v2/modify/circle/likeCircle")
    public ResultInfo<?> likeCircle(@RequestBody Map<String, Object> map) throws Exception {
        return circleService.likeCircle(map);
    }


    /**
     * 关注用户或者取消关注
     *
     * @param map
     * @param bindingResult
     * @return
     * @throws Exception
     * @date 2018年6月22日
     * @author lmd
     */
    @ApiOperation(value = "关注用户或者取消关注", notes = " userId:用户ID  followUserId:被关注用户ID type:0 关注  1 取消关注")
    @PostMapping("v2/modify/circle/follow")
    public ResultInfo<?> follow(@RequestBody Map<String, Object> map) throws Exception {
        return circleService.follow(map);
    }


    /**
     * 转发文章
     *
     * @param map
     * @return
     * @throws Exception
     * @date 2018年6月22日
     * @author lmd
     */
    @ApiOperation(value = "转发文章", notes = " userId:用户ID  circleId:被转发文章ID ")
    @PostMapping("v2/modify/circle/forwarded")
    public ResultInfo<?> forwarded(@RequestBody Map<String, Object> map, HttpServletRequest httpServletRequest) throws Exception {
        return circleService.forwarded(map, httpServletRequest);
    }


    /**
     * 我的发布列表  我的收藏列表 我的下架列表
     *
     * @param userId
     * @param page
     * @param size
     * @return
     * @throws Exception
     * @date 2018年6月22日
     * @author dql
     */
    @ApiOperation(value = "我的发布/我的收藏/我的下架列表", notes = "userId:用户id type:0=我的发布列表  1=我的收藏列表   2=我的下架列表")
    @GetMapping("v2/circle/get/my/list")
    public ResultInfo<?> myCircleList(@ApiParam("用户id") @RequestParam(value = "userId") String userId, @ApiParam("类型: 0:我的发布列表  1:我的收藏列表   2:我的下架列表") @RequestParam(value = "type", defaultValue = "0") Integer type, @ApiParam("搜索文本内容") @RequestParam(value = "searchContent", required = false) String searchContent, @ApiParam(value = "页数(默认是0)") @RequestParam(value = "page", defaultValue = "0") Integer page, @ApiParam(value = "显示数量(默认是10)") @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        List<CircleDisplayResponse> list = circleService.myCircleList(userId, type, searchContent, page, size);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 我的关注好友列表
     *
     * @param userId
     * @param page
     * @param size
     * @return
     * @throws Exception
     * @date 2018年6月22日
     * @author dql
     */
    @ApiOperation(value = "我的关注好友列表", notes = "userId:用户id")
    @GetMapping("v2/circle/get/follow/list")
    public ResultInfo<?> getFollowList(@ApiParam("用户id") @RequestParam(value = "userId") String userId, @ApiParam(value = "页数(默认是0)") @RequestParam(value = "page", defaultValue = "0") Integer page, @ApiParam(value = "显示数量(默认是10)") @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        List<FollowResponse> list = circleService.getFollowList(userId, page, size);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }


    /**
     * 文章发布
     *
     * @param userId      用户ID
     * @param content     文章内容
     * @param industryId  行业类型ID
     * @param articleType 文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)
     * @param userNumber  总共奖励用户数
     * @param number      扣除阅读者用户麦钻数量或者单个用户奖励麦钻数量
     * @param fileType    0:没有文件  1:只有图片  2:只有视频  3:有视频有图片
     * @param password    交易密码
     * @return request
     * @throws Exception
     * @date 2018年6月23日
     * @author lmd
     */
    @ApiOperation(value = "文章发布", notes = "提交格式：multipart/form-data")
    @PostMapping("v2/get/circle/release")
    public ResultInfo<?> circleRelease(@ApiParam("用户id") @RequestParam(value = "userId") Long userId, @ApiParam("用户id") @RequestParam(value = "circleId") Long circleId,
                                       @ApiParam("文章内容") @RequestParam(value = "content") String content, @ApiParam("行业类别ID") @RequestParam(value = "industryId") long industryId,
                                       @ApiParam("0:默认  1:奖励用户麦钻  2:扣减用户麦钻") @RequestParam(value = "articleType") int articleType, @ApiParam("0:新发布  1:编辑发布") @RequestParam(value = "type") int type,
                                       @ApiParam("总共奖励用户数") @RequestParam(value = "userNumber", defaultValue = "0") int userNumber, @ApiParam("扣除阅读者用户麦钻数量") @RequestParam(value = "number", defaultValue = "0") String number,
                                       @ApiParam("0:没有文件  1:只有图片  2:只有视频  3:有视频有图片") @RequestParam(value = "fileType") int fileType, @ApiParam("文件路径") @RequestParam(value = "fileUrl") String fileUrl
            , @ApiParam("文章内容") @RequestParam(value = "password") String password, HttpServletRequest httpServletRequest) throws Exception {
        return circleService.circleRelease(userId, circleId, content, industryId, articleType, userNumber, number, fileType, fileUrl, type, password, httpServletRequest);
    }

    /**
     * 文章发布---文件上传
     *
     * @return request
     * @throws Exception
     * @date 2018年6月27日
     * @author lmd
     */
    @ApiOperation(value = "文章发布---图片上传", notes = "提交格式：multipart/form-data ; 文件参数名用：files")
    @PostMapping("v2/get/circle/circleUploadImage")
    public ResultInfo<?> circleUploadImage(HttpServletRequest request) throws Exception {
        return circleService.circleUploadImage(request);
    }

    /**
     * 文章编辑--获取文章详细信息
     *
     * @return request
     * @throws Exception
     * @date 2018年6月27日
     * @author lmd
     */
    @ApiOperation(value = "文章编辑--获取文章详细信息", notes = "circleId:文章ID type 0转发  1原文 ")
    @PostMapping("v2/get/circle/getCircle")
    public ResultInfo<?> getCircle(@RequestBody Map<String, Object> map) throws Exception {
        return circleService.getCircle(map);
    }


    /**
     * 发布文章--行业类型列表
     *
     * @return
     * @throws Exception
     * @date 2018年6月23日
     * @author lmd
     */
    @ApiOperation(value = "发布文章--行业类型列表", notes = "")
    @GetMapping("v2/get/circle/industryTypeList")
    public ResultInfo<?> getIndustryTypeList() throws Exception {
        List<Map<Object, Object>> list = circleService.getIndustryTypeList();
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }


    /**
     * 用户行业列表
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     * @date 2018年6月25日
     * @author dql
     */
    @ApiOperation(value = "用户行业列表", notes = "用户行业列表")
    @GetMapping("v2/circle/get/user/industry/list")
    public ResultInfo<?> getUserIndustryList(HttpServletRequest httpServletRequest) throws Exception {
        List<Map<Object, Object>> list = circleService.getUserIndustryList();
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 获取用户的发布列表
     *
     * @param userId
     * @param page
     * @param size
     * @return
     * @throws Exception
     * @date 2018年6月25日
     * @author dql
     */
    @ApiOperation(value = "用户的发布列表", notes = "userId:用户id page:页数  size:每页显示数")
    @GetMapping("v2/circle/get/user/circle/list")
    public ResultInfo<?> getUserList(@ApiParam("用户id") @RequestParam(value = "userId") String userId, @ApiParam("搜索文本内容") @RequestParam(value = "searchContent", required = false) String searchContent, @ApiParam(value = "页数(默认是0)") @RequestParam(value = "page", defaultValue = "0") Integer page, @ApiParam(value = "显示数量(默认是10)") @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        List<CircleDisplayResponse> list = circleService.getUserCircleList(userId, searchContent, page, size);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 大V排行榜
     *
     * @param entity
     * @return
     * @throws Exception
     * @date 2018年6月26日
     * @author dql
     */
    @ApiOperation(value = "大V排行榜", notes = "userId:用户id    industryId:用户行业类型id    type:0=周阅读排行 1=月阅读排行  2=周粉丝排行 3=月粉丝排行   ")
    @PostMapping("v2/circle/get/ranking/list")
    public ResultInfo<?> getRankingList(@Valid @ApiParam("userId:用户ID  type:类型(0=周阅读排行 1=月阅读排行  2=周粉丝排行 3=月粉丝排行)  industryId:朋友圈类型数组(id)  page:页数(默认0) size:每页显示数量") @RequestBody RankingListRequest entity) throws Exception {
        Map<Object, Object> result = circleService.getRankingList(entity);
        Integer status = (Integer) result.get("status");
        if (status == 0) {
            List<CircleDisplayResponse> list = (List<CircleDisplayResponse>) result.get("list");
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
        } else if (status == 1) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }

    /**
     * 获取大V页面推荐用户列表
     *
     * @param userId
     * @param page
     * @param size
     * @return
     * @throws Exception
     * @date 2018年6月26日
     * @author dql
     */
    @ApiOperation(value = "大V获取推荐用户", notes = "userId:用户id page:页数  size:每页显示数")
    @GetMapping("v2/circle/get/recommend/user/list")
    public ResultInfo<?> getRecommendUserList(@ApiParam("用户id") @RequestParam(value = "userId") String userId, @ApiParam(value = "页数(默认是0)") @RequestParam(value = "page", defaultValue = "0") Integer page, @ApiParam(value = "显示数量(默认是10)") @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        PageResponse list = circleService.getRecommendUserList(userId, page, size);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 删除朋友圈
     *
     * @param entity
     * @return
     * @throws Exception
     * @date 2018年6月27日
     * @author dql
     */
    @ApiOperation(value = "删除朋友圈", notes = "userId:用户id circleId:朋友圈id")
    @PostMapping("v2/circle/delete")
    public ResultInfo<?> deleteCircle(@Valid @ApiParam("userId:用户id circleId:朋友圈id") @RequestBody CircleRequest entity) throws Exception {
        long result = circleService.deleteCircle(entity);
        if (result == 0) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else if (result == 1) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }

        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
    }
}
