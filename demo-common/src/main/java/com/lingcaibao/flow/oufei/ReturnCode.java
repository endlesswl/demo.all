package com.lingcaibao.flow.oufei;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：欧飞接口返回值枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月20日 下午5:15:15</p>
 * <p>类全名：com.lingcaibao.flow.oufei.ReturnCode</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum ReturnCode
{
	R0001("1", "成功"), 
	R0105("105", "请求失败"), 
	R0334("334", "订单生成超时"), 
	R1043("1043", "支付超时，订单处理失败"), 
	R9999("9999", "未知错误"), 
	;
	String	code;
	String	name;

	private ReturnCode(String code, String name)
	{
		this.code = code;
		this.name = name;
	}

	public String getCode()
	{
		return this.code;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(String code)
	{
		for (ReturnCode ret : ReturnCode.values())
		{
			if (StringUtils.equals(ret.getCode(), code))
			{
				return ret.getName();
			}
		}
		return null;
	}
}
