package com.lingcaibao.statuscode;

/**
 * <p>标题：用户实名认证标识枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月8日 上午11:41:49</p>
 * <p>类全名：com.lingcaibao.statuscode.RealNameAuthFlags</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum RealNameAuthFlags
{
	AUTH_FLAGS_UNAUTH("未认证"),
	AUTH_FLAGS_SUCCESS("已认证");
	private String	name;

	private RealNameAuthFlags(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(int flag)
	{
		for (RealNameAuthFlags authFlag : RealNameAuthFlags.values())
		{
			if (authFlag.ordinal() == flag)
			{
				return authFlag.getName();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(RealNameAuthFlags.AUTH_FLAGS_UNAUTH.ordinal());
		
	}
}
