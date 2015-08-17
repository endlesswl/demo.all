package com.lingcaibao.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class BaseTest {
	public Map<String, String> paramToURLEncoder(Map<String, String> paramMap, String encode)
			throws UnsupportedEncodingException {
		for (String key : paramMap.keySet()) {
			paramMap.put(key, URLEncoder.encode(paramMap.get(key), encode));
		}
		return paramMap;
	}

	public String paramToUrl(String url, Map<String, String> paramMap) {
		url += "?";
		for (String key : paramMap.keySet()) {
			url += key + "=" + paramMap.get(key) + "&";
		}
		return url;
	}
}
