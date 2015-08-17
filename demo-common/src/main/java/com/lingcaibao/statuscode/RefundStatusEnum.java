package com.lingcaibao.statuscode;
/**
 * <p>标题：返款状态枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月30日 下午5:06:06</p>
 * <p>类全名：com.lingcaibao.task.statuscode.RefundStatusEnum</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum RefundStatusEnum {
	REFUND_WAITING("未返款"), 
	REFUND_SUCCESS("返款成功"), 
	REFUND_FAILED("返款失败"); 
	;
	private String	name;

	private RefundStatusEnum(String name)
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
	public static String getName(int ord)
	{
		for (RefundStatusEnum status : RefundStatusEnum.values())
		{
			if (status.ordinal() == ord)
			{
				return status.getName();
			}
		}
		return null;
	}
}
