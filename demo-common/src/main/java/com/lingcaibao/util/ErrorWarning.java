package com.lingcaibao.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.nutz.http.Http;

import com.lingcaibao.conf.Conf;
import com.lingcaibao.statuscode.WarningEnum;

/**
 * 监控 微信报警
 * @author nzh
 * 
 */
public class ErrorWarning {
	/**
	 * 监控异常
	 * 
	 * @param code
	 *            消息编码
	 * @param level
	 *            错误等级
	 * @param content
	 *            内容
	 */
	public static void sendWarning(String code, String level, String content) {
		// send
		String url = "http://nt.lingcaibao.com/monitor";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("code", code);
		paramMap.put("level", level);
		paramMap.put("content", content);
		try {
			// URLEncoder
			paramMap = paramToURLEncoder(paramMap, "UTF-8");

			url = paramToUrl(url, paramMap);
			System.out.println("url:" + url);
			// return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 监控异常
	 * 
	 * @param code
	 *            消息编码 默认编码
	 * @param level
	 *            错误等级
	 * @param content
	 *            内容
	 */
	public static void sendWarning(String level, String content) {
		// send
		String code = Conf.get("");
		String url = "http://nt.lingcaibao.com/monitor";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("code", code);
		paramMap.put("level", level);
		paramMap.put("content", content);
		try {
			// URLEncoder
			paramMap = paramToURLEncoder(paramMap, "UTF-8");

			url = paramToUrl(url, paramMap);
			System.out.println("url:" + url);
			// return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String paramToUrl(String url, Map<String, String> paramMap) {
		url += "?";
		for (String key : paramMap.keySet()) {
			url += key + "=" + paramMap.get(key) + "&";
		}
		return url;
	}

	private static Map<String, String> paramToURLEncoder(Map<String, String> paramMap, String encode)
			throws UnsupportedEncodingException {
		for (String key : paramMap.keySet()) {
			paramMap.put(key, URLEncoder.encode(paramMap.get(key), encode));
		}
		return paramMap;
	}
	
	public static void main(String[] args) {
		sendWarning(WarningEnum.MARKETPLAN.getCode(), "2", "测试异常");
	}
}
