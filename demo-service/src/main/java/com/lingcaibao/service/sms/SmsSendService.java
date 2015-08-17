package com.lingcaibao.service.sms;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.lingcaibao.conf.Conf;
/**
 * <p>标题：短信发送服务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月19日 上午11:35:31</p>
 * <p>类全名：com.lingcaibao.service.sms.SmsSendService</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Service
public class SmsSendService
{
	private static Logger	logger	= LoggerFactory.getLogger(SmsSendService.class);
	private String			smsChannel;

	public SmsSendService(String smsChannel)
	{
		this.smsChannel = Conf.get("lingcai.smschannel");
	}

	/**
	 * 短信单条发送
	 * @param mobile
	 * @param content
	 * @return
	 */
	public boolean sendSingle(String mobile, String content)
	{
		try
		{
			SmsServiceInterface smsService = (SmsServiceInterface) Class.forName(smsChannel).newInstance();
			return smsService.sendSingle(mobile, content);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("------- sms channel err ------:{}", smsChannel);
			return false;
		}
	}

	/**
	 * 短信群发
	 * @param mobileArray
	 * @param content
	 */
	public void sendBatch(List<String> mobileArray, String content)
	{
		try
		{
			SmsServiceInterface smsService = (SmsServiceInterface) Class.forName(smsChannel).newInstance();
			smsService.sendBatch(mobileArray, content);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("------- sms channel err ------:{}", smsChannel);
		}
	}
}
