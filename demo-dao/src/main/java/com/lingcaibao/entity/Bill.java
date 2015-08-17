package com.lingcaibao.entity;

import java.math.BigDecimal;
public class Bill
{
	//alias
	public static final String	TABLE_ALIAS			= "Bill";
	public static final String	ALIAS_ID			= "id";
	public static final String	ALIAS_ORDERID		= "orderid";
	public static final String	ALIAS_SERIALNO		= "serialno";
	public static final String	ALIAS_TRADENO		= "tradeno";
	public static final String	ALIAS_AMOUNT		= "amount";
	public static final String	ALIAS_USERID		= "userid";
	public static final String	ALIAS_MARKETID		= "marketid";
	public static final String	ALIAS_BILLTIME		= "billtime";
	public static final String	ALIAS_INDECR		= "indecr";
	public static final String	ALIAS_BILLCHANNEL	= "billchannel";
	public static final String	ALIAS_BILLRET		= "billret";
	public static final String	ALIAS_PREBALANCE	= "prebalance";
	public static final String	ALIAS_IPADDR		= "ipaddr";
	public static final String	ALIAS_SALT			= "salt";
	public static final String	ALIAS_PACKETS		= "packets";
	public static final String	ALIAS_CREATETIME	= "createtime";
	public static final String	ALIAS_MODIFYTIME	= "modifytime";
	public static final String	ALIAS_EXPIRETIME	= "expiretime";
	public static final String	ALIAS_INFORMATION	= "information";
	public static final String	ALIAS_LOCKED		= "locked";
	public static final String	ALIAS_CONFIRMED		= "confirmed";
	public static final String	ALIAS_AFTERBALANCE	= "afterbalance";
	public static final String	ALIAS_SUBJECT		= "subject";
	public static final String	ALIAS_METHOD		= "method";
	public static final String	ALIAS_BALANCEID		= "balanceid";
	public static final String	ALIAS_USERLOTTERYID	= "userLotteryId";
	/**
	 * 
	 */
	private Long				id;
	/**
	 * 定单号
	 */
	private Long				orderid;
	/**
	 * 交易流水号
	 */
	private Long				serialno;
	/**
	 * 第三方交易流水号
	 */
	private String				tradeno;
	/**
	 * 操作的金额，分正负
	 */
	private BigDecimal			amount;
	/**
	 * 用户ID
	 */
	private Long				userid;
	/**
	 * 营销计划ID
	 */
	private Long				marketid;
	/**
	 * 交易时间
	 */
	private Long				billtime;
	/**
	 * 余额变动方式，加或减
	 */
	private Integer				indecr;
	/**
	 * 交易来源，0(充值),1(直接支付),2(回滚),3(结算),4(系统操作),5(兑奖),6(提现),7(零彩券),8(账户余额)
	 */
	private Integer				billchannel;
	/**
	 * 交易返回码, 0未处理、1成功、2失败
	 */
	private Integer				billret;
	/**
	 * 发生前的余额
	 */
	private BigDecimal			prebalance;
	/**
	 * 操作IP
	 */
	private String				ipaddr;
	/**
	 * 交易校验标识位
	 */
	private String				salt;
	/**
	 * 
	 */
	private String				packets;
	/**
	 * 创建时间
	 */
	private Long				createtime;
	/**
	 * 更新时间
	 */
	private Long				modifytime;
	/**
	 * 过期时间
	 */
	private Long				expiretime;
	/**
	 * 交易明细信息
	 */
	private String				information;
	/**
	 * 锁定状态 0正常 1锁定
	 */
	private Integer				locked;
	/**
	 * 是否对过账 0无 1有
	 */
	private Integer				confirmed;
	/**
	 * 
	 */
	private BigDecimal			afterbalance;
	/**
	 * 
	 */
	private String				subject;
	/**
	 * 具体的交易方式
	 */
	private Integer				method;
	/**
	 * 
	 */
	private Long				balanceid;
	/**
	 * 
	 */
	private Long				userLotteryId;

	public java.lang.Long getId()
	{
		return this.id;
	}

	public void setId(java.lang.Long value)
	{
		this.id = value;
	}

	public java.lang.Long getOrderid()
	{
		return this.orderid;
	}

	public void setOrderid(java.lang.Long value)
	{
		this.orderid = value;
	}

	public java.lang.Long getSerialno()
	{
		return this.serialno;
	}

	public void setSerialno(java.lang.Long value)
	{
		this.serialno = value;
	}

	public java.lang.String getTradeno()
	{
		return this.tradeno;
	}

	public void setTradeno(java.lang.String value)
	{
		this.tradeno = value;
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

	public java.lang.Long getBilltime()
	{
		return this.billtime;
	}

	public void setBilltime(java.lang.Long value)
	{
		this.billtime = value;
	}

	public Integer getIndecr()
	{
		return this.indecr;
	}

	public void setIndecr(Integer value)
	{
		this.indecr = value;
	}

	public Integer getBillchannel()
	{
		return this.billchannel;
	}

	public void setBillchannel(Integer value)
	{
		this.billchannel = value;
	}

	public Integer getBillret()
	{
		return this.billret;
	}

	public void setBillret(Integer value)
	{
		this.billret = value;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public BigDecimal getPrebalance()
	{
		return prebalance;
	}

	public void setPrebalance(BigDecimal prebalance)
	{
		this.prebalance = prebalance;
	}

	public BigDecimal getAfterbalance()
	{
		return afterbalance;
	}

	public void setAfterbalance(BigDecimal afterbalance)
	{
		this.afterbalance = afterbalance;
	}

	public java.lang.String getIpaddr()
	{
		return this.ipaddr;
	}

	public void setIpaddr(java.lang.String value)
	{
		this.ipaddr = value;
	}

	public java.lang.String getSalt()
	{
		return this.salt;
	}

	public void setSalt(java.lang.String value)
	{
		this.salt = value;
	}

	public java.lang.String getPackets()
	{
		return this.packets;
	}

	public void setPackets(java.lang.String value)
	{
		this.packets = value;
	}

	public java.lang.Long getCreatetime()
	{
		return this.createtime;
	}

	public void setCreatetime(java.lang.Long value)
	{
		this.createtime = value;
	}

	public java.lang.Long getModifytime()
	{
		return this.modifytime;
	}

	public void setModifytime(java.lang.Long value)
	{
		this.modifytime = value;
	}

	public java.lang.Long getExpiretime()
	{
		return this.expiretime;
	}

	public void setExpiretime(java.lang.Long value)
	{
		this.expiretime = value;
	}

	public java.lang.String getInformation()
	{
		return this.information;
	}

	public void setInformation(java.lang.String value)
	{
		this.information = value;
	}

	public Integer getLocked()
	{
		return this.locked;
	}

	public void setLocked(Integer value)
	{
		this.locked = value;
	}

	public Integer getConfirmed()
	{
		return this.confirmed;
	}

	public void setConfirmed(Integer value)
	{
		this.confirmed = value;
	}

	public java.lang.String getSubject()
	{
		return this.subject;
	}

	public void setSubject(java.lang.String value)
	{
		this.subject = value;
	}

	public Integer getMethod()
	{
		return this.method;
	}

	public void setMethod(Integer value)
	{
		this.method = value;
	}

	public java.lang.Long getBalanceid()
	{
		return this.balanceid;
	}

	public void setBalanceid(java.lang.Long value)
	{
		this.balanceid = value;
	}

	public java.lang.Long getUserLotteryId()
	{
		return this.userLotteryId;
	}

	public void setUserLotteryId(java.lang.Long value)
	{
		this.userLotteryId = value;
	}
}