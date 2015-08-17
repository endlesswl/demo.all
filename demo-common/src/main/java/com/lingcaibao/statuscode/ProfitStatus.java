package com.lingcaibao.statuscode;

/**
 * <p>标题：收益状态枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月29日 下午2:49:12</p>
 * <p>类全名：com.lingcaibao.statuscode.ProfitStatus</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum ProfitStatus
{
	TRANSFER_NO("未入账"), 
	TRANSFER_ALREADY("已入账"), 
	TRANSFER_FAILED("入账失败"), ;
	private String	name;

	private ProfitStatus(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(int ord)
	{
		for (ProfitStatus status : ProfitStatus.values())
		{
			if (status.ordinal() == ord)
			{
				return status.getName();
			}
		}
		return null;
	}
}
