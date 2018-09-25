package com.xdaocloud.futurechain.httpapi;

import com.xdaocloud.futurechain.response.yunxin.AddTeamResponse;
import com.xdaocloud.futurechain.response.yunxin.CreateTeamResponse;
import com.xdaocloud.futurechain.response.yunxin.TeamQueryResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * 群组功能（高级群）
 *
 * @author LuoFuMin
 * @data 2018/9/11
 */
public interface IMTeamService {

    /**
     * 创建群
     *
     * @param headers  ：动态请求头
     * @param tname    ：群名称，最大长度64字符
     * @param owner    ：群主用户帐号，最大长度32字符
     * @param members  ：["aaa","bbb"](JSONArray对应的accid，如果解析出错会报414)，一次最多拉200个成员
     * @param msg      ：邀请发送的文字，最大长度150字符
     * @param magree   ：管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群。其它会返回414
     * @param joinmode ：群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。其它返回414
     * @param params   ：参数集合
     *                 param announcement ：群公告，最大长度1024字符（非必填）
     *                 param intro ：群描述，最大长度512字符（非必填）
     *                 param custom ：自定义高级群扩展属性，第三方可以跟据此属性自定义扩展自己的群属性。（建议为json）,最大长度1024字符（非必填）
     *                 param icon ：群头像，最大长度1024字符（非必填）
     *                 param beinvitemode （int）：被邀请人同意方式，0-需要同意(默认),1-不需要同意。其它返回414 （非必填）
     *                 param invitemode （int）：谁可以邀请他人入群，0-管理员(默认),1-所有人。其它返回414 （非必填）
     *                 param uptinfomode （int）：谁可以修改群资料，0-管理员(默认),1-所有人。其它返回414 （非必填）
     *                 param upcustommode （int）：谁可以更新群自定义属性，0-管理员(默认),1-所有人。其它返回414 （非必填）
     * @return ResultMsg 案例：  {"code":200,"tid":"11001","faccid":{"accid":["a","b","c"],"msg":"team count exceed"}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/create.action")
    Call<CreateTeamResponse> create(@HeaderMap Map<String, String> headers, @Field("tname") String tname, @Field("owner") String owner,
                                    @Field("members") String members, @Field("msg") String msg, @Field("magree") int magree,
                                    @Field("joinmode") int joinmode, @FieldMap Map<String, Object> params);

    /**
     * 拉人入群
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner   ：群主用户帐号，最大长度32字符
     * @param members ：["aaa","bbb"](JSONArray对应的accid，如果解析出错会报414)，一次最多拉200个成员
     * @param msg     ：邀请发送的文字，最大长度150字符
     * @param magree  ：管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群。其它会返回414
     * @return ResultMsg 案例：{"code":200,"faccid":{"accid":["a","b","c"],"msg":"team count exceed"}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/add.action")
    Call<ResultMsg> add(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner,
                        @Field("members") String members, @Field("msg") String msg, @Field("magree") int magree);

    /**
     * 踢人出群
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner   ：群主用户帐号，最大长度32字符
     * @param member  ：被移除人的accid，用户账号，最大长度32字符;注：member或members任意提供一个，优先使用member参数
     * @return ResultMsg 案例：  {"code":200,"faccid":{"accid":["a","b","c"],"msg":"team count exceed"}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/kick.action")
    Call<ResultMsg> kick(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner,
                         @Field("member") String member);


    /**
     * 踢人出群（批量）
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner   ：群主用户帐号，最大长度32字符
     * @param members ：["aaa","bbb"]（JSONArray对应的accid，如果解析出错，会报414）一次最多操作200个accid; 注：member或members任意提供一个，优先使用member参数
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/kick.action")
    Call<ResultMsg> kicks(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner,
                          @Field("members") String members);


    /**
     * 解散群
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner   ：群主用户帐号，最大长度32字符
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/remove.action")
    Call<ResultMsg> remove(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner);

    /**
     * 移除管理员
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner   ：群主用户帐号，最大长度32字符
     * @param members ：["aaa","bbb"](JSONArray对应的accid，如果解析出错会报414)，长度最大1024字符（一次解除最多10个管理员）
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/remove.action")
    Call<ResultMsg> removeManager(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner, @Field("members") String members);

    /**
     * 编辑群资料
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner   ：群主用户帐号，最大长度32字符
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/update.action")
    Call<ResultMsg> update(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner, @FieldMap Map<String, Object> params);

    /**
     * 群信息与成员列表查询
     *
     * @param headers ：动态请求头
     * @param tids    ：群id列表，如["3083","3084"]
     * @param ope     ：1表示带上群成员列表，0表示不带群成员列表，只返回群信息
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/query.action")
    Call<TeamQueryResponse> query(@HeaderMap Map<String, String> headers, @Field("tids") String tids, @Field("ope") int ope);

    /**
     * 获取群组详细信息
     *
     * @param headers ：动态请求头
     * @param tid     ：群id
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/queryDetail.action")
    Call<ResultMsg> queryDetail(@HeaderMap Map<String, String> headers, @Field("tid") String tid);

    /**
     * 获取群组已读消息的已读详情信息
     *
     * @param headers   ：动态请求头
     * @param tid       ：群id
     * @param msgid     ：发送群已读业务消息时服务器返回的消息ID
     * @param fromAccid ：消息发送者账号
     * @return ResultMsg 案例： {"code": 200,"data": {"readSize": 2,"unreadSize": 1,"readAccids": ["user1","user2"],"unreadAccids": ["user3"],}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/getMarkReadInfo.action")
    Call<ResultMsg> getMarkReadInfo(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("msgid") String msgid,
                                    @Field("fromAccid") String fromAccid);


    /**
     * 移交群主
     *
     * @param headers  ：动态请求头
     * @param tid      ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner    ：群主用户帐号，最大长度32字符
     * @param newowner ：新群主帐号，最大长度32字符
     * @param leave    ：1:群主解除群主后离开群，2：群主解除群主后成为普通成员。其它414
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/update.action")
    Call<ResultMsg> update(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner,
                           @Field("newowner") String newowner, @Field("leave") int leave);


    /**
     * 任命管理员
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner   ：群主用户帐号，最大长度32字符
     * @param members ：["aaa","bbb"](JSONArray对应的accid，如果解析出错会报414)，长度最大1024字符（一次添加最多10个管理员）
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/addManager.action")
    Call<ResultMsg> addManager(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner,
                               @Field("members") String members);

    /**
     * 将群组整体禁言
     *
     * @param headers  ：动态请求头
     * @param tid      ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param owner    ：群主用户帐号，最大长度32字符
     * @param mute     ：true:禁言，false:解禁(mute和muteType至少提供一个，都提供时按mute处理)
     * @param muteType ：禁言类型 0:解除禁言，1:禁言普通成员 3:禁言整个群(包括群主)
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/muteTlistAll.action")
    Call<ResultMsg> muteTlistAll(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("owner") String owner,
                                 @Field("muteType") int muteType);

    /**
     * 退群
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param accid   ：退群的accid
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/leave.action")
    Call<ResultMsg> leave(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("accid") String accid);

    /**
     * 修改消息提醒开关
     *
     * @param headers ：动态请求头
     * @param tid     ：网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
     * @param accid   ：退群的accid
     * @param ope     ：1：关闭消息提醒，2：打开消息提醒，其他值无效
     * @return ResultMsg 案例：  {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("team/muteTeam.action")
    Call<ResultMsg> muteTeam(@HeaderMap Map<String, String> headers, @Field("tid") String tid, @Field("accid") String accid, @Field("ope") int ope);


}
