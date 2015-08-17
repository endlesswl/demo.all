package com.lingcaibao.statuscode;

/**
 * 交易来源枚举
 * Created by jianhe on 14-2-19.
 */
public enum PaymentMethodEnum
{
	ACCOUNT("账户余额"), //账户余额
	ALIPAY("支付宝"), //支付宝
	UNIONPAY("银联"), //银联
	COUPON("零彩券"), //零彩券
	COM_ALIPAY_COUPON("支付宝+零彩卷"), //组合支付方式(支付宝+其他卷)
	COM_UNIONPAY_COUPON("银联+零彩卷"), //组合支付方式(银联+其他卷)
	COM_ACCOUNT_COUPON("账户余额+零彩卷"), //组合支付方式(账户余额+其他卷)
	ADMIN("超级管理员"), //超级管理员
	H5ACCOUNT("H5派奖"), //H5派奖
	PRIZE_BALANCE("奖金余额"), //奖金余额
	YY_USER_PRIZE("瑶瑶派奖"), // 瑶瑶派奖
	TCT_USER_PRIZE("铁彩通派奖"), // 铁彩通
	COM_ACCOUNT_ALIPAY("账户余额+支付宝"), //组合支付方式(账户余额+支付宝)
	COM_ACCOUNT_UNIONPAY("账户余额+银联"), //组合支付方式(账户余额+银联)
	COM_ACCOUNT_UNIONPAY_COUPON("账户余额+银联+零彩卷"), //组合支付方式(账户余额+银联+零彩卷)
	COM_ACCOUNT_ALIPAY_COUPON("账户余额+支付宝+零彩卷")//组合支付方式(账户余额+支付宝+零彩卷)
	;
	private String	name;

	private PaymentMethodEnum(String name)
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
			for (PaymentMethodEnum channelEnum : PaymentMethodEnum.values())
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
