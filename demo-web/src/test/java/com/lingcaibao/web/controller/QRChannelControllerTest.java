package com.lingcaibao.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nutz.http.Http;

public class QRChannelControllerTest extends BaseTest {
	
	@Test
	public void getMarketQRCodes(){
    	//send
		String url = "http://localhost:8080/home/qrchannel/getMarketQRCodes";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("marketId", "10012");
		try {
			
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
