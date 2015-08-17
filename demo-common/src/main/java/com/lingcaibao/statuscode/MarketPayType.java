package com.lingcaibao.statuscode;

/**
 * <p>标题：计划付款类型 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月12日 下午1:40:58</p>
 * <p>类全名：com.lingcaibao.statuscode.MarketPayType</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum MarketPayType
{
	TYPE_DEPOSIT("活动充值"), 
	TYPE_RENEW("活动续费"), ;
	private String	name;

	private MarketPayType(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(int ord)
	{
		for (MarketPayType type : MarketPayType.values())
		{
			if (type.ordinal() == ord)
			{
				return type.getName();
			}
		}
		return null;
	}
}
