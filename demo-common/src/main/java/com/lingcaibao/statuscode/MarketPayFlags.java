package com.lingcaibao.statuscode;

/**
 * <p>标题：活动付款标识枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年6月11日 下午4:59:55</p>
 * <p>类全名：com.lingcaibao.statuscode.MarketPayFlags</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum MarketPayFlags
{
	WAITING("待付款"), 
	SUCCESS("付款成功"), 
	FAILED("付款失败"), 
	EXPIRE("付款过期"), ;
	private String	name;

	private MarketPayFlags(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(int flag)
	{
		for (MarketPayFlags flags : MarketPayFlags.values())
		{
			if (flags.ordinal() == flag)
			{
				return flags.getName();
			}
		}
		return null;
	}
}
