package com.lingcaibao.util;

import java.math.BigDecimal;
/**
 * 通用状态标识类
 *
 * @author Administrator
 */
public class CommonStatus
{
	/**
	 * SESSION用户KEY
	 */
	final static public String		SESSION_USER_NAME					= "lingcaibao_user";
	/**
	 * SESSION蓝标商家用户KEY
	 */
	final static public String		SESSION_BLUEMP_USER_KEY					= "lingcaibao_bluemp_user";
	/**
	 * 授权验签KEY
	 */
	final static public String		USER_SYNCHRO_SIGN					= "www.lingcaibao.com-88888";
	/**
	 * 礼券类型  0 无 ；1折扣券 ； 2代金券
	 */
	public static int				REWARD_COUPON_DEFAULT				= 0;
	/**
	 * 礼券类型 1折扣券
	 */
	public static int				REWARD_COUPON_REBATE				= 1;
	/**
	 * 礼券类型 2代金券
	 */
	public static int				REWARD_COUPON_REPLACE				= 2;
	/**
	 * 其他奖励的计划类型 0其他
	 */
	public static final int			REWARD_MARKETTYPE_OTHER				= 0;
	/**
	 * 其他奖励的计划类型 1实物奖励
	 */
	public static final int			REWARD_MARKETTYPE_PHYSICAL			= 1;
	/**
	 * 其他奖励的计划类型 2礼券类型
	 */
	public static final int			REWARD_MARKETTYPE_COUPON			= 2;
	/**
	 * 问题类型 0未知的
	 */
	public static final int			QUESTION_QUESTIONTYPE_UNKOWN		= 0;
	/**
	 * 问题类型 1 单选
	 */
	public static final int			QUESTION_QUESTIONTYPE_RADIO			= 1;
	/**
	 * 问题类型 2 多选
	 */
	public static final int			QUESTION_QUESTIONTYPE_CHECKBOX		= 2;
	/**
	 * 问题类型 3文本
	 */
	public static final int			QUESTION_QUESTIONTYPE_TEXT			= 3;
	/**
	 * 问题类型 4 随机
	 */
	public static final int			QUESTION_QUESTIONTYPE_RANDOM		= 4;
	/**
	 * 答案状态 0正确 ； 1不正确； 2答案不完全
	 */
	public static final int			ANSWER_ISRIGHT_RIGHT				= 0;
	/**
	 * 答案状态 1不正确
	 */
	public static final int			ANSWER_ISRIGHT_ERROR				= 1;
	/**
	 * 答案状态 2答案不完全
	 */
	public static final int			ANSWER_ISRIGHT_NOTALL				= 2;
	/**
	 * 答案状态 3标识该答案是调查问卷类型
	 */
	public static final int			ANSWER_ISRIGHT_TEXT					= 3;
	/**
	 * 跳转地址 无权限提示页
	 */
	public static final String		NOAUTH_REDRIECT						= "redirect:/noauth";
	/**
	 * 记录不存在
	 */
	public static final String		NO_RESOUCE_EXISTS					= "marketplan/noexist";
	/**
	 * 重定向到 零彩计划创建地址
	 */
	public static final String		CREATE_MARKETPLAN					= "redirect:/admin/marketplan/create";
	/**
	 * 验证码SESSION KEY
	 */
	public static final String		KAPTCHA_SESSION_KEY					= "kaptcha_seesion_key";
	/**
	 * 激活码SESSION KEY
	 */
	public static final String		ACTIVATE_SESSION_KEY				= "activate_seesion_key";
	/**
	 * 订单类型   0 充值订单
	 */
	public static final int			RECHARGE_ORDERTYPE_RECHARGE			= 0;
	/**
	 * 订单类型 1 支付订单
	 */
	public static final int			RECHARGE_ORDERTYPE_PAYMENT			= 1;
	/**
	 * 订单支付渠道  0零彩宝账户
	 */
	public static final int			RECHARGE_CHANNEL_LINGCAI			= 0;
	/**
	 * 订单充值和支付渠道 1支付宝
	 */
	public static final int			RECHARGE_CHANNEL_ALIPAY				= 1;
	/**
	 * 充值订单状态  0未处理，待付款
	 */
	public static final int			RECHARGE_BILL_STATUS_DEFAULT		= 0;
	/**
	 * 充值订单状态 1处理成功，已支付
	 */
	public static final int			RECHARGE_BILL_STATUS_SUCCESS		= 1;
	/**
	 * 充值订单状态 2处理失败，支付失败
	 */
	public static final int			RECHARGE_BILL_STATUS_FAIL			= 2;
	/**
	 * 充值订单状态 3未处理，订单已过期
	 */
	public static final int			RECHARGE_BILL_STATUS_EXPIRE			= 3;
	/**
	 * 订单不存在
	 */
	public static final int			RECHARGE_NOT_FOUND					= 1;
	/**
	 * 订单处理成功
	 */
	public static final int			RECHARGE_PROCCESS_STATUS_SUCCESS	= 2;
	/**
	 * 订单处于未知状态
	 */
	public static final int			RECHARGE_STATUS_UNKONW				= 3;
	/**
	 * 订单已经处理成功
	 */
	public static final int			RECHARGE_STATUS_UPDATEED			= 4;
	/**
	 * 订单验证签名失败
	 */
	public static final int			RECHARGE_VALIDATESIGN_FAIL			= 4;
	/**
	 * 交易渠道  0 充值
	 */
	//public static final int			BILL_CHANNEL_RECHARGE				= 0;
	/**
	 * 交易渠道  1 支付
	 */
	//public static final int			BILL_CHANNEL_PAYMENT				= 1;
	/**
	 * 交易渠道  2 回滚
	 */
	//public static final int			BILL_CHANNEL_ROLLBACK				= 2;
	/**
	 * 交易渠道  3 结算
	 */
	//public static final int			BILL_CHANNEL_SETTLE					= 3;
	/**
	 * 交易渠道  4 系统操作
	 */
	//public static final int			BILL_CHANNEL_SYSTEM					= 4;
	/**
	 * 交易渠道  5 兑奖
	 */
	//public static final int			BILL_CHANNEL_EXPIRY					= 5;
	/**
	 * 交易渠道  6 提现
	 */
	//public static final int			BILL_CHANNEL_WITHDRAW				= 6;
	/**
	 * 交易渠道  7 零彩券
	 */
	//public static final int			BILL_CHANNEL_LC_MONEY				= 7;
	/**
	 * 交易渠道  8 账户余额
	 */
	//public static final int			BILL_CHANNEL_BALANCE				= 8;
	/**
	 * 交易余额 变更方式 0不变 1 加  0 减
	 */
	public static final int			BILL_INDECR_ZERO					= 0;
	/**
	 * 交易余额 变更方式 1 加  0 减
	 */
	public static final int			BILL_INDECR_ADD						= 1;
	/**
	 * 交易余额 变更方式  2 减
	 */
	public static final int			BILL_INDECR_SUBTRACT				= 2;
	/**
	 * 交易返回码 0未处理
	 */
	//public static final int			BILL_RET_DEFAUTL					= 0;
	/**
	 * 交易返回码 1成功
	 */
	//public static final int			BILL_RET_OK							= 1;
	/**
	 * 交易返回码 2失败
	 */
	//public static final int			BILL_RET_FAIL						= 2;
	/**
	 * 购彩表，中奖状态，已中奖
	 */
	public static final int			LOTTERY_ISPRIZE_PRIZED				= 1;
	/**
	 * 购彩表，中奖状态，未中奖
	 */
	public static final int			LOTTERY_ISPRIZE_UNPRIZED			= 0;
	/**
	 * 购彩表，中奖状态，等待中奖
	 */
	public static final int			LOTTERY_ISPRIZE_WAITPRIZE			= -1;
	/**
	 * 开奖表，中奖状态，未开奖
	 */
	public static final int			PRIZE_STATUS_UNOPEN					= 0;
	/**
	 * 开奖表，中奖状态，未中奖
	 */
	public static final int			PRIZE_STATUS_UNPRIZED				= 1;
	/**
	 * 开奖表，中奖状态，已中奖
	 */
	public static final int			PRIZE_STATUS_PRIZED					= 2;
	/**
	 * 开奖表，中奖状态，已派奖
	 */
	public static final int			PRIZE_STATUS_PRIZEFIXED				= 3;
	/**
	 * 开奖表，中奖状态，等待开奖结果
	 */
	public static final int			PRIZE_STATUS_WAITPRIZE				= 4;
	/**
	 * 全国平台返回状态码，等待交易
	 */
	public static final String		PALM_STATUSCODE_WAITDEAL			= "0000";
	/**
	 * 全国平台返回状态码，交易中
	 */
	public static final String		PALM_STATUSCODE_DEALING				= "0001";
	/**
	 * 全国平台返回状态码，交易成功
	 */
	public static final String		PALM_STATUSCODE_SUCCESS				= "0002";
	/**
	 * 全国平台返回状态码，交易失败
	 */
	public static final String		PALM_STATUSCODE_FAIL				= "0003";
	/**
	 * 全国平台返回状态码，发起成功
	 */
	public static final String		PALM_STATUSCODE_SUBMIT_SUCCESS		= "0004";
	/**
	 * 全国平台返回状态码，系统错误
	 */
	public static final String		PALM_STATUSCODE_SYSTEM_ERROR		= "9999";
	/**
	 * 管理员充值
	 */
	public static final int			RECHARGE_CHANNEL_ADMIN				= 3;
	/**
	 * 系统赠送礼金
	 */
	public static final int			RECHARGE_CHANNEL_COUPON				= 4;
	/**
	 * 人工兑奖金额
	 */
	public static final BigDecimal	FIXEDPRIZE_MONEY					= new BigDecimal("5000");
}
