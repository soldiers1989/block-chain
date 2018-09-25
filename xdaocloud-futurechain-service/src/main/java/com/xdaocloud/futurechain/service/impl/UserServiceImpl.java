package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.base.utils.RedisUtils;
import com.xdaocloud.futurechain.feignapi.UserCenterService;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.common.QiniuConfig;
import com.xdaocloud.futurechain.constant.Constant;
import com.xdaocloud.futurechain.constant.EOSConstant;
import com.xdaocloud.futurechain.constant.OrdersConstant;
import com.xdaocloud.futurechain.constant.RewardTypeConstant;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.exchange.GetAchievementABRequest;
import com.xdaocloud.futurechain.dto.req.huanxin.Message;
import com.xdaocloud.futurechain.dto.req.huanxin.SendMessageRequest;
import com.xdaocloud.futurechain.dto.req.user.*;
import com.xdaocloud.futurechain.dto.resp.friend.GroupResponse;
import com.xdaocloud.futurechain.dto.resp.friend.InvitationFriendsResponse;
import com.xdaocloud.futurechain.dto.resp.orders.AchievementResponse;
import com.xdaocloud.futurechain.dto.resp.user.*;
import com.xdaocloud.futurechain.dto.user.AssetsDTO;
import com.xdaocloud.futurechain.dto.user.UserChildrenDTO;
import com.xdaocloud.futurechain.eos.EosAccount;
import com.xdaocloud.futurechain.httpapi.IMUserService;
import com.xdaocloud.futurechain.httpapi.ResultMsg;
import com.xdaocloud.futurechain.huanxin.HuanxinMessage;
import com.xdaocloud.futurechain.mapper.*;
import com.xdaocloud.futurechain.model.*;
import com.xdaocloud.futurechain.request.uc.*;
import com.xdaocloud.futurechain.response.uc.UCLoginResponse;
import com.xdaocloud.futurechain.response.uc.UCUserDetailResponse;
import com.xdaocloud.futurechain.response.uc.UCUserResponse;
import com.xdaocloud.futurechain.response.yunxin.CreateAccIdResponse;
import com.xdaocloud.futurechain.response.yunxin.RefreshTokenResponse;
import com.xdaocloud.futurechain.service.*;
import com.xdaocloud.futurechain.util.YunXinUtils;
import com.xdaocloud.futurechain.util.QiNiuUtils;
import com.xdaocloud.futurechain.util.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.xdaocloud.futurechain.constant.Constant.*;
import static com.xdaocloud.futurechain.constant.RewardTypeConstant.INVITATION;
import static com.xdaocloud.futurechain.constant.RewardTypeConstant.SIGN_IN;


/**
 * 用户管理
 *
 * @author LuoFuMin
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);


    @Value("${app.download.url}")
    private String appDownloadUrl;

    /**
     * 用户操作
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 发送短信服务类
     */
    @Autowired
    private SMSService smsService;

    /**
     * Redis 操作类
     */
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 平台邀请码
     */
    @Value("${platform.InviteCode}")
    private String platformInviteCode;

    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 七牛云储存配置
     */
    @Autowired
    private QiniuConfig qiniuConfig;

    /**
     * 矿石交易记录
     */
    @Autowired
    private OreTransactionMapper oreTransactionMapper;
    /**
     * 签到
     */
    @Autowired
    private SignInMapper signInMapper;

    /**
     * eos账户操作
     */
    @Autowired
    private EosAccount eosAccount;

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private SpecialUserMapper specialUserMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private AddFriendMapper addFriendMapper;

    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private EosTransactionMapper eosTransactionMapper;

    @Autowired
    private AppLoginRecordMapper appLoginRecordMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private FriendPhoneMapper friendPhoneMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private HuanxinMessage huanxinMessage;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IndustryMapper industryMapper;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private UserService userService;

    @Autowired
    private EosService eosService;

    @Autowired
    private BankCardMapper bankCardMapper;

    @Autowired
    private EOSConstant eosConstant;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private IMUserService imUserService;


    /**
     * 同步用户
     *
     * @param user
     * @return
     * @date 2018年8月28日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public Boolean syncUser(User user) {
        User selectUser = userMapper.selectByPrimaryKey(user.getId());
        if (selectUser != null) {
            LOG.info("》》》 mq 更新数据");
            return userMapper.updateByPrimaryKeySelective(user) > 0;
        } else {
            LOG.info("》》》 mq save数据");
            try {
                return userMapper.save(user) > 0;
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
                LOG.info("保存数据异常》》");
                return userMapper.updateByPrimaryKeySelective(user) > 0;
            } catch (Exception e) {
                e.printStackTrace();
                LOG.info(" Exception 保存数据异常》》");
                return false;
            }
        }
    }

    /**
     * 用户注册
     *
     * @param registerRequest
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ResultInfo<?> userRegister(RegisterRequest registerRequest, Long rootId, Long
            inviteUserId, HttpServletRequest httpServletRequest) throws Exception {
        LoginResponse loginResponse = null;
        User userInvite = null;
        String yxToken = "";
        String appId = httpServletRequest.getHeader(Constant.APPID_KEY);
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", registerRequest.getNickname());
        CreateAccIdResponse resultMsg = imUserService.createUser(YunXinUtils.getheaders(), registerRequest.getMobileNumber(), params).execute().body();
        LOG.info("》》》 创建云信账户 == " + resultMsg.getCode());
        if (resultMsg.getCode().intValue() != 200) {//判断是否注册成功
            if (resultMsg.getCode().intValue() == 414) {//判断是否是已注册，已注册刷新token
                RefreshTokenResponse refreshTokenResponse = imUserService.refreshToken(YunXinUtils.getheaders(), registerRequest.getMobileNumber()).execute().body();
                LOG.info("》》》 刷新云信账户 token code == " + refreshTokenResponse.getCode());
                if (refreshTokenResponse.getCode() == 200) {
                    yxToken = refreshTokenResponse.getInfo().getToken();
                } else {
                    return new ResultInfo<>(ResultInfo.FAILURE, refreshTokenResponse.getDesc());
                }
            }
        } else {
            yxToken = resultMsg.getInfo().getToken();
        }

        //请求用户中心
        UCRegisterRequest register = new UCRegisterRequest();
        register.setPhone(registerRequest.getMobileNumber());
        register.setNickname(registerRequest.getNickname());
        register.setPassword(registerRequest.getPassword());
        ResultInfo<UCUserResponse> userResponseResultInfo = userCenterService.userRegister(register);
        if (userResponseResultInfo.getCode().intValue() != 200) {
            return userResponseResultInfo;
        }
        Long id = userResponseResultInfo.getData().getId();
        //生成唯一邀请码
        String uuid = createInviteCode();
        //保存用户注册信息
        saveUserInfo(registerRequest, inviteUserId, rootId, uuid, id, userResponseResultInfo.getData().getUsername());
        //邀请好友获取奖励
        if (userInvite != null) {
            sendReward(userInvite);
        }
        //登录
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMobileNumber(registerRequest.getMobileNumber());
        loginRequest.setPassword(registerRequest.getPassword());
        loginRequest.setAppVersion(registerRequest.getAppVersion());
        loginRequest.setMac(registerRequest.getMac());
        loginRequest.setPhoneCompany(registerRequest.getPhoneCompany());
        loginRequest.setPhoneModel(registerRequest.getPhoneModel());
        loginRequest.setPhoneSystemVersion(registerRequest.getPhoneSystemVersion());
        loginRequest.setSpbillCreateIp(registerRequest.getSpbillCreateIp());
        loginResponse = (LoginResponse) userLogin(loginRequest, httpServletRequest).getData();
        loginResponse.setYxToken(yxToken);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, loginResponse);
    }


    /**
     * 邀请好友获取奖励
     *
     * @param userInvite
     */

    private void sendReward(User userInvite) throws Exception {
        EosWallet eosWallet = eosWalletMapper.findOneByParam(new EosWallet(userInvite.getId()));
        if (eosWallet != null) {
            //查询邀请好友奖励
            Reward reward = rewardMapper.findOneByType(2);
            if (reward != null) {
                //eosAccount.sysTransaction(eosWallet.getWalletName(), String.valueOf(reward.getEosAmount()), INVITATION);
                eosService.saveTransaction((long) 0, eosConstant.maioperate, eosWallet.getWalletName(), reward.getEosAmount().toString(), RewardTypeConstant.INVITATION_LOCK_POSITION, 7);
                userMapper.addOreByUserid(userInvite.getId(), reward.getOreAmount());
                oreTransactionMapper.insertSelective(new OreTransaction(userInvite.getId(), reward.getOreAmount(), INVITATION));
            }
        }
    }


    /**
     * 生成唯一邀请码
     *
     * @return String
     */
    private String createInviteCode() {
        String uuid;
        User findUser;
        do {
            uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4).toUpperCase();
            findUser = userMapper.findByInviteCode(uuid);
        } while (findUser != null);
        return uuid;
    }


    /**
     * 保存用户注册信息
     *
     * @param registerRequest       注册参数
     * @param registerInviteUserId  邀请注册用户id
     * @param friendChainRootUserId 朋友圈跟用户id
     * @param uuid                  自己的邀请码
     * @param id                    用户中心id
     * @param username              用户名
     * @return void
     * @author LuoFuMin
     * @date 2018/8/21
     */

    private void saveUserInfo(RegisterRequest registerRequest, Long registerInviteUserId, Long friendChainRootUserId, String uuid, Long id, String username) {
        User saveUser = new User();
        saveUser.setId(id);
        saveUser.setRegisterInviteCode(registerRequest.getInviteCode());
        saveUser.setMobileNumber(registerRequest.getMobileNumber());
        saveUser.setUsername(username);
        saveUser.setRegisterInviteUserId(registerInviteUserId);
        saveUser.setFriendChainRootUserId(friendChainRootUserId);
        saveUser.setInviteCode(uuid);
        saveUser.setPasswordMd5(passwordEncoder.encode(registerRequest.getPassword()));
        //注册送一百麦粒
        saveUser.setOre(Long.valueOf(100));
        saveUser.setNickname(registerRequest.getNickname());
        saveUser.setRegisterStatus(true);
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            try {
                userMapper.insertSelective(saveUser);
            } catch (DuplicateKeyException e) {
                //e.printStackTrace();
                userMapper.updateByPrimaryKeySelective(saveUser);
            }
        } else {
            userMapper.updateByPrimaryKeySelective(saveUser);
        }
        //保存麦粒记录
        addOreTransactionRecord(saveUser.getId(), (long) 100, "注册奖励");
    }


    /**
     * 根据手机号码查询用户信息
     *
     * @param mobileMumber 手机号码
     * @return ResultInfo
     * @throws Exception
     * @date 2018年3月2日
     * @author LuoFuMIn
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findByMobileMumber(String mobileMumber) throws Exception {
        User user = userMapper.findByMobileMumber(mobileMumber);
        if (user != null) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, user);
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_DATA_NOT_FOUND);
    }


    /**
     * 用户登录
     *
     * @param loginRequest
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ResultInfo<?> userLogin(LoginRequest loginRequest, HttpServletRequest httpServletRequest) throws Exception {
        ResultInfo<?> x = passWordCheck(loginRequest.getMobileNumber());
        if (x != null) {
            return x;
        }
        ResultInfo<UCLoginResponse> resultInfo = userCenterService.login(new UCLoginRequest(loginRequest.getMobileNumber(), loginRequest.getPassword()));
        if (resultInfo.getCode().intValue() != 200) {
            //保存错误登录记录
            saveErrorLoginCount(loginRequest.getMobileNumber());
            return resultInfo;
        }
        String token = resultInfo.getData().getUserToken();
        String phone = resultInfo.getData().getPhone();
        String avatar = resultInfo.getData().getAvatar();
        String name = resultInfo.getData().getName();
        String idcard = resultInfo.getData().getIdcard();
        String nickname = resultInfo.getData().getNickname();
        String username = resultInfo.getData().getUsername();

        RefreshTokenResponse refreshTokenResponse = imUserService.refreshToken(YunXinUtils.getheaders(), loginRequest.getMobileNumber()).execute().body();
        LOG.info("》》》 刷新云信账户 token code == " + refreshTokenResponse.getCode());
        String yxToken = "";
        if (refreshTokenResponse.getCode().intValue() != 200) {
            if (refreshTokenResponse.getCode().intValue() == 414 && refreshTokenResponse.getDesc().contains("not register")) {//如果不存在就创建新账户
                Map<String, Object> params = new HashMap<>();
                params.put("name", nickname);
                CreateAccIdResponse createAccIdResponse = imUserService.createUser(YunXinUtils.getheaders(), phone, params).execute().body();
                LOG.info("》》》 创建云信账户 == " + createAccIdResponse.getCode());
                LOG.info("》》》 accid == " + createAccIdResponse.getInfo().getAccid());
                if (createAccIdResponse.getCode().intValue() != 200) {
                    return new ResultInfo<>(ResultInfo.FAILURE, createAccIdResponse.getDesc());
                }
                LOG.info("》》》 创建云信账户 == " + createAccIdResponse.getInfo().getToken());
                yxToken = createAccIdResponse.getInfo().getToken();
            }
        } else {
            yxToken = refreshTokenResponse.getInfo().getToken();
        }
        LOG.info("》》》 刷新云信账户 token == " + yxToken);
        //登录成功设置redis存登录错次数为0
        stringRedisTemplate.opsForValue().set(LOGIN_ERROR_KEY_COUNT + loginRequest.getMobileNumber(), String.valueOf(0));
        User user = userMapper.findByMobileMumber(phone);
        //保存登录记录
        saveLoginRecord(loginRequest, user.getId());
        LoginResponse response = new LoginResponse();

        //查询eos钱包是否存在
        EosWallet eosWallet = new EosWallet();
        eosWallet.setUserId(user.getId());
        eosWallet.setIsDeleted(false);
        Integer countEosWallet = eosWalletMapper.findCountByParam(eosWallet);
        response.setUserid(user.getId());
        response.setUsername(username);
        response.setToken(token);
        response.setNickname(nickname);
        response.setAvatar(avatar);
        response.setMobileNumber(phone);
        response.setName(name);
        response.setIdcard(idcard);
        response.setInviteCode(user.getInviteCode());
        response.setYxToken(yxToken);
        if (countEosWallet > 0) {
            response.setExistEosWallet(true);
        } else {
            response.setExistEosWallet(false);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, response);
    }

    /**
     * 保存错误登录记录
     *
     * @param phone 手机号码
     */
    private void saveErrorLoginCount(String phone) {
        String errorCountStr = stringRedisTemplate.opsForValue().get(LOGIN_ERROR_KEY_COUNT + phone);
        if (StringUtils.isBlank(errorCountStr)) {
            errorCountStr = "0";
        }
        LOG.info(">>> strError == " + errorCountStr);
        Integer errorCount = Integer.valueOf(errorCountStr) + 1;
        stringRedisTemplate.opsForValue().set(LOGIN_ERROR_KEY_COUNT + phone, String.valueOf(errorCount));
        if (errorCount < 5) {
            stringRedisTemplate.opsForValue().set(LOGIN_ERROR_KEY + phone, String.valueOf(errorCount), 3, TimeUnit.MINUTES);
        }
        if (errorCount == 5) {
            stringRedisTemplate.opsForValue().set(LOGIN_ERROR_KEY + phone, String.valueOf(errorCount), 1, TimeUnit.MINUTES);
        }
        if (errorCount == 6) {
            stringRedisTemplate.opsForValue().set(LOGIN_ERROR_KEY + phone, String.valueOf(errorCount), 5, TimeUnit.MINUTES);
        }
        if (errorCount == 7) {
            stringRedisTemplate.opsForValue().set(LOGIN_ERROR_KEY + phone, String.valueOf(errorCount), 15, TimeUnit.MINUTES);
        }
        if (errorCount > 7) {
            stringRedisTemplate.opsForValue().set(LOGIN_ERROR_KEY + phone, String.valueOf(errorCount), 1, TimeUnit.HOURS);
        }
    }

    /**
     * 检查密码
     *
     * @param phone 手机号码
     * @return
     */
    private ResultInfo<?> passWordCheck(String phone) {
        String errorCount = stringRedisTemplate.opsForValue().get(LOGIN_ERROR_KEY + phone);
        LOG.info("》》》 errorCount = " + errorCount);
        if (!StringUtils.isEmpty(errorCount)) {
            Integer i = Integer.parseInt(errorCount);
            if (i < 5) {
                return null;
            }
            if (i == 5) {
                return new ResultInfo<>(ResultInfo.FAILURE, "密码错误次数过多，1分钟后再尝试");
            }
            if (i == 6) {
                return new ResultInfo<>(ResultInfo.FAILURE, "密码错误次数过多，5分钟后再尝试");
            }
            if (i == 7) {
                return new ResultInfo<>(ResultInfo.FAILURE, "密码错误次数过多，15分钟后再尝试");
            }
            if (i > 7) {
                return new ResultInfo<>(ResultInfo.FAILURE, "密码错误次数过多，1小时后再尝试");
            }
        }
        return null;
    }

    /**
     * 保存登录记录
     *
     * @param loginRequest
     * @param userId
     */
    private void saveLoginRecord(LoginRequest loginRequest, Long userId) {
        AppLoginRecord appLoginRecord = new AppLoginRecord();
        appLoginRecord.setSpbillCreateIp(loginRequest.getSpbillCreateIp());
        appLoginRecord.setUserId(userId);
        appLoginRecord.setMac(loginRequest.getMac());
        appLoginRecord.setAppVersion(loginRequest.getAppVersion());
        appLoginRecord.setPhoneModel(loginRequest.getPhoneModel());
        appLoginRecord.setPhoneSystemVersion(loginRequest.getPhoneSystemVersion());
        appLoginRecord.setPhoneCompany(loginRequest.getPhoneCompany());
        appLoginRecordMapper.insertSelective(appLoginRecord);
    }


    /**
     * 获取用户信息
     *
     * @param userid
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public UserMsgResponse getUserMsg(Long userid, HttpServletRequest httpServletRequest) throws Exception {
        ResultInfo<UCUserDetailResponse> resultInfo = userCenterService.getUserInfo(userid);
        if (resultInfo.getCode().intValue() != 200) {
            return null;
        }
        String phone = resultInfo.getData().getPhone();
        String name = resultInfo.getData().getName();
        String username = resultInfo.getData().getUsername();
        String avatar = resultInfo.getData().getAvatar();
        String email = resultInfo.getData().getEmail();
        String nickname = resultInfo.getData().getNickname();
        String birthday = resultInfo.getData().getBirthday();
        String sex = resultInfo.getData().getSex();

        User user = userMapper.selectByPrimaryKey(userid);
        if (user == null) {
            return null;
        }
        Boolean signIn = signInMapper.todaySignIn(userid);
        Integer friendCount = friendMapper.countByUserIdOfMyFriend(userid, userid);
        int followCount = followMapper.selectByUserIdAndPassiveUserId(userid, 0L);
        int fans = followMapper.selectByUserIdAndPassiveUserId(0L, userid);

        Industry industry = null;
        if (user.getProfession() != null) {
            industry = industryMapper.selectByPrimaryKey(Long.valueOf(user.getProfession()));
        }

        String eosWalletName = eosWalletMapper.findWalletNameByUserId(user.getId());


        Address address = addressMapper.findDefault(userid);
        StringBuffer addressbf = new StringBuffer();
        if (address != null) {
            addressbf.append(address.getProvince());
            addressbf.append(address.getCity());
            addressbf.append(address.getCounty());
            addressbf.append(address.getDetailed());
        }

        UserMsgResponse userMsgResponse = new UserMsgResponse();
        userMsgResponse.setBirthday(resultInfo.getData().getBirthday());
        userMsgResponse.setSignIn(signIn);
        userMsgResponse.setFriend(friendCount);
        userMsgResponse.setFollow(followCount); //关注数量
        userMsgResponse.setFans(fans); //粉丝数量
        userMsgResponse.setAvatar(avatar);
        userMsgResponse.setBirthday(birthday);
        userMsgResponse.setEmail(email);
        userMsgResponse.setId(user.getId());
        userMsgResponse.setIdcard(user.getIdcard());
        userMsgResponse.setName(name);
        userMsgResponse.setUsername(username);
        userMsgResponse.setMobileNumber(phone);
        userMsgResponse.setNickname(nickname);
        userMsgResponse.setInviteCode(user.getInviteCode());
        userMsgResponse.setSignature(user.getSignature());
        userMsgResponse.setSex(sex);
        userMsgResponse.setProfession(user.getProfession());
        if (industry != null) {
            userMsgResponse.setProfessionName(industry.getIndustryType());
        }
        userMsgResponse.setAddress(addressbf.toString());
        userMsgResponse.setOre(user.getOre());
        userMsgResponse.setEosWalletName(eosWalletName);
        userMsgResponse.setAgent(user.getAgent());
        userMsgResponse.setVipRank(getVipRank(userid));
        return userMsgResponse;
    }

    public int getVipRank(Long userid) {
        //查询充值总值
        BigDecimal feeCount = ordersMapper.selectSumFee(userid);
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
     * 修改用户信息
     *
     * @return
     * @throws Exception
     * @date 2018年3月7日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> UpdateUserInfo(UpdateUserInfoRequest request, HttpServletRequest httpServletRequest) throws Exception {
        if (StringUtils.isNoneBlank(request.getSignature())) {
            userMapper.updateSignature(Long.parseLong(request.getUserid()), request.getSignature());
        }
        if (StringUtils.isNoneBlank(request.getIndustryId())) {
            userMapper.updateIndustry(Long.parseLong(request.getUserid()), request.getIndustryId());
        }
        UCUpdateUserRequest ucUpdateUserRequest = new UCUpdateUserRequest();
        ucUpdateUserRequest.setId(Long.valueOf(request.getUserid()));
        ucUpdateUserRequest.setIdcard(request.getIdcard());
        ucUpdateUserRequest.setName(request.getName());
        ucUpdateUserRequest.setNickname(request.getNickname());
        ucUpdateUserRequest.setUsername(request.getUsername());
        ucUpdateUserRequest.setBirthday(request.getBirthday());
        ucUpdateUserRequest.setSex(request.getSex());
        ResultInfo<?> resultInfo = userCenterService.profile(ucUpdateUserRequest);

        String phone = userMapper.findPhoneById(Long.valueOf(request.getUserid()));
        Map<String, Object> prams = new HashMap<>();
        if (StringUtils.isNotEmpty(request.getNickname())) {
            prams.put("name", request.getNickname());
        }
        if (StringUtils.isNotEmpty(request.getSignature())) {
            prams.put("sign", request.getSignature());
        }
        if (StringUtils.isNotEmpty(request.getSex())) {
            if ("男".equals(request.getSex())) {
                prams.put("gender", 1);
            }
            if ("女".equals(request.getSex())) {
                prams.put("gender", 2);
            }
        }
        if (request.getBirthday() != null) {
            prams.put("birth", request.getBirthday());
        }
        ResultMsg resultMsg = imUserService.updateUinfo(YunXinUtils.getheaders(), phone, prams).execute().body();
        LOG.info("》》》  resultMsg ==" + resultMsg.getCode());
        return resultInfo;
    }

    /**
     * 上传头像
     *
     * @param multipartFile 上传文件
     * @param userid
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ResultInfo<?> avatarUpload(MultipartFile multipartFile, Long userid, HttpServletRequest httpServletRequest) throws Exception {
        String path = QiNiuUtils.pushFile(multipartFile, qiniuConfig);
        if (path == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        ResultInfo<?> resultInfo = userCenterService.pushAvatar(new UCPushAvatarRequest(userid, path));
        if (resultInfo.getCode().intValue() != 200) {
            return resultInfo;
        }
        String phone = userMapper.findPhoneById(userid);
        Map<String, Object> prams = new HashMap<>();
        prams.put("icon", path);
        ResultMsg resultMsg = imUserService.updateUinfo(YunXinUtils.getheaders(), phone, prams).execute().body();
        LOG.info("》》》 resultMsg ==" + resultMsg.getCode());
        Map<String, String> map = new HashMap<>(1);
        map.put("avatar", path);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
    }

    /**
     * 获取用户资产
     *
     * @param userid 用户id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public PropertyResponse findUserProperty(Long userid) throws Exception {
        Long ore = userMapper.findOreByUserId(userid);
        String walletName = eosWalletMapper.findWalletNameByUserId(userid);
        String balance = "0";
        if (StringUtils.isNoneBlank(walletName)) {
            balance = eosAccount.getBalance(walletName);
        }
        Product product = productMapper.findByProductCode(OrdersConstant.MAIZUAN);
        BigDecimal lock_balance = exchangeService.findlockEosBalance(userid);
        LOG.info("==balance==" + balance);
        BigDecimal sum = lock_balance.add(new BigDecimal(balance));
        PropertyResponse propertyResponse = new PropertyResponse(String.valueOf(ore), balance, product.getPrice().toString(), lock_balance.toString(), sum.toString());
        return propertyResponse;
    }


    /**
     * 获得用户的邀请码等参数
     *
     * @param userid 用户ID
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getInviteCodeInfo(Long userid) throws Exception {
        Map<String, Object> infoMap = new HashMap<>();
        // 用户的邀请码
        User user = userMapper.selectByPrimaryKey(userid);
        if (null == user || user.getIsDeleted() == true || user.getRegisterStatus() == false) {
            return new ResultInfo<>(ResultInfo.NOT_FOUND, ResultInfo.MSG_NOT_FOUND);
        }
        infoMap.put("inviteCode", user.getInviteCode());
        // 用户已邀请注册的用户数
        int invitedUserCount = userMapper.selectInvitedUserCount(userid);
        // 已邀请用户不为0时，按数据库设计，忆邀请用户数包括了用户自己，所以应减1
        infoMap.put("invitedCount", invitedUserCount == 0 ? 0 : invitedUserCount - 1);
        // 应用的下载地址
        infoMap.put("appUrl", appDownloadUrl);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, infoMap);
    }


    /**
     * 一键签到
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> clickSignIn(UserIdRequest request) throws Exception {
        Long userid = Long.valueOf(request.getUserid());
        if (!signInMapper.todaySignIn(userid)) {
            SignIn signIn = signInMapper.findLastByUserId(userid);
            if (signIn == null) {
                signInMapper.insertSelective(new SignIn(userid, 1, 1));
                sigin(userid, 1);
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
            } else {
                if (signIn.getContinuity() == null) {
                    signIn.setContinuity(0);
                }
                //自动 +1
                signIn.setContinuity(signIn.getContinuity() + 1);
                //获取最新一条记录时间，加一天
                Date date = DateUtils.addDays(DateUtils.parseDate(signIn.getGmtCreate(), TIME_BASE), 1);
                //判断是否是同一天
                boolean bool = DateUtils.isSameDay(new Date(), date);
                LOG.info("==连续签到天数==" + signIn.getContinuity());
                if (bool) {
                    //连续签到7天奖励11个麦粒
                    if (signIn.getContinuity() % 7 == 0) {
                        sigin(userid, 11);
                        signInMapper.insertSelective(new SignIn(userid, signIn.getContinuity(), 11));
                    } else {
                        sigin(userid, 1);
                        signInMapper.insertSelective(new SignIn(userid, signIn.getContinuity(), 1));
                    }
                } else {
                    sigin(userid, 1);
                    //连续签到断开，设置为 “1”
                    signInMapper.insertSelective(new SignIn(userid, 1, 1));
                }
                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
            }
        }
        return new ResultInfo<>(ResultInfo.FAILURE, "请不要重复签到");
    }

    private void sigin(Long userId, int reward) {
        userMapper.addOreByUserid(userId, (long) reward);
        addOreTransactionRecord(userId, (long) reward, SIGN_IN);
    }


    /**
     * 查询全部用户排行
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public UserOreRankingResponse findUserOreList(Page_Request request) throws Exception {
        Integer page = 0;
        if (request.getPage() != 0) {
            page = request.getSize() * request.getPage();
        }
        List<UserOreResponse> list = userMapper.findUOreRankingAll(page, request.getSize());
        UserOreRankingResponse userOreRankingResponse = userMapper.findOreRankingByUserId(Long.valueOf(request.getUserid()));
        userOreRankingResponse.setUserOreResponseList(list);
        return userOreRankingResponse;
    }


    /**
     * 查询朋友圈用户排行
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findOreRankingFriends(Page_Request request) throws Exception {
        Long userId = Long.valueOf(request.getUserid());
        //主动好友集合
        List<Long> friendsPassivity = friendMapper.findFriendsByFriendId(userId);
        //被动好友集合
        List<Long> friendsInitiative = friendMapper.findFriendsByUserId(userId);
        friendsPassivity.addAll(friendsInitiative);
        friendsPassivity.add(userId);
        List<UserOreResponse> userOreResponseList = null;
        if (!friendsPassivity.isEmpty()) {
            Integer page = 0;
            if (request.getPage() != 0) {
                page = request.getSize() * request.getPage();
            }
            userOreResponseList = userMapper.findOreRankingFriends(friendsPassivity, page, request.getSize());
        }
        UserOreRankingResponse userOreRankingResponse = userMapper.findOreRankingInFriendsByUserId(userId, friendsPassivity);
        userOreRankingResponse.setUserOreResponseList(userOreResponseList);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userOreRankingResponse);
    }


    /**
     * 根据用户id 查询群成员
     *
     * @param userid 用户id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> findGroupByUserId(Long userid) throws Exception {
        List<GroupResponse> list = userMapper.findGroupByUserId(userid);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 麦粒收支明细
     *
     * @param page_request
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getHxlTransaction(Page_Request page_request) throws Exception {
        PageHelper.startPage(page_request.getPage(), page_request.getSize(), "id DESC");
        List<OreTransaction> list = oreTransactionMapper.findByUserId(Long.valueOf(page_request.getUserid()));
        PageInfo<OreTransaction> pageInfo = new PageInfo<OreTransaction>(list);
        PageResponse response = new PageResponse(pageInfo);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, response);
    }

    /**
     * 找回密码
     *
     * @param backPassRequest
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> backPassWord(GetBackPassRequest backPassRequest, HttpServletRequest httpServletRequest) {
        Object object = redisUtils.get(SMS_BACK_PASS_KEY + backPassRequest.getMobileNumber());
        LOG.info("======修改密码验证码====" + object);
        if (object == null || !backPassRequest.getSmsCode().equalsIgnoreCase(String.valueOf(object))) {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码错误");
        }
        User user = userMapper.findByMobileMumber(backPassRequest.getMobileNumber());
        UCResetUserPwdRequest ucResetUserPwdRequest = new UCResetUserPwdRequest();
        ucResetUserPwdRequest.setIds(new Long[]{user.getId()});
        ucResetUserPwdRequest.setPassword(backPassRequest.getNewPass());
        return userCenterService.resetpwd(ucResetUserPwdRequest);
    }

    /**
     * 修改密码
     *
     * @param changePassWord
     * @param httpServletRequest
     * @return
     */
    @Override
    public ResultInfo<?> changePassWord(ChangePassWordRequest changePassWord, HttpServletRequest httpServletRequest) {
        UCUpdatePwdRequest ucUpdatePwdRequest = new UCUpdatePwdRequest();
        ucUpdatePwdRequest.setId(Long.valueOf(changePassWord.getUserid()));
        ucUpdatePwdRequest.setPassword(changePassWord.getNewPass());
        ucUpdatePwdRequest.setOldPassword(changePassWord.getOldPass());
        return userCenterService.pwd(ucUpdatePwdRequest);
    }


    /**
     * 根据id查询用户
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public User findUserByUserId(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }


    /**
     * 查询邀请的好友
     *
     * @param inviteCodeRequest
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public InvitationFriendsResponse getInvitationFriends(InviteCodeRequest inviteCodeRequest) {
        User user = new User();
        user.setRegisterInviteCode(inviteCodeRequest.getInviteCode());
        user.setRegisterStatus(true);
        //查询邀请好友总数
        int count = userMapper.findCountByParam(user);
        //查询邀请好友列表
        Integer page = 0;
        if (inviteCodeRequest.getPage() != 0) {
            page = inviteCodeRequest.getSize() * inviteCodeRequest.getPage() + 1;
        }
        User user1 = userMapper.findByInviteCode(inviteCodeRequest.getInviteCode());
        List<UserInfoResponse> list = userMapper.getInvitationFriends(user1.getId(), inviteCodeRequest.getInviteCode(), page, inviteCodeRequest.getSize());
        for (UserInfoResponse userInfo : list) {
            AddFriend addFriend = addFriendMapper.findOneByUserIdOrFriendId(user1.getId(), Long.valueOf(userInfo.getUserid()));
            if (addFriend != null && userInfo.getIs_friend() != 1) {
                userInfo.setIs_friend(2);
            }
        }
        return new InvitationFriendsResponse(count, list);
    }

    /**
     * 修改交易密码
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public ResultInfo<?> changeTransactionPassWord(BackTransactionPassWordRequest request) {

        User findUser = userMapper.selectByPrimaryKey(Long.valueOf(request.getUserid()));
        if (findUser == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        Boolean bool = smsService.checkSMS_Code(findUser.getMobileNumber(), request.getSmsCode(), SMS_BACK_TRANSACTION_PASS_KEY);
        if (bool) {
            User user = new User();
            user.setId(findUser.getId());
            user.setTransactionPassword(passwordEncoder.encode(request.getNewPass()));
            userMapper.updateByPrimaryKeySelective(user);
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, "验证码错误");
        }
    }


    /**
     * 添加 矿石记录
     *
     * @param userid 用户id
     * @param amount 数量
     * @param way    方式
     */
    private void addOreTransactionRecord(Long userid, Long amount, String way) {
        OreTransaction oreTransaction = new OreTransaction(userid, amount, way);
        int i = oreTransactionMapper.insertSelective(oreTransaction);
    }


    /**
     * 查询可以邀请的通讯录列表
     *
     * @param map
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> getInvitationList(Map<String, Object> map) {
        List<Map<String, Object>> mobileListS = new ArrayList<>();
        try {
            if (StringUtil.isEmpty(map.get("userId").toString())) {
                new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
            }
            if (StringUtil.isEmpty(map.get("mobileList").toString())) {
                new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
            }
            List<Map<String, Object>> mobileList = (List<Map<String, Object>>) map.get("mobileList");

            //判断类型 1 获取可邀请列表  2 获取可加好友列表  Number
            if ((int) map.get("type") == 1) {
                List<String> list = friendPhoneMapper.selectByPhoneList(mobileList);
                if (list != null) {
                    for (int j = 0; j < mobileList.size(); j++) {
                        for (String mob : list) {
                            if (mob.equals(mobileList.get(j).get("phone").toString())) {
                                mobileList.get(j).put("isInvitation", true);
                            } else {
                                if (mobileList.get(j).get("isInvitation") == null || !(Boolean) mobileList.get(j).get("isInvitation")) {
                                    mobileList.get(j).put("isInvitation", false);
                                }
                            }
                        }
                    }
                }

                if (mobileList != null) {
                    //判断是否注册
                    List<String> userlist = userMapper.selectByPhoneList(mobileList);
                    if (userlist != null) {
                        for (int j = 0; j < mobileList.size(); j++) {
                            boolean isExist = false;
                            for (int i = 0; i < userlist.size(); i++) {
                                if (userlist.get(i).equals(mobileList.get(j).get("phone").toString())) {
                                    isExist = true;
                                }
                            }
                            if (!isExist) {
                                Map<String, Object> map1 = new HashMap<>();
                                map1.put("phone", mobileList.get(j).get("phone"));
                                map1.put("name", mobileList.get(j).get("name"));
                                map1.put("isInvitation", mobileList.get(j).get("isInvitation"));
                                mobileListS.add(map1);
                            }
                        }
                    }
                }

                if (mobileListS != null) {
                    for (int j = 0; j < mobileListS.size(); j++) {
                        if (mobileListS.get(j).get("isInvitation") == null) {
                            mobileListS.get(j).put("isInvitation", false);
                        }
                    }
                }

                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, mobileListS);
            } else {

                mobileList = removeDuplicate(mobileList);

                //判断是否是好友
                List<Map<String, Object>> userlist = userMapper.selectByPhoneUserList(mobileList, Long.parseLong(map.get("userId").toString()));

                if (userlist != null && userlist.size() > 0) {
                    for (int j = 0; j < mobileList.size(); j++) {
                        for (int i = 0; i < userlist.size(); i++) {
                            if (userlist.get(i).get("mobileNumber").equals(mobileList.get(j).get("phone").toString())) {
                                Map<String, Object> map1 = new HashMap<>();
                                map1.put("phone", mobileList.get(j).get("phone"));
                                map1.put("name", mobileList.get(j).get("name"));
                                map1.put("inviteCode", userlist.get(i).get("inviteCode"));
                                map1.put("isFriend", userlist.get(i).get("isFriend"));
                                map1.put("avatar", userlist.get(i).get("avatar"));
                                map1.put("isAddFriend", userlist.get(i).get("isAddFriend"));
                                mobileListS.add(map1);
                            }
                        }

                    }
                }

                return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, mobileListS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
    }

    public static List removeDuplicate(List<Map<String, Object>> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).get("phone").equals(list.get(i).get("phone"))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 根据邀请码添加好友
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> invitationAddFriends(Map<String, Object> map) {

        if (StringUtil.isEmpty(map.get("userId").toString())) {
            new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }
        Long userId = Long.parseLong(map.get("userId").toString());
        if (StringUtil.isEmpty(map.get("codeList").toString())) {
            new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }
        if (StringUtil.isEmpty(map.get("remark").toString())) {
            new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }
        if (StringUtil.isEmpty(map.get("type").toString())) {
            new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }


        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }
        List<String> codeList = (List<String>) map.get("codeList");
        if (codeList == null && codeList.size() == 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
        }
        for (String code : codeList) {
            User friendUser = null;
            if ("inviteCode".equals(map.get("type").toString())) {
                friendUser = userMapper.findByInviteCode(code);
            } else {
                friendUser = userMapper.findByMobileMumber(code);
            }

            if (friendUser == null) {
                return new ResultInfo<>(ResultInfo.FAILURE, "该用户不存在");
            }
            if (user.getId().equals(friendUser.getId())) {
                return new ResultInfo<>(ResultInfo.FAILURE, "不要自己添加自己");
            }
            int f = friendMapper.findCountByUserIdAndFriendId(userId, friendUser.getId());
            if (f != 0) {
                return new ResultInfo<>(ResultInfo.FAILURE, "已经存在此好友");
            }
            int f2 = friendMapper.findCountByUserIdAndFriendId(friendUser.getId(), userId);
            if (f2 != 0) {
                return new ResultInfo<>(ResultInfo.FAILURE, "已经存在此好友");
            }
            AddFriend addF = addFriendMapper.findOneByUserIdAndFriendId(user.getId(), friendUser.getId());
            if (addF == null) {
                addF = addFriendMapper.findOneByUserIdAndFriendId(friendUser.getId(), user.getId());
            }
            if (addF != null) {
                if (!addF.getIsDeleted()) {
                    return new ResultInfo<>(ResultInfo.FAILURE, "等待对方同意");
                }
            }

            try {
                //发环信透传消息
                SendMessageRequest sendMessageRequest = new SendMessageRequest();
                Message message = new Message();
                message.setType("cmd");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "1");
                jsonObject.put("nickname", user.getNickname());
                jsonObject.put("remark", map.get("remark").toString());
                message.setAction(jsonObject.toJSONString());
                sendMessageRequest.setMsg(message);
                sendMessageRequest.setFrom("admin");
                List<String> list = new ArrayList<>();
                sendMessageRequest.setTarget(list);
                sendMessageRequest.setTarget_type("users");

                Object object = huanxinMessage.sendTextMessage(sendMessageRequest);
                LOG.info("==环信透传消息返回==" + JSONObject.toJSON(object).toString());
            } catch (Exception e) {
                e.printStackTrace();
                return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_ERROR);
            }
            //查询好友添加记录表里是否有该记录，有就更新，没有就添加
            Integer numb = addFriendMapper.selectByuserIdAndfriendUserId(user.getId(), friendUser.getId());
            if (numb > 0) {
                //更新时间
                addFriendMapper.updateByuserIdAndfriendUserId(user.getId(), friendUser.getId());
                continue;
            }
            //写添加好友记录表
            AddFriend addFriend = new AddFriend();
            addFriend.setUserId(user.getId());
            addFriend.setFriendId(friendUser.getId());
            addFriend.setAddType("通讯录添加");
            addFriend.setIsAgree((byte) 0);
            addFriend.setRemark(map.get("remark").toString());
            addFriend.setGmtModified(new Date());
            addFriend.setGmtCreate(new Date());
            addFriend.setIsDeleted(false);
            addFriendMapper.insert(addFriend);
        }

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * @param map
     * @return
     */
    @Override
    @Transactional
    public ResultInfo<?> addFriends(Map<String, Object> map) {
        try {
            if (StringUtil.isEmpty(map.get("userId").toString())) {
                new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
            }
            if (StringUtil.isEmpty(map.get("friendUserId").toString())) {
                new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
            }
            if (StringUtil.isEmpty(map.get("type").toString())) {
                new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM);
            }
            //1--已同意，-1--拒绝',
            if (Integer.parseInt(map.get("type").toString()) == 1) {
                //添加到好友表
                Friend friend = new Friend();
                friend.setUserId(Long.valueOf(map.get("userId").toString()));
                friend.setFriendId(Long.valueOf(map.get("friendUserId").toString()));
                friend.setUserReject(false);
                friend.setFriendReject(false);
                friend.setUserRemark(null);
                friend.setFriendRemark(null);
                friend.setGmtCreate(new Date());
                friend.setGmtModified(new Date());
                friend.setIsDeleted(false);
                friendMapper.insert(friend);
            }
            //修改添加好友记录表
            addFriendMapper.updateIsAgree(Long.valueOf(map.get("userId").toString()), Long.valueOf(map.get("friendUserId").toString()), (byte) Integer.parseInt(map.get("type").toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 查询下线
     *
     * @param inviteCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Object findChildrens(String inviteCode) {
        List<UserChildrenDTO> list = userMapper.findChildrenUserByInviteCode(inviteCode);
        Map<String, Object> map = new HashMap<>();
        List<Long> aLongs = mergeUserIds(list, new ArrayList<>());
        map.put("count", aLongs.size());
        map.put("childrens", list);
        return map;
    }

    public static List<Long> mergeUserIds(List<UserChildrenDTO> userChildrenDTOList, List<Long> aLongs) {
        if (aLongs == null) {
            aLongs = new ArrayList<>();
        }
        //合并ab级用户id到aLongs
        for (UserChildrenDTO userChildrenDTO : userChildrenDTOList) {
            aLongs.add(userChildrenDTO.getId());
            if (!userChildrenDTO.getChildrenUserIds().isEmpty()) {
                //递归
                mergeUserIds(userChildrenDTO.getChildrenUserIds(), aLongs);
            }
        }
        return aLongs;
    }


    /**
     * 查询 用户信息
     *
     * @param pageBaseRequest
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse getUsers(PageBase_Request pageBaseRequest) throws Exception {
        Integer page = 0;
        if (pageBaseRequest.getPage() != 0) {
            page = pageBaseRequest.getSize() * pageBaseRequest.getPage() + 1;
        }
        List<UserInfoResponse> list = new ArrayList<>();

        int count = 0;
        if (StringUtils.isEmpty(pageBaseRequest.getCondition())) {
            list = userMapper.getUsers(page, pageBaseRequest.getSize());
            count = userMapper.findCount();
        } else {
            list = userMapper.getUsersByPhone(pageBaseRequest.getCondition(), page, pageBaseRequest.getSize());
            count = 1;
        }
        List<WebUserResponse> webUserResponseList = new ArrayList<>();

        webUserResponseList = setExchangMsg(list, webUserResponseList);

        PageResponse pageResponse = new PageResponse();
        pageResponse.setItems(webUserResponseList);
        pageResponse.setPageNum(page);
        pageResponse.setPageSize(pageBaseRequest.getSize());
        pageResponse.setPageCount(webUserResponseList.size());
        pageResponse.setTotalCount(count);
        pageResponse.setTotalPages(count / pageBaseRequest.getSize());
        return pageResponse;
    }

    /**
     * 审核代理商
     * 0-代理商申请拒绝,1-成为代理商,2-审核中,3-初始默认状态,4-成为代理候选人
     *
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月29日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean toExamine(ToExamineRequest request) {
        Long userId = Long.valueOf(request.getUserid());
        User user = new User();
        user.setId(userId);
        user.setAgent(request.getExecute());
        user.setAgreeTime(com.xdaocloud.futurechain.util.DateUtils.getNowDateTime());
        userMapper.updateByPrimaryKeySelective(user);
        return true;
    }


    /**
     * 获取代理商信息
     *
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse getUsersByAgent(PageBase_Request pageBaseRequest) throws Exception {
        Integer page = 0;
        if (pageBaseRequest.getPage() != 0) {
            page = pageBaseRequest.getSize() * pageBaseRequest.getPage() + 1;
        }
        List<UserInfoResponse> list = new ArrayList<>();

        int count = 0;
        if (StringUtils.isEmpty(pageBaseRequest.getCondition())) {
            list = userMapper.getUsersByAgent(new int[]{1}, page, pageBaseRequest.getSize());
            count = userMapper.findCountByAgent(new int[]{1});
        } else {
            list = userMapper.getUsersByPhone(pageBaseRequest.getCondition(), page, pageBaseRequest.getSize());
            count = 1;
        }
        List<WebUserResponse> webUserResponseList = new ArrayList<>();

        webUserResponseList = setExchangMsg(list, webUserResponseList);

        PageResponse pageResponse = new PageResponse();
        pageResponse.setItems(webUserResponseList);
        pageResponse.setPageNum(page);
        pageResponse.setPageSize(pageBaseRequest.getSize());
        pageResponse.setPageCount(webUserResponseList.size());
        pageResponse.setTotalCount(count);
        pageResponse.setTotalPages(count / pageBaseRequest.getSize());
        return pageResponse;
    }

    /**
     * 添加银行卡
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年7月5日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public Boolean addBack(AddBackRequest request) {
        BankCard bankCard = new BankCard();
        bankCard.setUserId(Long.valueOf(request.getUserid()));
        bankCard.setBankName(request.getBackName());
        bankCard.setBankNumber(request.getBankNumber());
        bankCard.setBankType(request.getBankType());
        bankCard.setCardholder(request.getCardholder());
        bankCardMapper.insertSelective(bankCard);
        return true;
    }

    /**
     * 获取银行卡
     *
     * @param userId 用户id
     * @return
     * @date 2018年7月6日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public List<BankResponse> getBank(Long userId) {
        List<BankResponse> list = bankCardMapper.findListByUserId(userId);
        return list;
    }


    /**
     * 验证支付密码
     *
     * @param checkTransactionPassWordRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly = true)
    public ResultInfo<?> checkTransactionPassWord(CheckTransactionPassWordRequest checkTransactionPassWordRequest) throws Exception {
        String transactionPassWord = userMapper.findTransactionPassWord(checkTransactionPassWordRequest.getUserid());
        Boolean bool = passwordEncoder.matches(checkTransactionPassWordRequest.getTransactionPassWord(), transactionPassWord);
        if (bool) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }
        return new ResultInfo<>(ResultInfo.FAILURE, "密码错误");
    }

    /**
     * 组装业绩数据
     *
     * @param list
     * @param webUserResponseList
     * @return
     * @throws Exception
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    private List<WebUserResponse> setExchangMsg(List<UserInfoResponse> list, List<WebUserResponse> webUserResponseList) throws Exception {
        for (UserInfoResponse userInfo : list) {
            AchievementResponse achievement_a = exchangeService.getAchievement(new GetAchievementABRequest(userInfo.getUserid(), 1));
            AchievementResponse achievement_b = exchangeService.getAchievement(new GetAchievementABRequest(userInfo.getUserid(), 2));

            //AchievementResponse achievementResponse = exchangeService.findAchievement(Long.valueOf(userInfo.getUserid()));

            WebUserResponse webUserResponse = new WebUserResponse();
            webUserResponse.setAchievement_a(achievement_a);
            webUserResponse.setAchievement_b(achievement_b);
            webUserResponse.setUserInfo(userInfo);

            PropertyResponse property = userService.findUserProperty(Long.valueOf(userInfo.getUserid()));
            BigDecimal freeEos = new BigDecimal(property.getMai().replace("MAI", "").replace(" ", ""));
            if (freeEos == null) {
                freeEos = new BigDecimal(0);
            }
            BigDecimal lockEos = new BigDecimal(property.getLock_mai());
            if (lockEos == null) {
                lockEos = new BigDecimal(0);
            }
            AssetsDTO assets = new AssetsDTO();
            assets.setFreeEos(freeEos);
            assets.setLockEos(lockEos);
            assets.setSumEos(freeEos.add(lockEos));
            BigDecimal buySumEos = ordersMapper.findSumAmountByUserId(Long.valueOf(userInfo.getUserid()));
            assets.setBuySumEos(buySumEos);
            assets.setBuySumRmb(achievement_a.getRmb());
            webUserResponse.setAssets(assets);
            webUserResponseList.add(webUserResponse);
        }
        return webUserResponseList;
    }


    /**
     * 身份证正则校验
     */
    private static boolean checkIdNumberRegex(String idNumber) {
        return Pattern.matches("^([0-9]{17}[0-9Xx])|([0-9]{15})$", idNumber);
    }

}
