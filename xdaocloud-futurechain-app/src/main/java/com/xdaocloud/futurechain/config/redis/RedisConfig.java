package com.xdaocloud.futurechain.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@Configuration
public class RedisConfig {

    //CountDownLatch实例
    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    //初始化Redis消息监听器容器， 这个容器加载了RedisConnectionFactory和消息监听器
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@0__:expired"));
        return container;
    }

    //RedisKeyExpiredListener实例
    @Bean
    RedisKeyExpiredListener receiver(CountDownLatch latch) {
        return new RedisKeyExpiredListener(latch);
    }

    //MessageListenerAdapter实例
    @Bean
    MessageListenerAdapter listenerAdapter(RedisKeyExpiredListener redisKeyExpiredListener) {
        return new MessageListenerAdapter(redisKeyExpiredListener, "receiveMessage");
    }

}
