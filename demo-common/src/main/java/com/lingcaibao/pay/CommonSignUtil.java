package com.lingcaibao.pay;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.palm.commom.uitl.DateFormatUtil;
/**
 * <p>标题：签名工具 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月11日 下午4:29:10</p>
 * <p>类全名：com.lingcaibao.pay.CommonSignUtil</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class CommonSignUtil
{
	private static Logger		logger			= LoggerFactory.getLogger(CommonSignUtil.class);
	// 不需要签名的object中的对象key
	private static List<String>	unSignKeyList	= Arrays.asList("sign", "ipAddr", "merchantNo");

	/**
	 * md5对数据进行签名
	 * @param object
	 * @param salt
	 * @return
	 */
	public static String signMd5(Object object, String salt)
	{
		String md5Sign = "";
		try
		{
			String sourceSignString = CommonSignUtil.signString(object, unSignKeyList);
			System.out.println("MD5:" + sourceSignString + salt.trim());
			logger.debug("CommonSignUtil.signMd5 sourceSignString:{}", sourceSignString);
			byte[] bytes = (sourceSignString.trim() + salt.trim()).getBytes("utf-8");
			md5Sign = DigestUtils.md5Hex(bytes).toUpperCase();
			logger.debug("CommonSignUtil.signMd5 md5:{}", md5Sign);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return md5Sign;
	}

	/**
	 * md5对数据进行签名
	 * 
	 * @param object
	 *            需要签名的对象
	 * @param salt
	 *            盐
	 * @return
	 */
	public static String signMd5(String sourceSignString, String salt)
	{
		String md5Sign = "";
		try
		{
			logger.debug("CommonSignUtil.signRSA sourceSignString:{}", sourceSignString);
			byte[] bytes = (sourceSignString.trim() + salt.trim()).getBytes("utf-8");
			md5Sign = DigestUtils.md5Hex(bytes).toUpperCase();
			logger.debug("CommonSignUtil.signMd5 md5:{}", md5Sign);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return md5Sign;
	}

	/**
	 * 参数签名(字符串)
	 * @param signType
	 * @param sourceSignString
	 * @param salt
	 * @return
	 */
	public static String sign(String signType, String sourceSignString)
	{
		String mySign = "";
		if (signType.equals("MD5"))
		{
			mySign = CommonSignUtil.signMd5(sourceSignString, PayConstant.SIGN_KEY_MD5);
		} else if (signType.equals("RSA"))
		{
			mySign = CommonSignUtil.signRSA(sourceSignString, PayConstant.SIGN_KEY_RSA);
		}
		return mySign;
	}

	/**
	 * 参数签名(实体)
	 * @param signType
	 * @param sourceSignString
	 * @param salt
	 * @return
	 */
	public static String sign(String signType, Object source)
	{
		String mySign = "";
		if (signType.equals("MD5"))
		{
			mySign = CommonSignUtil.signMd5(source, PayConstant.SIGN_KEY_MD5);
		} else if (signType.equals("RSA"))
		{
			mySign = CommonSignUtil.signRSA(source, PayConstant.SIGN_KEY_RSA);
		}
		return mySign;
	}

	/**
	 * ras私钥对数据进行签名
	 * 
	 * @param object
	 *            需要签名的对象
	 * @param rsaPriKey
	 *            私钥
	 * @return
	 */
	public static String signRSA(String sourceSignString, String rsaPriKey)
	{
		String result = "";
		try
		{
			logger.debug("CommonSignUtil.signRSA sourceSignString:{}", sourceSignString);
			// 摘要
			String sha256SourceSignString = SHAUtil.Encrypt(sourceSignString, null);
			// 私钥对摘要进行加密
			byte[] newsks = RSACoder.encryptByPrivateKey(sha256SourceSignString.getBytes("UTF-8"), rsaPriKey);
			result = RSACoder.encryptBASE64(newsks);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ras私钥对数据进行签名
	 * 
	 * @param object
	 *            需要签名的对象
	 * @param rsaPriKey
	 *            私钥
	 * @return
	 */
	public static String signRSA(Object object, String rsaPriKey)
	{
		String result = "";
		try
		{
			// 获取签名需要字符串和类型
			String sourceSignString = CommonSignUtil.signString(object, unSignKeyList);
			logger.debug("CommonSignUtil.signRSA sourceSignString:{}", sourceSignString);
			// 摘要
			String sha256SourceSignString = SHAUtil.Encrypt(sourceSignString, null);
			// 私钥对摘要进行加密
			byte[] newsks = RSACoder.encryptByPrivateKey(sha256SourceSignString.getBytes("UTF-8"), rsaPriKey);
			result = RSACoder.encryptBASE64(newsks);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 生成签名原串
	 *
	 * @param object
	 * @param unSignKeyList
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String signString(Object object, List<String> unSignKeyList) throws IllegalArgumentException, IllegalAccessException
	{
		TreeMap<String,Object> map = CommonSignUtil.objectToMap(object);
		Date tradeTime = (Date) map.get("tradeTime");
		if (tradeTime != null)
		{
			map.put("tradeTime", DateFormatUtil.getSimpleStringDate(tradeTime));
		}
		// 删除不需要参与签名的属性
		for (String str : unSignKeyList)
		{
			map.remove(str);
		}
		// 连接
		return createLinkStr(map);
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkStr(TreeMap<String,Object> map)
	{
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i);
			String value = (String) map.get(key);
			if (i == keys.size() - 1)
			{// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else
			{
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	/**
	 * 对象转map
	 * 
	 * @param object
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static TreeMap<String,Object> objectToMap(Object object) throws IllegalArgumentException, IllegalAccessException
	{
		TreeMap<String,Object> map = new TreeMap<String,Object>();
		// 父类属性
		for (Class<?> cls = object.getClass(); cls != Object.class; cls = cls.getSuperclass())
		{
			// 添加属性key到list
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields)
			{
				f.setAccessible(true);
				map.put(f.getName(), f.get(object) == null ? "" : f.get(object));
			}
		}
		return map;
	}

	public static String toUrlParams(Object source) throws IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException
	{
		String url = "";
		Map<String,Object> params = CommonSignUtil.objectToMap(source);
		int i = 0;
		for (String key : params.keySet())
		{
			if (i > 0)
			{
				url += "&";
			}
			url += key + "=" + URLEncoder.encode((String) params.get(key), "UTF-8");
			i++;
		}
		return url;
	}

	public static void main(String[] args)
	{
		byte[] binaryData = "sicFkp+wDpE1+MTTsfgDR3FkTcNh7RyWFsEvkLfX9a5KGVRVPMKaBhApRCd9MwvxWQyM2ELNbwhxKl36owitqeacF0RSxGhkTrrqDZ3N4+sdf".getBytes();
		String encodeBase64URLSafeString = Base64.encodeBase64URLSafeString(binaryData);
		System.out.println(encodeBase64URLSafeString);
		byte[] decodeBase64 = Base64.decodeBase64(encodeBase64URLSafeString);
		String s = new String(decodeBase64);
		System.out.println(s.toString());
	}
}
