package com.lingcaibao.util;

/**
 * <p>标题：邮件工具 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月12日 下午3:41:54</p>
 * <p>类全名：com.lingcaibao.util.SendMail</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class SendMail
{
	private static final String SMTP = "imap.exmail.qq.com"; //smtp服务器
	private String username = "service@lingcaibao.com";// 发件人真实的账户名
	private String password = "palm_123";// 发件人密码
	private String from = "service@lingcaibao.com";// 邮件显示名称
	
	/**
	 * 外端调用发送邮件方法 
	 * @param to 收件人的邮件地址，必须是真实地址
	 * @param copyto 抄送人邮件地址
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 */
	public boolean sendMail(String to,String copyto,String subject,String content){
		return Mail.sendAndCc(SMTP, from, to, copyto, subject, content, username, password);
	}
	
	/**
	 * 发送邮件
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 */
	static public boolean send(String to,String copyto,String subject,String content){
		SendMail sendMail = new SendMail();
		return sendMail.sendMail(to,copyto,subject,content);
	}
}
