package com.xdaocloud.futurechain.httpapi;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * 消息功能
 *
 * @author LuoFuMin
 * @data 2018/9/11
 */
public interface IMMsgService {

    /**
     *
     1.文本消息
     {
     "from":"test1",
     "ope":0,
     "to":"test2",
     "type":0,//文本消息类型
     "body":{
     "msg":"哈哈哈"//消息内容
     }
     }

     2 图片消息
     {
     "from":"test1",
     "ope":0,
     "to":"test2",
     "type":1    //图片类型消息
     "body":{
     "name":"图片发送于2015-05-07 13:59",   //图片name
     "md5":"9894907e4ad9de4678091277509361f7",    //图片文件md5
     "url":"http://nimtest.nos.netease.com/cbc500e8-e19c-4b0f-834b-c32d4dc1075e",    //生成的url
     "ext":"jpg",    //图片后缀
     "w":6814,    //宽
     "h":2332,    //高
     "size":388245    //图片大小
     }
     }

     3 语音消息
     {
     "from":"test1",
     "ope":0,
     "to":"test2",
     "type":2    //语音类型消息
     "body":{
     "dur":4551,        //语音持续时长ms
     "md5":"87b94a090dec5c58f242b7132a530a01",    //语音文件的md5值
     "url":"http://nimtest.nos.netease.com/a2583322-413d-4653-9a70-9cabdfc7f5f9",    //生成的url
     "ext":"aac",        //语音消息格式，只能是aac格式
     "size":16420        //语音文件大小
     }
     }

     4 视频消息
     {
     "from":"test1",
     "ope":0,
     "to":"test2",
     "type":3    //视频类型消息
     "body":{
     "dur":8003,        //视频持续时长ms
     "md5":"da2cef3e5663ee9c3547ef5d127f7e3e",    //视频文件的md5值
     "url":"http://nimtest.nos.netease.com/21f34447-e9ac-4871-91ad-d9f03af20412",    //生成的url
     "w":360,    //宽
     "h":480,    //高
     "ext":"mp4",    //视频格式
     "size":16420    //视频文件大小
     }
     }

     5 发送地理位置消息
     {
     "from":"test1",
     "ope":0,
     "to":"test2",
     "type":4    //地理位置类型消息
     "body":{
     "title":"中国 浙江省 杭州市 网商路 599号",    //地理位置title
     "lng":120.1908686708565,        // 经度
     "lat":30.18704515647036            // 纬度
     }
     }

     6 发送文件消息
     {
     "from":"test1",
     "ope":0,
     "to":"test2",
     "type":6    //文件消息
     "body":{
     "name":"BlizzardReg.ttf",    //文件名
     "md5":"79d62a35fa3d34c367b20c66afc2a500", //文件MD5
     "url":"http://nimtest.nos.netease.com/08c9859d-183f-4daa-9904-d6cacb51c95b", //文件URL
     "ext":"ttf",    //文件后缀类型
     "size":91680    //大小
     }
     }

     7 发送第三方自定义消息
     {
     "from":"test1",
     "ope":0,
     "to":"test2",
     "type":100    //第三方自定义消息
     //第三方定义的body体，json串
     "body":{
     "myKey":myValue
     }
     }
     */


    /**
     * 发送普通消息
     *
     * @param headers ：动态请求头
     * @param from    ：发送者accid，用户帐号，最大32字符，必须保证一个APP内唯一
     * @param to      ：ope==0是表示accid即用户id，ope==1表示tid即群id
     * @param type    ：0 表示文本消息,1 表示图片，2 表示语音，3 表示视频，4 表示地理位置信息，6 表示文件，100 自定义消息类型（特别注意，对于未对接易盾反垃圾功能的应用，该类型的消息不会提交反垃圾系统检测）
     * @param ope     ：0：点对点个人消息，1：群消息（高级群），其他返回414
     * @param body    ：最大长度5000字符，为一个JSON串（示例：{"msg":"hello"}'）
     * @param params  ：
     *                params antispam	：对于对接了易盾反垃圾功能的应用，本消息是否需要指定经由易盾检测的内容（antispamCustom）。true或false, 默认false。只对消息类型为：100 自定义消息类型 的消息生效。
     *                params antispamCustom ： 在antispam参数为true时生效。自定义的反垃圾检测内容, JSON格式，长度限制同body字段，不能超过5000字符，要求antispamCustom格式如下：{"type":1,"data":"custom content"}
     *                字段说明：
     *                1. type: 1：文本，2：图片。
     *                2. data: 文本内容or图片地址
     * @return ResultMsg 案例： {"code":200,"data":{"msgid":1200510468189,"antispam":false}
     * }
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("msg/sendMsg.action")
    Call<ResultMsg> add(@HeaderMap Map<String, String> headers, @Field("from") String from, @Field("to") String to, @Field("ope") int ope,
                        @Field("type") int type, @Field("body") int body, @FieldMap Map<String, Object> params);


    /**
     * 批量发送点对点普通消息
     *
     * @param headers   ：动态请求头
     * @param fromAccid ：发送者accid，用户帐号，最大32字符，必须保证一个APP内唯一
     * @param toAccids  ：["aaa","bbb"]（JSONArray对应的accid，如果解析出错，会报414错误），限500人
     * @param type      ：0 表示文本消息,1 表示图片，2 表示语音，3 表示视频，4 表示地理位置信息，6 表示文件，100 自定义消息类型（特别注意，对于未对接易盾反垃圾功能的应用，该类型的消息不会提交反垃圾系统检测）
     * @param ope       ：0：点对点个人消息，1：群消息（高级群），其他返回414
     * @param body      ：最大长度5000字符，为一个JSON串（示例：{"msg":"hello"}'）
     * @param params    ：可选参数
     *                  params antispam	：对于对接了易盾反垃圾功能的应用，本消息是否需要指定经由易盾检测的内容（antispamCustom）。true或false, 默认false。只对消息类型为：100 自定义消息类型 的消息生效。
     *                  params antispamCustom ： 在antispam参数为true时生效。自定义的反垃圾检测内容, JSON格式，长度限制同body字段，不能超过5000字符，要求antispamCustom格式如下：{"type":1,"data":"custom content"}
     *                  字段说明：
     *                  1. type: 1：文本，2：图片。
     *                  2. data: 文本内容or图片地址
     * @return ResultMsg 案例： {"code":200,"unregister":"["a","b"...]" //未注册的帐号}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("msg/sendBatchMsg.action")
    Call<ResultMsg> sendBatchMsg(@HeaderMap Map<String, String> headers, @Field("fromAccid") String fromAccid, @Field("toAccids") String toAccids, @Field("ope") int ope,
                                 @Field("type") int type, @Field("body") int body, @FieldMap Map<String, Object> params);

    /**
     * 批量发送点对点普通消息
     *
     * @param headers   ：动态请求头
     * @param fromAccid ：发送者accid，用户帐号，最大32字符，必须保证一个APP内唯一
     * @param to        ：msgtype==0是表示accid即用户id，msgtype==1表示tid即群id
     * @param msgtype   ：0：点对点自定义通知，1：群消息自定义通知，其他返回414
     * @param attach    ：自定义通知内容，第三方组装的字符串，建议是JSON串，最大长度4096字符
     * @param params    ：可选参数
     *                  params pushcontent(String)：iOS推送内容，第三方自己组装的推送内容,不超过150字符
     *                  params payload (String)： iOS推送对应的payload,必须是JSON,不能超过2k字符
     *                  params sound （String）：	如果有指定推送，此属性指定为客户端本地的声音文件名，长度不要超过30个字符，如果不指定，会使用默认声音
     *                  params save（int）：1表示只发在线，2表示会存离线，其他会报414错误。默认会存离线
     *                  params option（String）：发消息时特殊指定的行为选项,Json格式，可用于指定消息计数等特殊行为;option中字段不填时表示默认值。option示例：
     *                  {"badge":false,"needPushNick":false,"route":false}
     *                  字段说明：
     *                  1. badge:该消息是否需要计入到未读计数中，默认true;
     *                  2. needPushNick: 推送文案是否需要带上昵称，不设置该参数时默认false(ps:注意与sendMsg.action接口有别);
     *                  3. route: 该消息是否需要抄送第三方；默认true (需要app开通消息抄送功能)
     * @return ResultMsg 案例： {"code":200,"unregister":"["a","b"...]" //未注册的帐号}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("msg/sendAttachMsg.action")
    Call<ResultMsg> sendAttachMsg(@HeaderMap Map<String, String> headers, @Field("fromAccid") String fromAccid, @Field("to") String to, @Field("attach") String attach,
                                  @Field("msgtype") int msgtype, @FieldMap Map<String, Object> params);

    /**
     * 批量发送点对点自定义系统通知
     *
     * @param headers   ：动态请求头
     * @param fromAccid ：发送者accid，用户帐号，最大32字符，必须保证一个APP内唯一
     * @param toAccids  ：["aaa","bbb"]（JSONArray对应的accid，如果解析出错，会报414错误），最大限500人
     * @param attach    ：自定义通知内容，第三方组装的字符串，建议是JSON串，最大长度4096字符
     * @param params    ：可选参数
     *                  params pushcontent(String)：iOS推送内容，第三方自己组装的推送内容,不超过150字符
     *                  params payload (String)： iOS推送对应的payload,必须是JSON,不能超过2k字符
     *                  params sound （String）：	如果有指定推送，此属性指定为客户端本地的声音文件名，长度不要超过30个字符，如果不指定，会使用默认声音
     *                  params save（int）：1表示只发在线，2表示会存离线，其他会报414错误。默认会存离线
     *                  params option（String）：发消息时特殊指定的行为选项,Json格式，可用于指定消息计数等特殊行为;option中字段不填时表示默认值。option示例：
     *                  {"badge":false,"needPushNick":false,"route":false}
     *                  字段说明：
     *                  1. badge:该消息是否需要计入到未读计数中，默认true;
     *                  2. needPushNick: 推送文案是否需要带上昵称，不设置该参数时默认false(ps:注意与sendMsg.action接口有别);
     *                  3. route: 该消息是否需要抄送第三方；默认true (需要app开通消息抄送功能)
     * @return ResultMsg 案例： {"code":200,"unregister":"["a","b"...]" //未注册的帐号}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("msg/sendBatchAttachMsg.action")
    Call<ResultMsg> sendBatchAttachMsg(@HeaderMap Map<String, String> headers, @Field("fromAccid") String fromAccid, @Field("toAccids") String toAccids,
                                       @Field("attach") String attach, @FieldMap Map<String, Object> params);


    /**
     * 文件上传
     *
     * @param headers ：动态请求头
     * @param content ：字符流base64串(Base64.encode(bytes)) ，最大15M的字符流
     * @param params  ：可选参数
     *                params type(String)：上传文件类型
     *                params ishttps (String)：返回的url是否需要为https的url，true或false，默认false
     *                params expireSec （Integer）：文件过期时长，单位：秒，必须大于等于86400
     *                params tag（String）：文件的应用场景，不超过32个字符
     * @return ResultMsg 案例： {"code":200,"url":"xxx"}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("msg/upload.action")
    Call<ResultMsg> upload(@HeaderMap Map<String, String> headers, @Field("content") String content, @FieldMap Map<String, Object> params);


    /**
     * 文件上传（multipart方式）
     * <p>
     * File file = new File(imgPath);
     * RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
     * MultipartBody.Part body = MultipartBody.Part.createFormData("app_user_header", fileName, requestFile);
     * file部分，最终拼接成以下部分（注意“app_user_header”是后台定义好的，后台会用它作为key去查询你传的图片信息）：
     * *Content-Disposition: form-data; name="app_user_header"; filename=fileNameByTimeStamp *Content-Type: image/jpeg *Content-Length: 52251(图片流字节数组的长度，底层的Okhttp帮我们计算了)
     *
     * @param headers ：动态请求头
     * @param content ：Multipart 文件
     * @param params  ：可选参数
     *                params type(String)：上传文件类型
     *                params ishttps (String)：返回的url是否需要为https的url，true或false，默认false
     *                params expireSec （Integer）：文件过期时长，单位：秒，必须大于等于86400
     *                params tag（String）：文件的应用场景，不超过32个字符
     * @return ResultMsg 案例： {"code":200,"url":"xxx"}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @Multipart
    @POST("msg/fileUpload.action")
    Call<ResultMsg> fileUpload(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part filet, @FieldMap Map<String, Object> params);

    /**
     * 消息撤回
     *
     * @param headers     ：动态请求头
     * @param deleteMsgid ：要撤回消息的msgid
     * @param timetag     ：要撤回消息的创建时间
     * @param type        ：7:表示点对点消息撤回，8:表示群消息撤回，其它为参数错误
     * @param from        ：发消息的accid
     * @param to          ：	如果点对点消息，为接收消息的accid,如果群消息，为对应群的tid
     * @return ResultMsg 案例： {"code":200}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("msg/recall.action")
    Call<ResultMsg> recall(@HeaderMap Map<String, String> headers, @Field("deleteMsgid") String deleteMsgid, @Field("timetag") Long timetag,
                           @Field("type") int type, @Field("from") String from, @Field("to") String to);

    /**
     * 发送广播消息
     *
     * @param headers ：动态请求头
     * @param body    ：广播消息内容，最大4096字符
     * @param params  ：要撤回消息的创建时间
     *                param ttl（int）：存离线状态下的有效期，单位小时，默认7天
     *                param from (String) ：发消息的accid
     *                param isOffline (String) ：是否存离线，true或false，默认false
     *                param targetOs (String) ：目标客户端，默认所有客户端，jsonArray，格式：["ios","aos","pc","web","mac"]
     * @return ResultMsg 案例： {"code": 200,"msg": {"expireTime": 1505502793520,"body": "abc","createTime": 1505466793520,"isOffline": true,"broadcastId": 48174937359009,"targetOs": ["ios","pc","aos"]}}
     * @author LuoFuMin
     * @date 2018/9/11
     */
    @FormUrlEncoded
    @POST("msg/broadcastMsg.action")
    Call<ResultMsg> broadcastMsg(@HeaderMap Map<String, String> headers, @Field("body") String body, @FieldMap Map<String, Object> params);
}
