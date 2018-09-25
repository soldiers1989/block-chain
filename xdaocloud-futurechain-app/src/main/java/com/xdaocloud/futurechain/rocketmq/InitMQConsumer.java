package com.xdaocloud.futurechain.rocketmq;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xdaocloud.futurechain.dto.user.UserCenterDTO;
import com.xdaocloud.futurechain.model.User;
import com.xdaocloud.futurechain.service.UserService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * rockermq 消息队列 消费者
 *
 * @author LuoFuMin
 * @data 2018/8/24
 */
@Component
@Order(1)
public class InitMQConsumer implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(InitMQConsumer.class);

    @Value("${rocketmq.address}")
    String namesrvAddr;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        /**
         * 集群消费 : consumer group
         * 消费同一类消息的多个 consumer 实例组成一个消费者组，
         * 也可以称为一个 consumer 集群，这些 consumer 实例使用同一个group name。
         * 需要注意一点，除了使用同一个group name，订阅的tag也必须是一样的，
         * 只有符合这两个条件的 consumer 实例才能组成 consumer 集群。
         *
         * 当 consumer 使用集群消费时，每条消息只会被 consumer 集群内的任意一个 consumer 实例消费一次。
         * 举个例子，当一个 consumer 集群内有 3 个consumer 实例（假设为consumer 1、consumer 2、consumer 3）时，一条消息投递过来，只会被consumer 1、consumer 2、consumer 3中的一个消费。
         * 同时记住一点，使用集群消费的时候，consumer 的消费进度是存储在 broker 上，consumer 自身是不存储消费进度的。
         * 消息进度存储在 broker 上的好处在于，当你 consumer 集群是扩大或者缩小时，由于消费进度统一在broker上，消息重复的概率会被大大降低了。
         * 注意：在集群消费模式下，并不能保证每一次消息失败重投都投递到同一个 consumer 实例。
         */
        //声明并初始化一个consumer,需要一个consumer group名字作为构造方法的参数;
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xdaocloud-maichain");
        //同样也要设置NameServer地址
        consumer.setNamesrvAddr(namesrvAddr);
        /**
         *  这里设置的是一个consumer的消费策略
         * CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
         * CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
         * CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置consumer所订阅的Topic和Tag，*代表全部的Tag
        consumer.subscribe("top-user-sync", "TagA");
        /**
         *  消费模式：顺序消费；
         *  设置一个Listener，主要进行消息的逻辑处理，
         *  MessageListenerOrderly表示一个队列只会被一个线程取到 ，第二个线程无法访问这个队列,Toppic可以有多个队列。
         */
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext consumeOrderlyContext) {
                //返回消费状态 //CONSUME_SUCCESS 消费成功 //RECONSUME_LATER 消费失败，需要稍后重新消费
                for (MessageExt messageExt : msgs) {
                    String data = byteArrayToStr(messageExt.getBody());
                    LOG.info("》》》 rocketMQ messageExt：" + messageExt);
                    LOG.info("》》》 rocketMQ messageExt：getMsgId  =  " + messageExt.getMsgId());
                    LOG.info("》》》 rocketMQ messageExt：getTransactionId  =  " + messageExt.getTransactionId());
                    LOG.info("》》》 rocketMQ 消息：" + data);
                    UserCenterDTO userCenter = JSON.parseObject(data, new TypeReference<UserCenterDTO>() {
                    });
                    if (updateUserInfo(userCenter)) {
                        return ConsumeOrderlyStatus.SUCCESS;
                    } else {
                        // 返回 SUSPEND_CURRENT_QUEUE_A_MOMENT mq 消息会继续推送未成功消费的消息
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                }
                //返回消费状态
                //CONSUME_SUCCESS 消费成功
                //RECONSUME_LATER 消费失败，需要稍后重新消费
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //调用start()方法启动consumer
        consumer.start();
        LOG.info("》》》 Consumer Started.");
    }

    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }

    /**
     * 更新用户资料
     *
     * @param userCenter
     * @return
     */
    public Boolean updateUserInfo(UserCenterDTO userCenter) {
        if (userCenter == null || userCenter.getId() == null) {
            return false;
        }
        User user = new User(userCenter.getId());
        //更新头像
        if (userCenter.getAvatar() != null) {
            user.setAvatar(userCenter.getAvatar());
        }
        //更新昵称
        if (userCenter.getNickname() != null) {
            user.setNickname(userCenter.getNickname());
        }
        //更新真实姓名
        if (userCenter.getName() != null) {
            user.setName(userCenter.getName());
        }
        //更新身份证号码
        if (userCenter.getIdcard() != null) {
            user.setIdcard(userCenter.getIdcard());
        }
        //更新性别
        if (userCenter.getSex() != null) {
            user.setSex(userCenter.getSex());
        }
        //更新邮箱
        if (userCenter.getEmail() != null) {
            user.setEmail(userCenter.getEmail());
        }
        //保存用户名
        if (userCenter.getUsername() != null) {
            user.setUsername(userCenter.getUsername());
        }
        //保存手机号码
        if (userCenter.getPhone() != null) {
            user.setMobileNumber(userCenter.getPhone());
        }
        //保存时间
        if (userCenter.getUpdateTime() != null) {
            user.setGmtCreate(DateUtil.date(Long.valueOf(userCenter.getUpdateTime())));
        }
        return userService.syncUser(user);
    }
}
