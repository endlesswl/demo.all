package com.lingcaibao.flow.feiyin;

/**
 * <p>标题：掌上平台流量直充返回报文对象 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月12日 下午9:17:54</p>
 * <p>类全名：com.lingcaibao.flow.OpenCardRsp</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class OpenCardRsp
{
	String	transactionid;
	String	rspcode;
	String	resultmsg;
	String	impporderid;

	public String getTransactionid()
	{
		return transactionid;
	}

	public void setTransactionid(String transactionid)
	{
		this.transactionid = transactionid;
	}

	public String getRspcode()
	{
		return rspcode;
	}

	public void setRspcode(String rspcode)
	{
		this.rspcode = rspcode;
	}

	public String getResultmsg()
	{
		return resultmsg;
	}

	public void setResultmsg(String resultmsg)
	{
		this.resultmsg = resultmsg;
	}

	public String getImpporderid()
	{
		return impporderid;
	}

	public void setImpporderid(String impporderid)
	{
		this.impporderid = impporderid;
	}
}
