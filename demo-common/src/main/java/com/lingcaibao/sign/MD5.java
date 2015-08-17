package com.lingcaibao.sign;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import org.apache.commons.codec.digest.DigestUtils;
import com.lingcaibao.encrypt.UCEncryptor;

/**
 * <p>标题：签名(MD5) </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月10日 下午5:41:24</p>
 * <p>类全名：com.lingcaibao.sign.MD5</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class MD5
{
	/**
	 * 签名字符串
	 * @param text 需要签名的字符串
	 * @param key 密钥
	 * @param input_charset 编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String key, String input_charset)
	{
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * 签名字符串
	 * @param text 需要签名的字符串
	 * @param sign 签名结果
	 * @param key 密钥
	 * @param input_charset 编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String key, String input_charset)
	{
		text = text + key;
		String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException 
	 */
	private static byte[] getContentBytes(String content, String charset)
	{
		if (charset == null || "".equals(charset))
		{
			return content.getBytes();
		}
		try
		{
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	public static void main(String[] args)
	{
		System.out.println(sign("aaaa", "key", "utf-8"));
		String encrypt = UCEncryptor.encrypt("111111111111");
		System.out.println(encrypt);
		System.out.println(URLEncoder.encode(encrypt));
	}
}