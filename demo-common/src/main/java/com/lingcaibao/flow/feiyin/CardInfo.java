package com.lingcaibao.flow.feiyin;

/**
 * <p>标题：卡品信息实体 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月13日 下午10:25:28</p>
 * <p>类全名：com.lingcaibao.pay.CardInfo</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class CardInfo
{
	/**
	 * 卡品编号
	 */
	String	cardcode;
	/**
	 * 卡品名称
	 */
	String	cardname;
	/**
	 * 流量值
	 */
	String	flownumber;
	/**
	 * 卡品面值
	 */
	String	cardvalue;
	/**
	 * 可销售省代码
	 */
	String	saledprovincecode;
	/**
	 * 账户余额
	 */
	String	diffcount;
	/**
	 * 运营商代码标识
	 */
	String	operatorno;
	/**
	 * 折扣上线
	 */
	String	maxdiscount;
	/**
	 * 折扣下线
	 */
	String	mindiscount;

	public String getCardcode()
	{
		return cardcode;
	}

	public void setCardcode(String cardcode)
	{
		this.cardcode = cardcode;
	}

	public String getCardname()
	{
		return cardname;
	}

	public void setCardname(String cardname)
	{
		this.cardname = cardname;
	}

	public String getFlownumber()
	{
		return flownumber;
	}

	public void setFlownumber(String flownumber)
	{
		this.flownumber = flownumber;
	}

	public String getCardvalue()
	{
		return cardvalue;
	}

	public void setCardvalue(String cardvalue)
	{
		this.cardvalue = cardvalue;
	}

	public String getSaledprovincecode()
	{
		return saledprovincecode;
	}

	public void setSaledprovincecode(String saledprovincecode)
	{
		this.saledprovincecode = saledprovincecode;
	}

	public String getDiffcount()
	{
		return diffcount;
	}

	public void setDiffcount(String diffcount)
	{
		this.diffcount = diffcount;
	}

	public String getOperatorno()
	{
		return operatorno;
	}

	public void setOperatorno(String operatorno)
	{
		this.operatorno = operatorno;
	}

	public String getMaxdiscount()
	{
		return maxdiscount;
	}

	public void setMaxdiscount(String maxdiscount)
	{
		this.maxdiscount = maxdiscount;
	}

	public String getMindiscount()
	{
		return mindiscount;
	}

	public void setMindiscount(String mindiscount)
	{
		this.mindiscount = mindiscount;
	}
}
