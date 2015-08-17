package com.lingcaibao.util;

/**
 * <p>标题：账户常量 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月12日 下午5:03:17</p>
 * <p>类全名：com.lingcaibao.util.AccountContent</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class AccountContent
{
	// 商家后台-个人用户角色
	final static public String	ROLE_USER					= "user";
	// 商家后台-商家用户角色
	final static public String	ROLE_BUSINESS				= "business";
	// 商家后台-管理员角色
	final static public String	ROLE_ADMIN					= "administrator";
	// 商家后台-代理商角色
	final static public String	ROLE_PROXY					= "proxy";
	// 账户正常
	final static public int		FLAG_NORMAL					= 0;
	// 账户锁定
	final static public int		FLAG_LOCKED					= 1;
	// 账户删除
	final static public int		FLAG_DELETED				= 2;
	// 账户未激活
	final static public int		FLAG_UNACTIVE				= 3;
	// 账户审核中
	final static public int		FLAG_AUDIT					= 4;
	// 账户审核失败
	final static public int		FLAG_AUDIT_FAILED			= 5;
	// 账户需初始化密码
	final static public int		FLAG_PWD_INITIALIZE			= 6;
	// 手机来源
	final static public int		SOURCE_WAP					= 1;
	// 网站来源
	final static public int		SOURCE_WEB					= 2;
	// 代理商来源
	final static public int		SOURCE_PROXY				= 3;
	// 用户类型-个人用户
	final static public int		TYPE_USER					= 1;
	// 用户类型-商家用户
	final static public int		TYPE_BUSINESS				= 2;
	// 用户类型-管理员
	final static public int		TYPE_ADMINISTRATOR			= 3;
	// 用户类型-代理商
	final static public int		TYPE_PROXY					= 4;
	// 绑定标识-未绑定
	final static public int		BIND_FLAGS_NONE				= 0;
	// 绑定标识-手机绑定
	final static public int		BIND_FLAGS_MOBILE			= 1;
	// 绑定标识-邮箱绑定
	final static public int		BIND_FLAGS_EMAIL			= 2;
	// 绑定标识-银行卡绑定
	final static public int		BIND_FLAGS_BANK				= 4;
	// 用户资产兑换标识 未领取
	final static public int		ASSETS_STATUS_UPCONVERT		= 0;
	// 用户资产兑换标识 处理中
	final static public int		ASSETS_STATUS_CONVERTING	= 1;
	// 用户资产兑换标识 成功
	final static public int		ASSETS_STATUS_SUCCESS		= 2;
	// 用户资产兑换标识 失败
	final static public int		ASSETS_STATUS_FAILED		= 3;
}
