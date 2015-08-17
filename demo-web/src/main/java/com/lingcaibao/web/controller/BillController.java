package com.lingcaibao.web.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lingcaibao.entity.Bill;
import com.lingcaibao.entity.ResultPage;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.service.BillService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.util.LcbJson;
import com.lingcaibao.web.entityutil.BillUtil;
import com.palm.commom.uitl.DateFormatUtil;
/**
 * @Title:
 * @Description:
 * @Author jhe
 * @Date 2013-12-8
 * @Version V1.0
 * @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
 */
@Controller
@RequestMapping(value = "/home/bill")
public class BillController
{
	private static Logger				logger				= LoggerFactory.getLogger(BillController.class);
	@Autowired
	private BillService					billService;
	private static final String			PAGE_SIZE			= "10";
	// 允许排序的字段
	private static final List<String>	ALLOW_SORT_COLUMNS	= ImmutableList.of("id", "createtime");

	@ResponseBody
	@RequestMapping(value = "/billList")
	public String billList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "createtime_") String sort, ServletRequest request, Model model)
	{
		//交易方式
		String billchannel = request.getParameter("billchannel");
		String billret = request.getParameter("billret");
		String sBillTime = request.getParameter("sBillTime");
		String eBillTime = request.getParameter("eBillTime");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Map<String,Object> searchParams = Maps.newHashMap();
		searchParams.put("userid", shiroUser.id);//997790L
		// 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
		String sortStr = buildSort(sort);
		if (sortStr.length() > 0)
		{
			searchParams.put("sortColumns", sortStr);
		}
		if (!StringUtils.isEmpty(sBillTime))
		{
			searchParams.put("sBillTime", DateFormatUtil.toDate(sBillTime + "  00:00:00", "yyyy-MM-dd HH:mm:ss").getTime());
		}
		if (!StringUtils.isEmpty(eBillTime))
		{
			searchParams.put("eBillTime", DateFormatUtil.toDate(eBillTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime());
		}
		if (!StringUtils.isEmpty(billret))
		{
			searchParams.put("billret", billret);
		}
		if (!StringUtils.isEmpty(billchannel))
		{
			searchParams.put("billchannel", billchannel);
		}
		Page<Bill> page = new Page<Bill>(pageNumber, pageSize);
		page = billService.searchPage(searchParams, page);
		ResultPage resultPage = new ResultPage(page);
		@SuppressWarnings("unchecked")
		List<Bill> list = (List<Bill>) resultPage.getList();
		List<Map<String,Object>> rePMap = Lists.newArrayList();
		for (Bill bill : list)
		{
			Map<String,Object> map = BillUtil.getBillJson(bill);
			rePMap.add(map);
		}
		resultPage.setList(rePMap);
		return LcbJson.toJSONString(resultPage);
	}

	@ResponseBody
	@RequestMapping(value = "/billDetail")
	public String billDetial(ServletRequest request, Model model, @RequestParam(value = "id") Long id)
	{
		Map<String,Object> map = billService.getUserBillDetial(id);
		String result = LcbJson.toJSONString(map);
		model.addAttribute("billDetail", result);
		return "bill/billDetail";
	}

	/**
	 * 构建排序SQL
	 * 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
	 * @param sort
	 * @return
	 */
	public static String buildSort(String sort)
	{
		StringBuffer strf = new StringBuffer();
		for (String sortStr : sort.split("-"))
		{
			// 如果传入的字段不允许排序，则跳出
			if (!ALLOW_SORT_COLUMNS.contains(sortStr.replace("_", "")))
			{
				continue;
			}
			// 去除以下划线开头结尾的参数，只能以固定格式
			if (sortStr.startsWith("_") && sortStr.endsWith("_"))
			{
				continue;
			}
			if (sortStr.startsWith("_"))
			{// 以下划线开始为升序
				strf.append(sortStr.substring(1)).append(" ASC");
			} else if (sortStr.endsWith("_"))
			{// 以下划线结束为降序
				strf.append(sortStr.substring(0, sortStr.length() - 1)).append(" DESC");
			}
		}
		return strf.toString();
	}
}
