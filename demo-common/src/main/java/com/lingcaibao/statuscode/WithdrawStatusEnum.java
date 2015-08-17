package com.lingcaibao.statuscode;

/**
 * 
* 标题：   提现状态枚举
* 项目名称：market-common   
* 类名称：WithdrawStatusEnum   
* 类描述：   
* 创建人：xpj   
* 创建时间：2015年4月3日 下午2:30:07   
* 公司：北京零彩宝网络技术有限公司 
* 版权：Copyright (c) 2015  
* 修改备注：   
* @version   1.0 
*
 */
public enum WithdrawStatusEnum
{
	APPLICATIONSSUBMITTED("提交申请", "提现中"), 
	APPROVE("审核通过", "提现中"), 
	UNAPPROVE("审核不通过", "提现失败"), 
	TRANSFERSUCCESS("转账成功", "提现成功"), 
	TRANSFERFAILED("账不成功", "提现失败"), ;
	private String	name;
	private String  listName;

	private WithdrawStatusEnum(String name, String listName)
	{
		this.name = name;
		this.listName = listName;
	}

	public String getName()
	{
		return this.name;
	}

	public String getListName()
	{
		return this.listName;
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
			for (WithdrawStatusEnum statusEnum : WithdrawStatusEnum.values())
			{
				if (statusEnum.ordinal() == value)
				{
					return statusEnum.getName();
				}
			}
		}
		return "";
	}

	/**
	 * 获取列表显示名称
	 * @param value
	 * @return
	 */
	public static String getListName(Integer value)
	{
		if (value != null)
		{
			for (WithdrawStatusEnum statusEnum : WithdrawStatusEnum.values())
			{
				if (statusEnum.ordinal() == value)
				{
					return statusEnum.getListName();
				}
			}
		}
		return "";
	}
}
