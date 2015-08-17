package com.lingcaibao.entity;


public class UserRole {

	//alias
	public static final String TABLE_ALIAS = "UserRole";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ROLEID = "roleid";
	public static final String ALIAS_USERID = "userid";

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private Long roleid;
	/**
	 * 
	 */
	private Long userid;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getRoleid() {
		return this.roleid;
	}
	
	public void setRoleid(java.lang.Long value) {
		this.roleid = value;
	}
	public java.lang.Long getUserid() {
		return this.userid;
	}
	
	public void setUserid(java.lang.Long value) {
		this.userid = value;
	}
}