package com.xdaocloud.futurechain.dto.req.huanxin;

public class CreateGroupRequest {

	/**
	 * 群组名称，此属性为必须的
	 */
	private String groupname;
	/**
	 * 群组描述，此属性为必须的
	 */
	private String desc;
	/**
	 * 是否是公开群，此属性为必须的
	 */
	private Boolean common;
	/**
	 * 群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
	 */
	private Integer maxusers;
	/**
	 * 加入群是否需要群主或者群管理员审批，默认是false
	 */
	private Boolean members_only;
	/**
	 * 是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主或者管理员才可以往群里加人。
	 */
	private Boolean allowinvites;
	/**
	 * 群组的管理员，此属性为必须的
	 */
	private String owner;
	/**
	 * 群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）
	 */
	private String[] members;


	public CreateGroupRequest(String groupname, String desc, Boolean common, Integer maxusers, Boolean members_only, Boolean allowinvites, String owner) {
		this.groupname = groupname;
		this.desc = desc;
		this.common = common;
		this.maxusers = maxusers;
		this.members_only = members_only;
		this.allowinvites = allowinvites;
		this.owner = owner;
	}

	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Boolean getCommon() {
		return common;
	}
	public void setCommon(Boolean common) {
		this.common = common;
	}
	public Integer getMaxusers() {
		return maxusers;
	}
	public void setMaxusers(Integer maxusers) {
		this.maxusers = maxusers;
	}
	public Boolean getMembers_only() {
		return members_only;
	}
	public void setMembers_only(Boolean members_only) {
		this.members_only = members_only;
	}
	public Boolean getAllowinvites() {
		return allowinvites;
	}
	public void setAllowinvites(Boolean allowinvites) {
		this.allowinvites = allowinvites;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String[] getMembers() {
		return members;
	}
	public void setMembers(String[] members) {
		this.members = members;
	}


}
