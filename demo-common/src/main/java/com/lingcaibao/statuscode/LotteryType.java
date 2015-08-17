package com.lingcaibao.statuscode;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：彩票枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月27日 下午5:57:40</p>
 * <p>类全名：com.lingcaibao.entityenum.LotteryType</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum LotteryType
{
	QXC(1, "QXC", "七星彩", "http://image.lingcaibao.com/image/2015/3/2015_3_afd42d47-e1a8-4305-a66b-704588ebbb7b.png"), 
	DLT(1, "DLT", "大乐透", "http://image.lingcaibao.com/image/2015/3/2015_3_1e1b7c66-83ed-4a66-863e-22126b95a7ac.png"), 
	SSQ(2, "SSQ", "双色球", "http://image.lingcaibao.com/image/2015/3/2015_3_c551cd3f-0b9d-483a-8cca-81bd9911799b.png"),
	QLC(2, "QLC", "七乐彩", "http://image.lingcaibao.com/image/2015/3/2015_3_35505ed7-7b12-47dd-a7b2-783cc1947b74.png"),
	SD(2, "3D", "3D", "http://image.lingcaibao.com/image/2015/3/2015_3_787ad254-d446-4c29-9969-cc6cf65ef256.png"),
	KS(2, "K3", "快3", "http://image.lingcaibao.com/image/2015/3/2015_3_13ab2199-63b8-43c6-b62d-93b98f010caf.png"),
	;
	private int		type;	// 彩票种类: 1为体彩 2为福彩
	private String	code;	// 彩票类型: SSQ,3D等
	private String	name;	// 彩票名称
	private String	image;	// 图片地址

	LotteryType(int type, String code, String name, String image)
	{
		this.type = type;
		this.code = code;
		this.name = name;
		this.image = image;
	}

	/**
	 * 取彩票种类
	 * @return
	 */
	public int getType()
	{
		return this.type;
	}

	/**
	 * 取彩票类型
	 * @return
	 */
	public String getCode()
	{
		return this.code;
	}

	/**
	 * 取彩票名称
	 * @return
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * 图片地址
	 * @return
	 */
	public String getImage()
	{
		return this.image;
	}

	/**
	 * 取彩票ID
	 * @return
	 */
	public String getId()
	{
		return this.name();
	}

	/**
	 * 按照彩票ID获取彩票名称
	 * @param id
	 * @return
	 */
	static public String getName(String id)
	{
		for (LotteryType lottery : LotteryType.values())
		{
			if (StringUtils.equals(lottery.getCode(), id))
			{
				return lottery.getName();
			}
		}
		return null;
	}

	/**
	 * 获取彩种
	 * @param id
	 * @return
	 */
	static public Integer getType(String id)
	{
		for (LotteryType lottery : LotteryType.values())
		{
			if (StringUtils.equals(lottery.getCode(), id))
			{
				return lottery.getType();
			}
		}
		return null;
	}

	/**
	 * 获取图标地址
	 * @param id
	 * @return
	 */
	static public String getImage(String id)
	{
		for (LotteryType lottery : LotteryType.values())
		{
			if (StringUtils.equals(lottery.getCode(), id))
			{
				return lottery.getImage();
			}
		}
		return null;
	}

	/**
	 * 获取彩票奖品编号列表
	 * @return
	 */
	static public String getCodes()
	{
		String codes = null;
		for (LotteryType type : LotteryType.values())
		{
			codes = (StringUtils.isEmpty(codes) ? type.name() : codes + ","+ type.name());
		}
		return codes;
	}
}
