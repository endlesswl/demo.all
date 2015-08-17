package com.lingcaibao.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
/**
 * <p>标题：3DES 解密和加密算法 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年6月30日 下午3:06:55</p>
 * <p>类全名：com.lingcaibao.util.ThreeDesUtils</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class ThreeDesUtils
{
	static public String	DEFAULT_KEY	= "02ca9e87636d438a8d1c6000d42cf5e4";

	public static String encryptThreeDESECB(String src, String key)
	{
		try
		{
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			byte[] b = cipher.doFinal(src.getBytes());
			return BASE64.encode(b).replaceAll("\r", "").replaceAll("\n", "");
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	//3DESECB解密,key必须是长度大于等于 3*8 = 24 位
	public static String decryptThreeDESECB(String src, String key)
	{
		try
		{
			//--解密的key
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			//--Chipher对象解密
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, securekey);
			byte[] retByte = cipher.doFinal(BASE64.decode(src));
			return new String(retByte);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		String source = "666666";
		String edes = ThreeDesUtils.encryptThreeDESECB(source, ThreeDesUtils.DEFAULT_KEY);
		System.err.println(edes);
		String ddes = ThreeDesUtils.decryptThreeDESECB(edes, ThreeDesUtils.DEFAULT_KEY);
		System.err.println(ddes);
	}
}
