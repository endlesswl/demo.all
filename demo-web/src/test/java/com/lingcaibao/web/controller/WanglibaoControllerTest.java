package com.lingcaibao.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nutz.http.Http;

import com.palm.commom.uitl.PalmBuildParams;

public class WanglibaoControllerTest extends BaseTest {

	@Test
	public void TestGetUserListCount(){
		try {
			String url = "http://localhost:8080/web/wanglibao/login";
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("username", "13840167708");
			String mySign = PalmBuildParams.buildRequestMysign(paramMap, "wanglibao");
			paramMap.put("sign", mySign);
			url= paramToUrl(url, paramMap);
			System.out.println("url:"+url);
			//return
			//String result = Http.get(url).getContent();
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
