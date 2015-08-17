package com.lingcaibao.entity;


public class Permission {

	//alias
	public static final String TABLE_ALIAS = "Permission";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_RESOURCE = "resource";
	public static final String ALIAS_ACL = "acl";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_DESCRIPTION = "description";

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private Integer resource;
	/**
	 * 
	 */
	private Integer acl;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String description;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Integer getResource() {
		return this.resource;
	}
	
	public void setResource(java.lang.Integer value) {
		this.resource = value;
	}
	public java.lang.Integer getAcl() {
		return this.acl;
	}
	
	public void setAcl(java.lang.Integer value) {
		this.acl = value;
	}
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
}