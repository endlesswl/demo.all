package com.lingcaibao.flow.feiyin;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class PalmSignUtil
{
	static private String	SIGN_KEY	= "9FK99t7262afZEL6";

	public static boolean signMD5Verify(String appSecret, Map<String,String> params, String charSet)
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
		String sign = signMD5(map, charSet);
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
	 * MD5加密签名
	 * @param params
	 * @param charSet
	 * @return
	 */
	public static String signMD5(Map<String,String> params, String charSet)
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
			sign = toHexValue(encryptMD5(string.getBytes(Charset.forName(charSet))));
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

	
	/**
	 * AES加密签名
	 * @param params
	 * @param charSet
	 * @return
	 */
	static public String signAES(Map<String,String> params, String charSet)
	{
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String content = "";
		for (String key : keys)
		{
			content += params.get(key);
		}
		return encryptAES(content, charSet);
	}

	public static String encryptAES(String content, String charSet)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = content.getBytes(Charset.forName(charSet));
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0)
			{
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(SIGN_KEY.getBytes(Charset.forName(charSet)), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(SIGN_KEY.getBytes(Charset.forName(charSet)));
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return parseByte2HexStr(encrypted);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String parseByte2HexStr(byte buf[])
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
}
