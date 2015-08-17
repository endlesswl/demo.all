package com.lingcaibao.flow.oufei;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <p>标题：欧飞签名工具 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月19日 下午8:51:12</p>
 * <p>类全名：com.lingcaibao.flow.oufei.SPSignTool</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class SPSignTool
{
	public static boolean signVerify(String appSecret, Map<String,String> params)
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("appSecret", appSecret);
		for (String key : params.keySet())
		{
			if (!key.equals("sign"))
			{
				map.put(key, params.get(key));
			}
		}
		String sign = sign(map);
		if (sign.equals(params.get("sign")))
		{
			return true;
		}
		return false;
	}

	private static String toHexValue(byte[] messageDigest)
	{
		if (messageDigest == null)
			return "";
		StringBuilder hexValue = new StringBuilder();
		for (byte aMessageDigest : messageDigest)
		{
			int val = 0xFF & aMessageDigest;
			if (val < 16)
			{
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 签名接口
	 * @param params
	 * @return
	 */
	public static String sign(Map<String,String> params)
	{
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String string = "";
		for (String s : keys)
		{
			string += params.get(s);
		}
		String sign = "";
		try
		{
			sign = toHexValue(encryptMD5(string.getBytes(Charset.forName("utf-8")))).toUpperCase();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("md5 error");
		}
		return sign;
	}

	private static byte[] encryptMD5(byte[] data) throws Exception
	{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}

	public static void main(String[] args)
	{
		String appKey = "key";
		String appSecret = "secret";
		Map<String,String> params = new HashMap<String,String>();
		params.put("appKey", appKey);
		params.put("appSecret", appSecret);
		params.put("date", new Date().getTime() + "");
		String sign = sign(params);
		params.put("sign", sign);
		System.out.println(signVerify(appSecret, params));
	}
}
