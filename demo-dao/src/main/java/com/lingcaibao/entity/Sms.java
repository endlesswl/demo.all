package com.lingcaibao.entity;

public class Sms
{
	//alias
	public static final String	TABLE_ALIAS			= "Sms";
	public static final String	ALIAS_ID			= "id";
	public static final String	ALIAS_MOBILE		= "mobile";
	public static final String	ALIAS_USERID		= "userid";
	public static final String	ALIAS_CONTENT		= "content";
	public static final String	ALIAS_OPERATOR		= "operator";
	public static final String	ALIAS_CREATETIME	= "createtime";
	public static final String	ALIAS_NUMBERS		= "numbers";
	public static final String	ALIAS_MSGID			= "msgid";
	/**
	 * 
	 */
	private Long				id;
	/**
	 * 手机
	 */
	private String				mobile;
	/**
	 * 用户ID
	 */
	private Long				userid;
	/**
	 * 短信内容
	 */
	private String				content;
	/**
	 * 操作员
	 */
	private String				operator;
	/**
	 * 发送时间
	 */
	private java.util.Date		createtime;
	/**
	 * 数量
	 */
	private Integer				numbers;
	/**
	 * 消息ID
	 */
	private Long				msgid;

	public java.lang.Long getId()
	{
		return this.id;
	}

	public void setId(java.lang.Long value)
	{
		this.id = value;
	}

	public java.lang.String getMobile()
	{
		return this.mobile;
	}

	public void setMobile(java.lang.String value)
	{
		this.mobile = value;
	}

	public java.lang.Long getUserid()
	{
		return this.userid;
	}

	public void setUserid(java.lang.Long value)
	{
		this.userid = value;
	}

	public java.lang.String getContent()
	{
		return this.content;
	}

	public void setContent(java.lang.String value)
	{
		this.content = value;
	}

	public java.lang.String getOperator()
	{
		return this.operator;
	}

	public void setOperator(java.lang.String value)
	{
		this.operator = value;
	}

	public java.util.Date getCreatetime()
	{
		return this.createtime;
	}

	public void setCreatetime(java.util.Date value)
	{
		this.createtime = value;
	}

	public java.lang.Integer getNumbers()
	{
		return this.numbers;
	}

	public void setNumbers(java.lang.Integer value)
	{
		this.numbers = value;
	}

	public Long getMsgid()
	{
		return msgid;
	}

	public void setMsgid(Long msgid)
	{
		this.msgid = msgid;
	}
}