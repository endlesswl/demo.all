package com.lingcaibao.pay;

import org.apache.commons.lang3.StringUtils;
public enum CallBackStatus
{
	PARAM_EMPTY("参数为空"), 
	SIGN_ERR("验签错误"), 
	ORDER_NO_ERR("订单号错误"), 
	PARTNER_NO_ERR("商户身份不符"), 
	SIGN_TYPE_UNSUPPORT("不支持签名方式"),
	CALL_BACK_SIGN_ERR("通知签名错误"),
	FAILED("回调信息处理失败"), 
	SUCCESS("回调信息处理成功"),
	;
	private String	name;

	private CallBackStatus(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(String code)
	{
		for (CallBackStatus status : CallBackStatus.values())
		{
			if (StringUtils.equals(code, status.name()))
			{
				return status.getName();
			}
		}
		return null;
	}
}
