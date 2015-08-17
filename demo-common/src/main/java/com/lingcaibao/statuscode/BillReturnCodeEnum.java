package com.lingcaibao.statuscode;

/**
 * 交易状态枚举
 * Created by jianhe on 14-2-19.
 */
public enum BillReturnCodeEnum
{
	NORMAL("交易中"), //充值中
	OK("交易成功"), //交易成功
	FAIL("交易失败"), //交易失败
	EXPIRE("交易过期"), //交易过期
	OPEN("交易打开"), //交易开启
	CLOSE("交易关闭"), //交易关闭
	CALLBACK("交易回滚");//交易回滚
	private String	name;

	private BillReturnCodeEnum(String name)
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
			for (BillReturnCodeEnum channelEnum : BillReturnCodeEnum.values())
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
