package com.xdaocloud.futurechain.httpapi;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * 用户关系托管
 *
 * @author LuoFuMin
 * @data 2018/9/11
 */
public interface IMFriendService {

    /**
     * 加好友
     *
     * @param headers ：动态请求头
     * @param accid   ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param faccid  ：加好友接收者accid
     * @param type    ：1直接加好友，2请求加好友，3同意加好友，4拒绝加好友
     * @param msg     ：加好友对应的请求消息，第三方组装，最长256字符（非必填）
     * @return ResultMsg 案例： {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("friend/add.action")
    Call<ResultMsg> add(@HeaderMap Map<String, String> headers, @Field("accid") String accid, @Field("faccid") String faccid, @Field("type") int type, @Field("msg") String msg);


    /**
     * 更新好友相关信息
     *
     * @param headers  ：动态请求头
     * @param accid    ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param faccid   ：要修改朋友的accid
     * @param params   ：参数集合
     * param alias    ：给好友增加备注名，限制长度128（非必填）
     * param ex       ：修改ex字段，限制长度256（非必填）
     * param serverex ：修改serverex字段，限制长度256此字段client端只读，server端读写（非必填）
     * @return ResultMsg 案例： {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("friend/update.action")
    Call<ResultMsg> update(@HeaderMap Map<String, String> headers, @Field("accid") String accid, @Field("faccid") String faccid, @FieldMap Map<String, Object> params);


    /**
     * 删除好友
     *
     * @param headers ：动态请求头
     * @param accid   ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param faccid  ：要删除朋友的accid
     * @return ResultMsg 案例： {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("friend/delete.action")
    Call<ResultMsg> delete(@HeaderMap Map<String, String> headers, @Field("accid") String accid, @Field("faccid") String faccid);

    /**
     * 获取好友关系
     *
     * @param headers    ：动态请求头
     * @param accid      ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param updatetime ：更新时间戳，接口返回该时间戳之后有更新的好友列表
     * @return ResultMsg 案例：{"code":200,"size":2,"friends":[{"createtime":1440037706987,"bidirection":true,"faccid":"t2"},{"createtime":1440037718190,"bidirection":true,"faccid":"t3","alias":"t3"}]}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("friend/get.action")
    Call<ResultMsg> get(@HeaderMap Map<String, String> headers, @Field("accid") String accid, @Field("updatetime") Long updatetime);

    /**
     * 设置黑名单/静音
     *
     * @param headers      ：动态请求头
     * @param accid        ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param targetAcc    ：被加黑或加静音的帐号 relationType
     * @param relationType ：本次操作的关系类型,1:黑名单操作，2:静音列表操作
     * @param value        ：操作值，0:取消黑名单或静音，1:加入黑名单或静音
     * @return ResultMsg 案例： {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("friend/setSpecialRelation.action")
    Call<ResultMsg> setSpecialRelation(@HeaderMap Map<String, String> headers, @Field("accid") String accid, @Field("targetAcc") String targetAcc, @Field("relationType") int relationType, @Field("value") int value);

    /**
     * 查看指定用户的黑名单和静音列表
     *
     * @param headers ：动态请求头
     * @param accid   ：网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @return ResultMsg 案例：{"mutelist": [//被静音的帐号列表"abc","cde"],"blacklist": [//加黑的帐号列表"abc"],"code": 200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("friend/listBlackAndMuteList.action")
    Call<ResultMsg> listBlackAndMuteList(@HeaderMap Map<String, String> headers, @Field("accid") String accid);

}
