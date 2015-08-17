package com.lingcaibao.pay;

import org.apache.commons.lang3.StringUtils;
public enum TradeStatus
{
	WAIT("等待交易"), 
	TRADE("交易中"), 
	SUCCESS("交易成功"), 
	FAIL("交易失败"), 
	EXPIRED("交易过期"), ;
	private String	name;

	private TradeStatus(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	/**
	 * 按照code获取名称
	 * @param code
	 * @return
	 */
	static public String getName(String code)
	{
		for (TradeStatus status : TradeStatus.values())
		{
			if (StringUtils.equals(status.name(), code))
			{
				return status.getName();
			}
		}
		return null;
	}
}
