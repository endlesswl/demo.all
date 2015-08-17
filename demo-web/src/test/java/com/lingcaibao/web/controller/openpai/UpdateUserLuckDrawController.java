package com.lingcaibao.web.controller.openpai;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nutz.http.Http;

import com.palm.commom.uitl.PalmBuildParams;

public class UpdateUserLuckDrawController {

	@Test
	public void addUserLuckDrawByOrderid() {
		try {
			String signKey = "123456";
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("channel", "lingcaibao");
			String sign = PalmBuildParams.buildRequestMysign(paramMap, signKey);
			System.out.println(sign);
			paramMap.put("sign", sign);

			// URLEncoder
			paramMap = paramToURLEncoder(paramMap, "UTF-8");

			// send
			String url = "http://localhost:8080/openapi/uldApi/addDrawByOId";
			url = paramToUrl(url, paramMap);
			System.out.println("url:" + url);
			// return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addDrawByMarketId() {
		try {
			String signKey = "tianjinzhan";
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("channel", "tianjinzhan");
			String sign = PalmBuildParams.buildRequestMysign(paramMap, signKey);
			System.out.println(sign);
			paramMap.put("sign", sign);

			// URLEncoder
			// http://localhost:8080/openapi/uldApi/addDrawByMId?channel=tianjinzhan&sign=8f0ebcd94284a8821f8a7f4d0e406efb&
			paramMap = paramToURLEncoder(paramMap, "UTF-8");

			// send
			String url = "http://localhost:8080/openapi/uldApi/addDrawByMId";
			url = paramToUrl(url, paramMap);
			System.out.println("url:" + url);
			// return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addAllDraw(){
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			// http://localhost:8080/openapi/uldApi/addDrawByMId?channel=tianjinzhan&sign=8f0ebcd94284a8821f8a7f4d0e406efb&
			paramMap = paramToURLEncoder(paramMap, "UTF-8");

			// send
			String url = "http://localhost:8080/openapi/uldApi/addAllDraw";
			url = paramToUrl(url, paramMap);
			System.out.println("url:" + url);
			// return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<String, String> paramToURLEncoder(Map<String, String> paramMap, String encode)
			throws UnsupportedEncodingException {
		for (String key : paramMap.keySet()) {
			paramMap.put(key, URLEncoder.encode(paramMap.get(key), encode));
		}
		return paramMap;
	}

	private static String paramToUrl(String url, Map<String, String> paramMap) {
		url += "?";
		for (String key : paramMap.keySet()) {
			url += key + "=" + paramMap.get(key) + "&";
		}
		return url;
	}

}
