package com.xdaocloud;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = "com.**.mapper")
@EnableEurekaClient
@EnableFeignClients
public class ChainWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChainWebApplication.class, args);
    }

}

