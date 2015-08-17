package com.lingcaibao.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nutz.http.Http;


//import org.springside.modules.web.Servlets;

/**
 * @Title:支付接口
 * @Description:支付相关功能，包括活动定制支付相关功能
 * @Author nzh
 * @Date 2015-3-31
 * @Version V1.0
 * @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
 */

public class PaymentControllerTest extends BaseTest {

	public void getMarketplanPaymentDetails() {
	}

	@Test
	public void savePayment() {
		//send
		String url = "http://localhost:8080/home/payment/savePayment";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("money", "3");
		paramMap.put("payPwd", "123123");
		paramMap.put("ifInvoice", "2");
		paramMap.put("marketplanId", "10363");
		paramMap.put("balance", "0");
		paramMap.put("ifBalance", "false");
		paramMap.put("ifAlipay", "false");
		paramMap.put("alipay", "3");
		paramMap.put("ifunionpay", "false");
		paramMap.put("unionpay", "0");
		paramMap.put("userid", "10000048");
		
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

	public void saveRenewPayment() {

	}

	public static void buildSort() {
	}

	public void paymentList(){
		
	}

	public void paymentDetail() {

	}
}
