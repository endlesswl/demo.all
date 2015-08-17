package com.lingcaibao.flow.feiyin;

import org.apache.commons.lang.StringUtils;
public enum QueryCardInfoErrCode
{
	Code0("请求成功", "00"), 
	Code1("参数为空", "01"), 
	Code2("账户没有可充值的卡品", "02"), 
	Code4("非法用户（用户不存在、用户失效等）", "04"), 
	Code5("请求鉴权失败", "05"), 
	Code8("参数解析失败", "08"), 
	Code9("服务器异常", "09"), 
	Code99("其它未知错误", "99"), 
	;
	private String	resultMsg;
	private String	rspCode;

	private QueryCardInfoErrCode(String resultMsg, String rspCode)
	{
		this.resultMsg = resultMsg;
		this.rspCode = rspCode;
	}

	public String getResultMsg()
	{
		return this.resultMsg;
	}

	public String getRspCode()
	{
		return this.rspCode;
	}

	static public String getResultMsg(String rspCode)
	{
		for (QueryCardInfoErrCode code : QueryCardInfoErrCode.values())
		{
			if (StringUtils.equals(code.getRspCode(), rspCode))
			{
				return code.getResultMsg();
			}
		}
		return null;
	}
}
