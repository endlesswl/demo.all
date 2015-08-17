package com.lingcaibao.charge.duiba;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.nutz.http.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.palm.commom.uitl.UUIDUtils;
/**
 * 
* 标题：   兑吧话费对接
* 项目名称：market-common   
* 类名称：DBChargeUtil   
* 类描述：   
* 创建人：xpj   
* 创建时间：2015年4月20日 下午5:06:48   
* 公司：北京零彩宝网络技术有限公司 
* 版权：Copyright (c) 2015  
* 修改备注：   
* @version   1.0 
*
 */
public class DBChargeUtil
{
	//private static final String	appKey		= "qnimYQ2rfjV4a1Hs33zyVj27wZ6";
	private static final String	appKey		= "2mptyaGywiQYD5Qgssh9nRmjPweD";
	private static Logger		logger		= LoggerFactory.getLogger(DBChargeUtil.class);
	//private static final String	appSecret	= "2WRpaMh4ZywPdtoAN5iij3XiLx1W";
	private static final String	appSecret	= "WvcURM51t3Gq4Ef3JbCNDiHnaz1";
	private static final String	url			= "http://www.duiba.com.cn/charge/exchangePhonebill";

	/**
	 * 充值话费
	 * @param quantity
	 * @param phone
	 * @return 返回订单号
	 */
	public static String doRecharge(String quantity, String phone)
	{
		try
		{
			String orderid = UUIDUtils.getSerialID().toString();
			Map<String,String> params = Maps.newHashMap();
			params.put("appKey", appKey);
			params.put("appSecret", appSecret);
			params.put("phone", phone);
			params.put("quantity", quantity);
			params.put("bizId", orderid);
			params.put("timestamp", System.currentTimeMillis() + "");
			String sign = SignTool.sign(params);
			params.put("sign", sign);
			Map<String,Object> parm = Maps.newHashMap();
			parm.putAll(params);
			parm.remove("appSecret");
			String post = Http.post(url, parm, 1000);
			if (StringUtils.isNotEmpty(post))
			{
				JSONObject result = JSON.parseObject(post);
				if (result.getBooleanValue("success"))
				{
					return orderid;
				} else
				{
					throw new RuntimeException(result.getString("errorMessage"));
				}
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("------------mobile:{} do recharge err ------------:{}", phone, ex.getMessage());
		}
		return null;
	}

	public static void main(String[] args)
	{
		String orderid = doRecharge("1.00", "13718154066");
		System.out.println(orderid);
	}
}
