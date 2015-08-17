package com.lingcaibao.pay;

import com.lingcaibao.conf.Conf;
public class PayConstant
{
	/**
	 * 协议版本
	 */
	final static public String	PAY_VERSION						= "1.0";
	/**
	 * 支付平台服务URL
	 */
	final static public String	PAY_URL							= Conf.get("lingcai.pay_url");
	/**
	 * 交易协议
	 */
	final static public String	SERVICE_PAY						= "1000";												// 支付
	final static public String	SERVICE_QUERY					= "1001";												// 查询
	/**
	 * 商户编号
	 */
	final static public String	PARTNER_NO						= "a1234567890";
	/**
	 * 交易适用范围 WWW或者WAP
	 */
	final static public String	PAY_SCOPE_WEB					= "WWW";												// WEB端
	final static public String	PAY_SCOPE_WAP					= "WAP";												// WAP端
	/**
	 * 交易名称
	 */
	final static public String	PAY_NAME_DEPOSIT				= "账户充值";
	/**
	 * 交易描述
	 */
	final static public String	PAY_DESCRIPTION_DEPOSIT			= "零彩宝用户账户充值";
	/**
	 * 默认交易币种(人民币)
	 */
	final static public String	DEFAULT_CURRENCY				= "CNY";
	/**
	 * 加密KEY
	 */
	final static public String	DES_KEY							= "ta4Exasp";
	/**
	 * MD5签名KEY
	 */
	final static public String	SIGN_KEY_MD5					= "lingcaitest";
	/**
	 * RSA签名KEY
	 */
	final static public String	SIGN_KEY_RSA					= "lingcaitest";
	/**
	 * 支付异步通知URL
	 */
	final static public String	PAY_NOTIFY_URL					= Conf.get("lingcai.pay_notify_url");
	/**
	 * 支付成功跳转URL(WEB)
	 */
	final static public String	WEB_PAY_SUCCESS_CALL_BACK_URL	= Conf.get("lingcai.web_pay_success_call_back_url");
	/**
	 * 支付失败跳转路径(WEB)
	 */
	final static public String	WEB_PAY_FAIL_CALL_BACK_URL		= Conf.get("lingcai.web_pay_fail_call_back_url");
	/**
	 * 支付成功跳转URL(WAP)
	 */
	final static public String	WAP_PAY_SUCCESS_CALL_BACK_URL	= Conf.get("lingcai.wap_pay_success_call_back_url");
	/**
	 * 支付失败跳转路径(WAP)
	 */
	final static public String	WAP_PAY_FAIL_CALL_BACK_URL		= Conf.get("lingcai.wap_pay_fail_call_back_url");
	/**
	 * 付款窗口关闭回调URL(WEB)
	 */
	final static public String	WEB_FOR_PAY_LAYER_URL			= Conf.get("lingcai.web_for_pay_layer_url");
}
