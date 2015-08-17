package com.lingcaibao.entity;

import java.math.BigDecimal;
public class UserAssets
{
	//alias
	public static final String	TABLE_ALIAS			= "UserAssets";
	public static final String	ALIAS_ID			= "id";
	public static final String	ALIAS_USERID		= "userid";
	public static final String	ALIAS_MARKETID		= "marketid";
	public static final String	ALIAS_ASSETCODE		= "assetcode";
	public static final String	ALIAS_ASSETNAME		= "assetname";
	public static final String	ALIAS_EXCODE		= "excode";
	public static final String	ALIAS_QUANTITY		= "quantity";
	public static final String	ALIAS_UNIT			= "unit";
	public static final String	ALIAS_UNITPRICE		= "unitprice";
	public static final String	ALIAS_ORDERID		= "orderid";
	public static final String	ALIAS_STATUS		= "status";
	public static final String	ALIAS_SOURCE		= "source";
	public static final String	ALIAS_SCANIP		= "scanip";
	public static final String	ALIAS_CREATETIME	= "createtime";
	public static final String	ALIAS_MODIFYTIME	= "modifytime";
	public static final String	ALIAS_REMARK		= "remark";
	public static final String	ALIAS_PRIZEID		= "prizeid";
	public static final String	ALIAS_DRAWID		= "drawid";
	public static final String	ALIAS_MONEY			= "money";
	/**
	 * ID
	 */
	private Long				id;
	/**
	 * 用户ID
	 */
	private Long				userid;
	/**
	 * 活动ID
	 */
	private Long				marketid;
	/**
	 * 资产编号
	 */
	private String				assetcode;
	/**
	 * 资产名称
	 */
	private String				assetname;
	/**
	 * 兑换码
	 */
	private String				excode;
	/**
	 * 数量
	 */
	private Long				quantity;
	/**
	 * 单位
	 */
	private String				unit;
	/**
	 * 单价
	 */
	private BigDecimal			unitprice;
	/**
	 * 面额
	 */
	private BigDecimal			money;
	/**
	 * 订单号
	 */
	private String				orderid;
	/**
	 * 状态 0:未领取,1:领取成功,2:兑换成功,4:兑换失败
	 */
	private Integer				status;
	/**
	 * 访问渠道
	 */
	private String				source;
	/**
	 * 访问IP
	 */
	private String				scanip;
	/**
	 * 创建时间
	 */
	private java.util.Date		createtime;
	/**
	 * 修改时间
	 */
	private java.util.Date		modifytime;
	/**
	 * 备注
	 */
	private String				remark;
	/**
	 * 奖品ID
	 */
	private Long				prizeid;
	/**
	 * 抽奖ID
	 */
	private Long				drawid;

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

	public java.lang.Long getMarketid()
	{
		return this.marketid;
	}

	public void setMarketid(java.lang.Long value)
	{
		this.marketid = value;
	}

	public java.lang.String getAssetcode()
	{
		return this.assetcode;
	}

	public void setAssetcode(java.lang.String value)
	{
		this.assetcode = value;
	}

	public java.lang.String getAssetname()
	{
		return this.assetname;
	}

	public void setAssetname(java.lang.String value)
	{
		this.assetname = value;
	}

	public java.lang.String getExcode()
	{
		return this.excode;
	}

	public void setExcode(java.lang.String value)
	{
		this.excode = value;
	}

	public Long getQuantity()
	{
		return this.quantity;
	}

	public void setQuantity(Long value)
	{
		this.quantity = value;
	}

	public java.lang.String getUnit()
	{
		return this.unit;
	}

	public void setUnit(java.lang.String value)
	{
		this.unit = value;
	}

	public BigDecimal getUnitprice()
	{
		return this.unitprice;
	}

	public void setUnitprice(BigDecimal value)
	{
		this.unitprice = value;
	}

	public java.lang.String getOrderid()
	{
		return this.orderid;
	}

	public void setOrderid(java.lang.String value)
	{
		this.orderid = value;
	}

	public Integer getStatus()
	{
		return this.status;
	}

	public void setStatus(Integer value)
	{
		this.status = value;
	}

	public java.lang.String getSource()
	{
		return this.source;
	}

	public void setSource(java.lang.String value)
	{
		this.source = value;
	}

	public java.lang.String getScanip()
	{
		return this.scanip;
	}

	public void setScanip(java.lang.String value)
	{
		this.scanip = value;
	}

	public java.util.Date getCreatetime()
	{
		return this.createtime;
	}

	public void setCreatetime(java.util.Date value)
	{
		this.createtime = value;
	}

	public java.util.Date getModifytime()
	{
		return this.modifytime;
	}

	public void setModifytime(java.util.Date value)
	{
		this.modifytime = value;
	}

	public java.lang.String getRemark()
	{
		return this.remark;
	}

	public void setRemark(java.lang.String value)
	{
		this.remark = value;
	}

	public Long getPrizeid()
	{
		return prizeid;
	}

	public void setPrizeid(Long prizeid)
	{
		this.prizeid = prizeid;
	}

	public Long getDrawid()
	{
		return drawid;
	}

	public void setDrawid(Long drawid)
	{
		this.drawid = drawid;
	}

	public BigDecimal getMoney()
	{
		return money;
	}

	public void setMoney(BigDecimal money)
	{
		this.money = money;
	}
}