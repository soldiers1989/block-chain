package com.xdaocloud.futurechain.huanxin;

public class UserProperties {

	private String uuid;
	
	private String type;
	
	private Long created;
	
	private Long modified;
	
	private String username;
	
	private Boolean activated;
	
	private String nickname;
	
	private String device_token;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getModified() {
		return modified;
	}

	public void setModified(Long modified) {
		this.modified = modified;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDevice_token() {
		return device_token;
	}

	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}
	
	
}
