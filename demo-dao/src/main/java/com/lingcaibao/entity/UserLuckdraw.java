package com.lingcaibao.entity;


public class UserLuckdraw {

	//alias
	public static final String TABLE_ALIAS = "UserLuckdraw";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "userid";
	public static final String ALIAS_MARKETID = "marketid";
	public static final String ALIAS_PRIZEID = "prizeid";
	public static final String ALIAS_CREATETIME = "createtime";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_IP = "ip";
	public static final String ALIAS_INTYPE = "intype";
	public static final String ALIAS_SHORTURL = "shorturl";

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userid;
	/**
	 * 活动ID
	 */
	private Long marketid;
	/**
	 * 奖品ID
	 */
	private Long prizeid;
	/**
	 * 创建时间
	 */
	private java.util.Date createtime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 接入类型(0平台 1 IOS 2 android)
	 */
	private Integer intype;
	/**
	 * 短网址
	 */
	private String shorturl;
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getUserid() {
		return this.userid;
	}
	
	public void setUserid(java.lang.Long value) {
		this.userid = value;
	}
	public java.lang.Long getMarketid() {
		return this.marketid;
	}
	
	public void setMarketid(java.lang.Long value) {
		this.marketid = value;
	}
	public java.lang.Long getPrizeid() {
		return this.prizeid;
	}
	
	public void setPrizeid(java.lang.Long value) {
		this.prizeid = value;
	}
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.lang.String getIp() {
		return this.ip;
	}
	
	public void setIp(java.lang.String value) {
		this.ip = value;
	}
	public Integer getIntype() {
		return this.intype;
	}
	
	public void setIntype(Integer value) {
		this.intype = value;
	}

	public String getShorturl() {
		return shorturl;
	}

	public void setShorturl(String shorturl) {
		this.shorturl = shorturl;
	}
}