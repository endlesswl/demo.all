package com.lingcaibao.statuscode;

/**
 * <p>标题：中奖信息标识枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>类全名：com.lingcaibao.statuscode.PrizeStatusFlags</p>
 * <p>@version 1.0</p>
 */
public enum PrizeStatusFlags {
	PRIZE_STATUS_UNOPEN("未开奖"), 
	PRIZE_STATUS_UNPRIZED("未中奖"), 
	PRIZE_STATUS_PRIZED("已中奖"), 
	PRIZE_STATUS_PRIZEFIXED("已派奖");
	private String	name;

	private PrizeStatusFlags(String name)
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
