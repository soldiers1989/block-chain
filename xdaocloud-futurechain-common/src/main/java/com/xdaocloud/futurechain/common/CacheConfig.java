package com.xdaocloud.futurechain.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Configuration
//@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    @Autowired
    private CacheManager cacheManager;

    @Bean
    public KeyGenerator myKeyGenerator() {
        return new KeyGenerator() {

            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(".").append(method.getName()).append("_");
                for (Object obj : params) {
                    if (obj != null) {
                        sb.append(JSON.toJSONString(obj));
                    }
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public CacheResolver cacheResolver() {
        return new CacheResolver() {

            public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
                List<Cache> caches = new ArrayList<Cache>();
                caches.add(cacheManager.getCache(context.getTarget().getClass().getName()));
                return caches;
            }
        };
    }

    @Bean
    public CacheManager cacheManager(@Value("${spring.redis.expiration}") Long expiration, RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        // 设置缓存过期时间
        LOGGER.info("The cache expiration is " + expiration);
        rcm.setDefaultExpiration(expiration);// 秒
        return rcm;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory, RedisSerializer<?> fastJson2JsonRedisSerializer) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setValueSerializer(fastJson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {

            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                LOGGER.error("cache get error");
            }

            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                LOGGER.error("cache put error");
            }

            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                LOGGER.error("cache evict error");
            }

            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                LOGGER.error("cache clear error");
            }
        };
    }
}

