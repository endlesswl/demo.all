package com.lingcaibao.util;

import java.math.BigDecimal;
/**
 * <p>标题：活动常量 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月24日 下午5:58:00</p>
 * <p>类全名：com.lingcaibao.util.MarketplanContent</p>
 * <p>作者：nzh </p>
 * <p>@version 1.0</p>
 */
public class MarketplanContent
{
	/**
	 * 活动 未清算
	 */
	public static final int			MARKETPLAN_BALANCEFIXED_FAILED	= 1;
	/**
	 * 活动 已清算
	 */
	public static final int			MARKETPLAN_BALANCEFIXED_SUCCESS	= 0;
	/**
	 * 计划待审核
	 */
	public static final int			MARKETPLAN_AUDITFLAG_DEFAULT	= 0;
	/**
	 * 计划审核中
	 */
	public static final int			MARKETPLAN_AUDITFLAG_EXECUTE	= 1;
	/**
	 * 计划审核通过
	 */
	public static final int			MARKETPLAN_AUDITFLAG_SUCCESS	= 2;
	/**
	 * 计划审核失败
	 */
	public static final int			MARKETPLAN_AUDITFLAG_FAILED		= 3;
	/**
	 * 设置其他奖励计划 无
	 */
	public static int				MARKETPLAN_OTHERREWARD_DEFALUT	= 0;
	/**
	 * 设置其他奖励计划 实物奖励
	 */
	public static int				MARKETPLAN_OTHERREWARD_PHYSICAL	= 1;
	/**
	 * 设置其他奖励计划 礼券奖励
	 */
	public static int				MARKETPLAN_OTHERREWARD_GC		= 2;
	/**
	 * 设置其他奖励计划 现金奖励
	 */
	public static int				MARKETPLAN_OTHERREWARD_CASH		= 3;
	/**
	 * 支付标识 待支付
	 */
	public static int				MARKETPLAN_PAYFLAG_DEFAULT		= 0;
	/**
	 * 支付标识 支付成功
	 */
	public static int				MARKETPLAN_PAYFLAG_SUCCESS		= 1;
	/**
	 * 支付标识 支付失败
	 */
	public static int				MARKETPLAN_PAYFLAG_FAILED		= 2;
	/**
	 * 支付标识 已过期
	 */
	public static int				MARKETPLAN_PAYFLAG_EXPIRED		= 3;
	/**
	 * 计划状态 计划未付款
	 */
	final public static int			MARKETPLAN_STATUS_UNPAID		= 0;
	/**
	 * 计划状态 计划未提交审核
	 */
	final public static int			MARKETPLAN_STATUS_WAIT_SEND		= 1;
	/**
	 * 计划状态 计划审核中
	 */
	final public static int			MARKETPLAN_STATUS_AUDIT			= 2;
	/**
	 * 计划状态 计划审核失败
	 */
	final public static int			MARKETPLAN_STATUS_FAILED		= 3;
	/**
	 * 计划状态 计划审核通过
	 */
	final public static int			MARKETPLAN_STATUS_SUCCESS		= 4;
	/**
	 * 计划状态 计划进行中
	 */
	final public static int			MARKETPLAN_STATUS_PROCESS		= 5;
	/**
	 * 计划状态 计划已结束
	 */
	final public static int			MARKETPLAN_STATUS_END			= 6;
	/**
	 * 计划状态 计划已开票
	 */
	final public static int			MARKETPLAN_STATUS_INVOICE		= 7;
	/**
	 * 计划状态 计划已删除
	 */
	final public static int			MARKETPLAN_STATUS_DELETE		= 99;
	/**
	 * 介质种类  体彩
	 */
	final static public int			LOTTERY_TYPE_TC					= 1;
	/**
	 * 介质种类  福彩
	 */
	final static public int			LOTTERY_TYPE_FC					= 2;
	/**
	 * 介质各类 彩票
	 */
	final public static String		LOTTERY_TYPE_LOTTERY			= "LOTTERY";
	/**
	 * 介质种类  电话
	 */
	final static public String		LOTTERY_TYPE_CALL				= "CALL";
	/**
	 * 介质种类  流量
	 */
	final static public String		LOTTERY_TYPE_FLOW				= "FLOW";
	/**
	 * 介质种类  电影票
	 */
	final static public String		LOTTERY_TYPE_CINEMA				= "CINEMA";
	/**
	 * 介质种类  Q币
	 */
	final static public String		LOTTERY_TYPE_QB					= "QB";
	/**
	 * 彩票种类  福彩 3D
	 */
	final static public String		LOTTERY_TYPE_FC_3D				= "3D";
	/**
	 * 彩票种类  福彩 双色球
	 */
	final static public String		LOTTERY_TYPE_FC_SSQ				= "SSQ";
	/**
	 * 彩票种类  福彩 双色球
	 */
	final static public String		LOTTERY_TYPE_TC_DLT				= "DLT";
	/**
	 * 彩票种类 七星彩
	 */
	final static public String		LOTTERY_TYPE_TC_QXC				= "QXC";
	/**
	 *  基础互动服务费
	 */
	final static public BigDecimal	BAS_SERVICE_FEE					= new BigDecimal("0.20");
	/**
	 * 底部广告标识 0:不使用 1:使用
	 */
	final static public Integer		FOOTAD_FLAGS_NO					= 0;
	final static public Integer		FOOTAD_FLAGS_YES				= 1;
	/**
	 * 开票标识 0不开发票  1开发票
	 */
	final static public int			INVOICE_FLAGS_NO				= 0;						// 不开票
	final static public int			INVOICE_FLAGS_YES				= 1;						// 开发票
	/**
	 * 活动支付记录状态
	 */
	final static public int			PAY_STATUS_WAITING				= 0;						// 待支付
	final static public int			PAY_STATUS_SUCCESS				= 1;						// 支付成功
	final static public int			PAY_STATUS_FAILED				= 2;						// 支付失败
	final static public int			PAY_STATUS_DELETED				= 9;						// 已删除
	/**
	 * 滑动方式：0:向左滑动 1:向上滑动 2:翻转
	 */
	final static public int			MARKETPLAN_SLIPMODE_LEFT		= 0;
	final static public int			MARKETPLAN_SLIPMODE_UP			= 1;
	final static public int			MARKETPLAN_SLIPMODE_TURN		= 2;
	final static public int			USEED_CODEFLAGS					= 1;						// 使用提取码标识
	/**
	 * 平台发布标识
	 */
	final static public int			PLAM_PUB_FLAGS					= 1;
}
