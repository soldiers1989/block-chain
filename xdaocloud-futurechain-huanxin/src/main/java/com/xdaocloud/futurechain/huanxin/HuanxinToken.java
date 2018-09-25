package com.xdaocloud.futurechain.huanxin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
//@CacheConfig(cacheNames = "huanxinTokenCache", keyGenerator = "myKeyGenerator")
public class HuanxinToken {

    private static Logger LOG = LoggerFactory.getLogger(HuanxinToken.class);

    /**
     * 微服务调用类
     */
    @Resource(name = "http-client")
    private RestTemplate restTemplate;

    @Autowired
    private HXConfig config;


    /**
     * 环信获取token
     *
     * @return token信息
     * @date 2017年11月7日
     * @author ShiLijin
     */
    //@Cacheable(value = "cacheName:generateToken")
    public String generateToken() {
        String url = "http://a1.easemob.com/" + config.getOrgName() + "/" + config.getAppName() + "/" + "token";
        Map<String, Object> request = new HashMap<String, Object>();
        request.put("grant_type", "client_credentials");
        request.put("client_id", config.getClientId());
        request.put("client_secret", config.getClientSecret());
        return restTemplate.postForObject(url, request, TokenDTO.class).getAccess_token();
    }
}
