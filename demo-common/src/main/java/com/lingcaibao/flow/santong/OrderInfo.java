package com.lingcaibao.flow.santong;

/**
 * <p>标题：订单信息实体 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月22日 下午9:29:51</p>
 * <p>类全名：com.lingcaibao.flow.santong.OrderInfo</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class OrderInfo
{
	String	clientOrderId;
	String	mobile;
	String	reportTime;
	String	status;

	public String getClientOrderId()
	{
		return clientOrderId;
	}

	public void setClientOrderId(String clientOrderId)
	{
		this.clientOrderId = clientOrderId;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getReportTime()
	{
		return reportTime;
	}

	public void setReportTime(String reportTime)
	{
		this.reportTime = reportTime;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
