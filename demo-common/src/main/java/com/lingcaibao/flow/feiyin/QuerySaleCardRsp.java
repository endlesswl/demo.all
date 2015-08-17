package com.lingcaibao.flow.feiyin;

import java.util.List;
/**
 * <p>标题：查询卡品相应报文实体 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月13日 下午6:16:43</p>
 * <p>类全名：com.lingcaibao.flow.QuerySaleCardRsp</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class QuerySaleCardRsp
{
	String			transactionid;
	String			resultmsg;
	String			resultcode;
	String			operatorid;
	List<CardInfo>	cardinfolist;

	public String getTransactionid()
	{
		return transactionid;
	}

	public void setTransactionid(String transactionid)
	{
		this.transactionid = transactionid;
	}

	public String getResultmsg()
	{
		return resultmsg;
	}

	public void setResultmsg(String resultmsg)
	{
		this.resultmsg = resultmsg;
	}

	public String getResultcode()
	{
		return resultcode;
	}

	public void setResultcode(String resultcode)
	{
		this.resultcode = resultcode;
	}

	public String getOperatorid()
	{
		return operatorid;
	}

	public void setOperatorid(String operatorid)
	{
		this.operatorid = operatorid;
	}

	public List<CardInfo> getCardinfolist()
	{
		return cardinfolist;
	}

	public void setCardinfolist(List<CardInfo> cardinfolist)
	{
		this.cardinfolist = cardinfolist;
	}
}
