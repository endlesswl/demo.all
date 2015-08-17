package com.lingcaibao.entityenum;

import org.apache.commons.lang3.StringUtils;
import com.lingcaibao.conf.Conf;
/**
 * <p>标题：用户信息模板枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月20日 下午3:24:04</p>
 * <p>类全名：com.lingcaibao.entityenum.UserMsgTemp</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum UserMsgTemp
{
	// 用户注册验证码信息
	USER_REGISTER("零彩宝用户注册", "亲爱的${company}用户，您注册用户的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户设置交易密码验证码信息
	SET_PAYPWD("设置交易密码", "亲爱的${company}用户，您设置交易密码申请的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户修改交易密码验证码信息
	MODIFY_PAYPWD("修改交易密码", "亲爱的${company}用户，您修改交易密码申请的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户找回交易密码验证码信息
	FIND_PAYPWD("找回交易密码", "亲爱的${company}用户，您找回交易密码申请的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户修改登录密码验证码信息
	MODIFY_PWD("修改登录密码", "亲爱的${company}用户，您修改登录密码申请的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户找回登录密码信息
	FIND_PWD("找回登录密码", "亲爱的${company}用户，您的登录密码已经重置为：${m0}，为了确保您的账户安全，请您登录后立即修改登录密码！"),
	// 用户找回登录密码验证码信息
	FIND_PWD_CAPTCHA("找回登录密码验证码", "亲爱的${company}用户，您找回登录密码申请的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户申请修改绑定手机号信息
	APPLY_MODIFY_BIND_MOBILE("申请修改绑定手机号", "亲爱的${company}用户，您申请修改绑定手机号的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户绑定手机号信息
	BIND_MOBILE("绑定手机号", "亲爱的${company}用户，您绑定手机号的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户绑定邮箱信息
	BIND_EMAIL("邮箱绑定", "亲爱的${company}用户，请点击链接绑定您的邮箱:<a href=\"${m0}\">${m0}</a>。"),
	// 用户绑定银行卡信息
	BIND_BANK_CARD("绑定银行卡", "亲爱的${company}用户，您绑定银行卡的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户创建银行卡信息
	CREATE_BANK_CARD("创建银行卡", "亲爱的${company}用户，您创建银行卡的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户修改银行卡信息
	UPDATE_BANK_CARD("修改银行卡", "亲爱的${company}用户，您修改银行卡的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	// 用户删除银行卡信息
	DELETE_BANK_CARD("删除银行卡", "亲爱的${company}用户，您删除银行卡的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！"),
	//网利宝提现手机校验
	WITHDRAW_WLB("网利宝提现手机校验", "亲爱的${company}用户，您本次操作提取现金的验证码为：${m0}，验证码有效时间为30分钟，请您妥善保管，切勿转告他人！");
	private String	title;
	private String	content;

	UserMsgTemp(String title, String content)
	{
		this.title = title;
		this.content = content;
	}

	public String getTitle()
	{
		return this.title;
	}

	public String getContent()
	{
		return this.content;
	}

	/**
	 * 信息内容,并处理宏替换
	 * @param code
	 * @param macros
	 * @return
	 */
	public String getMessage(String... macros)
	{
		String message = this.content;
		if (macros != null)
		{
			String company = Conf.get("lingcai.company", "");
			for (int i = 0; i < macros.length; i++)
			{
				message = StringUtils.replace(message, "${company}", company);
				message = StringUtils.replace(message, "${m" + i + "}", macros[i]);
			}
		}
		return message;
	}

	/**
	 * 获取信息模板对象
	 * @param name
	 * @return
	 */
	static public UserMsgTemp getMsgTemp(String name)
	{
		for (UserMsgTemp temp : UserMsgTemp.values())
		{
			if (StringUtils.equals(temp.name(), name))
			{
				return temp;
			}
		}
		return null;
	}
}
