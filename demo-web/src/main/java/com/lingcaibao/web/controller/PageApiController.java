package com.lingcaibao.web.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lingcaibao.entity.Bill;
import com.lingcaibao.entity.ResultPage;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.service.BillService;
import com.lingcaibao.util.SqlUtils;
import com.palm.commom.uitl.Servlets;

@Controller
@RequestMapping(value = "/admin/pageApi")
public class PageApiController {
	@Autowired
	private BillService billService;

	private static final String PAGE_SIZE = "10";
	//http://localhost:8080/market-web/admin/pageApi?page=1&rows=2&sort=id_
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "id_") String sort,
			ServletRequest request, Model model) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "s_");
		
		String sortStr = SqlUtils.buildSort(sort);
		if (sortStr.length() > 0) {
			searchParams.put("sortColumns", sortStr);
		}
		
		Page<Bill> page = new Page<Bill>(pageNumber, pageSize);
		page = billService.searchPage(searchParams, page);
		ResultPage resultPage = new ResultPage(page);
		
		model.addAttribute("page", resultPage);
		
		return "pageApi";
	}
	
	//http://localhost:8080/market-web/admin/pageApi/list2?page=1&rows=2&sort=id_
	@ResponseBody
	@RequestMapping(value = "/list2")
	public String listByJson(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "id_") String sort,
			ServletRequest request, Model model) throws ParseException {
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "s_");
		
		String sortStr = SqlUtils.buildSort(sort);
		if (sortStr.length() > 0) {
			searchParams.put("sortColumns", sortStr);
		}
		
		Page<Bill> page = new Page<Bill>(pageNumber, pageSize);
		page = billService.searchPage(searchParams, page);
		ResultPage resultPage = new ResultPage(page);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultPage", resultPage);
		map.put("userId", 1);
		
		return JSON.toJSONString(map);
	}
	
}
