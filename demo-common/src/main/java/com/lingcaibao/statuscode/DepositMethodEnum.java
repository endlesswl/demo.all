package com.lingcaibao.statuscode;

public enum DepositMethodEnum
{
	ALIPAY("支付宝"), UNIONPAY("银联"), ADMIN("超级用户充值");
	private String	name;

	private DepositMethodEnum(String name)
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
			for (DepositMethodEnum depositMethodEnum : DepositMethodEnum.values())
			{
				if (depositMethodEnum.ordinal() == value)
				{
					return depositMethodEnum.name;
				}
			}
		}
		return "";
	}
}
