package com.xdaocloud.futurechain.huanxin;

import java.util.Date;
import java.util.Map;

public class ResponseRuslt<T> {

	/**
	 * 请求方式
	 */
	private String action;
	/**
	 * 应用
	 */
	private String application;
	/**
	 * 请求参数
	 */
	private Map<String,Object> params;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 完整路径
	 */
	private String uri;
	
	/**
	 * 数据
	 */
	private T data;
	
	/**
	 * 时间戳
	 */
	private Date timestamp;
	/**
	 * 请求时长
	 */
	private Long duration;
	/**
	 * 公司名称
	 */
	private String organization;
	/**
	 * app名称
	 */
	private String applicationName;
	
	/**
	 * http返回的状态码
	 */
	private Integer httpCode = -1;
	
	public Integer getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
