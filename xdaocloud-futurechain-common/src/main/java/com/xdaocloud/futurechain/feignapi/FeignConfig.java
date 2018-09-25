
package com.xdaocloud.futurechain.feignapi;


import feign.Contract;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * 添加请求头
 *
 * @author LuoFuMin
 * @data 2018/9/3
 */

@Configuration
public class FeignConfig {
    private final Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    //使用Feign自己的注解，使用springmvc的注解就会报错

    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String key = headerNames.nextElement();
                        String value = request.getHeader(key);
                        //遍历请求头里面的属性字段
                       /* logger.info("添加自定义请求头key:" + key + ",value:" + value);*/
                        requestTemplate.header(key, value);
                    }
                } else {
                    logger.error("FeignHeadConfig", "获取请求头失败！");
                }
            }
        };
    }

}
