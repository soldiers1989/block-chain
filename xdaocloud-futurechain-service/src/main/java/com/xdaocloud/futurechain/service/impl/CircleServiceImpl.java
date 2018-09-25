package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.github.pagehelper.util.StringUtil;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.common.QiniuConfig;
import com.xdaocloud.futurechain.constant.AESConstant;
import com.xdaocloud.futurechain.constant.Constant;
import com.xdaocloud.futurechain.dto.req.circle.*;
import com.xdaocloud.futurechain.dto.resp.baiduyun.BaiDuYunImageResponse;
import com.xdaocloud.futurechain.dto.resp.baiduyun.BaiDuYunTextResponse;
import com.xdaocloud.futurechain.dto.resp.baiduyun.ImageResponseData;
import com.xdaocloud.futurechain.dto.resp.circle.CircleDisplayResponse;
import com.xdaocloud.futurechain.dto.resp.circle.FollowResponse;
import com.xdaocloud.futurechain.dto.resp.circle.RankingResponse;
import com.xdaocloud.futurechain.eos.EosAccount;
import com.xdaocloud.futurechain.mapper.*;
import com.xdaocloud.futurechain.model.*;
import com.xdaocloud.futurechain.service.CircleService;
import com.xdaocloud.futurechain.util.QiNiuUtils;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static com.xdaocloud.futurechain.util.EncoderUtils.decryptB;


@Service
@SuppressWarnings({"rawtypes", "unchecked"})
public class CircleServiceImpl implements CircleService {

    @Autowired
    private CircleMapper circleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IndustryTypeMapper industryTypeMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private LoseInterestedMapper loseInterestedMapper;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ReadingRecordMapper readingRecordMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private DiscussMapper discussMapper;

    @Autowired
    private PraiseMapper praiseMapper;

    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private EosAccount eosAccount;
    /**
     * 七牛云储存配置
     */
    @Autowired
    private QiniuConfig qiniuConfig;

    @Autowired
    private IndustryMapper industryMapper;


    @Autowired
    private SystemSetupMapper systemSetupMapper;

    @Autowired
    private FriendMapper friendMapper;
    //设置APPID/AK/SK
    public static final String APP_ID = "11483913";
    public static final String API_KEY = "w899fHS4gYIWjrFhyYtUK45R";
    public static final String SECRET_KEY = "CSvKGqIy4t2shf3MMKaSDsytSCK2VOvL";

    private AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);


    @Autowired
    private EosServiceImpl eosService;


    /**
     * 获取朋友圈类型列表
     */
    @Override
    @Transactional(readOnly=true)
    public List<Map<Object, Object>> getIndustryList(HttpServletRequest httpServletRequest) {
        List<Map<Object, Object>> result = new ArrayList<Map<Object, Object>>();
        Map<Object, Object> map = new HashMap<>();
        map.put("id", 0);
        map.put("name", "全部");
        result.add(map);
        result.addAll(industryTypeMapper.getIndustryList());
        return result;
    }


    /**
     * 收藏朋友圈
     */
    @Override
    @Transactional
    public long collectCircle(CollectRequest entity) {
        String circleId = entity.getCircleId();
        String userId = entity.getUserId();
        boolean isCollect = entity.getIsCollect();

        Collect collect = new Collect();
        collect.setCircleId(Long.valueOf(circleId));
        collect.setUserId(Long.valueOf(userId));
        if (isCollect) {
            Long count = collectMapper.selectCount(collect);
            if (count == 0) {
                collectMapper.insertSelective(collect);
            }
            return 0;
        } else {
            Long id = collectMapper.selectCollectId(collect);

            collect.setId(id);
            collect.setGmtModified(new Date());
            collect.setIsDelete(true);
            int updateByPrimaryKeySelective = collectMapper.updateByPrimaryKeySelective(collect);

            if (updateByPrimaryKeySelective > 0) {
                return 0;
            }
        }
        return -1;
    }


    /**
     * 朋友圈列表  type:0=热门  1=好友  2=关注
     */
    @Override
    @Transactional(readOnly=true)
    public Map<Object, Object> getCircleList(CircleListRequest entity,HttpServletRequest httpServletRequest) {
        String appId = httpServletRequest.getHeader(Constant.APPID_KEY);
        Integer type = entity.getType();
        String userId = entity.getUserId();
        Integer page = entity.getPage();
        Integer size = entity.getSize();
        Long[] industryId = entity.getIndustryId();
        String searchContent = entity.getSearchContent();

        Map<Object, Object> map = new HashMap<>();
        if (type == null) {
            map.put("status", 1);
            return map;
        }
        List<Long> userIDList = new ArrayList<>();
        userIDList = friendMapper.selectFriendIdListBuUserId(Long.valueOf(userId));

        List<CircleDisplayResponse> list = null;
        if (industryId == null || industryId.length == 0) {
            industryId = null;
        }
        if (page != 0) {
            page = size * page;
        }
        if (type == 0) {        //热门 按阅读量倒序-一个月
            list = circleMapper.getCircleListByPopular(Long.valueOf(userId), industryId, searchContent,appId, page, size);
        } else if (type == 1) {   //好友 按创建时间倒序 （加上自己发布的文章）
            if (userIDList != null && userIDList.size() > 0) {
                list = circleMapper.getCircleListByFriend(Long.valueOf(userId), userIDList, industryId, appId,searchContent, page, size);
            }

        } else if (type == 2) {   //关注 按创建时间倒序
            list = circleMapper.getCircleListByAttention(Long.valueOf(userId), industryId, searchContent,appId, page, size);
        }

        if (list == null) {
            map.put("status", 0);
            map.put("list", new ArrayList<>());
            return map;
        }

        for (CircleDisplayResponse circleDisplayResponse : list) {
            Long userIds = circleDisplayResponse.getUserId();
            Long circleId = circleDisplayResponse.getId();
            boolean isForwarding = circleDisplayResponse.getIsForwarding();

            //是否转发文章
            long readNum = 0;
            long readFriendNum = 0;
            boolean readResult = false;
            //是否收藏
            boolean isCollect = false;
            if (isForwarding) {
                Long originalId = circleDisplayResponse.getOriginalId();
                CircleDisplayResponse originalCircle = circleMapper.findOriginalCircle(originalId, Long.parseLong(userId));
                if (originalCircle != null) {
                    //文件地址转数组
                    String fileAddress = originalCircle.getFileAddress();
                    if (StringUtil.isNotEmpty(fileAddress)) {
                        String[] split = fileAddress.split(",");
                        if (split == null) {
                            String[] address = {fileAddress};
                            circleDisplayResponse.setImages(address);
                        } else {
                            //只显示三张图
                            int addressSize = split.length < 3 ? split.length : 3;
                            String[] address = new String[addressSize];
                            for (int i = 0; i < split.length; i++) {
                                if (i < 3) {
                                    address[i] = split[i];
                                }
                            }
                            circleDisplayResponse.setImages(address);
                        }
                    } else {
                        circleDisplayResponse.setImages(new String[]{});
                    }
                    //是否收藏
                    int count = collectMapper.selectCountByUserIdAndCircleId(Long.parseLong(userId), circleId);
                    if (count > 0) {
                        isCollect = true;
                    }
                    circleDisplayResponse.setTagName(originalCircle.getTagName());
                    circleDisplayResponse.setContent(originalCircle.getContent());
                    circleDisplayResponse.setPrice(originalCircle.getPrice());
                    circleDisplayResponse.setForwarding(originalCircle.getForwarding());
                    circleDisplayResponse.setDiscuss(originalCircle.getDiscuss());
                    circleDisplayResponse.setGood(originalCircle.getGood());
                } else {
                    circleDisplayResponse.setOriginalId(null);
                }

                //是否阅读
                long isRead = readingRecordMapper.selectIsRead(Long.parseLong(userId), originalId);
                readResult = false;
                if (isRead > 0) {
                    readResult = true;
                }

                //本月阅读数
                readNum = readingRecordMapper.selectReadCountByMonth(originalId);
                //阅读本文好友数
                //  readFriendNum = circleMapper.selectReadFriendNum(userIDList,originalId);
                //共同好友阅读该文章
                readFriendNum = readingRecordMapper.selectReadFriendNum(Long.valueOf(userId), originalId);
            } else {
                //文件地址转数组
                String fileAddress = circleDisplayResponse.getFileAddress();
                if (StringUtil.isNotEmpty(fileAddress)) {
                    String[] split = fileAddress.split(",");
                    if (split == null) {
                        String[] address = {fileAddress};
                        circleDisplayResponse.setImages(address);
                    } else {
                        //只显示三张图
                        int addressSize = split.length < 3 ? split.length : 3;
                        String[] address = new String[addressSize];
                        for (int i = 0; i < split.length; i++) {
                            if (i < 3) {
                                address[i] = split[i];
                            }
                        }
                        circleDisplayResponse.setImages(address);
                    }
                } else {
                    circleDisplayResponse.setImages(new String[]{});
                }

                //是否收藏
                int count = collectMapper.selectCountByUserIdAndCircleId(Long.parseLong(userId), circleId);
                if (count > 0) {
                    isCollect = true;
                }

                //是否阅读
                long isRead = readingRecordMapper.selectIsRead(Long.parseLong(userId), circleId);
                readResult = false;
                if (isRead > 0) {
                    readResult = true;
                }

                //本月阅读数
                readNum = readingRecordMapper.selectReadCountByMonth(circleId);

                //阅读本文好友数
                //  readFriendNum = circleMapper.selectReadFriendNum(userIDList,originalId);
                //共同好友阅读该文章
                readFriendNum = readingRecordMapper.selectReadFriendNum(Long.valueOf(userId), circleId);
            }

            //用户等级
            int vipRank = getVipRank(userIds);
            circleDisplayResponse.setCollect(isCollect);
            circleDisplayResponse.setReadNum(readNum);
            circleDisplayResponse.setReaded(readResult);
            circleDisplayResponse.setReadFriendNum(readFriendNum);
            circleDisplayResponse.setUserLevel(vipRank);
        }

        map.put("status", 0);
        map.put("list", list);
        return map;
    }


    /**
     * 设置为不感兴趣
     */
    @Override
    @Transactional
    public long loseInterest(CircleRequest entity) {
        String circleId = entity.getCircleId();
        String userId = entity.getUserId();

        int count = loseInterestedMapper.selectByUserIdAndCircleId(Long.parseLong(userId), Long.parseLong(circleId));
        if (count != 0) {
            return 0;
        }
        LoseInterested loseInterested = new LoseInterested();
        loseInterested.setCircleId(Long.valueOf(circleId));
        loseInterested.setUserId(Long.valueOf(userId));
        loseInterestedMapper.insertSelective(loseInterested);
        return 0;
    }


    /**
     * 举报朋友圈
     */
    @Override
    @Transactional
    public long report(ReportRequest entity) {
        String circleId = entity.getCircleId();
        String userId = entity.getUserId();

        Circle circle = new Circle();
        circle.setAuditResult((byte) 0);//被举报后下架、变成待审核，麦钻不返回，等人工审核进行判断
        circle.setArticleStatus(true);
        circle.setGmtModified(new Date());
        circle.setId(Long.valueOf(circleId));
        int result = circleMapper.updateByPrimaryKeySelective(circle);

        if (result == 1) {
            Report report = new Report();
            report.setUserId(Long.valueOf(userId));
            report.setCircleId(Long.valueOf(circleId));
            report.setRepContent(entity.getRepContent());
            report.setAuditResults((byte) 0);
            reportMapper.insertSelective(report);
            return 0;
        }
        return -1;
    }


    /**
     * 上下架朋友圈动态
     */
    @Override
    @Transactional
    public long modifyCircleStatus(ModifyCircleStatusRequest entity) throws Exception {
        String circleId = entity.getCircleId();
        String userId = entity.getUserId();
        //true 下架结算是否还有未发完的麦钻 有：返还
        if (entity.getStatus()) {
            Circle circle = circleMapper.selectByPrimaryKey(Long.parseLong(circleId));
            if (circle == null) {
                return -1;
            }
            if (circle.getArticleType() == 1 && circle.getSurplusRewardsNumber() > 0) {
                //文章作者可返现的麦钻 = 剩余奖励个数 * 单个用户奖励数量
                BigDecimal bigDecimal = new BigDecimal(circle.getSurplusRewardsNumber()).multiply(circle.getRewardsNumber());
                //文章作者的钱包
                EosWallet wenz = eosWalletMapper.findOneByUserId(Long.parseLong(userId));
                eosAccount.fromTempTransaction(wenz.getWalletName(), bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toString(), "发布文章未被阅读返现");
            }
        }

        Circle circle = new Circle();
        circle.setId(Long.valueOf(circleId));
        circle.setUserId(Long.valueOf(userId));
        circle.setArticleStatus(entity.getStatus());
        circle.setGmtModified(new Date());
        int result = circleMapper.updateByPrimaryKeySelective(circle);
        if (result == 1) {
            return 0;
        }
        return -1;
    }

    /**
     * 查询充值等级
     */
    public int getVipRank(Long userId) {
        //查询充值总值
        BigDecimal feeCount = ordersMapper.selectSumFee(userId);
        if (feeCount == null) {
            feeCount = new BigDecimal(0);
        }
        int vipRank = 1;
        if (0 <= feeCount.longValue() && feeCount.longValue() < 500000) {
            vipRank = 1;
        } else if (500000 <= feeCount.longValue() && feeCount.longValue() < 1000000) {
            vipRank = 2;
        } else if (1000000 <= feeCount.longValue() && feeCount.longValue() < 10000000) {
            vipRank = 3;
        } else if (10000000 <= feeCount.longValue() && feeCount.longValue() < 100000000) {
            vipRank = 4;
        } else if (100000000 <= feeCount.longValue()) {
            vipRank = 5;
        }
        return vipRank;
    }


    /**
     * 麦圈详情--扣费
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> buckleMoney(Map<String, Object> entity) {

        Circle circle = circleMapper.selectByPrimaryKey(Long.parseLong(entity.get("circleId").toString()));
        Map<String, Object> map = new HashedMap();
        if (circle == null) {
            map.put("type", 1);//
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
        }
        //type 1转发 2 原文
        if ("1".equals(entity.get("type").toString())) {
            circle = circleMapper.selectByPrimaryKey(Long.parseLong(entity.get("originalId").toString()));
            if (circle == null) {
                map.put("type", 2);//
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
            }
        }


        try {
            //本人就不扣除、奖励费用
            if (!(circle.getUserId() == Long.parseLong(entity.get("userId").toString()))) {
                /**
                 * 费用加加减减
                 */
                int nmb = eosCharge(entity, circle);
                switch (nmb) {
                    case -1:
                        return new ResultInfo<>(ResultInfo.FAILURE, "余额不足");
                    case -2:
                        return new ResultInfo<>(ResultInfo.FAILURE, "账号不存在");
                    case -3:
                        map.put("type", 3);
                        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map); //请输入交易密码
                    case -4:
                        map.put("type", 4);
                        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map); //交易密码错误
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        map.put("type", 0);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
    }

    /**
     * 麦圈详情
     *
     * @param entity
     * @return
     * @date 2018年6月21日
     * @author lmd
     */
    @Override
    @Transactional(readOnly=true)
    public ResultInfo<?> circleDetails(Map<String, Object> entity) throws Exception {

        Circle circle = circleMapper.selectByPrimaryKey(Long.parseLong(entity.get("circleId").toString()));
        if (circle == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "该文章不存在");
        }

        insertReadingRecord(Long.parseLong(entity.get("userId").toString()), circle.getId());

        //返回改文章的信息
        Map<String, Object> maps = circleMapper.selectCircleDetails(Long.parseLong(entity.get("circleId").toString()));

        //本人就不扣除、奖励费用
        if (!(circle.getUserId() == Long.parseLong(entity.get("userId").toString()))) {
            /*int nmb = eosCharge(entity,circle);
            if(nmb != 0 ){
                return new ResultInfo<>(ResultInfo.FAILURE, "余额不足");
            }*/

            if (circle.getArticleType() == 1) {
                //t_circle  修改总阅读数 total_reading   剩余奖励个数  surplus_rewards_number  修改时间 gmt_modified
                if (circle.getSurplusRewardsNumber() - 1 == 0) {
                    //奖励完就直接下线
                    circle.setArticleStatus(true);
                }
                circle.setSurplusRewardsNumber(circle.getSurplusRewardsNumber() - 1);
            }

            //t_circle  修改总阅读数 total_reading   剩余奖励个数  surplus_rewards_number  修改时间 gmt_modified
            circle.setTotalReading(circle.getTotalReading() + 1);
            circle.setGmtModified(new Date());
            circleMapper.updateByPrimaryKeySelective(circle);
        }


        if (maps.get("fileAddress") != null) {
            //文件地址List<String> : {"aaaaaaa","bbbbbbb","ccccccccc"}
            String str[] = maps.get("fileAddress").toString().split(",");
            maps.put("fileAddress", Arrays.asList(str));
        }

        //阅读量加上当前的1
        maps.put("totalReading", Long.parseLong(maps.get("totalReading").toString()) + 1);

        //共同好友阅读该文章
        long count = readingRecordMapper.selectReadFriendNum(Long.parseLong(entity.get("userId").toString()), Long.parseLong(entity.get("circleId").toString()));
        maps.put("totalReadingFriend", count);
        maps.put("vipRank", getVipRank(circle.getUserId()));

        //是否关注 关注用户表 t_follow
        int follow = followMapper.selectByUserIdAndPassiveUserId(Long.parseLong(entity.get("userId").toString()), circle.getUserId());
        if (follow == 0) {
            maps.put("isFollow", false);
        } else {
            maps.put("isFollow", true);
        }
        //是否点赞  t_praise
        int praise = praiseMapper.selectByUserIdAndCircleId(Long.parseLong(entity.get("userId").toString()), circle.getId());
        if (praise == 0) {
            maps.put("isPraise", false);
        } else {
            maps.put("isPraise", true);
        }

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, maps);
    }


    public int eosCharge(Map<String, Object> entity, Circle circle) throws Exception {

        if (circle.getArticleType() != 0) {
            long count = 0;
            ////判断该用户是否查看过该文章？
            //type 1转发 2 原文
            if ("1".equals(entity.get("type").toString())) {
                count = readingRecordMapper.selectIsRead(Long.parseLong(entity.get("userId").toString()), Long.parseLong(entity.get("originalId").toString()));
            } else {
                count = readingRecordMapper.selectIsRead(Long.parseLong(entity.get("userId").toString()), Long.parseLong(entity.get("circleId").toString()));
            }

            if (count == 0) {
                //是否要奖励或者扣除麦钻？
                //文章类别 1:奖励用户麦钻
                if (circle.getArticleType() == 1 && circle.getSurplusRewardsNumber() > 0) {
                    //平台手续费
                    BigDecimal pingtai = circle.getRewardsNumber().multiply(new BigDecimal("0.03"));
                    //1 为转发进来的
                    if ("1".equals(entity.get("type").toString())) {
                        //读者所得到的麦钻 =（总共要扣的钱-平台所得的钱）* 60%
                        BigDecimal duzhe = circle.getRewardsNumber().subtract(pingtai.setScale(4, BigDecimal.ROUND_DOWN)).multiply(new BigDecimal("0.6"));
                        //读者的钱包
                        EosWallet wallet = eosWalletMapper.findOneByUserId(Long.parseLong(entity.get("userId").toString()));
                        eosAccount.fromTempTransaction(wallet.getWalletName(), duzhe.setScale(4, BigDecimal.ROUND_DOWN).toString(), "阅读文章获取");
                        //========================================================================================
                        //转发者所得到的麦钻 =（总共要扣的钱-平台所得的钱）* 40%
                        BigDecimal zhuanfazhe = circle.getRewardsNumber().subtract(pingtai.setScale(4, BigDecimal.ROUND_DOWN)).multiply(new BigDecimal("0.4"));
                        //转发者的钱包
                        EosWallet zhuanwallet = eosWalletMapper.findOneByUserId(Long.parseLong(entity.get("zUserId").toString()));
                        eosAccount.fromTempTransaction(zhuanwallet.getWalletName(), zhuanfazhe.setScale(4, BigDecimal.ROUND_DOWN).toString(), "转发文章被阅读获取");
                    } else {
                        //读者所得到的麦钻 =总共要扣的钱-平台所得的钱
                        BigDecimal duzhe = circle.getRewardsNumber().subtract(pingtai.setScale(4, BigDecimal.ROUND_DOWN));
                        //读者的钱包
                        EosWallet wallet = eosWalletMapper.findOneByUserId(Long.parseLong(entity.get("userId").toString()));
                        eosAccount.fromTempTransaction(wallet.getWalletName(), duzhe.setScale(4, BigDecimal.ROUND_DOWN).toString(), "阅读文章获取");
                    }
                    return 0;
                } else if (circle.getArticleType() == 2) {

                    //读者需要扣取 =总共要扣的钱
                    BigDecimal duzhe = circle.getDeductionNumber().setScale(4, BigDecimal.ROUND_DOWN);

                    User user = userMapper.selectByPrimaryKey(Long.valueOf(entity.get("userId").toString()));
                    if (user == null) {
                        return -2; //账号不存在
                    }
                    //判断是否要交易密码
                    int numb = eosService.maiQuanCheckTransactionPassword(duzhe, entity.get("password").toString(), user);
                    switch (numb) {
                        case 1:
                            return -3; //请输入交易密码
                        case 2:
                            return -4; //交易密码错误
                    }

                    // 2:扣减用户麦钻

                    //判断用户是否足够麦钻扣取
                    //读者的钱包
                    EosWallet wallet = eosWalletMapper.findOneByUserId(Long.parseLong(entity.get("userId").toString()));
                    //读者所扣麦钻 = 总共要扣的钱
                    BigDecimal dz = circle.getDeductionNumber().setScale(4, BigDecimal.ROUND_DOWN);
                    String key = decryptB(AESConstant.KEY, wallet.getActivePrivateKey());
                    String mai = eosAccount.getBalance(wallet.getWalletName());
                    if (new BigDecimal(mai).compareTo(dz) < 0) {
                        return -1;
                    }
                    eosAccount.toTempTransaction(Long.parseLong(entity.get("userId").toString()), wallet.getWalletName(), duzhe.toString(), "阅读文章扣取", key);
                    //文章作者钱包
                    EosWallet eosWallet = eosWalletMapper.findOneByUserId(circle.getUserId());
                    //平台手续费
                    BigDecimal pingtai = circle.getDeductionNumber().multiply(new BigDecimal("0.03"));
                    //1 为转发进来的
                    if ("1".equals(entity.get("type").toString())) {

                        //转发者的钱包
                        EosWallet zhuanwallet = eosWalletMapper.findOneByUserId(Long.parseLong(entity.get("zUserId").toString()));
                        //转发者所得到的麦钻 =（总共要扣的钱-平台所得的钱）* 40%
                        BigDecimal zhuanfazhe = circle.getDeductionNumber().subtract(pingtai.setScale(4, BigDecimal.ROUND_DOWN)).multiply(new BigDecimal("0.4"));
                        eosAccount.fromTempTransaction(zhuanwallet.getWalletName(), zhuanfazhe.setScale(4, BigDecimal.ROUND_DOWN).toString(), "转发文章被阅读获取");

                        //文章作者的所得到的麦钻 =（总共要扣的钱-平台所得的钱）* 60%
                        BigDecimal wenzhang = circle.getDeductionNumber().subtract(pingtai.setScale(4, BigDecimal.ROUND_DOWN)).multiply(new BigDecimal("0.6"));
                        eosAccount.fromTempTransaction(eosWallet.getWalletName(), wenzhang.setScale(4, BigDecimal.ROUND_DOWN).toString(), "文章被阅读获取");
                        return 0;
                    } else {
                        // //文章作者的所得到的麦钻 =总共要扣的钱-平台所得的钱
                        BigDecimal zuozhe = circle.getDeductionNumber().subtract(pingtai.setScale(4, BigDecimal.ROUND_DOWN));
                        eosAccount.fromTempTransaction(eosWallet.getWalletName(), zuozhe.setScale(4, BigDecimal.ROUND_DOWN).toString(), "文章被阅读获取");

                        return 0;
                    }
                }
            }
        }
        return 0;
    }

    private boolean insertReadingRecord(long userId, long circleId) {
        //t_reading_record  写阅读记录表
        ReadingRecord readingRecord = new ReadingRecord();
        readingRecord.setUserId(userId);
        readingRecord.setCircleId(circleId);
        readingRecord.setGmtModified(new Date());
        readingRecord.setGmtCreate(new Date());
        readingRecord.setIsDelete(false);
        readingRecordMapper.insert(readingRecord);
        return true;
    }


    /**
     * 麦圈详情评论列表
     * pageNum     当前页码
     * pageSize    一页多少条
     *
     * @param map
     * @return
     * @date 2018年6月21日
     * @author lmd
     */
    @Override
    @Transactional(readOnly=true)
    public ResultInfo<?> detailsComment(Map<String, Object> map) {
        List<Map<String, Object>> listData = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        int pageNum = 1;
        pageNum = Integer.parseInt(map.get("pageNum").toString());

        int pageSize = 0;
        pageSize = Integer.parseInt(map.get("pageSize").toString());

        int count = discussMapper.selectByCircleIdCount(Long.parseLong(map.get("circleId").toString())); //总记录数
        int Pages, pageNums = 0;
        if (count % pageSize == 0) {
            Pages = count / pageSize;
        } else {
            Pages = count / pageSize + 1;
        }
        //如果传入的当前页比最大页大就返回空
        if (pageNum <= Pages) {
            if (pageNum > 1) {
                pageNums = (pageNum - 1) * pageSize;
            } else {
                pageNums = 0;
            }
            //查询用户
            list = discussMapper.selectByCircleIdList(Long.parseLong(map.get("circleId").toString()), pageNums, pageSize);
        }

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> dataMaps = new LinkedHashMap();
                dataMaps.put("avatar", list.get(i).get("avatar"));
                dataMaps.put("nickname", list.get(i).get("nickname"));
                dataMaps.put("discussContent", list.get(i).get("discussContent"));
                dataMaps.put("uId", list.get(i).get("uId"));
                dataMaps.put("gmtModified", list.get(i).get("gmtModified"));
                dataMaps.put("vipRank", getVipRank(Long.parseLong(list.get(i).get("uId").toString())));
                listData.add(dataMaps);
            }
        }


        Map<String, Object> dataMap = new LinkedHashMap();
        dataMap.put("commentData", listData);
        dataMap.put("pages", Pages);
        dataMap.put("pagesSize", pageSize);
        dataMap.put("total", count);
        dataMap.put("pagesNum", pageNum);

        //评论记录表 t_discuss
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, dataMap);
    }

    /**
     * 评论文章
     *
     * @param map
     * @return
     * @date 2018年6月22日
     * @author lmd
     */
    @Override
    @Transactional
    public ResultInfo<?> setCircleComment(Map<String, Object> map) {
        try {

            if (map.get("discussContent").toString().isEmpty()) {
                return new ResultInfo<>(ResultInfo.ERROR, "评论不能为空");
            }
            //留言校验
            JSONObject response = client.antiSpam(map.get("discussContent").toString(), null);
            BaiDuYunTextResponse baiDuYunTextResponse = JSON.parseObject(response.get("result").toString(), new TypeReference<BaiDuYunTextResponse>() {
            });
            if ((int) baiDuYunTextResponse.getSpam() != 0) {
                return new ResultInfo<>(ResultInfo.ERROR, "存在敏感词");
            }
            Discuss discuss = new Discuss();
            discuss.setUserId(Long.parseLong(map.get("userId").toString()));
            discuss.setCircleId(Long.parseLong(map.get("circleId").toString()));
            discuss.setDiscussContent(map.get("discussContent").toString());
            discuss.setGmtCreate(new Date());
            discuss.setGmtModified(new Date());
            discuss.setIsDelete(false);
            discussMapper.insert(discuss);
            //文章总点评论数+1
            circleMapper.updatePraiseOrDiscussByCcircleId(Long.parseLong(map.get("circleId").toString()), 2);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 点赞文章或者取消点赞
     *
     * @param map
     * @return
     * @date 2018年6月22日
     * @author lmd
     */
    @Override
    @Transactional
    public ResultInfo<?> likeCircle(Map<String, Object> map) {
        try {
            if ("0".equals(map.get("type").toString())) {
                int isPra = praiseMapper.selectByUserIdAndCircleId(Long.parseLong(map.get("userId").toString()), Long.parseLong(map.get("circleId").toString()));
                if (isPra != 0) {
                    return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_FAILURE, "已点赞");
                }
                Praise praise = new Praise();
                praise.setUserId(Long.parseLong(map.get("userId").toString()));
                praise.setCircleId(Long.parseLong(map.get("circleId").toString()));
                praise.setGmtCreate(new Date());
                praise.setGmtModified(new Date());
                praise.setIsDelete(false);
                praiseMapper.insert(praise);
                //文章总点赞数+1
                circleMapper.updatePraiseOrDiscussByCcircleId(Long.parseLong(map.get("circleId").toString()), 1);

            } else {
                praiseMapper.updateByUserIdAndCircleId(Long.parseLong(map.get("userId").toString()), Long.parseLong(map.get("circleId").toString()));
                Circle circle = circleMapper.selectByArticle(Long.parseLong(map.get("circleId").toString()));
                if (circle != null) {
                    if (circle.getTotalPraise() > 0) {
                        //文章总点赞数-1
                        circleMapper.updatePraiseOrDiscussByCcircleId(Long.parseLong(map.get("circleId").toString()), 0);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_FAILURE, "系统异常");
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 关注或取消关注用户
     *
     * @param map
     * @return
     * @date 2018年6月22日
     * @author lmd
     */
    @Override
    @Transactional
    public ResultInfo<?> follow(Map<String, Object> map) {
        try {
            if ("0".equals(map.get("type").toString())) {
                //判断是否已关注
                long num = followMapper.selectIsFollow(Long.parseLong(map.get("userId").toString()), Long.parseLong(map.get("followUserId").toString()));
                if (num > 0) {
                    return new ResultInfo<>(ResultInfo.ERROR, "不要重复关注");
                }

                Follow follow = new Follow();
                follow.setUserId(Long.parseLong(map.get("userId").toString()));
                follow.setPassiveUserId(Long.parseLong(map.get("followUserId").toString()));
                follow.setGmtModified(new Date());
                follow.setGmtCreate(new Date());
                follow.setIsDelete(false);
                followMapper.insert(follow);
            } else {
                followMapper.updateByUserIdAndFollowUserId(Long.parseLong(map.get("userId").toString()), Long.parseLong(map.get("followUserId").toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_FAILURE, "系统异常");
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 转发文章
     *
     * @param map
     * @return
     * @date 2018年6月22日
     * @author lmd
     */
    @Override
    @Transactional
    public ResultInfo<?> forwarded(Map<String, Object> map,HttpServletRequest httpServletRequest) {
        String appId =httpServletRequest.getHeader(Constant.APPID_KEY);
        try {
            if (StringUtil.isEmpty(map.get("content").toString().trim())) {
                return new ResultInfo<>(ResultInfo.ERROR, "文本内容不能为空");
            }

           /* Circle zCircleId = circleMapper.selectById(Long.parseLong(map.get("zCircleId").toString()));
            if( zCircleId == null ){
                return new ResultInfo<>(ResultInfo.ERROR, "被转发文章已下架");
            }*/

            //过来文章内容是否包含敏感词?
            JSONObject response = client.antiSpam(map.get("content").toString(), null);
            BaiDuYunTextResponse baiDuYunTextResponse = JSON.parseObject(response.get("result").toString(), new TypeReference<BaiDuYunTextResponse>() {
            });
            if ((int) baiDuYunTextResponse.getSpam() != 0) {
                return new ResultInfo<>(ResultInfo.ERROR, "存在敏感词");
            }

            //判断是否编辑发布  0:新转发发布 1:编辑转发发布
            if (Integer.parseInt(map.get("type").toString()) == 1) {
                Circle circle = circleMapper.selectById(Long.parseLong(map.get("circleId").toString()));
                if (circle == null) {
                    return new ResultInfo<>(ResultInfo.ERROR, "该转发不存在");
                }
                circle.setAppId(appId);
                circle.setFeelings(map.get("content").toString());
                circle.setArticleStatus(false);//朋友圈状态(0:上架  1:下架)
                circle.setGmtModified(new Date());
                circleMapper.updateByPrimaryKeySelective(circle);
            } else {
                Circle circle = new Circle();
                circle.setAppId(appId);
                circle.setUserId(Long.parseLong(map.get("userId").toString()));
                circle.setCircleId(Long.parseLong(map.get("zCircleId").toString()));
                circle.setFeelings(map.get("content").toString()); //转发感想(原文为null)
                circle.setArticleType((byte) 0);//文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)
                circle.setArticleCategory(true);//文章类型(0:原文  1:转发)
                circle.setAuditResult((byte) 1);//检查结果(0:待审核 1:审核通过 2:审核不通过)
                circle.setArticleStatus(false);//朋友圈状态(0:上架  1:下架)
                circle.setGmtModified(new Date());
                circle.setGmtCreate(new Date());
                circle.setIsDeleted(false);
                circleMapper.insertSelective(circle);
                //文章总转发数+1
                circleMapper.updatePraiseOrDiscussByCcircleId(Long.parseLong(map.get("zCircleId").toString()), 3);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_FAILURE, "系统异常");
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 我的发布列表 type:0=我的发布列表  1=我的收藏列表   2=我的下架列表
     */
    @Override
    @Transactional(readOnly=true)
    public List<CircleDisplayResponse> myCircleList(String userId, Integer type, String searchContent, Integer page, Integer size) {
        List<CircleDisplayResponse> list = null;
        if (page != 0) {
            page = size * page;
        }

        if (type == 0) {
            list = circleMapper.findMyCircleList(Long.valueOf(userId), searchContent, page, size);    //我的发布列表
        } else if (type == 1) {
            list = circleMapper.findMyCollectList(Long.valueOf(userId), page, size);
        } else if (type == 2) {
            list = circleMapper.findMyDropCircleList(Long.valueOf(userId), page, size);
        }

        if (list == null) {
            return new ArrayList<>();
        }


        for (CircleDisplayResponse circleDisplayResponse : list) {
            Long userIds = circleDisplayResponse.getUserId();
            Long circleId = circleDisplayResponse.getId();
            boolean isForwarding = circleDisplayResponse.getIsForwarding();

            //是否转发文章
            long readNum = 0;
            long readFriendNum = 0;
            boolean readResult = false;
            if (isForwarding) {
                Long originalId = circleDisplayResponse.getOriginalId();
                CircleDisplayResponse originalCircle = circleMapper.findOriginalCircle(originalId, Long.parseLong(userId));
                if (originalCircle != null) {
                    //文件地址转数组
                    String fileAddress = originalCircle.getFileAddress();
                    if (StringUtil.isNotEmpty(fileAddress)) {
                        String[] split = fileAddress.split(",");
                        if (split == null) {
                            String[] address = {fileAddress};
                            circleDisplayResponse.setImages(address);
                        } else {
                            //只显示三张图
                            int addressSize = split.length < 3 ? split.length : 3;
                            String[] address = new String[addressSize];
                            for (int i = 0; i < split.length; i++) {
                                if (i < 3) {
                                    address[i] = split[i];
                                }
                            }
                            circleDisplayResponse.setImages(address);
                        }
                    } else {
                        circleDisplayResponse.setImages(new String[]{});
                    }

                    circleDisplayResponse.setTagName(originalCircle.getTagName());
                    circleDisplayResponse.setContent(originalCircle.getContent());
                    circleDisplayResponse.setPrice(originalCircle.getPrice());
                    circleDisplayResponse.setForwarding(originalCircle.getForwarding());
                    circleDisplayResponse.setDiscuss(originalCircle.getDiscuss());
                    circleDisplayResponse.setGood(originalCircle.getGood());
                } else {
                    circleDisplayResponse.setOriginalId(null);
                }

                //是否阅读
                long isRead = readingRecordMapper.selectIsRead(Long.valueOf(userId), originalId);
                readResult = false;
                if (isRead > 0) {
                    readResult = true;
                }

                //本月阅读数
                readNum = readingRecordMapper.selectReadCountByMonth(originalId);


                //阅读本文好友数
                //  readFriendNum = circleMapper.selectReadFriendNum(userIDList,originalId);
                //共同好友阅读该文章
                readFriendNum = readingRecordMapper.selectReadFriendNum(Long.valueOf(userId), originalId);
            } else {
                //文件地址转数组
                String fileAddress = circleDisplayResponse.getFileAddress();
                if (StringUtil.isNotEmpty(fileAddress)) {
                    String[] split = fileAddress.split(",");
                    if (split == null) {
                        String[] address = {fileAddress};
                        circleDisplayResponse.setImages(address);
                    } else {
                        //只显示三张图
                        int addressSize = split.length < 3 ? split.length : 3;
                        String[] address = new String[addressSize];
                        for (int i = 0; i < split.length; i++) {
                            if (i < 3) {
                                address[i] = split[i];
                            }
                        }
                        circleDisplayResponse.setImages(address);
                    }
                } else {
                    circleDisplayResponse.setImages(new String[]{});
                }

                //是否阅读
                long isRead = readingRecordMapper.selectIsRead(Long.valueOf(userId), circleId);
                readResult = false;
                if (isRead > 0) {
                    readResult = true;
                }

                //本月阅读数
                readNum = readingRecordMapper.selectReadCountByMonth(circleId);

                //阅读本文好友数
                //  readFriendNum = circleMapper.selectReadFriendNum(userIDList,originalId);
                //共同好友阅读该文章
                readFriendNum = readingRecordMapper.selectReadFriendNum(Long.valueOf(userId), circleId);
            }

            //用户等级
            int vipRank = getVipRank(userIds);

            circleDisplayResponse.setReadNum(readNum);
            circleDisplayResponse.setReaded(readResult);
            circleDisplayResponse.setReadFriendNum(readFriendNum);
            circleDisplayResponse.setUserLevel(vipRank);
        }

        return list;
    }


    /**
     * 我的关注好友列表
     */
    @Override
    @Transactional(readOnly=true)
    public List<FollowResponse> getFollowList(String userId, Integer page, Integer size) {
        if (page != 0) {
            page = size * page;
        }
        List<FollowResponse> list = userMapper.getFollowList(Long.valueOf(userId), page, size);
        for (FollowResponse followResponse : list) {
            if (followResponse != null) {
                Long id = followResponse.getUserId();

                //用户等级
                int vipRank = getVipRank(id);
                followResponse.setLevel(vipRank);
            }
        }

        return list;
    }


    /**
     * @param userId      用户ID
     * @param circleId    文章ID
     * @param content     文章内容
     * @param industryId  行业类型ID
     * @param articleType 文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)
     * @param userNumber  总共奖励用户数
     * @param number      扣除阅读者用户麦钻数量或者单个用户奖励麦钻数量
     * @param fileType    0:没有文件  1:只有图片  2:只有视频  3:有视频有图片
     * @param fileUrl
     * @param type        0:新发布  1:编辑发布
     * @param password    交易密码
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> circleRelease(Long userId, Long circleId, String content, long industryId, int articleType, int userNumber, String number, int fileType, String fileUrl, int type, String password,HttpServletRequest httpServletRequest) throws Exception {

        String appId = httpServletRequest.getHeader(Constant.APPID_KEY);
        try {
            if (StringUtil.isEmpty(content.trim())) {
                return new ResultInfo<>(ResultInfo.ERROR, "文章内容不能为空");
            }
            if (articleType != 0) {
                if (number.compareTo("0.01") < 0) {
                    return new ResultInfo<>(ResultInfo.ERROR, "奖励或扣减麦钻数最小为0.01");
                }
            }

            int keyValue = 0;
            //判断是否有足够的麦钻扣取
            //文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)
            if (articleType == 1) {
                //文章作者的扣取麦钻 =  总共要扣的钱
                BigDecimal wenzhang = new BigDecimal(number).multiply(new BigDecimal(userNumber));

                //文章作者钱包
                EosWallet eosWallet = eosWalletMapper.findOneByUserId(userId);
                //String key = decryptB(AESConstant.KEY, eosWallet.getActivePrivateKey());
                String mai = eosAccount.getBalance(eosWallet.getWalletName());
                if (new BigDecimal(mai).compareTo(wenzhang) < 0) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "余额不足");
                }

                //判断是否需要支付密码
                User user = userMapper.selectByPrimaryKey(userId);
                if (user == null) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "用户不存在");
                }
                //判断是否要交易密码
                int numb = eosService.maiQuanCheckTransactionPassword(wenzhang, password, user);
                switch (numb) {
                    case 1:
                        return new ResultInfo<>(ResultInfo.FAILURE, "请输入交易密码"); //请输入交易密码
                    case 2:
                        return new ResultInfo<>(ResultInfo.FAILURE, "交易密码错误"); //交易密码错误
                }

            }
            //过来文章内容是否包含敏感词? TODO
          /*  JSONObject response = client.antiSpam(content, null);
            BaiDuYunTextResponse baiDuYunTextResponse = JSON.parseObject(response.get("result").toString(), new TypeReference<BaiDuYunTextResponse>() {
            });*/

            Boolean isOk = false;//默认审核中
            //第三方返回结果OK TODO
            //if ((int) baiDuYunTextResponse.getSpam() == 0) {
            if (true) {
                //判断系统设置表 第三方通过是否开启人工审核
                keyValue = systemSetupMapper.selectByKeyName("okSwitch");
                if (keyValue != 1) {
                    isOk = true; //审核通过
                }
            } else {
                //判断系统设置表  第三方不通过是否开启人工审核
                keyValue = systemSetupMapper.selectByKeyName("ngSwitch");
                if (keyValue != 1) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "信息包含敏感词语");
                }
            }
            //0:新发布  1:编辑发布
            if (type == 1) {
                Circle circle = circleMapper.selectById(circleId);
                circle.setAppId(appId);
                if (circle == null) {
                    return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_FAILURE, "不存在该文章");
                }
                //isOk  true 审核通过  false 审核中
                if (isOk) {
                    circle.setAuditResult((byte) 1);//检查结果(0:审核中 1:审核通过 2:审核不通过)
                    circle.setArticleStatus(false);//朋友圈状态(0:上架  1:下架)
                } else {
                    circle.setArticleStatus(true);//朋友圈状态(0:上架  1:下架)
                    circle.setAuditResult((byte) 0);//检查结果(0:审核中 1:审核通过 2:审核不通过)
                }
                circle.setUserId(userId);
                circle.setContent(content);
                if (fileType == 0) {
                    circle.setFileAddress(null);//文件地址List<String> : {"aaaaaaa","bbbbbbb","ccccccccc"}
                } else {
                    circle.setFileAddress(fileUrl);//文件地址List<String> : {"aaaaaaa","bbbbbbb","ccccccccc"}
                }

                circle.setIndustryId(industryId); //行业ID

                circle.setArticleType((byte) articleType);//文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)

                if (articleType == 1) {
                    circle.setRewardsUserNumber(userNumber);
                    circle.setSurplusRewardsNumber(userNumber);
                    circle.setRewardsNumber(new BigDecimal(number));

                    circle.setDeductionNumber(null);
                } else if (articleType == 2) {
                    circle.setDeductionNumber(new BigDecimal(number));

                    circle.setRewardsUserNumber(null);
                    circle.setSurplusRewardsNumber(null);
                    circle.setRewardsNumber(null);
                }
                circle.setArticleCategory(false);//文章类型(0:原文  1:转发)
                circle.setGmtModified(new Date());
                circle.setIsDeleted(false);
                circleMapper.updateByPrimaryKey(circle);

            } else {

                //过来文章内容是否包含敏感词?

                Circle circle = new Circle();
                //isOk  true 审核通过  false 审核中
                if (isOk) {
                    circle.setAuditResult((byte) 1);//检查结果(0:审核中 1:审核通过 2:审核不通过)
                    circle.setArticleStatus(false);//朋友圈状态(0:上架  1:下架)
                } else {
                    circle.setArticleStatus(true);//朋友圈状态(0:上架  1:下架)
                    circle.setAuditResult((byte) 0);//检查结果(0:审核中 1:审核通过 2:审核不通过)
                }
                circle.setUserId(userId);
                circle.setContent(content);
                if (fileType != 0) {
                    circle.setFileAddress(fileUrl);//文件地址List<String> : {"aaaaaaa","bbbbbbb","ccccccccc"}
                }
                circle.setIndustryId(industryId); //行业ID
                circle.setArticleType((byte) articleType);//文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)
                if (articleType == 1) {
                    circle.setRewardsUserNumber(userNumber);
                    circle.setSurplusRewardsNumber(userNumber);
                    circle.setRewardsNumber(new BigDecimal(number));
                } else if (articleType == 2) {
                    circle.setDeductionNumber(new BigDecimal(number));
                }
                circle.setArticleCategory(false);//文章类型(0:原文  1:转发)
                circle.setGmtModified(new Date());
                circle.setGmtCreate(new Date());
                circle.setIsDeleted(false);
                circleMapper.insertSelective(circle);
            }

            if (articleType == 1 && keyValue == 0) {
                //扣取作者的麦钻
                //文章作者钱包
                EosWallet eosWallet = eosWalletMapper.findOneByUserId(userId);
                //文章作者的扣取麦钻 =  总共要扣的钱
                BigDecimal wenzhang = new BigDecimal(number).multiply(new BigDecimal(userNumber));
                String key = decryptB(AESConstant.KEY, eosWallet.getActivePrivateKey());
                eosAccount.toOperateTransaction(userId, eosWallet.getWalletName(), wenzhang.setScale(4, BigDecimal.ROUND_DOWN).toString(), "发布文章扣除", key);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_FAILURE, "系统异常");
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 文章发布---文件上传
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> circleUploadImage(HttpServletRequest request) {
        try {
            //处理图片或者视频上传
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");
            List<String> pachList = new ArrayList<>();
            //循环获取file数组中得文件
            for (int i = 0; i < files.size(); ++i) {
                MultipartFile file = files.get(i);
                // 判断文件是否为空
                if (file != null && !file.isEmpty()) {
                    //调用第三方接口判断图片或者视频是否合格?
                    //百度API 校验图片
                    int resultNumb = ImageCensorSample(file);
                    //resultNumb，-1 合格 0 审核不通过 1：色情、 3：暴恐、4:恶心、 9：敏感词、10：自定义敏感词
                    switch (resultNumb) {
                        case 0:
                            return new ResultInfo<>(ResultInfo.ERROR, "图片审核失败");
                        case 1:
                            return new ResultInfo<>(ResultInfo.ERROR, "图片包含色情信息");
                        case 3:
                            return new ResultInfo<>(ResultInfo.ERROR, "图片包含暴恐信息");
                        case 4:
                            return new ResultInfo<>(ResultInfo.ERROR, "图片包含恶心信息");
                        case 9:
                            return new ResultInfo<>(ResultInfo.ERROR, "图片包含敏感词信息");
                       /* case 10:
                            return new ResultInfo<>(ResultInfo.ERROR, "图片审核失败");*/
                        default:
                            break;
                    }
                    String path = QiNiuUtils.pushFile(file, qiniuConfig);
                    if (!StringUtil.isEmpty(path)) {
                        pachList.add(path);
                    } else {
                        return new ResultInfo<>(ResultInfo.FAILURE, "上传失败");
                    }
                }
            }

            if (pachList == null || pachList.size() == 0) {
                return new ResultInfo<>(ResultInfo.FAILURE, "上传失败");
            }
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, pachList);
        } catch (Exception e) {
            return new ResultInfo<>(ResultInfo.FAILURE, "上传失败");
        }
    }

    /**
     * 百度云API 图片校验
     *
     * @param files
     * @return
     */
    public int ImageCensorSample(MultipartFile files) {
        if (client == null) {
            client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        }
        if (files == null) {
            return 0;
        }

        try {
            // 参数为本地图片文件二进制数组
            byte[] file = files.getBytes();
            JSONObject response = client.imageCensorUserDefined(file, null);

            BaiDuYunImageResponse baiDuYunImageResponse = JSON.parseObject(response.toString(), new TypeReference<BaiDuYunImageResponse>() {
            });


            if (baiDuYunImageResponse.getConclusionType() == 1) {
                return -1;
            }
            if (baiDuYunImageResponse.getConclusionType() != 4) {
                for (ImageResponseData imageResponseData : baiDuYunImageResponse.getData()) {
                    //审核类型，1：色情、2：性感、3：暴恐、4:恶心、5：水印码、6：二维码、7：条形码、8：政治人物、9：敏感词、10：自定义敏感词
                    if (imageResponseData.getType() == 1 || imageResponseData.getType() == 3 || imageResponseData.getType() == 4 ||
                            imageResponseData.getType() == 9 || imageResponseData.getType() == 10) {
                        return imageResponseData.getType();
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            System.out.println("图片校验失败");
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 文章编辑--获取文章详细信息
     *
     * @param map circleId:被转发文章ID
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public ResultInfo<?> getCircle(Map<String, Object> map) {
        try {

            //返回文章的信息
            Circle circle = circleMapper.selectById(Long.parseLong(map.get("circleId").toString()));
            if (circle == null) {
                return new ResultInfo<>(ResultInfo.FAILURE, "该文章不存在");
            }
            Map<String, Object> maps = new HashMap<>();
            if (Integer.parseInt(map.get("type").toString()) == 0) {
                maps.put("content", circle.getFeelings()); //转发文字感想
                maps.put("circleId", circle.getId()); //id
            } else {
                maps.put("circleId", circle.getId()); //id
                maps.put("content", circle.getContent()); //文字内容
                maps.put("industryId", circle.getIndustryId()); //行业类别id
                maps.put("rewardsUserNumber", circle.getRewardsUserNumber());//总共奖励用户数
                maps.put("rewardsNumber", circle.getRewardsNumber());//单个用户奖励数量
                maps.put("deductionNumber", circle.getDeductionNumber());//扣除阅读者用户麦钻数量
                maps.put("articleType", circle.getArticleType());//文章类别(0:默认  1:奖励用户麦钻  2:扣减用户麦钻)
                if (circle.getFileAddress() != null) {
                    String str[] = circle.getFileAddress().split(",");
                    maps.put("fileAddress", Arrays.asList(str)); // 文件地址
                }
            }


            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, maps);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
    }

    /**
     * 获取行业类型列表
     *
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public List<Map<Object, Object>> getIndustryTypeList() {
        return industryTypeMapper.getIndustryList();
    }


    /**
     * 获取用户行业列表
     */
    @Override
    public List<Map<Object, Object>> getUserIndustryList() {
        List<Map<Object, Object>> result = new ArrayList<Map<Object, Object>>();
        Map<Object, Object> map = new HashMap<>();
        map.put("id", 0);
        map.put("industryName", "全部");
        map.put("industryType", "全部");
        map.put("industryColor", "");
        map.put("parentId", 0);
        result.add(map);
        result.addAll(industryMapper.getUserIndustryList());
        return result;
    }


    /**
     * 用户的发布朋友圈列表
     */
    @Override
    @Transactional(readOnly=true)
    public List<CircleDisplayResponse> getUserCircleList(String userId, String searchContent, Integer page, Integer size) {
        if (page != 0) {
            page = size * page;
        }
        List<CircleDisplayResponse> list = circleMapper.findUserCircleList(Long.valueOf(userId), searchContent, page, size);


        for (CircleDisplayResponse circleDisplayResponse : list) {
            Long userIds = circleDisplayResponse.getUserId();
            Long circleId = circleDisplayResponse.getId();
            boolean isForwarding = circleDisplayResponse.getIsForwarding();

            //是否转发文章
            long readNum = 0;
            long readFriendNum = 0;
            boolean readResult = false;
            if (isForwarding) {
                Long originalId = circleDisplayResponse.getOriginalId();
                CircleDisplayResponse originalCircle = circleMapper.findOriginalCircle(originalId, Long.parseLong(userId));
                if (originalCircle != null) {
                    //文件地址转数组
                    String fileAddress = originalCircle.getFileAddress();
                    if (StringUtil.isNotEmpty(fileAddress)) {
                        String[] split = fileAddress.split(",");
                        if (split == null) {
                            String[] address = {fileAddress};
                            circleDisplayResponse.setImages(address);
                        } else {
                            //只显示三张图
                            int addressSize = split.length < 3 ? split.length : 3;
                            String[] address = new String[addressSize];
                            for (int i = 0; i < split.length; i++) {
                                if (i < 3) {
                                    address[i] = split[i];
                                }
                            }
                            circleDisplayResponse.setImages(address);
                        }
                    } else {
                        circleDisplayResponse.setImages(new String[]{});
                    }

                    circleDisplayResponse.setTagName(originalCircle.getTagName());
                    circleDisplayResponse.setContent(originalCircle.getContent());
                    circleDisplayResponse.setPrice(originalCircle.getPrice());
                    circleDisplayResponse.setForwarding(originalCircle.getForwarding());
                    circleDisplayResponse.setDiscuss(originalCircle.getDiscuss());
                    circleDisplayResponse.setGood(originalCircle.getGood());
                }

                //是否阅读
                long isRead = readingRecordMapper.selectIsRead(Long.parseLong(userId), originalId);
                readResult = false;
                if (isRead > 0) {
                    readResult = true;
                }

                //本月阅读数
                readNum = readingRecordMapper.selectReadCountByMonth(originalId);

                //阅读本文好友数
                //  readFriendNum = circleMapper.selectReadFriendNum(userIDList,originalId);
                //共同好友阅读该文章
                readFriendNum = readingRecordMapper.selectReadFriendNum(Long.valueOf(userId), originalId);
            } else {
                //文件地址转数组
                String fileAddress = circleDisplayResponse.getFileAddress();
                if (StringUtil.isNotEmpty(fileAddress)) {
                    String[] split = fileAddress.split(",");
                    if (split == null) {
                        String[] address = {fileAddress};
                        circleDisplayResponse.setImages(address);
                    } else {
                        //只显示三张图
                        int addressSize = split.length < 3 ? split.length : 3;
                        String[] address = new String[addressSize];
                        for (int i = 0; i < split.length; i++) {
                            if (i < 3) {
                                address[i] = split[i];
                            }
                        }
                        circleDisplayResponse.setImages(address);
                    }
                } else {
                    circleDisplayResponse.setImages(new String[]{});
                }

                //是否阅读
                long isRead = readingRecordMapper.selectIsRead(Long.parseLong(userId), circleId);
                readResult = false;
                if (isRead > 0) {
                    readResult = true;
                }

                //本月阅读数
                readNum = readingRecordMapper.selectReadCountByMonth(circleId);

                //阅读本文好友数
                //  readFriendNum = circleMapper.selectReadFriendNum(userIDList,originalId);
                //共同好友阅读该文章
                readFriendNum = readingRecordMapper.selectReadFriendNum(Long.valueOf(userId), circleId);
            }

            //用户等级
            int vipRank = getVipRank(userIds);

            circleDisplayResponse.setReadNum(readNum);
            circleDisplayResponse.setReaded(readResult);
            circleDisplayResponse.setReadFriendNum(readFriendNum);
            circleDisplayResponse.setUserLevel(vipRank);
        }

        return list;
    }


    /**
     * 获取大V排行榜
     */
    @Override
    @Transactional(readOnly=true)
    public Map<Object, Object> getRankingList(RankingListRequest entity) {
        Integer type = entity.getType();
        String userId = entity.getUserId();
        Integer page = entity.getPage();
        Integer size = entity.getSize();
        Long[] industryId = entity.getIndustryId();

        Map<Object, Object> map = new HashMap<>();
        if (type == null || userId == null) {
            map.put("status", 1);
            return map;
        }

        List<RankingResponse> list = null;
        if (industryId == null || industryId.length == 0) {
            industryId = null;
        }
        if (page != 0) {
            page = size * page;
        }
        if (type == 0 || type == 1) {                     //阅读数排行
            list = circleMapper.getReadRankingList(type, industryId, page, size);
        } else if (type == 2 || type == 3) {                //粉丝数排行
            list = circleMapper.getFansRankingList(Long.valueOf(userId), industryId, page, size);
        }

        if (list == null) {
            map.put("status", 0);
            map.put("list", new ArrayList<>());
            return map;
        }

        for (RankingResponse rankingResponse : list) {
            Long userIds = rankingResponse.getUserId();

            //用户等级
            int vipRank = getVipRank(userIds);

            //是否已关注
            long isFollow = followMapper.selectIsFollow(Long.valueOf(userId), userIds);
            boolean follow = false;
            if (isFollow > 0) {
                follow = true;
            }

            //发布文章总数
            long releaseTotal = circleMapper.selectReleaseTotal(userIds);

            //阅读总数
            long readCount = readingRecordMapper.selectReadCountAll(userIds);

            //粉丝总数
            long followCount = followMapper.selectFollowCountAll(userIds);

            //好友关注数
            long friendFollowCount = followMapper.selectFriendFollowNum(Long.valueOf(userId), userIds);

            rankingResponse.setReadCount(readCount);
            rankingResponse.setArticleCount(releaseTotal);
            rankingResponse.setAttentioned(follow);
            rankingResponse.setFansNum(followCount);
            rankingResponse.setFirendFansNum(friendFollowCount);
            rankingResponse.setLevel(vipRank);
        }

        map.put("status", 0);
        map.put("list", list);
        return map;
    }


    /**
     * 获取大V页面推荐用户列表-待完善
     */
    @Override
    @Transactional(readOnly=true)
    public PageResponse getRecommendUserList(String userId, Integer page, Integer size) {

        //从后台获取推荐数据---待后台功能完善
        return null;
    }


    /**
     * 删除朋友圈
     */
    @Override
    @Transactional
    public long deleteCircle(CircleRequest entity) {
        String circleId = entity.getCircleId();
        String userId = entity.getUserId();

        Circle circle = new Circle();
        circle.setUserId(Long.valueOf(userId));
        circle.setId(Long.valueOf(circleId));
        circle.setGmtModified(new Date());
        circle.setIsDeleted(true);
        int updateByPrimaryKeySelective = circleMapper.updateByPrimaryKeySelective(circle);
        if (updateByPrimaryKeySelective > 0) {
            return 0;
        }

        return -1;
    }


    /**
     * web管理界面--文章列表
     *
     * @param webCircleListRequest
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public ResultInfo<?> webGetCircleList(WebCircleListRequest webCircleListRequest) {
        List<Map<String, Object>> listData = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        int pageNum = 1;
        pageNum = webCircleListRequest.getPage();
        int pageSize = 0;
        pageSize = webCircleListRequest.getSize();
        int count = circleMapper.selectWebCircleListCount(webCircleListRequest); //总记录数
        int Pages, pageNums = 0;
        if (count % pageSize == 0) {
            Pages = count / pageSize;
        } else {
            Pages = count / pageSize + 1;
        }
        //如果传入的当前页比最大页大就返回空
        if (pageNum <= Pages) {
            if (pageNum > 1) {
                pageNums = (pageNum - 1) * pageSize;
            } else {
                pageNums = 0;
            }
            webCircleListRequest.setPage(pageNums);
            //查询用户
            list = circleMapper.selectWebCircleList(webCircleListRequest);
        }

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = new HashedMap();
                map.put("name", list.get(i).get("name"));//昵称
                map.put("nickName", list.get(i).get("nicKname"));//昵称
                map.put("mobileNumber", list.get(i).get("mobileNumber"));//手机号码
                //map.put("gmtModified",list.get(i).get("gmtModified"));//文章更新时间
                map.put("gmtModified", list.get(i).get("gmtModified").toString().substring(0, list.get(i).get("gmtModified").toString().lastIndexOf(".0")));
                if (list.get(i).get("content").toString().length() > 10) {
                    map.put("content", list.get(i).get("content").toString().substring(0, 11));// 文章内容
                } else {
                    map.put("content", list.get(i).get("content"));// 文章内容
                }

                map.put("totalReading", list.get(i).get("totalReading"));//总点赞数
                map.put("totalRetransmission", list.get(i).get("totalRetransmission"));// 总转发数


                map.put("auditStatus", list.get(i).get("auditStatus"));//审核状态
            /*    if((int)list.get(i).get("auditStatus") == 0 ){
                    map.put("auditStatus","审核中");//审核状态
                }else if((int)list.get(i).get("auditStatus") == 1 ){
                    map.put("auditStatus","审核通过");//审核状态
                }else{
                    map.put("auditStatus","审核不通过");//审核状态
                }*/

                if ((boolean) list.get(i).get("stateType")) {
                   /* map.put("stateType","上架");//上下线状态*/
                    map.put("stateType", 1);//上下线状态
                } else {
                    map.put("stateType", 2);//上下线状态
                  /*  map.put("stateType","下架");//上下线状态*/
                }
                map.put("circleId", list.get(i).get("circleId"));//文章ID

                map.put("reportType", list.get(i).get("reportType"));//举报状态

                listData.add(map);
            }
        }

        PageResponse pageResponse = new PageResponse();
        pageResponse.setItems(listData);
        pageResponse.setPageCount(listData.size());
        pageResponse.setPageNum(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(count);
        pageResponse.setTotalPages(Pages);

/*        mapData.put("page", pageNum);
        mapData.put("pageSize", webCircleListRequest.getSize());
        mapData.put("countPageSize", count);
        mapData.put("countPage", Pages);
        mapData.put("data", listData);*/
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, pageResponse);
    }


    /**
     * web管理界面--文章详情
     *
     * @param map
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public ResultInfo<?> getCircleDetails(WebCircleRequest map) {
/*        Circle circle = circleMapper.selectByPrimaryKey(map.getCircleId());
        if(circle == null ){
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "该文章不存在");
        }*/
        //返回改文章的信息
        Map<String, Object> maps = circleMapper.selectWebCircleDetails(map.getCircleId());
        String nickname = maps.get("nickname").toString();
        maps.put("name", maps.get("name"));
        maps.put("nickName", nickname);
        maps.remove("nickname");
        maps.put("gmtModified", maps.get("gmtModified").toString().substring(0, maps.get("gmtModified").toString().lastIndexOf(".0")));

        if ((Boolean) maps.get("stateType")) {
            maps.put("stateType", 1);
        } else {
            maps.put("stateType", 0);
        }

        if (maps.get("fileAddress") != null) {
            String str[] = maps.get("fileAddress").toString().split(",");
            maps.put("fileAddress", Arrays.asList(str));
        } else {
            maps.put("fileAddress", Arrays.asList());
        }
        //用户等级
        int vipRank = getVipRank(Long.parseLong(maps.get("userId").toString()));
        maps.put("vipRank", vipRank);

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, maps);
    }

    /**
     * 麦圈详情评论列表
     * pageNum     当前页码
     * pageSize    一页多少条
     *
     * @param map
     * @return
     * @date 2018年6月21日
     * @author lmd
     */
    @Override
    @Transactional(readOnly=true)
    public ResultInfo<?> webDetailsComment(webCircleCommentRequest map) {
        List<Map<String, Object>> listData = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        int pageNum = map.getPage();

        int pageSize = map.getSize();

        int count = discussMapper.selectByCircleIdCount(map.getCircleId()); //总记录数
        int Pages, pageNums = 0;
        if (count % pageSize == 0) {
            Pages = count / pageSize;
        } else {
            Pages = count / pageSize + 1;
        }
        //如果传入的当前页比最大页大就返回空
        if (pageNum <= Pages) {
            if (pageNum > 1) {
                pageNums = (pageNum - 1) * pageSize;
            } else {
                pageNums = 0;
            }
            //查询用户
            list = discussMapper.selectByCircleIdList(map.getCircleId(), pageNums, pageSize);
        }

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> dataMaps = new LinkedHashMap();
                dataMaps.put("avatar", list.get(i).get("avatar"));
                dataMaps.put("nickName", list.get(i).get("nickname"));
                dataMaps.put("name", list.get(i).get("name"));
                dataMaps.put("discussContent", list.get(i).get("discussContent"));
                dataMaps.put("uId", list.get(i).get("uId"));
                dataMaps.put("gmtModified", list.get(i).get("gmtModified").toString().substring(0, list.get(i).get("gmtModified").toString().lastIndexOf(".0")));
                dataMaps.put("vipRank", getVipRank(Long.parseLong(list.get(i).get("uId").toString())));
                listData.add(dataMaps);
            }
        }
        PageResponse pageResponse = new PageResponse();
        pageResponse.setItems(listData);
        pageResponse.setPageCount(listData.size());
        pageResponse.setPageNum(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(count);
        pageResponse.setTotalPages(Pages);

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, pageResponse);
    }


    /**
     * web管理界面--文章编辑
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> updateCircle(webCircleOperationRequest map) throws Exception {
        switch (map.getType()) {
            case 1:
                //删除
                //  结算是否还有未发完的麦钻 有：返还
                Circle circles = circleMapper.selectWebByPrimaryKey(map.getCircleId());
                if (circles == null) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "该文章不存在");
                }
                if (circles.getArticleType() == 1 && circles.getSurplusRewardsNumber() > 0) {
                    //文章作者可返现的麦钻 = 剩余奖励个数 * 单个用户奖励数量
                    BigDecimal bigDecimal = new BigDecimal(circles.getSurplusRewardsNumber()).multiply(circles.getRewardsNumber());
                    //文章作者的钱包
                    EosWallet wenz = eosWalletMapper.findOneByUserId(circles.getUserId());
                    eosAccount.fromTempTransaction(wenz.getWalletName(), bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toString(), "发布文章未被阅读返现");
                }
                circles.setArticleStatus(true);
                circles.setGmtModified(new Date());
                circles.setIsDeleted(true);
                circleMapper.updateByPrimaryKeySelective(circles);
                break;
            /*case 2:
                //上架
                //判断是否是审核通过的文章
                Circle c =circleMapper.selectByArticleStatus(Long.parseLong(map.get("circleId").toString()));
                if(c == null ){
                    return new ResultInfo<>(ResultInfo.FAILURE, "该文章不是审核通过状态不能上架");
                }
                c.setArticleStatus(false);
                c.setGmtModified(new Date());
                break;*/
            case 2:
                // 下架
                // 结算是否还有未发完的麦钻 有：返还
                Circle circlenew = circleMapper.selectByPrimaryKey(map.getCircleId());
                if (circlenew == null) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "该文章不存在或者不是上架状态");
                }
                if (circlenew.getArticleType() == 1 && circlenew.getSurplusRewardsNumber() > 0) {
                    //文章作者可返现的麦钻 = 剩余奖励个数 * 单个用户奖励数量
                    BigDecimal bigDecimal = new BigDecimal(circlenew.getSurplusRewardsNumber()).multiply(circlenew.getRewardsNumber());
                    //文章作者的钱包
                    EosWallet wenz = eosWalletMapper.findOneByUserId(circlenew.getUserId());
                    eosAccount.fromTempTransaction(wenz.getWalletName(), bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toString(), "发布文章未被阅读返现");
                }
                circlenew.setArticleStatus(true);
                circlenew.setGmtModified(new Date());
                circleMapper.updateByPrimaryKeySelective(circlenew);
                break;
            case 3:
                //审核不通过
                Circle cc = circleMapper.selectByArticle(map.getCircleId());
                if (cc == null) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "该文章不存在或者不是审核中状态");
                }
                if (cc.getArticleType() == 1 && cc.getSurplusRewardsNumber() > 0) {
                    //文章作者可返现的麦钻 = 剩余奖励个数 * 单个用户奖励数量
                    BigDecimal bigDecimal = new BigDecimal(cc.getSurplusRewardsNumber()).multiply(cc.getRewardsNumber());
                    //文章作者的钱包
                    EosWallet wenz = eosWalletMapper.findOneByUserId(cc.getUserId());
                    eosAccount.fromTempTransaction(wenz.getWalletName(), bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toString(), "发布文章未被阅读返现");
                }
                cc.setAuditResult((byte) 2);
                cc.setArticleStatus(true);
                cc.setGmtModified(new Date());
                circleMapper.updateByPrimaryKeySelective(cc);
                break;
            case 4:
                //审核通过--直接上线
                Circle circle = circleMapper.selectByArticle(map.getCircleId());
                if (circle == null) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "该文章不存在或者不是审核中状态");
                }
                circle.setAuditResult((byte) 1);
                circle.setArticleStatus(false);
                circle.setGmtModified(new Date());
                circleMapper.updateByPrimaryKeySelective(circle);
                break;
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * web管理界面--审核开关列表
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getSystemSetup() {
        //查询列表
        return systemSetupMapper.selectSystemSetup();
    }

    /**
     * web管理界面--更改审核开关
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public int updateSystemSetup(WebSystemSetupRequest map) {
        try {

            if (map.getKeyValue() != 1 && map.getKeyValue() != 0) {
                return -1;
            }
            SystemSetup systemSetup = new SystemSetup();
            systemSetup.setId(map.getId());
            systemSetup.setKeyvalue(map.getKeyValue());
            systemSetupMapper.updateByPrimaryKeySelective(systemSetup);
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }
}
