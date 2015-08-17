package com.lingcaibao.statuscode;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：接口验证返回信息 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月3日 下午12:58:01</p>
 * <p>类全名：com.lingcaibao.statuscode.VerifyResultType</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum VerifyResultType
{
	PARAM_EMPTY("001"), // 参数为空
	SGIN_ERROR("002"), // 验签失败
	ACCESS_TOKEN_ERR("003"), // 令牌错误
	;
	private String	code;

	private VerifyResultType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return this.code;
	}

	public String getMsg()
	{
		return this.name();
	}

	/**
	 * 按照code获取name
	 * @param code
	 * @return
	 */
	static public String getMsg(String code)
	{
		for (VerifyResultType type : VerifyResultType.values())
		{
			if (StringUtils.equals(code, type.getCode()))
			{
				return type.name();
			}
		}
		return null;
	}
}
