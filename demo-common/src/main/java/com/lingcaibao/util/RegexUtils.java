package com.lingcaibao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的正则表达式
 * 
 * @author nzh
 * 
 */
public class RegexUtils {
	/**
	 * 判断是否是正确的IP地址
	 * 
	 * @param ip
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isIp(String ip) {
		if (null == ip || "".equals(ip))
			return false;
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		return ip.matches(regex);
	}

	/**
	 * 判断是否是正确的邮箱地址
	 * 
	 * @param email
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return email.matches(regex);
	}

	/**
	 * 判断是否为密码安全最高等级
	 */
	public static boolean idPwdBest(String pwd) {
		if (null == pwd || "".equals(pwd))
			return false;
		String regex = "(?=^.{8,12}$)(?=(?:.*?\\d){1})(?=.*[a-z])(?=(?:.*?[A-Z]){1})(?=(?:.*?[~!@#$%*()_+^&}{:;?.]){1})(?!.*\\s)[0-9a-zA-Z~!@#$%*()_+^&]*$";
		return pwd.matches(regex);
	}

	/**
	 * 判断是否含有中文，仅适合中国汉字，不包括标点
	 * 
	 * @param text
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isChinese(String text) {
		if (null == text || "".equals(text))
			return false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(text);
		return m.find();
	}

	/**
	 * 判断是否正整数
	 * 
	 * @param number
	 *            数字
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isNumber(String number) {
		if (null == number || "".equals(number))
			return false;
		String regex = "[0-9]*";
		return number.matches(regex);
	}

	/**
	 * 判断几位小数(正数)
	 * 
	 * @param decimal
	 *            数字
	 * @param count
	 *            小数位数
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isDecimal(String decimal, int count) {
		if (null == decimal || "".equals(decimal))
			return false;
		String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count + "})?$";
		return decimal.matches(regex);
	}

	/**
	 * 判断是否是手机号码
	 * 
	 * @param phoneNumber
	 *            手机号码
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		if (null == phoneNumber || "".equals(phoneNumber))
			return false;
		String regex = "^1[3|4|5|8][0-9]\\d{8}$";
		return phoneNumber.matches(regex);
	}

	/**
	 * 判断是否含有特殊字符
	 * 
	 * @param text
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean hasSpecialChar(String text) {
		if (null == text || "".equals(text))
			return false;
		if (text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
			// 如果不包含特殊字符
			return true;
		}
		return false;
	}

	/**
	 * 判断时间格式是否正确
	 * 
	 * @see (1)能匹配的年月日类型有：
	 * @see 2014年4月19日
	 * @see 2014年4月19号
	 * @see 2014-4-19
	 * @see 2014/4/19
	 * @see 2014.4.19
	 * @see (2)能匹配的时分秒类型有：
	 * @see 15:28:21
	 * @see 15:28
	 * @see 5:28 pm
	 * @see 15点28分21秒
	 * @see 15点28分 15点
	 * @see (3)能匹配的年月日时分秒类型有：
	 * @see (1)和(2)的任意组合，
	 * @see 二者中间可有任意多个空格
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isTime(String text) {
		Pattern p = Pattern
				.compile(
						"(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
						Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		return p.matcher(text).matches();
	}

	/**
	 * 判断字符串是否是URL
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url) {
		Pattern p = Pattern
				.compile(
						"^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
						Pattern.CASE_INSENSITIVE);
		return p.matcher(url).matches();
	}

	/**
	 * 频率格式为限制
	 * 
	 * @see（1/d） 0:不限 1:总共1次 h:小时 d:日 w:周 m:月 q:季度 y:年
	 * @see 1/0
	 * @see 1/d
	 * @see 1/h
	 * @see 1/d
	 * @see 1/w
	 * @see 1/m
	 * @see 1/q
	 * @see 1/y
	 * @param text
	 * @return
	 */
	public static boolean isFrequency(String text) {
		String regex = "^[\\d]+/[01hdwmqy]$";
		return text.matches(regex);
	}

	/**
	 * 适应CJK（中日韩）字符集，部分中日韩的字是一样的
	 */
	public static boolean isChinese2(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String m = "http://www.qqgb.com/Program/Java/JavaFAQ/JavaJ2SE/Program_146959.html";    
		m = "http://baike.baidu.com/view/230199.htm?fr=ala0_1";  
		System.out.println(isUrl(m));  
		m = "http://www.google.cn/gwt/x?u=http%3A%2F%2Fanotherbug.blog.chinajavaworld.com%2Fentry%2F4550%2F0%2F&btnGo=Go&source=wax&ie=UTF-8&oe=UTF-8";  
		System.out.println(isUrl(m));  
		m = "http://zh.wikipedia.org:80/wiki/Special:Search?search=tielu&go=Go";  
        m = "http://www.baidu.com";
        System.out.println(isUrl(m));  
		
//		System.out.println(isFrequency("3/d"));
//		System.out.println(RegexUtils.isNumber("5245df"));
//		System.out.println(RegexUtils.isNumber("5245df"));
	}
}
