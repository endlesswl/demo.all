package com.lingcaibao.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.junit.Test;
import org.nutz.http.Http;
import org.springframework.web.bind.annotation.RequestMapping;

import com.palm.commom.uitl.PalmBuildParams;
import com.palm.commom.uitl.UUIDUtils;

public class UserLuckdrawControllerTest extends BaseTest {
	
	@Test
	public void TestGetUserListCount(){
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("marketId", "8624");
			
			//send
			String url = "http://localhost:8080/home/userLuckdraw/getUserListCount";
			url= paramToUrl(url,paramMap);
			System.out.println("url:"+url);
			//return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getUserMobileDateReport(){
		String url = "http://localhost:8080/home/userLuckdraw/getUMDReport";
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			paramMap.put("marketId", "9972");
			
			//send
			url= paramToUrl(url,paramMap);
			System.out.println("url:"+url);
			//return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAllPrizeMoneyReport() {
		String url = "http://localhost:8080/home/userLuckdraw/getAPMReport";
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			paramMap.put("marketId", "9970");
			paramMap.put("timeType", "d");
			
			//send
			url= paramToUrl(url,paramMap);
			System.out.println("url:"+url);
			//return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		String url = "http://localhost:8080/home/phoneArea/initPhoneArea";
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			
			//send
			url= paramToUrl(url,paramMap);
			System.out.println("url:"+url);
			//return
			String result = Http.get(url).getContent();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
