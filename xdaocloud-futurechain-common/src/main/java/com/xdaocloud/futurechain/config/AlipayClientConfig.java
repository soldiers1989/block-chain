package com.xdaocloud.futurechain.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.xdaocloud.futurechain.constant.AlipayConstant.*;

@Configuration
public class AlipayClientConfig {

    //初始化支付宝工具
    @Bean
    AlipayClient alipayClient() {
        return new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }
}
