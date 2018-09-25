package com.xdaocloud.futurechain.httpapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Retrofit 配置
 *
 * @author LuoFuMin
 * @data 2018/9/11
 */
@Configuration
public class RetrofitConfig {

    private static final String baseUrl = "https://api.netease.im/nimserver/";

    private static Retrofit buildRetrofit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build();
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(chain -> {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .build();
                Response response = chain.proceed(request);
                return response;
            })
            .build();

    private static final Retrofit retrofit = buildRetrofit(baseUrl, okHttpClient);

    @Bean
    public IMUserService initIMUserService() throws InterruptedException {
        return retrofit.create(IMUserService.class);
    }
    @Bean
    public IMFriendService initIMFriendService() throws InterruptedException {
        return retrofit.create(IMFriendService.class);
    }

    @Bean
    public IMMsgService initIMMsgService() throws InterruptedException {
        return retrofit.create(IMMsgService.class);
    }

    @Bean
    public IMTeamService initIMTeamService() throws InterruptedException {
        return  retrofit.create(IMTeamService.class);
    }

}
