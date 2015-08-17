package com.lingcaibao.web.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lingcaibao.cache.memcached.MarketMemcachedClient;
import com.lingcaibao.entity.Bank;
import com.lingcaibao.entity.ResultPage;
import com.lingcaibao.entity.User;
import com.lingcaibao.entityenum.UserMsgTemp;
import com.lingcaibao.exception.LotteryException;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.service.BankService;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.WithdrawService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.statuscode.WithdrawStatusEnum;
import com.lingcaibao.util.LcbJson;
import com.palm.commom.plugin.paginator.mybatis.page.Common;
import com.palm.commom.uitl.Digests;
import com.palm.commom.uitl.Encodes;
import com.palm.commom.uitl.Servlets;
/**
 * <p>标题：用户提现接口 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月14日 下午6:46:39</p>
 * <p>类全名：com.lingcaibao.web.controller.WithdrawController</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Controller
@RequestMapping(value = "/home/withdraw")
public class WithdrawController
{
	private static Logger				logger				= LoggerFactory.getLogger(WithdrawController.class);
	@Autowired
	private WithdrawService				withdrawService;
	@Autowired
	private UserService					userService;
	@Autowired
	private BankService					bankService;
	@Autowired
	private MarketMemcachedClient memcachedClient;
	
	private static final String			PAGE_SIZE			= "10";
	// 允许排序的字段
	private static final List<String>	ALLOW_SORT_COLUMNS	= ImmutableList.of("id");

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "id_") String sort, ServletRequest request, Model model)
	{
		Map<String,Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		// 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
		String sortStr = buildSort(sort);
		if (sortStr.length() > 0)
		{
			searchParams.put("sortColumns", sortStr);
		}
		Page<Map<String,Object>> pageable = new Page<Map<String,Object>>(pageNumber, pageSize);
		Page<Map<String,Object>> page = withdrawService.searchPage(searchParams, pageable);
		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return "withdraw/withdrawList";
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

	/**
	 * 提现申请接口
	 * @param bankid
	 * @param amount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/do")
	public String applyWithDraw(
			@RequestParam("bankid") Long bankid, 
			@RequestParam("amount") BigDecimal amount, 
			@RequestParam("captcha") String captcha, 
			HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		User user = userService.get(shiroUser.id);

		Map<String, String> resultMap = Maps.newHashMap();
		// 校验合法用户
		if (user == null) {
			resultMap.put("result", "false");
			resultMap.put("message", "该用户不存在！");
			return JSON.toJSONString(resultMap);
		}
		// 校验验证码
		String msgKey = "CAPTCHA_" + UserMsgTemp.WITHDRAW_WLB.name() + "_" + user.getUsername();
		String captchaInfo = memcachedClient.get(msgKey);
		if (!StringUtils.equals(captchaInfo, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		
		Bank bank = bankService.get(bankid);
		if (bank == null)
		{
			resultMap.put("result", "false");
			resultMap.put("message", "无效的银行卡");
			return JSON.toJSONString(resultMap);
		}
		
		try
		{
			withdrawService.doWithDraw(user.getId(), bankid, amount, Common.getIpAddr(request));
			resultMap.put("result", "true");
			resultMap.put("message", "提现申请成功");
			return JSON.toJSONString(resultMap);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("------------ apply with draw err --------------:{}", ex);
			resultMap.put("result", "false");
			resultMap.put("message", ex instanceof LotteryException ? ex.getMessage() : "提现申请失败");
			return JSON.toJSONString(resultMap);
		}
	}

	/**
	 * 获取提现列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sort
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/withdrawList")
	public String withdrawList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "id_") String sort, ServletRequest request, Model model) throws Exception
	{
		//提现状态
		String status = request.getParameter("status");
		String sWithdrawTime = request.getParameter("sWithdrawTime");
		String eWithdrawTime = request.getParameter("eWithdrawTime");
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
		if (!StringUtils.isEmpty(sWithdrawTime))
		{
			searchParams.put("sWithdrawTime", sWithdrawTime);
		}
		if (!StringUtils.isEmpty(eWithdrawTime))
		{
			searchParams.put("eWithdrawTime", eWithdrawTime);
		}
		if (!StringUtils.isEmpty(status))
		{
			searchParams.put("status", status);
		}
		Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageNumber, pageSize);
		page = withdrawService.searchPage(searchParams, page);
		ResultPage resultPage = new ResultPage(page);
		List<Map<String,Object>> rePMap = Lists.newArrayList();
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String,Object>>) resultPage.getList();
		Map<String,Object> map = null;
		for (Map<String,Object> withdraw : list)
		{
			map = withdraw;
			map.put("status", WithdrawStatusEnum.getName((Integer) withdraw.get("status")));
			rePMap.add(map);
		}
		resultPage.setList(rePMap);
		return LcbJson.toJSONString(resultPage);
	}

	/**
	 * 获取详细提现数据
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/withdrawDetail")
	public String withdrawDetail(@PathVariable("id") Long id, Model model)
	{
		model.addAttribute("withdraw", withdrawService.get(id));
		return "withdraw/withdrawdetail";
	}
}
