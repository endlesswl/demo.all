package com.lingcaibao.service.sms;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ctc.smscloud.xml.http.RespInfo;
import com.ctc.smscloud.xml.http.utils.XMLHttpClientUtil;
/**
 * <p>标题：大汉三通短信服务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月19日 下午1:47:48</p>
 * <p>类全名：com.lingcaibao.service.sms.STSmsService</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class STSmsService implements SmsServiceInterface
{
	private static Logger		logger		= LoggerFactory.getLogger(STSmsService.class);
	private static final String	ST			= "http://3tong.net";
	private static String		account		= "dh52621";									// 用户名
	private static String		password	= "P0M?m@4d";									// 密码(明文)
	private static RespInfo		_respInfo	= null;

	/**
	 * 发送短信接口(支持批量)
	 * @param msgid
	 * @param phone
	 * @param content
	 * @param sign
	 * @param subcode
	 * @param sendtime
	 * @return
	 */
	public boolean sentSMS(String msgid, String phone, String content, String sign, String subcode, String sendtime)
	{
		// 服务端地址，默认可不设置
		XMLHttpClientUtil.setServerUrlBase(ST);
		logger.info("------------ st sms channel begin --------------:{}", phone);
		// 调用短信下行接口
		_respInfo = XMLHttpClientUtil.sendSms(account, password, msgid, phone, content, sign, subcode, sendtime);
		// 调用成功获取短信报告
		logger.info("--------------- send sms result ------------------:{}", XMLHttpClientUtil.whetherSuccess(_respInfo));
		if (XMLHttpClientUtil.whetherSuccess(_respInfo))
		{
			// 获取短信报告,解析发送结果
			_respInfo = XMLHttpClientUtil.getReport(account, password, phone);
			String respVal = _respInfo.getRespVal();
			logger.info("--------------- sms report ------------------:{}", _respInfo.getRespVal());
			try
			{
				Element root = DocumentHelper.parseText(respVal).getRootElement();
				String result = root.element("result").getText();
				logger.info("--------------- sms result ------------------:{}", result);
				if (StringUtils.equals(result, "0"))
				{
					Element report = root.element("report");
					if (report != null)
					{
						String status = root.element("report").elementText("status");
						logger.info("--------------- sms status ------------------:{}", status);
						return StringUtils.equals(status, "0");
					}
					return true;
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
				logger.error("---------- respVal parse err ----------:{}", ex.getMessage());
			}
		}
		logger.info("------------ st sms channel end --------------");
		return false;
	}

	/**
	 * 获取状态报告
	 * @param phone
	 * @return
	 */
	public String getSMSHttpStatus(String phone)
	{
		System.out.println("*************状态报告*************");
		_respInfo = XMLHttpClientUtil.getReport(account, password, phone);
		return _respInfo.getHttpStatus() + "," + _respInfo.getRespVal();
	}

	/**
	 * 获取余额
	 * @return
	 */
	public String getSMSHttpBalance()
	{
		System.out.println("*************获取余额*************");
		_respInfo = XMLHttpClientUtil.getBalance(account, password);
		return _respInfo.getHttpStatus() + "," + _respInfo.getRespVal();
	}

	/**
	 * 获取上行
	 * @return
	 */
	public String getSMSHttpMT()
	{
		System.out.println("*************获取上行*************");
		_respInfo = XMLHttpClientUtil.getSms(account, password);
		return _respInfo.getHttpStatus() + "," + _respInfo.getRespVal();
	}

	/**
	 * 获取敏感词
	 * @param content
	 * @param phone
	 * @return
	 */
	public String getSMSHttpKeywordCheck(String content, String phone)
	{
		System.out.println("*************获取敏感词*************");
		_respInfo = XMLHttpClientUtil.checkKeyWord(account, password, content);
		return _respInfo.getHttpStatus() + "," + _respInfo.getRespVal();
	}

	/**
	 * 获取黑名单
	 * @param phone
	 * @return
	 */
	public String getSMSHttpBlackListCheck(String phone)
	{
		System.out.println("*************获取黑名单*************");
		_respInfo = XMLHttpClientUtil.checkBlacklist(account, password, phone);
		return _respInfo.getHttpStatus() + "," + _respInfo.getRespVal();
	}

	@Override
	public boolean sendSingle(String mobile, String content)
	{
		return sentSMS("", mobile, content, "", "", "");
	}

	@Override
	public void sendBatch(List<String> mobiles, String content)
	{
		String mobile = mobiles.toString();
		sentSMS("", mobile, content, "", "", "");
	}
}
