package com.lingcaibao.pay;

public enum PayMethod
{
	ALIPAY("支付宝", "2088211249090950"), //支付宝
	UNIONPAY("银联", "22294531");//银联
	private String	name;		// 充值方式名称
	private String	merchantNo; // 商户号

	private PayMethod(String name, String merchantNo)
	{
		this.name = name;
		this.merchantNo = merchantNo;
	}

	public String getName()
	{
		return this.name;
	}

	public String getMerchantNo()
	{
		return this.merchantNo;
	}

	/**
	 * 根据标识位获取中文名称
	 * @param value
	 * @return
	 */
	public static String getName(int value)
	{
		for (PayMethod channelEnum : PayMethod.values())
		{
			if (channelEnum.ordinal() == value)
			{
				return channelEnum.getName();
			}
		}
		return null;
	}

	/**
	 * 根据标识位获取商户号
	 * @param value
	 * @return
	 */
	public static String getMerchantNo(int value)
	{
		for (PayMethod channelEnum : PayMethod.values())
		{
			if (channelEnum.ordinal() == value)
			{
				return channelEnum.getMerchantNo();
			}
		}
		return null;
	}
}
