package com.xdaocloud.futurechain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        Parameter parameter = tokenPar.name("Authorization").description("Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        Parameter parameter2 = tokenPar.name("appId").description("应用id").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(parameter);
        pars.add(parameter2);
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors
            .basePackage("com.xdaocloud.futurechain.controller")).paths(PathSelectors.any()).build().globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("RESTful APIs").version("V1.0").description("").build();
    }
}

