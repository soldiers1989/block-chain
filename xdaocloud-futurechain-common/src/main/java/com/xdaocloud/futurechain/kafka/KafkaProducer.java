/*
package com.xdaocloud.futurechain.kafka;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

*/
/**
 * 消息提供者
 * 
 * @author WangPengHua
 *//*

//@Component
public class KafkaProducer {

    */
/**
     * 邮件消息
     *//*

    private static final String TOPIC_EMAIL = "xdao_mcm_email";
    */
/**
     * 个推消息
     *//*

    private static final String TOPIC_MSG = "xdao_mcm_msg";
    */
/**
     * 短信消息
     *//*

    private static final String TOPIC_SMS = "xdao_mcm_sms";

    */
/**
     * kafkaTemplate
     *//*

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    */
/**
     * 发送个推消息
     * 
     * @param message 消息内容
     * @date 2017年8月24日
     * @author WangPengHua
     *//*

    @Async
    public void sendMsg(KafkaMessage kafkaMessage) {
        kafkaMessage.setAppCode("楼楼通");
        kafkaMessage.setSysCode("楼楼通");
        kafkaTemplate.send(TOPIC_MSG, JSON.toJSONString(kafkaMessage));
    }

    */
/**
     * 发送邮件消息
     * 
     * @param message 消息内容
     * @date 2017年8月24日
     * @author WangPengHua
     *//*

    @Async
    public void sendEmail(KafkaMessage kafkaMessage) {
        kafkaMessage.setAppCode("楼楼通");
        kafkaMessage.setSysCode("楼楼通");
        kafkaTemplate.send(TOPIC_EMAIL, JSON.toJSONString(kafkaMessage));
    }

    */
/**
     * 发送短信消息
     * 
     * @param message 消息内容
     * @date 2017年8月24日
     * @author WangPengHua
     *//*

    @Async
    public void sendSms(KafkaMessage kafkaMessage) {
        kafkaMessage.setAppCode("楼楼通");
        kafkaMessage.setSysCode("楼楼通");
        kafkaTemplate.send(TOPIC_SMS, JSON.toJSONString(kafkaMessage));
    }
}*/
