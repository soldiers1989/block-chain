package com.xdaocloud.futurechain.huanxin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "huanxin.config")
public class HXConfig {

    /**
     * 公司名称
     */
	@Value("${huanxin.config.orgName}")
	private String orgName;
    /**
     * App名称
     */
	@Value("${huanxin.config.appName}")
	private String appName;
    /**
     * 应用标识AppKey
     */
	private String appKey = getOrgName() + "#" + getAppName();
    /**
     * ClientID
     */
	@Value("${huanxin.config.clientId}")
	private String clientId;
    /**
     * Client Secret:
     */
	@Value("${huanxin.config.clientSecret}")
	private String clientSecret;
    /**
     * application 不会过期
     */
	private String application;
    /**
     * 接口路径前缀
     */
	private String pathPrefix = "http://a1.easemob.com/" + orgName + "/" + appName + "/";
    
    /**
     * token（默认两个月时效）
     */
	private String token = "YWMtGTUGrPnkEeeesZuxzjh3uAAAAAAAAAAAAAAAAAAAAAEbzo7gyagR54x3pd1pB2lqAgMAAAFg-X-IFQBPGgBI_nzEut0bPstOOi_NnCOcuMpBX6t36VZ_M6EMrekHhg";

	public String getOrgName() {
		return orgName;
	}

	public String getAppName() {
		return appName;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getApplication() {
		return application;
	}

	public String getAppKey() {
		return appKey;
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public String getToken() {
		return token;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	public void setToken(String token) {
		this.token = token;
	}

    
}
