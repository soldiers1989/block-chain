package com.xdaocloud.futurechain.constant;

public class Constant {

    /**
     * JWT密码
     */
    public static final String JWT_SECRET = "7D02504806779FFBCE20A6F564F5FB75";

    /**
     * Token
     */
    public static final String USER_TOKEN = "UC:USER:TOKEN:";

    /**
     * Token有效期(秒) 1d
     */
    public static final long TOKEN_EXP_SECENDS = 60 * 60 * 24L;


    /**
     * 应用id
     */
    public static final String APPID = "maichain";


    /**
     * 时间格式
     */
    public static final String TIME_BASE = "yyyy-MM-dd HH:mm:ss";


    /**
     * redis矿包键（监听矿包24小时失效）
     */
    public static final String REDIS_ENVELOP = "chain:ore-envelope-id:";

    /**
     * redis注册键（获取短信验证码）
     */
    public static final String SMS_REGISTER_KEY = "chain:register:sms:";

    /**
     * redis找回密码键（获取短信验证码）
     */
    public static final String SMS_BACK_PASS_KEY = "chain:backPass:sms:";

    /**
     * redis找回交易密码键（获取短信验证码）
     */
    public static final String SMS_BACK_TRANSACTION_PASS_KEY = "chain:backTransactionPass:sms:";

    /**
     * redis登录密码错误键（记录登录错误次数, 设置失效时间）
     */
    public static final String LOGIN_ERROR_KEY = "chain:login:error:";

    /**
     * redis登录密码错误键（记录登录错误次数, 不设置失效时间，登录成功后重置为0）
     */
    public static final String LOGIN_ERROR_KEY_COUNT = "chain:login:error:count";


    /**
     * redis web用户id键
     */
    public static final String WEB_USER_ID = "chain:web:user:id:";

    /**
     * redis web用户token键
     */
    public static final String WEB_USER_TOKEN = "chain:web:user:token:";

    /**
     * redis web登录验证码键
     */
    public static final String WEB_VERIFY_CODE = "chain:web:verify:code:";


    /**
     * appId 键
     */
    public static final String APPID_KEY = "appId";


    /**
     * 获取redis token 的 key
     *
     * @param appId
     * @param userId
     * @return String
     */
    public static String getTokenKey(String appId, Long userId) {
        return USER_TOKEN + appId + ":" + userId;
    }


    /**
     * 云信AppKey
     */
    public static String NETEASE_APP_KEY = "5e80bbf7c43c38f9d3b410c860a84c53";

    /**
     * 云信AppSecret
     */
    public static String NETEASE_APP_SECRET = "f0889ed7d3b2";


}
