package com.lingcaibao.entity;


public class Role {

	//alias
	public static final String TABLE_ALIAS = "Role";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_DESCRIPTION = "description";
	public static final String ALIAS_RULE = "rule";

	/**
	 * 
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色描述
	 */
	private String description;
	/**
	 * 优先级
	 */
	private Integer rule;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
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
	public java.lang.Integer getRule() {
		return this.rule;
	}
	
	public void setRule(java.lang.Integer value) {
		this.rule = value;
	}
}