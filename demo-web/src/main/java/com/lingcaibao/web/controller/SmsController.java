package com.lingcaibao.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
//import org.springside.modules.web.Servlets;
import com.palm.commom.uitl.Servlets;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.lingcaibao.entity.Sms;
import com.lingcaibao.entity.User;
import com.lingcaibao.service.SmsService;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.service.sms.SmsSendService;

/**
 * @Title:
 * @Description:
 * @Author jhe
 * @Date 2013-12-8
 * @Version V1.0
 * @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
 */
@Controller
@RequestMapping(value = "/home/sms")
public class SmsController {
	private static Logger logger = LoggerFactory
			.getLogger(SmsController.class);
	@Autowired
	private SmsService smsService;

	@Autowired
	private UserService					userService;
	
	private static final String PAGE_SIZE = "10";

	// 允许排序的字段
	private static final List<String> ALLOW_SORT_COLUMNS = ImmutableList.of("id");

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "id_") String sort,
			ServletRequest request, Model model) {
		Sort _sort = new Sort(Direction.DESC, sort);
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "s_");
		
		// 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
		String sortStr = buildSort(sort);
		if (sortStr.length() > 0) {
			searchParams.put("sortColumns", sortStr);
		}

		Page pageable = new Page(pageNumber, pageSize);
		Page<Sms> page = smsService.searchPage(searchParams, pageable);

		model.addAttribute("page", page);
		model.addAttribute("sort", sort);

		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams",
				Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));

		return "sms/smsList";
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public List<Sms> listByJson(PageRequest pageRequest,
			ServletRequest request, Model model) throws ParseException {
		logger.info("begin-----------------------list{countinue........}---------");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "s_");
		List<Sms> list = smsService.search(searchParams);
		// 将搜索条件编码成字符串
		model.addAttribute("searchParams",
				Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return list;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("sms", new Sms());
		model.addAttribute("action", "create");
		return "sms/smsForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Sms newSms,
			RedirectAttributes redirectAttributes) {
		smsService.insert(newSms);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/admin/sms";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sms", smsService.get(id));
		model.addAttribute("action", "update");
		return "sms/smsForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("sms") Sms sms,
			RedirectAttributes redirectAttributes) {
		smsService.update(sms);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/admin/sms";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		smsService.delete(id);
		redirectAttributes.addFlashAttribute("message","删除成功");
		return "redirect:/admin/sms";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getSms(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("sms", smsService.get(id));
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
				strf.append(sortStr.substring(0, sortStr.length() - 1)).append(
						" DESC");
			}
		}
		return strf.toString();
	}
	/**用户管理 发送信息
	 * @return 返回处理结果信息 
	 */
	@ResponseBody
	@RequestMapping(value = "/sendMobileMessage")
	public JSONObject sendMessage(ServletRequest request,Model  model){
		String  returnMessageString="success";
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		String userIds=request.getParameter("userIds");
		String []userIdList=userIds.split(",");
		List<String> mobileArray=new ArrayList<String>();//存放手机号码
		String mobileContent="";//短信内容
		for(String userId:userIdList){
			User user=userService.get(Long.valueOf(userId));//根据id获取用户
			mobileArray.add(user.getUsername());
			//发送短息记录
			Sms sms=new Sms();
			sms.setMobile(user.getUsername());
			sms.setContent(mobileContent);//短信内容
			sms.setOperator(shiroUser.id.toString());//发送用户
			sms.setUserid(Long.valueOf(userId));//接收用户
			sms.setCreatetime(new Date());//创建时间
			sms.setNumbers(1);
			smsService.insert(sms);
		}
		try{
			SmsSendService sendService = new SmsSendService(null);
			sendService.sendBatch(mobileArray, mobileContent);
		}catch(Exception ex){
			returnMessageString="failure";
			ex.printStackTrace();
		}
		JSONObject object=new JSONObject();
		object.put("result", returnMessageString);
		return object;
	}
}
