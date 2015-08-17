package com.lingcaibao.flow.feiyin;

import org.apache.commons.lang.StringUtils;
public enum OpenCardErrCode
{
	Code0("请求成功", "00"), 
	Code1("参数为空", "01"), 
	Code2("非法卡品", "02"), 
	Code3("非法手机号", "03"), 
	Code4("非法用户（用户不存在、用户失效等）", "04"), 
	Code5("请求鉴权失败", "05"), 
	Code6("重复的请求交易流水号", "06"), 
	Code7("用户账户余额不足", "07"), 
	Code8("参数解析失败", "08"), 
	Code9("服务器异常", "09"), 
	Code10("未授权服务器地址", "10"), 
	Code99("其它未知错误", "99"),
	;
	private String	resultMsg;
	private String	rspCode;

	private OpenCardErrCode(String resultMsg, String rspCode)
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
		for (OpenCardErrCode code : OpenCardErrCode.values())
		{
			if (StringUtils.equals(code.getRspCode(), rspCode))
			{
				return code.getResultMsg();
			}
		}
		return null;
	}
}
