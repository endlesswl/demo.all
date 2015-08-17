package com.lingcaibao.statuscode;

/**
 * <p>标题：意见反馈状态枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年6月24日 上午11:53:13</p>
 * <p>类全名：com.lingcaibao.statuscode.OpinionStatus</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum OpinionStatus
{
	PENDING("待处理"), ACCEPT("接受"), REJECT("驳回"), ;
	private OpinionStatus(String name)
	{
		this.name = name;
	}

	private String	name;

	public String getName()
	{
		return this.name;
	}

	static public String getName(Integer status)
	{
		if (status != null)
		{
			for (OpinionStatus opinion : OpinionStatus.values())
			{
				if (opinion.ordinal() == status)
				{
					return opinion.getName();
				}
			}
		}
		return null;
	}
}
