package com.xdaocloud.futurechain.httpapi;

import com.xdaocloud.futurechain.response.yunxin.CreateAccIdResponse;
import com.xdaocloud.futurechain.response.yunxin.RefreshTokenResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;


/**
 * 网易云信api 接口
 *
 * @author LuoFuMin
 * @data 2018/9/7
 */
public interface IMUserService {

    /**
     * demo 动态添加请求头
     *
     * @param headers
     * @return
     */
    @GET("journalismApi")
    Call<ResponseBody> getTest(@HeaderMap Map<String, String> headers);

    /**
     * 创建网易云 ID
     *
     * @param headers ：动态请求头
     * @param accid   ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一（只允许字母、数字、半角下划线_、@、半角点以及半角-组成，不区分大小写，会统一小写处理，请注意以此接口返回结果中的accid为准）
     * @param params  ：参数集合
     * param name    ：网易云通信ID昵称，最大长度64字符，用来PUSH推送时显示的昵称（非必填）
     * param icon    ：用户头像，最大长度1024字节（非必填）
     * param sign    ：用户签名，最大长度256字符（非必填）
     * param email   ：用户email，最大长度64字符（非必填）
     * param birth   ：用户生日，最大长度16字符（非必填）
     * param mobile  ：用户mobile，最大长度32字符，非中国大陆手机号码需要填写国家代码(如美国：+1-xxxxxxxxxx)或地区代码(如香港：+852-xxxxxxxx)（非必填）
     * param gender  ：用户性别，0表示未知，1表示男，2女表示女，其它会报参数错误（是否必填：否）
     * param ex      ：用户名片扩展字段，最大长度1024字符，用户可自行扩展，建议封装成JSON字符串（非必填）

     * @return ResultMsg  案例：{"code":200,"info":{"token":"xx","accid":"xx","name":"xx"}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("user/create.action")
    Call<CreateAccIdResponse> createUser(@HeaderMap Map<String, String> headers, @Field("accid") String accid, @FieldMap Map<String, Object> params);


    /**
     * 网易云通信ID更新
     *
     * @param accid   ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param headers ：动态请求头
     * @return ResultMsg 案例： {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("user/update.action")
    Call<ResultMsg> update(@HeaderMap Map<String, String> headers, @Field("accid") String accid);


    /**
     * 更新并获取新token
     *
     * @param accid   ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param headers ：动态请求头
     * @return ResultMsg 案例： {"code":200,"info":{"token":"xxx","accid":"xx" }}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("user/refreshToken.action")
    Call<RefreshTokenResponse> refreshToken(@HeaderMap Map<String, String> headers, @Field("accid") String accid);


    /**
     * 封禁网易云通信ID
     *
     * @param accid    ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param needkick ：是否踢掉被禁用户，true或false，默认false（非必填）
     * @param headers  ：动态请求头
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("user/block.action")
    Call<ResultMsg> block(@HeaderMap Map<String, String> headers, @Field("accid") String accid, @Field("needkick") String needkick);

    /**
     * 解禁网易云通信ID
     *
     * @param accid   ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param headers ：动态请求头
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("user/unblock.action")
    Call<ResultMsg> unblock(@HeaderMap Map<String, String> headers, @Field("accid") String accid);


    /**
     * 更新用户名片
     *
     * @param accid   ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param headers ：动态请求头
     * @param params  ：参数集合
     * param name    ：用户昵称，最大长度64字符（非必填）
     * param icon    ：用户头像，最大长度1024字节（非必填）
     * param sign    ：用户签名，最大长度256字符（非必填）
     * param email   ：用户email，最大长度64字符（非必填）
     * param birth   ：用户生日，最大长度16字符（非必填）
     * param mobile  ：用户mobile，最大长度32字符，非中国大陆手机号码需要填写国家代码(如美国：+1-xxxxxxxxxx)或地区代码(如香港：+852-xxxxxxxx)（非必填）
     * param gender  ：用户性别，0表示未知，1表示男，2女表示女，其它会报参数错误（非必填）
     * param ex      ：用户名片扩展字段，最大长度1024字符，用户可自行扩展，建议封装成JSON字符串（非必填）
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("user/updateUinfo.action")
    Call<ResultMsg> updateUinfo(@HeaderMap Map<String, String> headers, @Field("accid") String accid, @FieldMap Map<String, Object> params);
}
