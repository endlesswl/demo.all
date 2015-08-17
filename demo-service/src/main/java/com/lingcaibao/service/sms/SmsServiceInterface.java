package com.lingcaibao.service.sms;

import java.util.List;

/**
 * <p>标题：短信服务接口 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月19日 下午12:18:45</p>
 * <p>类全名：com.lingcaibao.service.sms.SmsServiceInterface</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public interface SmsServiceInterface
{
	public boolean sendSingle(String mobile, String content);
	
	public void sendBatch(List<String> mobiles, String content);
}
