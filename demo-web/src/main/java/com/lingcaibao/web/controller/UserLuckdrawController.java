package com.lingcaibao.web.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.lingcaibao.entity.ResultPage;
import com.lingcaibao.entity.UserLuckdraw;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.service.DictionaryService;
import com.lingcaibao.service.UserLotteryService;
import com.lingcaibao.service.UserLuckdrawService;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.statuscode.RegexType;
import com.lingcaibao.util.CommonStatus;
import com.lingcaibao.util.LcbJson;
import com.lingcaibao.util.SqlUtils;
import com.lingcaibao.web.interceptor.Verify;
import com.lingcaibao.web.interceptor.VerifyKey;
import com.palm.commom.uitl.DateEditor;
import com.palm.commom.uitl.DateFormatUtil;
import com.palm.commom.uitl.Servlets;

/**
 * @Title:
 * @Description:
 * @Author jhe
 * @Date 2013-12-8
 * @Version V1.0
 * @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
 */
@Controller
@RequestMapping(value = "/home/userLuckdraw")
public class UserLuckdrawController {
	private static Logger logger = LoggerFactory.getLogger(UserLuckdrawController.class);
	@Autowired
	private UserLuckdrawService userLuckdrawService;

	@Autowired
	private UserService userService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private UserLotteryService userLotteryService;

	private static final String PAGE_SIZE = "10";
	// 允许排序的字段
	private static final List<String> ALLOW_SORT_COLUMNS = ImmutableList.of("id");

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize, @RequestParam(value = "sort", defaultValue = "id_") String sort, ServletRequest request, Model model) {
		// Sort _sort = new Sort(Direction.DESC, sort);
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		// 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
		String sortStr = buildSort(sort);
		if (sortStr.length() > 0) {
			searchParams.put("sortColumns", sortStr);
		}
		Page<UserLuckdraw> pageable = new Page<UserLuckdraw>(pageNumber, pageSize);
		Page<UserLuckdraw> page = userLuckdrawService.searchPage(searchParams, pageable);
		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return "userLuckdraw/userLuckdrawList";
	}

	/**
	 * 获取登陆用户最新福利列表(不分页)
	 * 
	 * @param top
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/newest")
	public String getUserInfo(@RequestParam("top") int top) {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		List<UserLuckdraw> draws = userLuckdrawService.getNewestDraw(shiroUser.id, null, top);
		Map<String, List<UserLuckdraw>> resultMap = Maps.newHashMap();
		resultMap.put("draws", draws);
		return JSON.toJSONString(resultMap);
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public List<UserLuckdraw> listByJson(PageRequest pageRequest, ServletRequest request, Model model) throws ParseException {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		List<UserLuckdraw> list = userLuckdrawService.search(searchParams);
		// 将搜索条件编码成字符串
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return list;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("userLuckdraw", new UserLuckdraw());
		model.addAttribute("action", "create");
		return "userLuckdraw/userLuckdrawForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid UserLuckdraw newUserLuckdraw, RedirectAttributes redirectAttributes) {
		userLuckdrawService.insert(newUserLuckdraw);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/admin/userLuckdraw";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userLuckdraw", userLuckdrawService.get(id));
		model.addAttribute("action", "update");
		return "userLuckdraw/userLuckdrawForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("userLuckdraw") UserLuckdraw userLuckdraw, RedirectAttributes redirectAttributes) {
		userLuckdrawService.update(userLuckdraw);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/admin/userLuckdraw";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		userLuckdrawService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/userLuckdraw";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUserLuckdraw(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("userLuckdraw", userLuckdrawService.get(id));
		}
	}

	/**
	 * 时间格式字段预处理
	 * 
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
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
	public static String buildSort(String sort) {
		StringBuffer strf = new StringBuffer();
		for (String sortStr : sort.split("-")) {
			// 如果传入的字段不允许排序，则跳出
			if (!ALLOW_SORT_COLUMNS.contains(sortStr.replace("_", ""))) {
				continue;
			}
			// 去除以下划线开头结尾的参数，只能以固定格式
			if (sortStr.startsWith("_") && sortStr.endsWith("_")) {
				continue;
			}
			if (sortStr.startsWith("_")) {// 以下划线开始为升序
				strf.append(sortStr.substring(1)).append(" ASC");
			} else if (sortStr.endsWith("_")) {// 以下划线结束为降序
				strf.append(sortStr.substring(0, sortStr.length() - 1)).append(" DESC");
			}
		}
		return strf.toString();
	}


	/**
	 * 获取活动总人数
	 * 
	 * @param marketplanId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInteractionCount")
	public String getInteractionCount(@RequestParam("marketplanId") String marketplanId, ServletRequest request) {
		int count = userLuckdrawService.getInteractionPersonCount(Long.valueOf(marketplanId), null, null);

		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("result", "true");
		resultMap.put("count", count);

		return LcbJson.toJSONString(resultMap);
	}

	/**
	 * 获取活动当天的总人数
	 * 
	 * @param marketplanId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInteractionDayCount")
	public String getInteractionDayCount(@RequestParam("marketplanId") String marketplanId, ServletRequest request) {
		int count = userLuckdrawService.getInteractionPersonCount(Long.valueOf(marketplanId), DateFormatUtil.getCurrentDayStartTime(), DateFormatUtil.getCurrentDayEndTime());
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("result", "true");
		resultMap.put("count", count);
		return LcbJson.toJSONString(resultMap);
	}



	/**
	 * 用户系统分布 报表
	 * 
	 * @param request
	 * @return
	 */
	@VerifyKey({ @Verify(key = "marketId", regexType = RegexType.NUMBER, desc = "活动ID") })
	@ResponseBody
	@RequestMapping("/getSysDReport")
	public String getSystemDistributionReport(ServletRequest request) {
		Map<String, Object> resultMap = Maps.newHashMap();
		Long marketId = Long.valueOf(request.getParameter("marketId"));
		resultMap = userLuckdrawService.getSystemDistributionReport(marketId);
		return LcbJson.toJSONString(resultMap);
	}


	/**
	 * 用户福利列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchMarketPrizePage")
	public String searchMarketPrizePage(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, 
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize, 
			ServletRequest request,
			@RequestParam(value = "sort", defaultValue = "createtime_") String sort) {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String marketName = request.getParameter("marketName");
		String sortStr = SqlUtils.buildSort(sort);
		searchParams.put("startTime", startTime);
		searchParams.put("endTime", endTime);
		searchParams.put("userId", shiroUser.id);
		searchParams.put("marketName", marketName);
		searchParams.put("sortColumns", sortStr);
		searchParams.put("isprize", request.getParameter("isprize"));
		
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNumber, pageSize);
		logger.info("----------- searchParams ---------:{}", searchParams);
		page = userLotteryService.getH5MyLottery(searchParams, page);
		ResultPage resultPage = new ResultPage(page);
		return LcbJson.toJSONString(resultPage);
	}

	
	@RequestMapping(value = "/toSearchMarketPrizeDetail")
	public String toSearchMarketPrizeDetail(@RequestParam("userLotteryId") Long userLotteryId, ServletRequest request, Model model) {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Long userId = shiroUser.id;
		model.addAttribute("user", userService.get(userId));
		model.addAttribute("userLotteryId", userLotteryId);
		return "person/myAccount/userLuckdrawDetail";
	}
	
	
	/**
	 * 用户福利详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchMarketPrizeDetail")
	public String searchMarketPrizeDetail( Model model, @RequestParam("userLotteryId") Long userLotteryId) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("userLotteryId", userLotteryId);
		// 彩票
		Map<String, Object> map = userLotteryService.getH5MyLotteryDetail(searchParams);
		if (map != null) {
			//map.put("image", PrizeType.getImage(code));
			if (CommonStatus.LOTTERY_ISPRIZE_WAITPRIZE == Integer.parseInt(map.get("isprize").toString())) {
				map.put("isprize", "等待开奖");
			} else if (CommonStatus.LOTTERY_ISPRIZE_PRIZED == Integer.parseInt(map.get("isprize").toString())) {
				map.put("isprize", "已中奖");
				Map<String, Object> userprize = userLotteryService.getUserPrizeByUserLotteryId(userLotteryId);
				map.put("prizeMoney", userprize.get("prizeMoney"));
				map.put("prizeMoneyAfterTax", userprize.get("prizeMoneyAfterTax"));
			} else if (CommonStatus.LOTTERY_ISPRIZE_UNPRIZED == Integer.parseInt(map.get("isprize").toString())) {
				map.put("isprize", "未中奖");
				String prizeBall = userLotteryService.getPrizeBallByIssueNo(map.get("issueNo")+"");
				map.put("prizeball", prizeBall);
			} else {
				map.put("isprize", "等待开奖");
			}
		} 
		return LcbJson.toJSONString(map);
	}
}