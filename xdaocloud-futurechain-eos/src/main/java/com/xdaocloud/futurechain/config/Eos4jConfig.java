package com.xdaocloud.futurechain.config;

import io.eblock.eos4j.Rpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化eos4j连接工具
 *
 * @author LuoFuMin
 * @data 2018/7/23
 */
@Configuration
public class Eos4jConfig {

    @Value("${eos.address}")
    public String eos_address;

    //初始化eos4j工具
    @Bean
    public Rpc CreateRpcClient() {
        Rpc rpc = new Rpc(eos_address);
        return rpc;
    }

}
