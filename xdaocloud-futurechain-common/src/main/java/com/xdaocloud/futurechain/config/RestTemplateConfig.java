package com.xdaocloud.futurechain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 初始化RestTemplate请求工具
 *
 * @author LuoFuMin
 * @date 2018/8/24
 */

@Configuration
public class RestTemplateConfig {

    /**
     * 获取http超时时间
     */
    @Value("${http.time.out}")
    private String httpTimeOut;


    /**
     * 初始化spring http工具，访问外网
     *
     * @return
     */
    @Bean("http-client")
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(Integer.parseInt(httpTimeOut));
        httpRequestFactory.setConnectTimeout(Integer.parseInt(httpTimeOut));
        httpRequestFactory.setReadTimeout(Integer.parseInt(httpTimeOut));
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
    }


    /**
     * 微服务，紧限于在SpringCould内使用
     *
     * @return
     */
    @Bean("eureka-client")
    @LoadBalanced
    public RestTemplate restTemplateEurekaClient() {
        return new RestTemplate();
    }
}
