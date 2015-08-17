package com.lingcaibao.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author pangxin
 *
 */
@Controller
@RequestMapping("/wx")
public class WeiXinApiController {
	/**
	 * 模板Test
	 * @param model
	 * @return
	 */
	@RequestMapping("/testVm")
	public String testVm(Model model,HttpServletRequest request) {
//		model.addAttribute("path", request);
		model.addAttribute("name", "asdfasdfadf");
		
		return "hello";
	}
}
