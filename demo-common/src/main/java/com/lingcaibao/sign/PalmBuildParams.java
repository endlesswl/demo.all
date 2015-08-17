package com.lingcaibao.sign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Maps;
public class PalmBuildParams
{
	/**
	 * 生成签名结果
	 *
	 * @param sPara 要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(Map<String,String> sPara, String key)
	{
		String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = MD5.sign(prestr, key, "utf-8");
		return mysign;
	}

	/**
	 * 生成签名结果
	 * @param sPara 要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(String key, Map<String,String> sPara, String input_charset)
	{
		String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = MD5.sign(prestr, key, input_charset);
		return mysign;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
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
	 *
	 * @param sArray 签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String,String> paraFilter(Map<String,String> sArray)
	{
		Map<String,String> result = new HashMap<String,String>();
		if (sArray == null || sArray.size() <= 0)
		{
			return result;
		}
		for (String key : sArray.keySet())
		{
			String value = sArray.get(key);
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
		String mysign = buildRequestMysign(params, channelSign);
		System.out.println(mysign);
	}
}
