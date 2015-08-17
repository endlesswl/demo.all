package com.lingcaibao.flow.oufei;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：欧飞流量种类枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月19日 下午6:41:23</p>
 * <p>类全名：com.lingcaibao.flow.oufei.SPFlow</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum SPFlow
{
	M2("2M", "1"), ;
	String	flow;
	String	money;

	private SPFlow(String flow, String money)
	{
		this.flow = flow;
		this.money = money;
	}

	public String getFlow()
	{
		return this.flow;
	}

	public String getMoney()
	{
		return this.money;
	}

	static public String getFlow(String name)
	{
		for (SPFlow sp : SPFlow.values())
		{
			if (StringUtils.equals(sp.name(), name))
			{
				return sp.getFlow();
			}
		}
		return null;
	}

	static public String getMoney(String flow)
	{
		for (SPFlow sp : SPFlow.values())
		{
			if (StringUtils.equals(sp.name(), flow))
			{
				return sp.getMoney();
			}
		}
		return null;
	}
}
