package com.lingcaibao.flow.feiyin;

/**
 * <p>标题：直充请求报文实体 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月12日 下午7:31:00</p>
 * <p>类全名：com.lingcaibao.charge.OpenCardReq</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class OpenCardReq
{
	String	transactionId; // 流水号
	String	channelId; // 渠道ID
	String	phoneNum; // 手机号
	String	cardCode; // 卡品编号

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

	public String getPhoneNum()
	{
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum)
	{
		this.phoneNum = phoneNum;
	}

	public String getCardCode()
	{
		return cardCode;
	}

	public void setCardCode(String cardCode)
	{
		this.cardCode = cardCode;
	}
}
