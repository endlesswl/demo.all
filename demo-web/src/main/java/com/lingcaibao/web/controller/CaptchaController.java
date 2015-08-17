package com.lingcaibao.web.controller;

import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lingcaibao.cache.memcached.MarketMemcachedClient;
import com.lingcaibao.email.E3Api;
import com.lingcaibao.entity.Sms;
import com.lingcaibao.entityenum.UserMsgTemp;
import com.lingcaibao.mail.LcbMail;
import com.lingcaibao.service.SmsService;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.sms.SmsSendService;
import com.palm.commom.uitl.UUIDUtils;
@Controller
@RequestMapping
public class CaptchaController
{
	private static Logger			logger	= LoggerFactory.getLogger(CaptchaController.class);
	@Autowired
	private UserService				userService;
	@Autowired
	private SmsService				smsService;
	@Autowired
	private MarketMemcachedClient	memcachedClient;

	/**
	 * 发送验证码短信
	 * @param type 短信类型
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "captcha/sms")
	public String sendSmsCaptcha(@RequestParam("type") String type, @RequestParam("mobile") String mobile)
	{
		Map<String,String> resultMap = Maps.newHashMap();
		// 获取消息模板
		UserMsgTemp temp = UserMsgTemp.getMsgTemp(type);
		if (temp == null)
		{
			resultMap.put("result", "false");
			resultMap.put("message", "暂不支持信息模板类型" + type);
			return JSON.toJSONString(resultMap);
		}
		try
		{
			// 获取用户验证码
			String msgKey = "CAPTCHA_" + temp.name() + "_" + mobile;
			String captcha = UUIDUtils.getNumberBySix();
			memcachedClient.set(msgKey, 60 * 30 * 1, captcha);
			logger.info("------------ sms mobile -------------:{}", mobile);
			logger.info("------------ sms captcha -------------:{}", captcha);
			String content = temp.getMessage(captcha);
			SmsSendService sendService = new SmsSendService(null);
			if (sendService.sendSingle(mobile, content))
			{
				Sms sms = new Sms();
				sms.setOperator(mobile);
				sms.setContent(content);
				sms.setMobile(mobile);
				sms.setCreatetime(new Date());
				sms.setNumbers(1);
				smsService.insert(sms);
				resultMap.put("result", "true");
				resultMap.put("message", "短信发送成功!");
				return JSON.toJSONString(resultMap);
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("-----------{} send sms err ------------:{}", mobile, ex.getMessage());
		}
		resultMap.put("result", "false");
		resultMap.put("message", "发送短信失败!");
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 发送验证码短信
	 * @param type 短信类型
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "captcha/email")
	public String sendEmailCaptcha(@RequestParam("type") String type, @RequestParam("email") String email)
	{
		Map<String,String> resultMap = Maps.newHashMap();
		// 获取消息模板
		UserMsgTemp temp = UserMsgTemp.getMsgTemp(type);
		if (temp == null)
		{
			resultMap.put("result", "false");
			resultMap.put("message", "暂不支持信息模板类型" + type);
			return JSON.toJSONString(resultMap);
		}
		try
		{
			// 获取用户验证码
			String msgKey = "CAPTCHA_" + temp.name() + "_" + email;
			String captcha = UUIDUtils.getNumberBySix();
			memcachedClient.set(msgKey, 60 * 30 * 1, captcha);
			logger.info("------------ send email -------------:{}", email);
			logger.info("------------ email captcha -------------:{}", captcha);
			if (E3Api.sendPost(email, temp.getTitle(), temp.getMessage(captcha)))
			{
				resultMap.put("result", "true");
				resultMap.put("message", "发送邮件成功!");
				return JSON.toJSONString(resultMap);
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("-----------{} send email err ------------:{}", email, ex.getMessage());
		}
		resultMap.put("result", "false");
		resultMap.put("message", "发送邮件失败!");
		return JSON.toJSONString(resultMap);
	}

	@ResponseBody
	@RequestMapping(value = "captcha/sendSms")
	public String sendSms()
	{
		String captcha = UUIDUtils.getNumberBySix();
		UserMsgTemp temp = UserMsgTemp.getMsgTemp("CREATE_BANK_CARD");
		String content = temp.getMessage(captcha);
		SmsSendService sendService = new SmsSendService(null);
		StringBuffer buf = new StringBuffer();
		for (LcbMail mail : LcbMail.values())
		{
			boolean result = false;
			try
			{
				result = sendService.sendSingle(mail.getMobile(), content);
			} catch (Exception ex)
			{
				ex.printStackTrace();
				logger.error("-------- sendSms err ---------:{}", ex.getMessage());
			}
			buf.append(mail.getName());
			buf.append("  ");
			buf.append(mail.getMobile());
			buf.append("  ");
			buf.append(mail.getOperator());
			buf.append("  ");
			buf.append(result ? "响应成功" : "响应失败");
			buf.append("\r\n");
		}
		return buf.toString();
	}
}
