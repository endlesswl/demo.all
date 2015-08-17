package com.lingcaibao.statuscode;

/**
 * 
* 标题：用户福利使用枚举   
* 项目名称：market-common   
* 类名称：LuckdrawStatusEnum   
* 类描述：   
* 创建人：xpj   
* 创建时间：2015年4月17日 上午11:45:41   
* 公司：北京零彩宝网络技术有限公司 
* 版权：Copyright (c) 2015  
* 修改备注：   
* @version   1.0 
*
 */
public enum LuckdrawStatusEnum
{
	UNCONVERT("未领取"), 
	CONVERTING("处理中"), 
	CONVERT_SUCCESS("领取成功"), 
	CONVERT_FAILED("领取失败"), ;
	private String	name;

	private LuckdrawStatusEnum(String name)
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
	public static String getName(int value)
	{
		for (LuckdrawStatusEnum luckdrawStatusEnum : LuckdrawStatusEnum.values())
		{
			if (luckdrawStatusEnum.ordinal() == value)
			{
				return luckdrawStatusEnum.name;
			}
		}
		return "";
	}
}
