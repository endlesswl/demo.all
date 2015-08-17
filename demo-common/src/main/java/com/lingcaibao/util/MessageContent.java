package com.lingcaibao.util;

/**
 * <p>标题：消息常量 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月17日 上午11:53:51</p>
 * <p>类全名：com.lingcaibao.util.MessageContent</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class MessageContent
{
	// 消息任务状态
	final static public int	MESSAGE_STATUS_WAIT			= 0;	// 未发送
	final static public int	MESSAGE_STATUS_SUCCESS		= 1;	// 发送成功
	final static public int	MESSAGE_STATUS_FAILED		= 2;	// 发送失败
	// 消息类型
	final static public int	MESSAGE_SEND_TYPE_MOBILE	= 0;	//发送短信
	final static public int	MESSAGE_SEND_TYPE_EMAIL		= 1;	//发送邮件
	// 消息发送方式
	final static public int	MESSAGE_MODE_CLUSTER		= 0;	// 按群组发送
	final static public int	MESSAGE_MODE_USERID			= 1;	// 按群用户ID发送
}
