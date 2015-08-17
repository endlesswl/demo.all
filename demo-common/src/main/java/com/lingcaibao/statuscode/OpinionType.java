package com.lingcaibao.statuscode;

/**
 * <p>标题：意见反馈类型枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年6月24日 上午11:53:29</p>
 * <p>类全名：com.lingcaibao.statuscode.OpinionType</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum OpinionType
{
	PALM("平台反馈"), MARKETPLAN("活动反馈");
	private OpinionType(String name)
	{
		this.name = name;
	}

	private String	name;

	public String getName()
	{
		return this.name;
	}

	static public String getName(Integer type)
	{
		if (type != null)
		{
			for (OpinionType opinion : OpinionType.values())
			{
				if (opinion.ordinal() == type)
				{
					return opinion.getName();
				}
			}
		}
		return null;
	}
}
