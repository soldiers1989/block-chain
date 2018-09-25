package com.xdaocloud.futurechain.config.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * spring 拦截器配置
 *
 * @author LuoFuMin
 * @data 2018/7/13
 */
//@Configuration
public class WebFilterConfig extends WebMvcConfigurerAdapter {


    @Bean
    WebHandlerInterceptor webHandlerInterceptor() {
        return new WebHandlerInterceptor();
    }

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webHandlerInterceptor());
    }


}
