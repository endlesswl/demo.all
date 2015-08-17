package com.lingcaibao.statuscode;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：文章/公告频道枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月24日 上午11:31:53</p>
 * <p>类全名：com.lingcaibao.statuscode.ArticleChannel</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum ArticleChannel
{
	GATE_WAY("门户频道"), 
	PALM_PUBLIC("平台公共频道"), ;
	String	name;

	private ArticleChannel(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(String code)
	{
		for (ArticleChannel channel : ArticleChannel.values())
		{
			if (StringUtils.equals(channel.name(), code))
			{
				return channel.getName();
			}
		}
		return null;
	}
}
