package com.lingcaibao.flow.santong;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>标题：产品查询响应信息 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月22日 下午7:33:43</p>
 * <p>类全名：com.lingcaibao.flow.santong.ProductRsp</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum ProductRsp
{
	M00("00", "查询成功"), 
	M10("10", "账号为空"), 
	M20("20", "查询失败，用户不存在或不可"), 
	;
	private String	code;
	private String	msg;

	private ProductRsp(String code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}

	public String getCode()
	{
		return this.code;
	}

	public String getMsg()
	{
		return this.msg;
	}

	static public String getMsg(String code)
	{
		for (ProductRsp rsp : ProductRsp.values())
		{
			if (StringUtils.equals(rsp.getCode(), code))
			{
				return rsp.getMsg();
			}
		}
		return null;
	}
}
