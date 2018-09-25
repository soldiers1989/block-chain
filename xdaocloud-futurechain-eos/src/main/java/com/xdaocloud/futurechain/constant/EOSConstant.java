package com.xdaocloud.futurechain.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author LuoFuMin
 * @data 2018/7/23
 */
@Configuration
public class EOSConstant {


    /**
     * 父账户
     */
    @Value("${eos.eosio}")
    public String eosio;

    /**
     * 私钥
     */
    @Value("${eos.eosio_private_key}")
    public String eosio_private_key;

    /**
     * 创始人团队账户
     */
    @Value("${eos.maifounder}")
    public String maifounder;

    /**
     * 私钥
     */
    @Value("${eos.maifounder_private_key}")
    public String maifounder_private_key;

    /**
     * 经营管理团队账户
     */
    @Value("${eos.maioperate}")
    public String maioperate = "maioperate";

    /**
     * 私钥
     */
    @Value("${eos.maioperate_private_key}")
    public String maioperate_private_key;

    /**
     * 平台维护账户
     */
    @Value("${eos.maiplatform}")
    public String maiplatform;

    /**
     * 私钥
     */
    @Value("${eos.maiplatform_private_key}")
    public String maiplatform_private_key;

    /**
     * 市场募集
     */
    @Value("${eos.maimarket}")
    public String maimarket;

    /**
     * 私钥
     */
    @Value("${eos.maimarket_private_key}")
    public String maimarket_private_key;

    /**
     * 手续费账户
     */
    @Value("${eos.maifee}")
    public String maifee = "maifee";

    /**
     * 私钥
     */
    @Value("${eos.maifee_private_key}")
    public String maifee_private_key;

    /**
     * 临时账户
     */
    @Value("${eos.maitemp}")
    public String maitemp;

    /**
     * 私钥
     */
    @Value("${eos.maitemp_private_key}")
    public String maitemp_private_key;

    /**
     * 代币账户
     */
    @Value("${eos.maitoken}")
    public String maitoken = "maitoken";

    /**
     * 私钥
     */
    @Value("${eos.maitoken_private_key}")
    public String maitoken_private_key;

}
