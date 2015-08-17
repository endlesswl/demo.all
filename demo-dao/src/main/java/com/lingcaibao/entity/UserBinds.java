package com.lingcaibao.entity;


public class UserBinds {

	//alias
	public static final String TABLE_ALIAS = "UserBinds";
	public static final String ALIAS_LINGCAI_USER_ID = "lingcaiUserId";
	public static final String ALIAS_OTHER_USER_ID = "otherUserId";
	public static final String ALIAS_SOURCE = "source";
	public static final String ALIAS_FLAG = "flag";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_MOBILE = "mobile";

	/**
	 * 零彩宝用户id
	 */
	private String lingcaiUserId;
	/**
	 * 第三方用户id
	 */
	private String otherUserId;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 状态
	 */
	private String flag;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 手机
	 */
	private String mobile;

	public java.lang.String getLingcaiUserId() {
		return this.lingcaiUserId;
	}
	
	public void setLingcaiUserId(java.lang.String value) {
		this.lingcaiUserId = value;
	}
	public java.lang.String getOtherUserId() {
		return this.otherUserId;
	}
	
	public void setOtherUserId(java.lang.String value) {
		this.otherUserId = value;
	}
	public java.lang.String getSource() {
		return this.source;
	}
	
	public void setSource(java.lang.String value) {
		this.source = value;
	}
	public java.lang.String getFlag() {
		return this.flag;
	}
	
	public void setFlag(java.lang.String value) {
		this.flag = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
}