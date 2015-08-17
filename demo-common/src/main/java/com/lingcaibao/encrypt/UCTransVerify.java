package com.lingcaibao.encrypt;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * (通讯相关) 
 * 与领彩宝通讯时的验签
 * @author siyuan.shi
 *
 */
public class UCTransVerify {

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String key, String input_charset) {
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String key,
			String input_charset) {
		text = text + key;
		String mysign = DigestUtils
				.md5Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
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
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}
	}

	public static void main(String[] args) throws Exception {

		String aaString = "aa";
		String bbString = "bb";
		String ccString = "cc";
		String ddString = "dd";
		String eeString = "ee";
		String ffString = "ff";

		List<String> list = new ArrayList<String>();

		list.add(aaString);
		list.add(aaString);
		list.add(bbString);
		list.add(aaString);
		list.add(ccString);
		list.add(aaString);
		list.add(ddString);
		list.add(eeString);
		list.add(ddString);

		list.add(ddString);
		list.add(ffString);
		list.add(ddString);
		list.add(ddString);
		Collections.sort(list);
		String sign = "321e1irpQoYy+DVkPP08Wow9EpGule1gd0CDxAhRJpIyGU/RwnzrVbB77hVezWTdLzmaUEMh90gDl8K780yfpQqI/cmmNDZdSiKj7W9yTpM";

		String sign2 = sign(sign, "key", "utf-8");
		System.out.println(sign2);
		boolean verify = verify(sign, sign2, "key", "utf-8");
		System.out.println(verify);
		
		System.out.println(URLEncoder.encode("321e1irpQoYy+DVkPP08Wow9EpGule1gd0CDxAhRJpIyGU/RwnzrVbB77hVezWTdLzmaUEMh90gDl8K780yfpQqI/cmmNDZdSiKj7W9yTpM", "UTF-8"));
		System.out.println(URLEncoder.encode(sign2, "utf-8"));
	}
}
