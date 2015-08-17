package com.lingcaibao.flow.santong;

import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.SUCCESSFUL;
/**
 * <p>标题：三通流量推送响应结果枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月26日 下午2:21:04</p>
 * <p>类全名：com.lingcaibao.flow.santong.STFlowPushRsp</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum STFlowPushRsp
{
	SUCCESS("0000", "处理成功！"), 
	FAILED("1111", "处理失败！"), ;
	String	code;
	String	name;

	private STFlowPushRsp(String code, String name)
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
		for (STFlowPushRsp rsp : STFlowPushRsp.values())
		{
			if (StringUtils.equals(rsp.getCode(), code))
			{
				return rsp.getName();
			}
		}
		return null;
	}
}
