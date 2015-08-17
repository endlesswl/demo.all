package com.lingcaibao.flow.feiyin;

import org.apache.commons.lang.StringUtils;
/**
 * <p>标题：掌上流量卡品枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月12日 下午6:52:35</p>
 * <p>类全名：com.lingcaibao.charge.CardCode</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum CardCode
{
	M50("", ""), 
	M100("", "");
	String	code;
	String	name;

	private CardCode(String code, String name)
	{
		this.code = code;
		this.name = name;
	}

	public String getCode()
	{
		return this.code;
	}

	public String getName()
	{
		return this.name;
	}

	public String getName(String code)
	{
		for (CardCode card : CardCode.values())
		{
			if (StringUtils.equals(card.getCode(), code))
			{
				return card.getName();
			}
		}
		return null;
	}
}
