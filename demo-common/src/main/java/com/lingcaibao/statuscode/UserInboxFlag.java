package com.lingcaibao.statuscode;

/**
 * <p>标题：用户邮件标志枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月4日 下午7:16:26</p>
 * <p>类全名：com.lingcaibao.statuscode.UserInboxFlag</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum UserInboxFlag
{
	EMAIL_NO_READ("未读"), 
	EMAIL_HAS_READ("已读"), 
	EMAIL_DELETED("已删除"), ;
	private String	name;

	private UserInboxFlag(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(int ord)
	{
		for (UserInboxFlag flag : UserInboxFlag.values())
		{
			if (flag.ordinal() == ord)
			{
				return flag.getName();
			}
		}
		return null;
	}
}
