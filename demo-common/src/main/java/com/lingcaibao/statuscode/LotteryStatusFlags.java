package com.lingcaibao.statuscode;

/**
 * <p>标题：彩票标识枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月31日 下午12:55:42</p>
 * <p>类全名：com.lingcaibao.statuscode.LotteryStatusFlags</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum LotteryStatusFlags
{
	ORDER_SUCCESS("下单成功"), 
	ORDER_FAILED("下单失败"), 
	LOTTERY_SUCCESS("出票成功"), 
	LOTTERY_FAILED("出票失败"), 
	LOTTERY_AGAIN_FAILED("出票重试失败"), 
	LOTTERY_PRE_SALE("预售");
	private String	name;

	private LotteryStatusFlags(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public int getFlags()
	{
		return this.ordinal() + 1;
	}

	/**
	 * 按标识位获取名称
	 * @param flags
	 * @return
	 */
	static public String getName(int flags)
	{
		for (LotteryStatusFlags status : LotteryStatusFlags.values())
		{
			if (flags == status.ordinal() + 1)
			{
				return status.getName();
			}
		}
		return null;
	}
}
