package com.lingcaibao.entityenum;

import java.math.BigDecimal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lingcaibao.entity.User;
import com.lingcaibao.util.AccountContent;
public enum UserInfoPercent
{
	// 商家用户信息
	INFO_COMPANY(AccountContent.TYPE_BUSINESS, "company", "10.0"), // 公司名称
	INFO_AVATAR(AccountContent.TYPE_BUSINESS, "avatar", "10.0"), // 头像
	INFO_EMAIL(AccountContent.TYPE_BUSINESS, "email", "20.0"), // 邮箱
	INFO_REALNAME(AccountContent.TYPE_BUSINESS, "realName", "10.0"), // 联系人姓名
	INFO_MOBILE(AccountContent.TYPE_BUSINESS, "mobile", "10.0"), // 联系人手机号
	INFO_ADDRESS(AccountContent.TYPE_BUSINESS, "address", "5.0"), // 公司地址
	INFO_PHONE(AccountContent.TYPE_BUSINESS, "phone", "5.0"), // 公司电话
	INFO_PWD(AccountContent.TYPE_BUSINESS, "pwd", "15.0"), // 登录密码
	INFO_PAYPWD(AccountContent.TYPE_BUSINESS, "payPwd", "15.0"); // 交易密码
	private String	atrname;
	private String	atrpercent;
	private int		userType;

	UserInfoPercent(int userType, String atrname, String atrpercent)
	{
		this.atrname = atrname;
		this.atrpercent = atrpercent;
		this.userType = userType;
	}

	public String getAtrname()
	{
		return this.atrname;
	}

	public String getAtrpercent()
	{
		return this.atrpercent;
	}

	public int getUserType()
	{
		return this.userType;
	}

	/**
	 * 获取用户信息完善百分比
	 * @param user
	 * @return
	 */
	static public BigDecimal getUserInfoPercent(User user)
	{
		BigDecimal percents = BigDecimal.ZERO;
		if (user != null)
		{
			JSONObject info = (JSONObject) JSON.toJSON(user);
			int userType = info.getIntValue("userType");
			for (UserInfoPercent userInfo : UserInfoPercent.values())
			{
				if (userInfo.getUserType() == userType)
				{
					if (info.get(userInfo.getAtrname()) != null)
					{
						percents = percents.add(new BigDecimal(userInfo.atrpercent));
					}
				}
			}
		}
		return percents;
	}
	
}
