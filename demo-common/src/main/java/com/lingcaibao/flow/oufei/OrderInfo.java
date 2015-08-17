package com.lingcaibao.flow.oufei;

/**
 * <p>标题：欧飞订单信息实体 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月19日 下午9:20:01</p>
 * <p>类全名：com.lingcaibao.flow.oufei.OrderInfo</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class OrderInfo
{
	String	errmsg;
	String	retcode;
	String	orderid;
	String	cardid;
	String	cardnum;
	String	ordercash;
	String	cardname;
	String	sporderid;
	String	gameuserid;
	String	gamestate;

	public String getErrmsg()
	{
		return errmsg;
	}

	public void setErrmsg(String errmsg)
	{
		this.errmsg = errmsg;
	}

	public String getRetcode()
	{
		return retcode;
	}

	public void setRetcode(String retcode)
	{
		this.retcode = retcode;
	}

	public String getOrderid()
	{
		return orderid;
	}

	public void setOrderid(String orderid)
	{
		this.orderid = orderid;
	}

	public String getCardid()
	{
		return cardid;
	}

	public void setCardid(String cardid)
	{
		this.cardid = cardid;
	}

	public String getCardnum()
	{
		return cardnum;
	}

	public void setCardnum(String cardnum)
	{
		this.cardnum = cardnum;
	}

	public String getOrdercash()
	{
		return ordercash;
	}

	public void setOrdercash(String ordercash)
	{
		this.ordercash = ordercash;
	}

	public String getCardname()
	{
		return cardname;
	}

	public void setCardname(String cardname)
	{
		this.cardname = cardname;
	}

	public String getSporderid()
	{
		return sporderid;
	}

	public void setSporderid(String sporderid)
	{
		this.sporderid = sporderid;
	}

	public String getGameuserid()
	{
		return gameuserid;
	}

	public void setGameuserid(String gameuserid)
	{
		this.gameuserid = gameuserid;
	}

	public String getGamestate()
	{
		return gamestate;
	}

	public void setGamestate(String gamestate)
	{
		this.gamestate = gamestate;
	}
}
