package com.lingcaibao.exception;

/**
 * 异常代码统一封装
 * Created by jianhe on 13-12-25.
 */
public class ExceptionCode {
    /**
     * 无需处理，只需正常显示返回信息即可
     */
    public static final String NORMAL = "0000";
    /**
     * 跳转到登录页
     */
    public static final String REDIRECT_LOGIN = "0001";

    /**
     * 查看其他正在执行的营销计划
     */
    public static final String VIEW_OTHER_MARKETPLAN = "0002";

    /**
     * 手机号码格式错误，需跳转返回重输入
     */
    public static final String MOBILE_FORMAT_ERROR = "0005";
    
    /**
     *支付通知返回消息code未知 
     */
    public static final String MSGCODE_UNKOWN="0001";
    /**
     * 支付订单已处理
     */
    public static final String ORDER_PROCESSED="0002";
    /**
     * 订单不存在
     */
	public static final String ORDER_NOT_EXISTS = "0003";
	/**
	 * 商品不存在
	 */
	public static final String SUBJECT_NOT_EXISTS = "0004";
	/**
	 * 商品已付款
	 */
	public static final String SUBJECT_PAYMENTED = "0005";
	/**
	 * 账户余额不足
	 */
	public static final String BALANCE_LACK = "0006";
		
	/**
	 * 零彩卷不存在
	 */
	public static final String COUPON_NOT_EXISTS="0007";
    
	/**
	 * 零彩卷不可用
	 */
	public static final String COUPON_NOT_USERED="0008";
	
	/**
	 * 不存在
	 */
	public static final String NOT_EXISTS="0009";
	/**
	 * 交易信息不匹配
	 */
	public static final String BILL_NO_MATCH="0010";
	
	public static final String WEIXIN_USER_NO_EXISTS="0011";
	/**
	 * 微信的openid不存在
	 */
	public static final String WEIXIN_OPENID_NOT_EXISTS="0012";
	/**
	 * 活动未开始
	 */
	public static final String MARTKET_NO_START="0013";
	/**
	 * 活动已经结束
	 */
	public static final String MARTKET_IS_OVER="0014";
	/**
	 * 零彩不存在
	 */
	public static final String LOTTERY_NOT_EXISTS="0015";
	/**
	 * 用户未注册
	 */
	public static final String USER_NOT_REGESTER="0016";
	/**
	 * 用户注册失败
	 */
	public static final String USER_REGESTER_FAILED="0023";
	/**
	 * 用户同步失败
	 */
	public static final String USER_SYNCHRO_FAILED="0024";
	/**
	 * 用户同步手机号错误
	 */
	public static final String USER_SYNCHRO_MOBILE_ERR="0025";
	/**
	 * 生成预售订单异常
	 */
	public static final String CREATE_PRE_SALE_ORDER_ERR="0026";
	/**
	 * 彩票领取失败
	 */
	public static final String LOTTERY_RECEIVE_FALSE="0017";
	/**
	 * 超过领取次数限制
	 */
	public static final String 	RECEIVE_COUNT_ERROE="0018";
	
	
	public static final String LOTTERY_NOT_SUPPORTED="0019";
	/**
	 * 超过发起次数限制
	 */
	public static final String 	SEND_COUNT_ERROE="0020";
	/**
	 * 单组重复
	 */
	public static final String 	GROUP_USER_EXIST="0021";
	/**
	 * 单场已经参与
	 */
	public static final String 	MATCH_USER_EXIST="0022";
	/**
	 * 提取码
	 */
	public static final String 	DRAW_CODE_ERR ="0030";
	/**
	 * 提取码被其他用户使用
	 */
	public static final String 	DRAW_CODE_USED_ALREADY ="0031";
	/**
	 * 提取码
	 */
	public static final String 	DRAW_CODE_EMPTY ="0032";
	/**
	 * 中心用户注册失败
	 */
	public static final String USER_CENTER_REGESTER_FAILED="0033";
}
