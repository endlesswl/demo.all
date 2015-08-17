package com.lingcaibao.statuscode;

/**
 * <p>标题：收益来源枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月29日 下午2:49:27</p>
 * <p>类全名：com.lingcaibao.statuscode.ProfitSource</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum ProfitSource
{
	MARKET_PLAN("营销活动"), 
	LINGCAI_POUCH("领彩荷包"), 
	;
	private String	name;

	private ProfitSource(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(int ord)
	{
		for (ProfitSource source : ProfitSource.values())
		{
			if (source.ordinal() == ord)
			{
				return source.getName();
			}
		}
		return null;
	}
}
