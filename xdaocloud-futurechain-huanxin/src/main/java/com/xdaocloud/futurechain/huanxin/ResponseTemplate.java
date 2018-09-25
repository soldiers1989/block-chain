package com.xdaocloud.futurechain.huanxin;

import java.util.List;
import java.util.Map;

public class ResponseTemplate {

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
	private List<String> data;
	
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	/**
	 * 用户属性
	 */
	private List<UserProperties> entities;
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
	public List<UserProperties> getEntities() {
		return entities;
	}
	public void setEntities(List<UserProperties> entities) {
		this.entities = entities;
	}
	
	
}
