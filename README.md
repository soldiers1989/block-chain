# 麦链科技架项目介绍

[TOC]

## 介绍：
> 本工程为麦链星球JAVA项目框架，基于Maven+SpringBoot搭建框架，集成 Mybatis、Mysql、Redis、Shiro等功能。以太坊模块引用web3j调用以太坊RPC接口，EOS模块引用eos4j调用EOSRPC接口；

## 结构:
> xdaocloud-futurechain-common  存放公共配置等信息

> xdaocloud-futurechain-utils  存放公共工具类等信息

> xdaocloud-futurechain-model   存放ORM关系映射Model，以及通用的持久化相关类等信息。

> xdaocloud-futurechain-service  业务逻辑层（service层）。

> xdaocloud-futurechain-mapper  数据持久层（dao层）。

> xdaocloud-futurechain-app  手机app视图层（view层）。

> xdaocloud-futurechain-web  浏览器视图层（view层）。

> xdaocloud-futurechain-eos  eos逻辑业务层（访问区块链）。

> xdaocloud-futurechain-huanxin  huanxin逻辑业务层（访问环信）。

> xdaocloud-futurechain-eth  eth逻辑业务层（访问以太坊）。


## 运行、重启、查看控制台日志：
> xdaocloud-futurechain-app和xdaocloud-futurechain-web项目可以单独运行，SpringBoot 默认内置Tomcat 。

> 1.以在本地Eclipse中运行为例，导入项目后，直接运行FutureChainAppApplication.java 的     main函数 , 微服务开始启动，如没有报错则启动成功。

> 2.在项目根目录下运行cmd执行mvn clean package命令后传到linux服务器上面，通过命令方式运行：
> - 默认方式：  nohup java -jar xxx.jar &  
> -  配置内存：nohup java -Xms64m -Xmx1024m -jar xxx.jar &
> -  指定端口： nohup java -jar xxx.jar  --server.port=8080 &

> 3.在Linux下 执行命令：
-  执行：ps -ef | grep java （查看正在运行jar所使用端口）
-  执行：kill -9 8080 （杀死8080端口）
-   执行：nohup java -jar xxx.jar &  重启。

> 4.使用 tail -f nohup.out 查看控制台日志

## 接口测试:
> 系统已集成SwaggerUI工具，微服务启动成功后，在浏览器中输入：
```
http://127.0.0.1:9019/connectionChain/swagger-ui.html
```
> 即可使用SwaggerUI进行接口测试。

## 特别说明:
>1-锁仓
```
系统存在两种状态的币，1-保存在区块链上，2-java系统保存在数据库上；
java保存在数据库的币是锁仓币，保存在eos交易记录表 “t_eos_transaction” - tran_state=7。tran_state=1是区块链真实交易记录；
锁仓币有3种途径获取：A-支付宝或微信赞赏，B-其他用户转账，C-代理商提现
计算用户锁仓币余额：入账（fromWallet）-出账（toWallet）- 已解锁 = 余额；
解锁表:t_unlock_eos,保存用户解锁记录，解锁过程：减少mysql锁仓币，给用户转移真实币，并保存解锁记录；
```
>2-支付宝和微信支付
```
微信支付和支付宝支付一共有两套，A:属于深脑科技有限公司，B:属于脉链科技有限公司；
需要的配置信息写在 "com.xdaocloud.futurechain.constant" 下
```
>3-奖励机制
```
奖励机制表 t_reward , 根据类型 "type" 判断（1-注册奖励；2-邀请好友奖励；3-签到奖励：5-充值奖励；6-分享奖励）,相应奖励在代码逻辑中查询此表；（注册奖励改为 80%锁仓，20%真实币）
```
>4-代理商
```
 用户表 t_user 字段 agent ： 0-代理商申请拒绝,1-成为代理商,2-审核中,3-初始默认状态,4-成为代理候选人；
 赞赏到达2000元 直接成为代理候选人，2级下线赞赏金额超过10万元可以申请成为代理商，通过人工审核可以成为代理商。
```
>5-免密支付
```
 免密支付表 t_quota，默认免密金额 1000 MAI;涉及到用户转账是否需要交易密码；
```
>6-请求接口注意事项
```
请求接口多使用 json 对象，创建实体类需要添加 @Valid @RequestBody 两个注解，实体类 需要实现 IBaseRequest接口，以便 ControllerLogAspect输出请求参数日志；controller集成BaseController，请求参数加入 BindingResult bindingResult并调用  parseBindingResult(bindingResult)方法，如果有不符合参数要求的请求会通过   throw new XdaoException(ResultInfo.INVALID_PARAM, str); 抛异常返回给前端；
```
>7-全局异常捕获
```
com.xdaocloud.futurechain.exception 下定义全局异常捕获类-DefaultExceptionHandler；unauthorizedExceptionHandler：捕获无权限异常、esourceAccessExceptionHandler：捕获eos请求异常、defaultExceptionHandler：捕获未知异常、xdaoExceptionHandler：小道自定义异常（主要捕获请求参数错误）
```
>8-矿包注意事项
``` 
矿包24小时失效功能通过监听redis实现key的方式实现；配置redis监听key失效事件在 com.xdaocloud.futurechain.config.redis 下 RedisConfig；负载均衡下只运行一服务器监听失效key，否则将会出现多次退款现象；实现监听还需要修改redis.conf配置文件“notify-keyspace-events Ex”，注意“notify-keyspace-events Ex”前面不能存在空格，否则无法生效；
```
>9-redis “key” 的使用
``` 
redis 使用一些key常量放在 com.xdaocloud.futurechain.constant 下 Constant类中，用户短信验证码也是存储在redis中，每个短信功能每天不能超过10条短信；
```

## 产品示意图
![Image text](/image/IMG_3177.PNG)
## 项目示意图
![Image text](/image/IMG_3082.PNG)




##模块详细设计

###1.0 用户模块
- 用户个人信息
    -  账户名称
    -  密码
    -  昵称
    -  手机号码
    -  头像
    -  邀请码
    -  真实姓名
    -  身份证号码
    -  个性签名
    -  邮箱
    -  所属行业
    -  麦钻
    -  代理商身份
    -  银行卡

####1.1.1用户注册

#####业务流程

```flow
st=>start: 注册
io1=>inputoutput: 输入手机号码、密码、短信验证码和邀请码 

cond=>condition: Yes or No?
cond1=>condition: Yes or No?
cond2=>condition: Yes or No?
cond3=>condition: Yes or No?

op=>operation: 校验短信验证码是否有效
op1=>operation: 检查邀请码是否存在
op2=>operation: 判断用户是否已注册
op3=>operation: 注册环信
op4=>operation: 生成唯一邀请码
op5=>operation: 保存用户信息，并给邀请用户发奖励
op6=>operation: 执行登录

e=>end

st->io1->op->cond
cond(yes)->op1->cond1
cond(no)->io1
cond1(yes)->op2->cond2
cond1(no)->io1
cond2(no)->op3->cond3
cond2(yes)->io1
cond3(yes)->op4->op5->op6->e
cond3(no)->e
```

##### 接口调用请求说明

##### HTTP请求方式

> POST http://ip:port/connectionChain/api/app/v2/user/register 

##### 请求参数格式： "Content-type","application/json; charset=utf-8"

##### 请求参数说明

| 参数名称           | 是否必须 | 类型   | 说明                      |
| :----------------- | :------- | :----- | ------------------------- |
| mobileNumber       | 否       | String | 手机号码                  |
| inviteCode         | 是       | String | 邀请码（平台邀请码 0608） |
| nickname           | 是       | String | 昵称                      |
| password           | 是       | String | 密码                      |
| smsCode            | 是       | String | 验证码                    |
| name               | 是       | String | 姓名                      |
| idcard             | 是       | String | 身份证号码                |
| spbillCreateIp     | 否       | String | IP地址                    |
| mac                | 是       | String | mac地址                   |
| appVersion         | 是       | String | app版本                   |
| phoneSystemVersion | 是       | String | 手机系统版本              |
| phoneModel         | 是       | String | 手机型号                  |
| phoneCompany       | 是       | String | 手机所属公司              |
| username           | 否       | String | 用户自定义用户名            |

##### 接口调用响应说明

##### 返回结果示例

正常情况下，系统会返回下述JSON数据包

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1527489803435,
 "data": {
  "userid": 50,
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1MCIsImV4cCI6MTUyNzU3NjIwM30.D-Ob8ko0TntZc-KyVxt8CsRLP0uBDVPBLOTgIZU2E9zpVSLmQdftZNTvAZNi7qHZeaL8wR9B444Tp1ZXJBblcQ",
  "mobileNumber": "15308962398",
  "avatar": "http://p5o7ose2w.bkt.clouddn.com/9b93fa8c9a9cef73588e15f83d3333f2.jpg",
  "name": "啊实打实",
  "idcard": "460030199201276688",
  "hxUsername": "1838157",
  "hxPassword": "fbee4f3f99190342bcaaebd9ac976d6b",
  "inviteCode": "DDC7",
  "nickname": "啊实打实",
  "existEosWallet": false
 }
}
```

##### 返回结果说明

| 属性名称     | 类型   | 说明       |
| :----------- | :----- | :--------- |
| userid       | long   | 用户id     |
| token        | String | 合约名称   |
| mobileNumber | String | 手机号码   |
| avatar       | String | 头像地址   |
| name         | String | 真名       |
| idcard       | String | 身份证号码 |
| hxUsername   | String | 环信账户   |
| hxPassword   | String | 环信密码   |
| inviteCode   | String | 邀请码     |
| nickname     | String | 昵称       |

异常情况下，系统会返回下述JSON数据包

```
{
 "code": 500,
 "message": "异常原因",
 "timestamp": 1502866768858
}
```

#### 1.1.2登录

##### 业务流程
```flow
st=>start: Start
io1=>inputoutput: 输入手机号码、密码
op=>operation: 判断手机号码或密码是否正确
cond=>condition: Yes or No?
e=>end
st->io1->op->cond
cond(yes)->e
cond(no)->io1
```

##### 接口调用请求说明

##### HTTP请求方式

> POSThttp://ip:port/connectionChain/api/app/v2/user/login 

##### 请求参数格式： "Content-type","application/json; charset=utf-8"
##### 请求参数说明

| 参数名称           | 是否必须 | 类型   | 说明         |
| :----------------- | :------- | :----- | :----------- |
| mobileNumber       | 是       | String | 手机号码     |
| password           | 是       | String | 密码         |
| spbillCreateIp     | 否       | String | IP地址       |
| mac                | 是       | String | mac地址      |
| appVersion         | 是       | String | app版本      |
| phoneSystemVersion | 是       | String | 手机系统版本 |
| phoneModel         | 是       | String | 手机型号     |
| phoneCompany       | 是       | String | 手机所属公司 |

##### 接口调用响应说明
##### 返回结果示例
正常情况下，系统会返回下述JSON数据包
```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1525419656301,
 "data": {
  "userid": 6,
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiZXhwIjoxNTI1NTA2MDU2fQ.DxwC_tp7ytgBbt3sczDldfT24f5zeHW5-TeHAmB4B6GRNdu6CVojGb60XfDb9m9-SA5gQarR3A4gBQy_xmbHug",
  "mobileNumber": "15308962307",
  "avatar": null,
  "name": null,
  "idcard": null,
  "hxUsername": "087987",
  "hxPassword": "2e333185e663a30bbf95c0c964d7b6c3",
  "inviteCode": "5C02",
  "nickname": "fumin",
  "existEosWallet" : false
 }
}
```

##### 返回结果说明

| 属性名称       | 类型    | 说明               |
| :------------- | :------ | :----------------- |
| userid         | long    | 用户id             |
| token          | String  | token              |
| mobileNumber   | String  | 手机号码           |
| avatar         | String  | 头像地址           |
| name           | String  | 真名               |
| idcard         | String  | 身份证号码         |
| hxUsername     | String  | 环信账户           |
| hxPassword     | String  | 环信密码           |
| inviteCode     | String  | 邀请码             |
| nickname       | String  | 昵称               |
| existEosWallet | Boolean | 判断有没有注册钱包 |

异常情况下，系统会返回下述JSON数据包

```
{
 "code": 500,
 "message": "异常原因",
 "timestamp": 1502867409613
}
```

### 

### 2.0 EOS钱包模块

- eos模块
  - 创建钱包
  - p2p转账交易
  - 赞赏充值
  - eos迁移账户（重新创建eos账户，账户名不变）
  - 转移用户eos币
  - 锁仓转移


####2.1.1 创建钱包
##### 业务流程
```flow
st=>start: Start
io1=>inputoutput: 输入钱包名，交易密码

op=>operation: 判断钱包名是否已经注册
op2=>operation: 调用eos接口注册钱包
op3=>operation: 加密保存用户钱包私钥并保存交易密码

cond=>condition: Yes or No?
cond2=>condition: Yes or No?

e=>end
st->io1->op->cond
cond(yes)->op2->cond2
cond(no)->io1
cond2(yes)->op3->e
cond2(no)->io1
```

##### 接口调用请求说明

##### HTTP请求方式

> POST http://ip:port/connectionChain/api/app/v2/eos/wallet/create

#####请求参数格式： "Content-type","application/json; charset=utf-8"

##### 请求参数说明

| 参数名称   | 是否必须 | 类型   | 说明                               |
| :--------- | :------- | :----- | :--------------------------------- |
| userid     | 是       | String | 用户id                             |
| walletName | 是       | String | 钱包名称（不能包含特殊字符和中文） |
| passPhrase | 是       | String | 钱包交易密码                       |
| remarks    | 是       | String | 备注                               |

##### 返回结果示例

正常情况下，系统会返回下述JSON数据包

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1526117940927
}
```

#####  返回失败示例

```
{
 "code": 300,
 "message": "失败",
 "timestamp": 1526117856235
}
```

#####  返回结果说明

|属性名称|类型|说明|
|:---|:---|:---|

####2.1.2 p2p转账交易
##### 业务流程
```flow
st=>start: Start
io1=>inputoutput: 输入对方钱包名，交易数量，交易密码

op=>operation: 判断是否有免密设置，判断金额是否超过免密金额，判断交易密码是否正确
op2=>operation: 调用eos转账接口
op3=>operation: 保存交易记录

cond=>condition: Yes or No?
cond2=>condition: 转账成功 or 转账失败?

e=>end
st->io1->op->cond
cond(yes)->op2->cond2
cond(no)->io1
cond2(yes)->op3->e
cond2(no)->io1
```

##### 接口调用请求说明

##### HTTP请求方式

> POST https://ip:port/connectionChain/api/app/v2/eos/wallet/transaction
>
> POST https://ip:port/connectionChain/api/app/v1/eos/wallet/transaction
>

#####请求参数格式： "Content-type","application/json; charset=utf-8"

##### 请求参数说明

| 参数名称            | 是否必须 | 类型   | 说明                                 |
| :------------------ | :------- | :----- | :----------------------------------- |
| userid              | 是       | String | 用户id                               |
| toAccount           | 是       | String | 转入账户                             |
| num                 | 是       | String | 麦钻数量                             |
| transactionPassword | 否       | String | 交易密码（超过免密码金额时不能为空） |
| desc                | 否       | String | 备注                                 |

#### 返回结果示例

正常情况下，系统会返回下述JSON数据包（和以前一样）

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1526462946278,
}

```

#### 返回失败示例

```
{
 "code": 300,
 "message": "失败",
 "timestamp": 1526117856235
}

```

#### 返回结果说明

| 属性名称 | 类型 | 说明 |
| :------- | :--- | :--- |
|          |      |      |

####2.1.3 赞赏充值
##### 业务流程

```flow
st=>start: Start
io1=>inputoutput: 输入赞赏金额，选择支付方式

op=>operation: 根据当日价格计算购买币的数量，生成内部订单
op1=>operation: 调用微信或支付宝进行签名，（微信支付后台执行二次签名，无需客户端签名）

cond=>condition: 金额正确 or 金额错误?

e=>end

st->io1->cond
cond(yes)->op->op1->e
cond(no)->io1

```
##### 接口调用请求说明

##### HTTP请求方式

> POST http://ip:port/connectionChain/api/app/v2/shopping/appPlace/order

##### 请求参数格式： "Content-type","application/json; charset=utf-8"

##### 请求参数说明

| 参数名称       | 是否必须 | 类型   | 说明                            |
| -------------- | -------- | ------ | ------------------------------- |
| amount         | 否       | String | 购买数量                        |
| money          | 是       | String | 购买金额                        |
| payment        | 是       | String | 购买渠道（payWeiXin or alipay） |
| productCode    | 是       | String | 商品编码                        |
| redirect_url   | 否       | String | 跳转地址                        |
| spbillCreateIp | 否       | String | ip地址                          |
| userid         | 是       | String | 用户id                          |

##### 返回结果示例 A（支付宝支付返回结果）

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1526703722180,
 "data": {
   "aliSign": "alipay_sdk=alipay-sdk-java-3.0.52.ALL&app_id=2018053160321389&biz_content=%7B%22body%22%3A%22%E8%B5%9E%E8%B5%8F%E9%BA%A6%E9%92%BB%22%2C%22out_trade_no%22%3A%221527763191649901%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%B5%9E%E8%B5%8F%E9%BA%A6%E9%92%BB%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2210%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Ftest.braindeep.cn%3A8822%2FconnectionChain%2Fv2%2Fshopping%2FalipayNotify&sign=KW8piAlhj1nXLZkly1T6mHgqTKfFkQ%2BomnelvTiPn0TOyq8JA%2BxIk35G%2FPZ5AljiyKs%2B8VZA%2BldtVm8o1QUgmlP8iIV0kqcMfs4zLpBWoXpFwUZNBtEVDVn6Ld8cL7N1PyW8COORZz%2BMvyZh8fq%2BQkeBZ1%2Fq5UcEt7c9jeI4si9jqxWKZp9AqUAIAqTWcPHkOIDFGTiAJXIJOBR24ohZgDl7P28vBMgaWnJuDu%2FOGpiFsRRJXLzYKS6j4u56LikEIp4PeQuaxC9rkpxbuNV9KimgWSFG2amgotingRcClM7NqFdynxm9wlQdemXcAr9c4GpEraDp4LZoXQY3PY6hhw%3D%3D&sign_type=RSA2&timestamp=2018-05-31+18%3A39%3A51&version=1.0"
 }
}

```

##### 返回结果说明

| 属性名称 | 类型   | 说明               |
| :------- | :----- | :----------------- |
| aliSign  | String | 拉起支付宝支付链接 |

##### 返回结果示例 B（微信支付返回结果，已经完成二次签名处理）

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1527909587635,
 "data": {
  	"appid": "wx5607a931db863583",
  "partnerid": "1505428581",
  "prepayid": "wx02111801413365618e6b43230878857620",
  "noncestr": "Iq1xrjVlKfVbn12g",
  "package": "Sign=WXPay",
  "timestamp": "123456789",
  "sign": "26C66DDADCFE835A9FFAE2CE2D3EC2B6",
 }
}

```

##### 返回结果说明

| 属性名称  | 类型   | 说明             |
| :-------- | :----- | :--------------- |
| noncestr  | String | 随机字符串       |
| appid     | String | 应用ID           |
| sign      | String | 签名             |
| package   | String | package扩展字段  |
| partnerid | String | 商户号           |
| prepayid  | String | 预支付交易会话ID |
| timestamp | String | 时间戳           |

#### 2.1.4 ！ eos迁移账户（重新创建eos账目，账户名不变），如果无法查明原因，只能还原eos，重新创建一遍账户

##### 业务流程

```flow
st=>start: Start
io1=>inputoutput: 输入操作密码 password：xiaodao27821306

op=>operation: 读取所有用户账户名，调用eos4j工具生成新的秘钥并把私钥导入账户
op1=>operation: 返回私钥和公钥，使用AES加密保存用户私钥
op2=>operation: 保存失败钱包，isDeleted状态设置为true

cond=>condition: Yes or No?

cond1=>condition: Yes or No?

e=>end

st->io1->cond
cond(yes)->op->cond1(yes)->op1->e
cond(no)->e
cond1(no)->op2->e


```

##### 接口调用请求说明

##### HTTP请求方式

> POST http://ip:port/connectionChain/api/app/v2/eos/wallet/moveWallet

##### 请求参数格式： "Content-type","form-data; charset=utf-8"

##### 请求参数说明

| 参数名称 | 是否必须 | 类型   | 说明     |
| -------- | -------- | ------ | -------- |
| password | 是       | String | 操作密码 |

##### 返回结果示例 

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1526703722180,
}
```

##### 返回结果说明

| 属性名称 | 类型 | 说明 |
| :------- | :--- | :--- |
|          |      |      |

#### 2.1.5 ！ 转移eos币， 给用户发币

##### 业务流程

```flow
st=>start: Start
io1=>inputoutput: 输入操作密码 password：xiaodao27821306

op=>operation: 根据用户eos交易记录计算余额（入账-出账=余额），使用平台账户给用户转币
op1=>operation: 保存用户迁币记录，交易记录状态为 tran_state：6（用户不可见）
op2=>operation: 保存迁币失败记录（用户不可见，看日志查询原因）

cond=>condition: Yes or No?

cond1=>condition: Yes or No?

e=>end

st->io1->cond
cond(yes)->op->cond1(yes)->op1->e
cond(no)->e
cond1(no)->op2->e


```

##### 接口调用请求说明

##### HTTP请求方式

> POST http://ip:port/connectionChain/api/app/v2/eos/wallet/moveTransaction

##### 请求参数格式： "Content-type","form-data; charset=utf-8"

##### 请求参数说明

| 参数名称 | 是否必须 | 类型   | 说明     |
| -------- | -------- | ------ | -------- |
| password | 是       | String | 操作密码 |

##### 返回结果示例 

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1526703722180,
}
```

##### 返回结果说明

| 属性名称 | 类型 | 说明 |
| :------- | ---- | ---- |
|          |      |      |
#### 2.1.6 锁仓转移

##### 业务流程

```flow
st=>start: Start
io1=>inputoutput: 输入交易密码，对方账户，转账金额
op=>operation: 判断转账金额是否正确，判断余额是否充足，判断交易密码是否正确（超过免密金额）
op1=>operation: 发起转账交易
cond=>condition: Yes or No?
cond1=>condition: Yes or No?
e=>end
st->io1->op->cond
cond(yes)->op1->cond1
cond(no)->e
cond1(yes)->e
cond1(no)->e
```
##### 接口调用请求说明

##### HTTP请求方式

> POST http://ip:port/connectionChain/api/app/v2/eos/wallet/lock_transaction

##### 请求参数格式： "Content-type","application/json; charset=utf-8"

##### 请求参数说明

| 参数名称            | 是否必须 | 类型   | 说明     |
| ------------------- | -------- | ------ | -------- |
| password            | 否       | String | 操作密码 |
| userid              | 是       | String | 用户id   |
| toAccount           | 是       | String | 转入账户 |
| num                 | 是       | String | 转移数量 |
| transactionPassword | 否       | String | 交易密码 |
| desc                | 否       | String | 备注信息 |

##### 返回结果示例 

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1526703722180,
}
```

##### 返回结果说明

| 属性名称 | 类型 | 说明 |
| :------- | ---- | ---- |
|          |      |      |

### 3.0 矿包模块

-  矿包

  - 发矿包

  - 抢矿包
  - 获取矿包列表


####3.1 发矿包

##### 业务流程
```flow
st=>start: Start
io1=>inputoutput: 输入矿包金额，矿包数量，矿包标题

op=>operation: 判断余额是否不足
op2=>operation: 计算红包随机数并保存矿包数据，转账给平台临时账户
op3=>operation: 设置redis矿包键，24小时失效监听是否有剩余红包，返还给用户

cond=>condition: Yes or No?


e=>end
st->io1->op->cond
cond(yes)->op2->op3->e
cond(no)->io1

```
##### 接口调用请求说明

##### HTTP请求方式

> POST https://ip:port/connectionChain/api/app/v2/ore/create/envelope
>
> POST https://ip:port/connectionChain/api/app/v1/ore/create/envelope

##### 请求参数格式： "Content-type","application/json; charset=utf-8"
##### 请求参数说明

| 参数名称            | 是否必须 | 类型    | 说明                                         |
| ------------------- | -------- | ------- | -------------------------------------------- |
| userid              | 是       | String  | 用户id                                       |
| count               | 是       | Integer | 矿包数量                                     |
| amount              | 是       | String  | 矿包金额                                     |
| ore_title           | 是       | String  | 标题                                         |
| transactionPassword | 否       | String  | 交易密码（当金额小于自己设定的金额可以为空） |

##### 返回结果示例

```
{
 "code": 200,
 "message": "执行成功",
 "timestamp": 1526703722180,
}
```
##### 返回结果说明（和以前一样）

| 属性名称 | 类型 | 说明 |
| :------- | :--- | :--- |
|          |      |      |

#### 3.2 抢矿包

##### 业务流程
```flow
st=>start: Start
op=>operation: 判断矿包是否抢完，判断用户是否已经抢过矿包，判断矿包是否超过24小时有效期
op1=>operation: 抢矿包成功，给用户发放奖励并保存记录 
cond=>condition: Yes or No?
e=>end

st->op->cond
cond(yes)->op1->e
cond(no)->e

```
##### 接口调用请求说明

##### HTTP请求方式

> POST https://ip:port/connectionChain/api/app/v1/ore/gra/envelope
>
> POST https://ip:port/connectionChain/api/app/v2/ore/gra/envelope

##### 请求参数格式： "Content-type","application/json; charset=utf-8"
##### 请求参数说明

| 参数名称      | 是否必须 | 类型   | 说明   |
| ------------- | -------- | ------ | ------ |
| userid        | 是       | String | 用户id |
| oreEnvelopeId | 是       | String | 矿包id |

##### 返回结果示例

```
{
  "code": 200,
  "message": "矿包已经抢完了！！！",
  "timestamp": 1534494771746,
  "data": {
    "userId": 65,
    "oreEnvelopeId": 21,
    "randomNumber": "0",
    "oreTitle": "大吉大利！",
    "sum": 3,
    "count": 3,
    "vie": 0,
    "nickname": "元",
    "avatar": "http://p5o7ose2w.bkt.clouddn.com/2018/06/30/249317dd-5873-420a-991b-e6991041f1de.png",
    "grabOreEnvelopeList": [
      {
        "id": 4,
        "userId": 59,
        "oreEnvelopeId": 21,
        "randomNumber": 5.4605,
        "nickname": "督查室",
        "avatar": "http://p5o7ose2w.bkt.clouddn.com/2018/06/12/98cd8c20-1c26-445a-8b52-63ebc5e45fae.png",
        "gmtCreate": "2018-05-19 18:25:04"
      },
      {
        "id": 5,
        "userId": 57,
        "oreEnvelopeId": 21,
        "randomNumber": 3.6235,
        "nickname": "澎超",
        "avatar": "http://p5o7ose2w.bkt.clouddn.com/2018/06/12/381cee86-0af1-4a0d-b021-9cb97deef4a6.jpg",
        "gmtCreate": "2018-05-19 18:25:25"
      },
      {
        "id": 7,
        "userId": 65,
        "oreEnvelopeId": 21,
        "randomNumber": 0.916,
        "nickname": "元",
        "avatar": "http://p5o7ose2w.bkt.clouddn.com/2018/06/30/249317dd-5873-420a-991b-e6991041f1de.png",
        "gmtCreate": "2018-05-19 20:47:00"
      },
      {
        "id": 15,
        "userId": 75,
        "oreEnvelopeId": 21,
        "randomNumber": 0,
        "nickname": "Xmx",
        "avatar": "http://p5o7ose2w.bkt.clouddn.com/9b93fa8c9a9cef73588e15f83d3333f2.jpg",
        "gmtCreate": "2018-05-20 21:03:41"
      },
      {
        "id": 29,
        "userId": 77,
        "oreEnvelopeId": 21,
        "randomNumber": 0,
        "nickname": "兒贝",
        "avatar": "http://p5o7ose2w.bkt.clouddn.com/9b93fa8c9a9cef73588e15f83d3333f2.jpg",
        "gmtCreate": "2018-05-20 23:29:07"
      },
      {
        "id": 47,
        "userId": 98,
        "oreEnvelopeId": 21,
        "randomNumber": 0,
        "nickname": "黄婷",
        "avatar": "http://p5o7ose2w.bkt.clouddn.com/9b93fa8c9a9cef73588e15f83d3333f2.jpg",
        "gmtCreate": "2018-05-21 00:33:08"
      },
      {
        "id": 304,
        "userId": 118,
        "oreEnvelopeId": 21,
        "randomNumber": 0,
        "nickname": "好在忒帅错在帅炸",
        "avatar": "http://p5o7ose2w.bkt.clouddn.com/9b93fa8c9a9cef73588e15f83d3333f2.jpg",
        "gmtCreate": "2018-05-21 11:56:17"
      }
    ]
  }
}
```
##### 返回结果说明

| 属性名称      | 类型   | 说明                     |
| :------------ | :----- | :----------------------- |
| userId        | 长整型 | 用户id                   |
| oreEnvelopeId | 长整型 | 矿包id                   |
| randomNumber  | 浮点型 | 强大矿包的金额           |
| oreTitle      | 字符串 | 矿包标题                 |
| sum           | 整型   | 矿包总数                 |
| count         | 整型   | 矿包已经抢的数量         |
| vie           | 整型   | 1-抢到矿包，0-没抢到矿包 |
| nickname      | 字符串 | 昵称                     |
| avatar        | 字符串 | 头像                     |
| grabOreEnvelopeList        | list对象 | 抢过矿包的记录，randomNumber=0表示没有抢到 |

#### 3.3 获取矿包列表

##### 业务流程


##### 接口调用请求说明

##### HTTP请求方式

> POST https://ip:port/connectionChain/api/app/v1/ore/gra/envelope
>
> POST https://ip:port/connectionChain/api/app/v2/ore/gra/envelope

##### 请求参数格式： "Content-type","application/json; charset=utf-8"

##### 请求参数说明

| 参数名称      | 是否必须 | 类型   | 说明   |
| ------------- | -------- | ------ | ------ |
| userid        | 是       | String | 用户id |
| oreEnvelopeId | 是       | String | 矿包id |

##### 返回结果示例

```
{
  "code": 200,
  "message": "执行成功",
  "timestamp": 1534497814996,
  "data": [
    {
      "id": 2382,
      "userId": 490,
      "count": 19,
      "oreTitle": "大吉大利！",
      "oreState": true,
      "userIsOpen": true,
      "avatar": "http://p5o7ose2w.bkt.clouddn.com/2018/08/06/cfaa48a7-d4ea-4961-aa7c-6284b7f1945a.png",
      "nickname": "嘎嘎买",
      "gmtCreate": "2018-08-08 10:31:17"
    },
    {
      "id": 2381,
      "userId": 269,
      "count": 8,
      "oreTitle": "大吉大利！",
      "oreState": true,
      "userIsOpen": false,
      "avatar": "http://p5o7ose2w.bkt.clouddn.com/2018/06/19/e25387e9-78c5-4218-bce1-7edd9de61886.png",
      "nickname": "maichain",
      "gmtCreate": "2018-07-11 10:23:10"
    }
  ]
}
```

##### 返回结果说明

| 属性名称   | 类型   | 说明                 |
| :--------- | :----- | :------------------- |
| id         | 长整型 | 矿包id               |
| userId     | 长整型 | 用户id               |
| count      | 整型   | 矿包数量             |
| oreTitle   | 字符串 | 矿包标题             |
| oreState   | 布尔型 | 矿包状态             |
| userIsOpen | 布尔型 | 当前用户是否抢过矿包 |
| avatar     | 字符串 | 头像                 |
| nickname   | 字符串 | 昵称                 |
| gmtCreate  | 字符串 | 创建时间             |