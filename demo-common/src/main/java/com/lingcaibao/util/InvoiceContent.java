package com.lingcaibao.util;

/**
 * <p>标题：发票常量 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月30日 下午12:02:43</p>
 * <p>类全名：com.lingcaibao.util.InvoiceContent</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class InvoiceContent
{
	// 发票类型
	final static public int	INVOICE_TYPE_NORMAL			= 0;	// 普通发票
	final static public int	INVOICE_TYPE_ADDTAX			= 0;	// 增值税发票
	// 发票来源
	final static public int	INVOICE_SOURCE_RECHARGE		= 0;	// 充值
	final static public int	INVOICE_SOURCE_PAYMENT		= 1;	// 支付
	// 发票状态
	final static public int	INVOICE_STATUS_UNSETTLE		= 0;	// 未结算
	final static public int	INVOICE_STATUS_UNINVOICE	= 1;	// 未开票
	final static public int	INVOICE_STATUS_ALREADY		= 2;	// 已开票
	final static public int	INVOICE_STATUS_POST			= 3;	// 已邮寄
	final static public int	INVOICE_STATUS_SIGN			= 4;	// 已签收
}
