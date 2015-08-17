package com.lingcaibao.statuscode;

/**
 * <p>标题：返款方式枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月30日 下午4:57:16</p>
 * <p>类全名：com.lingcaibao.statuscode.RefundMethodEnum</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum RefundMethodEnum
{
	ACCOUNT("余额"), COUPON("零彩卷"), ;
	private String	name;

	private RefundMethodEnum(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	/**
	 * 根据编号获取中文名称
	 * @param ord
	 * @return
	 */
	public static String getName(Integer ord)
	{
		if (ord != null)
		{
			for (RefundMethodEnum method : RefundMethodEnum.values())
			{
				if (method.ordinal() == ord)
				{
					return method.getName();
				}
			}
		}
		return null;
	}
}
