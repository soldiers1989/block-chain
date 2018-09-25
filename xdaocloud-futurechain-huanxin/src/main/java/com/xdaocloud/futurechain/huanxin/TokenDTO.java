package com.xdaocloud.futurechain.huanxin;

public class TokenDTO {

	/**
	 * 	token 值
	 */
	private String access_token;
	/**
	 * token 有效时间，以秒为单位，在有效期内不需要重复获取
	 */
	private String expires_in;
	/**
	 * 当前 APP 的 UUID 值
	 */
	private String application;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
	
}
