package com.lingcaibao.flow.oufei;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：订单状态枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月20日 下午2:01:20</p>
 * <p>类全名：com.lingcaibao.flow.oufei.GameState</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum GameState
{
	WAITING("0", "充值中"), 
	SUCCESS("1", "充值成功"), 
	CANCEL("9", "撤销"),
	INVALID_ORDER("-1", "无效订单"),
	;
	String	code;
	String	name;

	private GameState(String code, String name)
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
		for (GameState state : GameState.values())
		{
			if (StringUtils.equals(state.getCode(), code))
			{
				return state.getName();
			}
		}
		return null;
	}
}
