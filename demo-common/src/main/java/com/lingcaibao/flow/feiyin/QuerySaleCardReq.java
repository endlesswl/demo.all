package com.lingcaibao.flow.feiyin;

/**
 * <p>标题：查询卡品请求报文实体 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月13日 下午6:14:15</p>
 * <p>类全名：com.lingcaibao.flow.QuerySaleCardReq</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class QuerySaleCardReq
{
	String	transactionId;
	String	channelId;

	public String getTransactionId()
	{
		return transactionId;
	}

	public void setTransactionId(String transactionId)
	{
		this.transactionId = transactionId;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}
}
