package com.lingcaibao.flow.santong;

import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import com.lingcaibao.util.MathUtil;
public enum STFlow
{
	M10("10", "3"), 
	M30("30", "5"), 
	M50("50", "6"),
	M70("70", "10"), 
	M150("150", "20"), 
	M500("500", "30"), 
	G1("1000", "50"), ;
	String	flow;
	String	money;

	private STFlow(String flow, String money)
	{
		this.flow = flow;
		this.money = money;
	}

	public String getFlow()
	{
		return this.flow;
	}

	public String getMoney()
	{
		return this.money;
	}

	/**
	 * 按照名称获取流量
	 * @param name
	 * @return
	 */
	static public String getFlowByName(String name)
	{
		for (STFlow st : STFlow.values())
		{
			if (StringUtils.equals(st.name(), name))
			{
				return st.getFlow();
			}
		}
		return null;
	}

	/**
	 * 按照面额获取流量
	 * @param money
	 * @return
	 */
	static public String getFlowByMoney(BigDecimal money)
	{
		for (STFlow st : STFlow.values())
		{
			if (MathUtil.toDecimal(st.getMoney()).compareTo(money) == 0)
			{
				return st.getFlow();
			}
		}
		return null;
	}

	/**
	 * 按照流量获取面额
	 * @param flow
	 * @return
	 */
	static public String getMoney(String flow)
	{
		for (STFlow st : STFlow.values())
		{
			if (StringUtils.equals(st.name(), flow))
			{
				return st.getMoney();
			}
		}
		return null;
	}
}
