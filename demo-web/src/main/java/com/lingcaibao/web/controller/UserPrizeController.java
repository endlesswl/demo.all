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

import com.lingcaibao.plugin.page.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.palm.commom.uitl.DateEditor;
import com.palm.commom.uitl.DateFormatUtil;
//import org.springside.modules.web.Servlets;
import com.palm.commom.uitl.Servlets;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lingcaibao.entity.Bill;
import com.lingcaibao.entity.ResultPage;
import com.lingcaibao.entity.UserPrize;
import com.lingcaibao.service.UserPrizeService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.statuscode.LotteryType;
import com.lingcaibao.util.LcbJson;
import com.lingcaibao.util.MathUtil;
import com.lingcaibao.util.WebUtils;
import com.lingcaibao.web.entityutil.BillUtil;
/**
 * <p>标题：用户中奖信息业务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月25日 下午6:49:13</p>
 * <p>类全名：com.lingcaibao.web.controller.UserSubscribeController</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Controller
@RequestMapping(value = "/home/userPrize")
public class UserPrizeController
{
	private static Logger				logger				= LoggerFactory.getLogger(UserPrizeController.class);
	@Autowired
	private UserPrizeService			userPrizeService;
	private static final String			PAGE_NUM			= "1";
	private static final String			PAGE_SIZE			= "10";
	// 允许排序的字段
	private static final List<String>	ALLOW_SORT_COLUMNS	= ImmutableList.of("id");

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = PAGE_NUM) int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "id_") String sort, ServletRequest request, Model model)
	{
		Sort _sort = new Sort(Direction.DESC, sort);
		Map<String,Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		// 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
		String sortStr = buildSort(sort);
		if (sortStr.length() > 0)
		{
			searchParams.put("sortColumns", sortStr);
		}
		Page pageable = new Page(pageNumber, pageSize);
		Page<UserPrize> page = userPrizeService.searchPage(searchParams, pageable);
		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return "userPrize/userPrizeList";
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public List<UserPrize> listByJson(PageRequest pageRequest, ServletRequest request, Model model) throws ParseException
	{
		Map<String,Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		List<UserPrize> list = userPrizeService.search(searchParams);
		// 将搜索条件编码成字符串
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return list;
	}

	/**
	 * 获取中奖用户信息
	 * @param page
	 * @param rows
	 * @param issueNo
	 * @param startTime
	 * @param endTime
	 * @param gameid
	 * @param isfixed
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserPrize")
	public String getUserPrize(@RequestParam(value = "page", defaultValue = PAGE_NUM) int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "issueNo", required = false) String issueNo, @RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "gameid", required = false) String gameid,
			@RequestParam(value = "isfixed", required = false) String isfixed)
	{
		logger.info("begin------------------------------------------------getUserPrize()");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Page<UserPrize> pageable = new Page<UserPrize>(pageNumber, pageSize);
		Map<String,Object> searchParams = new HashMap<String,Object>();
		if (!StringUtils.isEmpty(shiroUser.id))
		{
			searchParams.put("userid",shiroUser.id);//1083759
		}
		if (!StringUtils.isEmpty(issueNo))
		{
			searchParams.put("issueNo", issueNo);
		}
		if (!StringUtils.isEmpty(gameid))
		{
			searchParams.put("gameid", gameid);
		}
		if (!StringUtils.isEmpty(isfixed))
		{
			searchParams.put("isfixed", 0);
		}
		if (!StringUtils.isEmpty(startTime))
		{
			searchParams.put("startTime", startTime+"  00:00:00");
			
		}
		if (!StringUtils.isEmpty(endTime))
		{
			searchParams.put("endTime", endTime+"  23:59:59");
			
		}
		Page<UserPrize> page = new Page<UserPrize>(pageNumber, pageSize);
		page = userPrizeService.searchPage(searchParams, pageable);
		ResultPage resultPage = new ResultPage(page);
		@SuppressWarnings("unchecked")
		List<UserPrize> list = (List<UserPrize>) resultPage.getList();
		List<Map<String,Object>> rePMap = Lists.newArrayList();
		for (UserPrize userPrize : list)
		{
			Map<String,Object> map = BillUtil.getUserPrizeJson(userPrize);
			rePMap.add(map);
		}
		resultPage.setList(rePMap);
		return LcbJson.toJSONString(resultPage);
		
    }
	/**
	 * 根据ID活动中奖用户详情
	 */
	@ResponseBody
	@RequestMapping("/getUserPrizeById")
	public String getUserPrizeById(@RequestParam(value="userid")Long userid ,Model model)
	{
		Map<String,Object> map = userPrizeService.getUserPrizeById(userid);
		String result =JSON.toJSONString(map);		
		return result;
	}
	
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model)
	{
		model.addAttribute("userPrize", new UserPrize());
		model.addAttribute("action", "create");
		return "userPrize/userPrizeForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid UserPrize newUserPrize, RedirectAttributes redirectAttributes)
	{
		userPrizeService.insert(newUserPrize);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/admin/userPrize";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model)
	{
		model.addAttribute("userPrize", userPrizeService.get(id));
		model.addAttribute("action", "update");
		return "userPrize/userPrizeForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("userPrize") UserPrize userPrize, RedirectAttributes redirectAttributes)
	{
		userPrizeService.update(userPrize);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/admin/userPrize";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes)
	{
		userPrizeService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/userPrize";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUserPrize(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model)
	{
		if (id != -1)
		{
			model.addAttribute("userPrize", userPrizeService.get(id));
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
	
	/**我的账户 按年 按月统计用户领彩数据
	 * @param pageNumber
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/userPrizeCount")
	public String getUserLotteryByMonth(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,ServletRequest request){
		String selectType=request.getParameter("selectType");//查看方式 按月、按年		
		Page pageable = new Page(pageNumber, pageSize);
		Page<Map<String, Object>> page=new Page<Map<String,Object>>();		
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();	
		//默认是月收益
		if(StringUtils.isEmpty(selectType)){
			selectType="month";
		}
		String startTime="";
		String endTime="";
		if("month".equals(selectType)){
			int month=DateFormatUtil.getMonth(new Date());//当前月份
			if(pageNumber==1){//当前时间
				startTime=DateFormatUtil.getYearMonth(new Date());
			}else{
				startTime=WebUtils.getTimeByMonth((pageNumber-1)*6);
			}
			endTime=WebUtils.getTimeByMonth(pageNumber*6);
		}else if("year".equals(selectType)){
			int year=Integer.parseInt(DateFormatUtil.getYear(new Date()));//当前年份
			if(pageNumber==1){
				startTime=DateFormatUtil.getYear(new Date());
			}else{
				startTime=WebUtils.getTimeByYear((pageNumber-1)*6);
			}
			endTime=WebUtils.getTimeByYear(pageNumber*6);
		}
		Map<String, Object> searchParmsMap=Maps.newHashMap();
		searchParmsMap.put("startTime", startTime);
		searchParmsMap.put("endTime", endTime);
		searchParmsMap.put("id", shiroUser.id);
		if("month".equals(selectType)){			
			page=userPrizeService.getUserPrizeByMonth(pageable,searchParmsMap);			
		}else if("year".equals(selectType)){
			page=userPrizeService.getUserPrizeByYear(pageable,searchParmsMap);
			
		}
		ResultPage resultPage = new ResultPage(page);
		////处理时间 月份的话处理下时间
		if("month".equals(selectType)){
			for(Map<String, Object> map:(List<Map<String, Object>>)resultPage.getList()){
//				if(map.get("times").toString().substring(5,7).startsWith("0")){					
//					map.put("times", map.get("times").toString().substring(6,7)+"月");
//				}else{
//					map.put("times", map.get("times").toString().substring(5,7)+"月");
//				}
				map.put("times", map.get("times").toString()+"月");
				map.put("money", MathUtil.getNumTwoDigitsAfter(map.get("money")));
			}
		}else {			
			for(Map<String, Object> map:(List<Map<String, Object>>)resultPage.getList()){
				map.put("times", map.get("times").toString()+"年");
				map.put("money", MathUtil.getNumTwoDigitsAfter(map.get("money")));
			}
		}
		
		return LcbJson.toJSONString(resultPage);
	}
}
