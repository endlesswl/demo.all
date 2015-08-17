package com.lingcaibao.statuscode;

/**
 * <p>标题：交易来源枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月24日 下午4:34:36</p>
 * <p>类全名：com.lingcaibao.task.statuscode.BillChannelEnum</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum BillChannelEnum
{
	DEPOSIT("充值"), //充值
	PAYMENT("支付"), //直接支付
	CALLBACK("回滚"), //回滚
	CASHUP("返款"), //返款
	SYSTEM("系统操作"), //系统操作
	FIXEDPRIZE("兑奖"), //兑奖
	WITHDRAW("提现"), //提现
	H5DPRIZE("H5兑奖"), //H5兑奖,不计入
	PROFIT("收益"), // 活动收益/零彩荷包
	;
	private String	name;

	private BillChannelEnum(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	/**
	 * 根据编码获取中文名称
	 * @param value
	 * @return
	 */
	public static String getName(Integer value)
	{
		if (value != null)
		{
			for (BillChannelEnum channelEnum : BillChannelEnum.values())
			{
				if (channelEnum.ordinal() == value)
				{
					return channelEnum.name;
				}
			}
		}
		return "";
	}
}
