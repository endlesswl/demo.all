package com.lingcaibao.entity;

import java.util.Date;

/**
 * <p>标题：用户分享表 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月19日 下午3:35:46</p>
 * <p>类全名：com.lingcaibao.entity.UserShare</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class UserShare {

	//alias
	public static final String TABLE_ALIAS = "UserShare";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_MARKETID = "marketid";
	public static final String ALIAS_SHAREUSERID = "shareuserid";
	public static final String ALIAS_PARTID = "partid";
	public static final String ALIAS_CREATETIME = "createtime";
	public static final String ALIAS_REMARK = "remark";

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 活动ID
	 */
	private Long marketid;
	/**
	 * 分享用户ID
	 */
	private Long shareuserid;
	/**
	 * 参与用户ID
	 */
	private Long partid;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 备注
	 */
	private String remark;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getMarketid() {
		return this.marketid;
	}
	
	public void setMarketid(java.lang.Long value) {
		this.marketid = value;
	}
	public java.lang.Long getShareuserid() {
		return this.shareuserid;
	}
	
	public void setShareuserid(java.lang.Long value) {
		this.shareuserid = value;
	}
	public java.lang.Long getPartid() {
		return this.partid;
	}
	
	public void setPartid(java.lang.Long value) {
		this.partid = value;
	}
	public Date getCreatetime() {
		return this.createtime;
	}
	
	public void setCreatetime(Date value) {
		this.createtime = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
}