package com.lingcaibao.service.sms;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lingcaibao.util.RegexValidator;
/**
 * <p>标题：漫道科技短信发送服务类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月19日 下午1:40:55</p>
 * <p>类全名：com.lingcaibao.service.sms.MDSmsService</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class MDSmsService implements SmsServiceInterface
{
	private static Logger		logger	= LoggerFactory.getLogger(MDSmsService.class);
	/**
	 * 漫道科技短信网关序列号
	 */
	private static final String	SN		= "SDK-BBX-010-19744";
	/**
	 * 漫道科技短信网关密码
	 */
	private static final String	PWD		= "d-063_-4";

	public MDSmsService()
	{
	}

	/**
	 * 批量发送短信
	 * @param mobile
	 * @param Content
	 */
	@Override
	public void sendBatch(List<String> mobileList, String content)
	{
		MDSmsClient client = null;
		try
		{
			client = new MDSmsClient(SN, PWD);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		int i = 1;
		StringBuffer strf = new StringBuffer();
		for (String mobile : mobileList)
		{
			if (RegexValidator.isMobile(mobile))
			{
				strf.append(mobile).append(",");
			}
			if (i % 1000 == 0)
			{
				String result_mt = client.mt(strf.substring(0, strf.length() - 1), content.trim() + "【零彩宝】", "", "", "");
				logger.debug("{}", strf);
				strf.delete(0, strf.length());
				if (result_mt.startsWith("-") || result_mt.equals(""))//发送短信，如果是以负号开头就是发送失败。
				{
					logger.debug("--------批量短信下发失败---------{}", result_mt);
				}
				//输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
				else
				{
					logger.debug("--------批量短信下发成功---------{}", result_mt);
				}
			}
			i++;
		}
		logger.debug("--1--{}", strf);
		String result_mt = client.mt(strf.substring(0, strf.length() - 1), content.trim() + "【零彩宝】", "", "", "");
		if (result_mt.startsWith("-") || result_mt.equals(""))//发送短信，如果是以负号开头就是发送失败。
		{
			logger.debug("--------批量短信下发失败---------{}", result_mt);
		}
		//输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
		else
		{
			logger.debug("--------单条短信下发成功---------{}", result_mt);
		}
	}

	/**
	 * 发送单条短信
	 *
	 * @param mobile
	 * @param content
	 */
	@Override
	public boolean sendSingle(String mobile, String content)
	{
		try
		{
			MDSmsClient client = new MDSmsClient(SN, PWD);
			String result_mt = client.mt(mobile, content + "【零彩宝】", "", "", "");
			if (result_mt.startsWith("-") || result_mt.equals(""))//发送短信，如果是以负号开头就是发送失败。
			{
				logger.debug("--------单条短信下发失败---------{}", result_mt);
			}
			//输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
			else
			{
				logger.debug("--------单条短信下发成功---------{}", result_mt);
				return true;
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
