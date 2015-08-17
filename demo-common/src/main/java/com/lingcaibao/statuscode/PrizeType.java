package com.lingcaibao.statuscode;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：奖品类型枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月7日 下午2:44:40</p>
 * <p>类全名：com.lingcaibao.statuscode.PrizeType</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum PrizeType
{
	CALL("话费", "http://image.lingcaibao.com/image/2015/3/2015_3_bbc0a363-c591-43c4-8c95-046f172a4219.png", "test/image"), 
	FLOW("流量", "http://image.lingcaibao.com/image/2015/3/2015_3_65b4bb83-950e-431b-804f-bb02a20ba309.png", "test/image"), 
	CINEMA("电影票", "test/image", "http://image.lingcaibao.com/image/2015/3/2015_3_5c46a804-e46c-4112-8027-62a66a00349b.png"), 
	LOTTERY("彩票", "http://image.lingcaibao.com/image/2015/3/2015_3_bec1c2fb-dac6-448f-9e50-43c8bb0ce706.png", "test/image"), ;
	final static public String	defaultIcon	= "";	// 默认图标地址
	private String				name;				// 奖品名称
	private String				icon;				// 奖品图标
	private String				image;				// 奖品图片

	private PrizeType(String name, String icon, String image)
	{
		this.name = name;
		this.icon = icon;
		this.image = image;
	}

	public String getName()
	{
		return this.name;
	}

	public String getIcon()
	{
		return this.icon;
	}

	public String getImage()
	{
		return this.image;
	}

	/**
	 * 获取奖品名称,彩票类型返回具体彩种名称,如:双色球,大乐透等
	 * @param code
	 * @return
	 */
	static public String getName(String code)
	{
		for (PrizeType type : PrizeType.values())
		{
			if (StringUtils.equals(type.name(), PrizeType.LOTTERY.name()))
			{
				for (LotteryType lottery : LotteryType.values())
				{
					if (StringUtils.equals(lottery.getCode(), code))
					{
						return lottery.getName();
					}
				}
			} else
			{
				if (StringUtils.equals(type.name(), code))
				{
					return type.getName();
				}
			}
		}
		return null;
	}

	/**
	 * 获取奖品类型图标,不区分具体彩种
	 * @param code
	 * @return
	 */
	static public String getIcon(String code)
	{
		for (PrizeType type : PrizeType.values())
		{
			if (StringUtils.equals(type.name(), code))
			{
				return type.getIcon();
			}
		}
		return defaultIcon;
	}

	/**
	 * 获取奖品图片,彩票类型返回具体彩种图片,如:双色球,大乐透等
	 * @param code
	 * @return
	 */
	static public String getImage(String code)
	{
		for (PrizeType type : PrizeType.values())
		{
			if (StringUtils.equals(type.name(), PrizeType.LOTTERY.name()))
			{
				for (LotteryType lottery : LotteryType.values())
				{
					if (StringUtils.equals(lottery.getCode(), code))
					{
						return lottery.getImage();
					}
				}
			} else
			{
				if (StringUtils.equals(type.name(), code))
				{
					return type.getImage();
				}
			}
		}
		return null;
	}
}
