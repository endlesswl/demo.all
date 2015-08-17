package com.lingcaibao.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.nutz.http.Http;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;

public class MarketplanControllerTest extends BaseTest {

	// 模拟request,response
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	// 注入userController
//	@Autowired
//	private MarketplanController marketplanController;

	// 执行测试方法之前初始化模拟request,response
	@Before
	public void setUp() {
//		request = new MockHttpServletRequest();
//		request.setCharacterEncoding("UTF-8");
//		response = new MockHttpServletResponse();
	}

	@Test
	public void testHeadTop() {
		//send
		String url = "http://localhost:8080/home/marketplan/testHeadTop";
		Map<String, String> paramMap = new HashMap<String, String>();
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
	
//
//	@Test
//	public void testGetMarketplanList() {
//
//	}
//
	@Test
	public void testIfRegisterUrl() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
         
        request.setRequestURI("/ifRegisterUrl");
        request.setMethod("GET");
         
        try{
//            final ModelAndView mav = this.excuteAction(request, response);
//            System.out.println(mav.getViewName());
//            Object msg = mav.getModelMap().get("roles");
            System.out.println(" = ");
        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
    @Test
    public void testList() throws Exception {

        String url = "http://localhost:8080/market-wap/mobile/marketplan/getServerCharge?userId=997790";

//        MvcResult result;
//        result = request.perform().andReturn();

        System.out.println("=======================================================");
//        System.out.println(result.getResponse().getContentAsString());
        System.out.println("=======================================================");

    }
    
    @Test
	public void getErrorLottery(){
    	//send
		String url = "http://localhost:8080/home/userLottery/getErrorLottery";
		Map<String, String> paramMap = new HashMap<String, String>();
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
    
    @Test
	public void doErrorLottery(){
    	//send
		String url = "http://localhost:8080/home/userLottery/doErrorLottery";
		Map<String, String> paramMap = new HashMap<String, String>();
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
    
    @Test
    public void planInfo(){
    	//send
		String url = "http://localhost:8080/home/marketplan/planInfo";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("marketplanId", "10012");
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
