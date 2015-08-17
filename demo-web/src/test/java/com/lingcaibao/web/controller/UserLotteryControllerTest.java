package com.lingcaibao.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nutz.http.Http;

public class UserLotteryControllerTest extends BaseTest {

	@Test
	public void TestGetUserListCount(){
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			//send
			String url = "http://localhost:8080/home/userLottery/test";
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
