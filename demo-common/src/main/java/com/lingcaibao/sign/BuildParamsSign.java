package com.lingcaibao.sign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Maps;
import com.lingcaibao.pay.PayConstant;
/**
 * <p>标题：签名生成工具 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月10日 下午5:46:50</p>
 * <p>类全名：com.lingcaibao.sign.BuildParamsSign</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class BuildParamsSign
{
	/**
	 * 生成签名结果
	 * @param params
	 * @param key
	 * @return
	 */
	public static String buildRequestMysign(Map<String,String> params, String signType)
	{
		String prestr = createLinkString(params);
		// MD5方式签名
		if (StringUtils.equals(signType, SignConstant.SIGN_TYPE_MD5))
		{
			return MD5.sign(prestr, PayConstant.SIGN_KEY_MD5, "utf-8");
		}
		// RSA方式签名
		else if (StringUtils.equals(signType, SignConstant.SIGN_TYPE_RSA))
		{
			// TODO
			return null;
		}
		return null;
	}

	/**
	 * 生成签名结果
	 * @param key
	 * @param params
	 * @param input_charset
	 * @return
	 */
	public static String buildRequestMysign(String key, Map<String,String> params, String signType, String input_charset)
	{
		String prestr = createLinkString(params);
		// MD5方式签名
		if (StringUtils.equals(signType, SignConstant.SIGN_TYPE_MD5))
		{
			return MD5.sign(prestr, key, input_charset);
		}
		// RSA方式签名
		else if (StringUtils.equals(signType, SignConstant.SIGN_TYPE_RSA))
		{
			// TODO
			return null;
		}
		return null;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param params
	 * @return
	 */
	public static String createLinkString(Map<String,String> params)
	{
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1)
			{//拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else
			{
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	/**
	 * 除去数组中的空值和签名参数
	 * @param params
	 * @return
	 */
	public static Map<String,String> paramFilter(Map<String,String> params)
	{
		Map<String,String> result = new HashMap<String,String>();
		if (params == null || params.size() <= 0)
		{
			return result;
		}
		for (String key : params.keySet())
		{
			String value = params.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type"))
			{
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	public static void main(String[] args)
	{
		String channelSign = "kkkkkk";
		Map<String,String> params = Maps.newHashMap();
		params.put("orderNo", "3333331111");
		params.put("mobile", "222222222");
		params.put("gameid", "SSQ");
		params.put("gameType", "2");
		params.put("issueNo", "10");
		params.put("channelKey", "dddddd");
		String mysign = buildRequestMysign(params, SignConstant.SIGN_TYPE_MD5);
		System.out.println(mysign);
	}
}
