package com.lingcaibao.entity;

import org.apache.commons.lang3.StringUtils;
public class Bank
{
	//alias
	public static final String	TABLE_ALIAS			= "Bank";
	public static final String	ALIAS_ID			= "id";
	public static final String	ALIAS_USERID		= "userid";
	public static final String	ALIAS_CITY			= "city";
	public static final String	ALIAS_BANKNAME		= "bankname";
	public static final String	ALIAS_ABBREVIATION	= "abbreviation";
	public static final String	ALIAS_DESCRIPTION	= "description";
	public static final String	ALIAS_ACCOUNTNAME	= "accountname";
	public static final String	ALIAS_CARDNO		= "cardno";
	public static final String	ALIAS_CARDTYPE		= "cardtype";
	public static final String	ALIAS_DELETEFLAG	= "deleteFlag";
	public static final String	ALIAS_PROVINCE		= "province";
	public static final String	ALIAS_SUBBRANCH		= "subbranch";
	/**
	 * 
	 */
	private Long				id;
	/**
	 * 用户ID
	 */
	private Long				userid;
	/**
	 * 开户行所在城市
	 */
	private String				city;
	/**
	 * 开户行全称
	 */
	private String				bankname;
	/**
	 * 开户行简写
	 */
	private String				abbreviation;
	/**
	 * 描述
	 */
	private String				description;
	/**
	 * 户名
	 */
	private String				accountname;
	/**
	 * 卡号
	 */
	private String				cardno;
	/**
	 * 卡类型
	 */
	private Integer				cardtype;
	/**
	 * 删除标识位 0正常 1删除
	 */
	private Integer				deleteFlag;
	/**
	 * 
	 */
	private String				province;
	/**
	 * 
	 */
	private String				subbranch;

	public java.lang.Long getId()
	{
		return this.id;
	}

	public void setId(java.lang.Long value)
	{
		this.id = value;
	}

	public java.lang.Long getUserid()
	{
		return this.userid;
	}

	public void setUserid(java.lang.Long value)
	{
		this.userid = value;
	}

	public java.lang.String getCity()
	{
		return this.city;
	}

	public void setCity(java.lang.String value)
	{
		this.city = value;
	}

	public java.lang.String getBankname()
	{
		return this.bankname;
	}

	public void setBankname(java.lang.String value)
	{
		this.bankname = value;
	}

	public java.lang.String getAbbreviation()
	{
		return this.abbreviation;
	}

	public void setAbbreviation(java.lang.String value)
	{
		this.abbreviation = value;
	}

	public java.lang.String getDescription()
	{
		return this.description;
	}

	public void setDescription(java.lang.String value)
	{
		this.description = value;
	}

	public java.lang.String getAccountname()
	{
		return this.accountname;
	}

	public void setAccountname(java.lang.String value)
	{
		this.accountname = value;
	}

	public java.lang.String getCardno()
	{
		return this.cardno;
	}

	public void setCardno(java.lang.String value)
	{
		this.cardno = value;
	}

	public Integer getCardtype()
	{
		return this.cardtype;
	}

	public void setCardtype(Integer value)
	{
		this.cardtype = value;
	}

	public Integer getDeleteFlag()
	{
		return this.deleteFlag;
	}

	public void setDeleteFlag(Integer value)
	{
		this.deleteFlag = value;
	}

	public java.lang.String getProvince()
	{
		return this.province;
	}

	public void setProvince(java.lang.String value)
	{
		this.province = value;
	}

	public java.lang.String getSubbranch()
	{
		return this.subbranch;
	}

	public void setSubbranch(java.lang.String value)
	{
		this.subbranch = value;
	}
}