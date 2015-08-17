package com.lingcaibao.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * <p>标题：用户认证BEEN </p>
 * <p>功能：用户登录信息 </p>
 * <p>版权： Copyright (c) 2014</p>
 * <p>公司: 北京阳光彩通</p>
 * <p>创建日期：2014年11月5日 上午10:43:37</p>
 * <p>类全名：com.palm.lingcai.web.shiro.UserToken</p>
 * <p>作者：JIJI</p>
 * <p>@version 1.0</p>
 */
public class UserToken extends UsernamePasswordToken
{
	private static final long	serialVersionUID	= 1L;

	public UserToken()
	{
		super();
	}

	private boolean	isChaAuth;	// 渠道授权
	// 用户类型 1:个人用户 2:商户 3:管理员 4:代理商
	private int		userType;

	public void setUserType(int userType)
	{
		this.userType = userType;
	}

	public int getUserType()
	{
		return userType;
	}

	public void isChaAuto(boolean isChaAuth)
	{
		this.isChaAuth = isChaAuth;
	}

	public boolean isChaAuto()
	{
		return isChaAuth;
	}

	public UserToken(final String username, final char[] password, final boolean rememberMe, int userType, final String host)
	{
		this(username, password, rememberMe, userType, host, false);
	}
	
	public void setLoginMode(int userType)
	{
		this.userType = userType;
	}

	public UserToken(final String username, final char[] password, final boolean rememberMe, int userType, final String host, final boolean isChaAuth)
	{
		super(username, password, rememberMe, host);
		this.isChaAuth = isChaAuth;
		this.userType = userType;
	}
}
