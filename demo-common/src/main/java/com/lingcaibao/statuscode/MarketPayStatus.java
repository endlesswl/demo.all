package com.lingcaibao.statuscode;

/**
 * <p>标题：活动支付状态枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月30日 下午12:11:13</p>
 * <p>类全名：com.lingcaibao.task.statuscode.MarketPayStatus</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum MarketPayStatus
{
	WAITING_PAY("待支付"), 
	PAY_SUCCESS("支付成功"), 
	PAY_FAILED("支付失败"), 
	DELETED("删除"), ;
	private String	name;

	private MarketPayStatus(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(int ord)
	{
		for (MarketPayStatus status : MarketPayStatus.values())
		{
			if (status.ordinal() == ord)
			{
				return status.getName();
			}
		}
		return null;
	}
}
