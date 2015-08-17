package com.lingcaibao.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.lingcaibao.cache.memcached.MarketMemcachedClient;
import com.lingcaibao.entity.Bank;
import com.lingcaibao.entity.Dictionary;
import com.lingcaibao.entity.User;
import com.lingcaibao.entityenum.BankEnum;
import com.lingcaibao.entityenum.BankStatusEnum;
import com.lingcaibao.entityenum.UserMsgTemp;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.service.BankService;
import com.lingcaibao.service.DictionaryService;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.util.AccountContent;
import com.lingcaibao.util.DictionaryContent;
import com.lingcaibao.util.LcbJson;
import com.palm.commom.uitl.DateEditor;
import com.palm.commom.uitl.Servlets;
/**
 * <p>标题：银行接口 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月8日 上午10:31:59</p>
 * <p>类全名：com.lingcaibao.web.controller.BankController</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Controller
@RequestMapping(value = "/home/bank")
public class BankController
{
	private static Logger				logger				= LoggerFactory.getLogger(BankController.class);
	@Autowired
	private BankService					bankService;
	@Autowired
	private UserService					userService;
	@Autowired
	private MarketMemcachedClient		memcachedClient;
	@Autowired
	private DictionaryService			dictionaryService;
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
		Page<Bank> pageable = new Page<Bank>(pageNumber, pageSize);
		Page<Bank> page = bankService.searchPage(searchParams, pageable);
		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return "bank/bankList";
	}

	/*
	 * 获取用户银行信息列表
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public String listByJson(ServletRequest request)
	{
		logger.info("start.................................listByJson()");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		List<Map<String,Object>> list = bankService.getUserBankList(shiroUser.id);//997790L
		if (list != null)
		{
			for (Map<String,Object> map : list)
			{
				map.put("safeCardno", BankService.getSafeCardNo((String) map.get("cardno")));
				map.put("cardTypeName", BankEnum.getName((Integer) map.get("cardtype")));
				//map.remove("cardno");
			}
		}
		Map<String,List<Map<String,Object>>> resultMap = Maps.newHashMap();
		resultMap.put("banks", list);
		return LcbJson.toJSONString(resultMap);
	}

	
	// 创建银行卡(个人用户)
	@RequestMapping("/create")
	@RequiresRoles("user")
	@ResponseBody
	public String creatBankCard(Bank newBank, @RequestParam(value = "captcha") String captcha, HttpServletRequest request)
	{
		logger.info("start.................................creatBankCard()");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Map<String,Object> resultMap = Maps.newHashMap();
		User user = userService.get(shiroUser.id);
		
		String msgKey = "CAPTCHA_" + UserMsgTemp.CREATE_BANK_CARD.name() + "_" + user.getMobile();
		String captchaInfo = memcachedClient.get(msgKey);
		if (!StringUtils.equals(captchaInfo, captcha))
		{
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		try
		{
			// 处理银行卡绑定标识
			user.setBindflags(AccountContent.BIND_FLAGS_BANK);
			userService.addBindflags(user);
			// 增加银行卡
			newBank.setUserid(user.getId());
			newBank.setCardtype(BankEnum.BANK_CARDTYPEJ.getType());
			newBank.setDeleteFlag(BankStatusEnum.BANK_DELETEFLAGZ.ordinal());
			bankService.insert(newBank);
			resultMap.put("result", "true");
			resultMap.put("message", "添加成功！");
			return JSON.toJSONString(resultMap);
		} catch (Exception e)
		{
			e.printStackTrace();
			resultMap.put("result", "false");
			resultMap.put("message", "添加失败！");
			return JSON.toJSONString(resultMap);
		}
	}

	/**
	 * 更新银行卡信息(个人用户)
	 * @param newBank
	 * @param captcha
	 * @param endfourCardNo
	 * @return
	 */
	@RequestMapping("/update")
	@RequiresRoles("user")
	@ResponseBody
	public String updateBankCard(Bank bank, @RequestParam("captcha") String captcha)
	{
		logger.info("start.................................updateBankCard()");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Map<String,Object> resultMap = Maps.newHashMap();
		User user = userService.get(shiroUser.id);
		String msgKey = "CAPTCHA_" + UserMsgTemp.UPDATE_BANK_CARD.name() + "_" + user.getMobile();
		String captchaInfo = memcachedClient.get(msgKey);
		// 校验验证码
		if (!captchaInfo.equals(captcha))
		{
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		// 更新银行卡信息
		try
		{
			bankService.update(bank);
			resultMap.put("result", "true");
			resultMap.put("message", "银行卡更新成功!");
			return JSON.toJSONString(resultMap);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			resultMap.put("result", "false");
			resultMap.put("message", "银行卡更新失败！");
			return JSON.toJSONString(resultMap);
		}
	}

	// 绑定银行卡(商家用户/代理商用户)
	@RequestMapping("/bindBankCard")
	@RequiresRoles(value = { "business", "proxy" })
	@ResponseBody
	public String bindBankCard(Bank newBank, @RequestParam(value = "captcha") String captcha, HttpServletRequest request)
	{
		logger.info("start.................................bindBankCard()");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Map<String,Object> resultMap = Maps.newHashMap();
		User user = userService.get(shiroUser.id);
		String msgKey = "CAPTCHA_" + UserMsgTemp.BIND_BANK_CARD.name() + "_" + user.getEmail();
		String captchaInfo = memcachedClient.get(msgKey);
		if (!StringUtils.equals(captchaInfo, captcha))
		{
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		// 处理银行卡绑定标识
		if (!user.isBindBankCard())
		{
			user.setBindflags(AccountContent.BIND_FLAGS_BANK);
			userService.addBindflags(user);// 绑定银行卡
		}
		// 绑定银行卡
		try
		{
			// 废弃原绑定银行卡
			Map<String,Object> params = Maps.newHashMap();
			params.put("userid", user.getId());
			bankService.discardBank(params);
			// 增加新银行卡
			newBank.setUserid(user.getId());
			newBank.setCardtype(BankEnum.BANK_CARDTYPEJ.getType());
			newBank.setDeleteFlag(BankStatusEnum.BANK_DELETEFLAGZ.ordinal());
			bankService.insert(newBank);
			resultMap.put("result", "true");
			resultMap.put("message", "银行卡绑定成功!");
			return JSON.toJSONString(resultMap);
		} catch (Exception e)
		{
			e.printStackTrace();
			resultMap.put("result", "false");
			resultMap.put("message", "银行卡绑定失败！");
			return JSON.toJSONString(resultMap);
		}
	}

	// 修改绑定银行卡(商家用户/代理商用户)
	@RequestMapping("/updateBindBankCard")
	@RequiresRoles(value = { "business", "proxy" })
	@ResponseBody
	public String updateBindBankCard(Bank newBank, @RequestParam("lastAccountName") String lastAccountName, @RequestParam("lastMidCardNo") String lastMidCardNo,
			@RequestParam(value = "captcha") String captcha, HttpServletRequest request)
	{
		logger.info("start.................................updateBindBankCard()");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Map<String,Object> resultMap = Maps.newHashMap();
		User user = userService.get(shiroUser.id);
		String msgKey = "CAPTCHA_" + UserMsgTemp.BIND_BANK_CARD.name() + "_" + user.getEmail();
		String captchaInfo = memcachedClient.get(msgKey);
		if (!StringUtils.equals(captchaInfo, captcha))
		{
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		// 校验原绑定卡信息
		Map<String,Object> params = Maps.newHashMap();
		params.put("userid", user.getId());
		params.put("deleteFlag", BankStatusEnum.BANK_DELETEFLAGZ.ordinal());
		List<Bank> list = bankService.search(params);
		Bank lastBank = (list == null ? null : list.get(0));
		if (lastBank == null)
		{
			resultMap.put("result", "false");
			resultMap.put("message", "原卡信息错误！");
			return JSON.toJSONString(resultMap);
		}
		if (!StringUtils.equals(lastBank.getAccountname(), lastAccountName))
		{
			resultMap.put("result", "false");
			resultMap.put("message", "原银行卡户名错误！");
			return JSON.toJSONString(resultMap);
		}
		if (!StringUtils.equals(lastBank.getCardno().substring(4, 12), lastMidCardNo))
		{
			resultMap.put("result", "false");
			resultMap.put("message", "原银行卡中间8位卡号错误！");
			return JSON.toJSONString(resultMap);
		}
		// 绑定银行卡
		try
		{
			// 废弃原绑定银行卡
			params.clear();
			params.put("userid", user.getId());
			bankService.discardBank(params);
			// 增加新银行卡
			newBank.setUserid(user.getId());
			newBank.setCardtype(BankEnum.BANK_CARDTYPEJ.getType());
			newBank.setDeleteFlag(BankStatusEnum.BANK_DELETEFLAGZ.ordinal());
			bankService.insert(newBank);
			resultMap.put("result", "true");
			resultMap.put("message", "银行卡绑定成功!");
			return JSON.toJSONString(resultMap);
		} catch (Exception e)
		{
			e.printStackTrace();
			resultMap.put("result", "false");
			resultMap.put("message", "银行卡绑定失败！");
			return JSON.toJSONString(resultMap);
		}
	}

	/**
	 * 获取详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/bankInfo")
	@RequiresRoles("user")
	@ResponseBody
	public String getBankInfo(@RequestParam("id") Long id)
	{
		logger.info("start.................................getBankInfo()");
		Map<String,Object> resultMap = Maps.newHashMap();
		Bank bank = bankService.get(id);
		if (bank != null)
		{
			resultMap.putAll(LcbJson.toMap(bank));
			resultMap.remove("cardno");
			resultMap.put("safeCardno", BankService.getSafeCardNo(bank.getCardno()));
			resultMap.put("cardTypeName", BankEnum.getName(bank.getCardtype()));
			if (StringUtils.isNotEmpty(bank.getAbbreviation()))
			{
				Dictionary dict = dictionaryService.findDictByCode(DictionaryContent.DICT_NO_BANK, bank.getAbbreviation());
				resultMap.put("imageUrl", dict.getRemark());
			}
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 获取绑定银行卡详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/bindBankInfo")
	@RequiresRoles(value = { "business", "proxy" })
	@ResponseBody
	public String getBindBankInfo()
	{
		logger.info("start.................................getBankById()");
		Map<String,Object> resultMap = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Map<String,Object> params = Maps.newHashMap();
		params.put("userid", shiroUser.id);
		params.put("deleteFlag", BankStatusEnum.BANK_DELETEFLAGZ.ordinal());
		List<Bank> list = bankService.search(params);
		Bank bank = ((list == null || list.size() <= 0) ? null : list.get(0));
		if (bank != null)
		{
			resultMap.putAll(LcbJson.toMap(bank));
			resultMap.remove("cardno");
			resultMap.put("safeCardno", BankService.getSafeCardNo(bank.getCardno()));
			resultMap.put("cardTypeName", BankEnum.getName(bank.getCardtype()));
			if (StringUtils.isNotEmpty(bank.getAbbreviation()))
			{
				Dictionary dict = dictionaryService.findDictByCode(DictionaryContent.DICT_NO_BANK, bank.getAbbreviation());
				resultMap.put("imageUrl", dict.getRemark());
			}
		}
		return JSON.toJSONString(resultMap);
	}

	@ResponseBody
	@RequestMapping(value = "/bankAdd")
	public String bankAdd(@RequestParam("cardno") String cardno,// 卡号
			@RequestParam("bankname") String bankname,// 开户行全称
			@RequestParam("abbreviation") String abbreviation)// 开户行简写
	{
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		User user = userService.get(shiroUser.id);
		Bank newBank = new Bank();
		newBank.setAccountname(user.getRealName());
		newBank.setBankname(bankname);
		newBank.setCardno(cardno);
		newBank.setAbbreviation(abbreviation);
		newBank.setUserid(user.getId());
		newBank.setCardtype(BankEnum.BANK_CARDTYPEJ.getType());
		newBank.setDeleteFlag(BankStatusEnum.BANK_DELETEFLAGZ.ordinal());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			bankService.insert(newBank);
			resultMap.put("result", "true");
			resultMap.put("message", "添加成功！");
		} catch (Exception e1) {
			resultMap.put("result", "false");
			resultMap.put("message", "添加失败！");
			e1.printStackTrace();
		}
		return LcbJson.toJSONString(resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/bankUpdate")
	public String bankUpdate(
			@RequestParam("id") Long id,
			@RequestParam("cardno") String cardno,// 卡号
			@RequestParam("bankname") String bankname,// 开户行全称
			@RequestParam("abbreviation") String abbreviation)//开户行简写
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Bank bank = bankService.get(id);
		if(bank == null){
			resultMap.put("result", "true");
			resultMap.put("message", "修改成功！");
			return LcbJson.toJSONString(resultMap);
		}
		bank.setCardno(cardno);
		bank.setBankname(bankname);
		bank.setAbbreviation(abbreviation);
		
		
		try {
			bankService.update(bank);
			resultMap.put("result", "true");
			resultMap.put("message", "修改成功！");
		} catch (Exception e1) {
			resultMap.put("result", "false");
			resultMap.put("message", "修改失败！");
			e1.printStackTrace();
		}
		return LcbJson.toJSONString(resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam("id") Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Bank bank = bankService.get(id);
		if(bank == null){
			resultMap.put("result", "true");
			resultMap.put("message", "修改成功！");
			return LcbJson.toJSONString(resultMap);
		}
		try {
			bank.setDeleteFlag(BankStatusEnum.BANK_DELETESTATUS.ordinal());
			bankService.update(bank);
			resultMap.put("result", "true");
			resultMap.put("message", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "false");
			resultMap.put("message", "删除失败！");
		}
		return LcbJson.toJSONString(resultMap);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getBank(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model)
	{
		if (id != -1)
		{
			model.addAttribute("bank", bankService.get(id));
		}
	}

	/**
	 * 时间格式字段预处理
	 * 
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception
	{
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	/**
	 * 构建排序SQL
	 * 
	 * 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
	 * 
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
