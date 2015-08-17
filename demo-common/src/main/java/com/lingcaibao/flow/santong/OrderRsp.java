package com.lingcaibao.flow.santong;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：流量下单响应信息 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月22日 下午7:31:53</p>
 * <p>类全名：com.lingcaibao.flow.santong.OrderRsp</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum OrderRsp
{
	M00("00", "处理成功"), 
	M01("01", "账号为空"), 
	M02("02", "手机号为空"), 
	M03("03", "包大小错误"), 
	M04("04", "签名为空"), 
	M05("05", "时间戳错误"), 
	M06("06", "手机号不能超过100个"), 
	M07("07", "账号不存在"), 
	M08("08", "签名不正确"), 
	M09("09", "余额不足"), 
	M11("11", "部分处理失败"), 
	M22("22", "所有处理失败"), 
	M33("33", "请求参数为空"), 
	M99("99", "数据库异常"), 
	;
	private String	code;
	private String	msg;

	private OrderRsp(String code, String msg)
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
		for (OrderRsp rsp : OrderRsp.values())
		{
			if (StringUtils.equals(rsp.getCode(), code))
			{
				return rsp.getMsg();
			}
		}
		return null;
	}
}
