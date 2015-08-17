package com.lingcaibao.web.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lingcaibao.service.UserService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.util.CommonStatus;
/**
 * @Title:
 * @Description:
 * @Author xin.pang
 */
@Controller
@RequestMapping()
public class LocationHrefController
{
	private static Logger	logger	= LoggerFactory.getLogger(LocationHrefController.class);
	@Autowired
	private UserService		userService;

	/**
	 * 跳转页面,需登录(商家用户)
	 * @param request
	 * @param path
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/location/business", method = RequestMethod.GET)
	public String busiPath(ServletRequest request, @RequestParam(value = "path", required = true) String path, Model model,HttpSession session)
	{
		logger.info("location");
		logger.info(path);
		try
		{
			Subject subject = SecurityUtils.getSubject();
			ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
			Long userId = shiroUser.id;
			model.addAttribute("user", userService.get(userId));
			Map<String,String[]> map = request.getParameterMap();
			Set<Entry<String,String[]>> set = map.entrySet();
			Iterator<Entry<String,String[]>> it = set.iterator();
			while (it.hasNext())
			{
				Entry<String,String[]> entry = it.next();
				logger.info("======  key={}, value={}  ======", entry.getKey(), entry.getValue()[0]);
				model.addAttribute(entry.getKey(), entry.getValue()[0]);
			}
			
			//从session中获取蓝标信息，确认蓝标入口
			String bluempStatus = (String) session.getAttribute(CommonStatus.SESSION_BLUEMP_USER_KEY);
			if(StringUtils.isNotBlank(bluempStatus)){
				model.addAttribute("bluempStatus", session.getAttribute(CommonStatus.SESSION_BLUEMP_USER_KEY));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * 跳转页面,需登录(个人用户)
	 * @param request
	 * @param path
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/location/user", method = RequestMethod.GET)
	public String userPath(ServletRequest request, @RequestParam(value = "path", required = true) String path, Model model)
	{
		logger.info("location");
		logger.info(path);
		try
		{
			Subject subject = SecurityUtils.getSubject();
			ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
			Long userId = shiroUser.id;
			model.addAttribute("user", userService.get(userId));
			Map<String,String[]> map = request.getParameterMap();
			Set<Entry<String,String[]>> set = map.entrySet();
			Iterator<Entry<String,String[]>> it = set.iterator();
			while (it.hasNext())
			{
				Entry<String,String[]> entry = it.next();
				logger.info("======  key={}, value={}  ======", entry.getKey(), entry.getValue()[0]);
				model.addAttribute(entry.getKey(), entry.getValue()[0]);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * 跳转页面,需登录(代理商用户)
	 * @param request
	 * @param path
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/location/proxy", method = RequestMethod.GET)
	public String proxyPath(ServletRequest request, @RequestParam(value = "path", required = true) String path, Model model)
	{
		logger.info("location");
		logger.info(path);
		try
		{
			Subject subject = SecurityUtils.getSubject();
			ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
			Long userId = shiroUser.id;
			model.addAttribute("user", userService.get(userId));
			Map<String,String[]> map = request.getParameterMap();
			Set<Entry<String,String[]>> set = map.entrySet();
			Iterator<Entry<String,String[]>> it = set.iterator();
			while (it.hasNext())
			{
				Entry<String,String[]> entry = it.next();
				logger.info("======  key={}, value={}  ======", entry.getKey(), entry.getValue()[0]);
				model.addAttribute(entry.getKey(), entry.getValue()[0]);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 静态跳转,无需登录
	 * @param request
	 * @param path
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/static", method = RequestMethod.GET)
	public String staticJump(ServletRequest request, @RequestParam(value = "path", required = true) String path, Model model)
	{
		logger.info("static jump");
		logger.info(path);
		try
		{
			Map<String,String[]> map = request.getParameterMap();
			Set<Entry<String,String[]>> set = map.entrySet();
			Iterator<Entry<String,String[]>> it = set.iterator();
			while (it.hasNext())
			{
				Entry<String,String[]> entry = it.next();
				logger.info("======  key={}, value={}  ======", entry.getKey(), entry.getValue()[0]);
				model.addAttribute(entry.getKey(), entry.getValue()[0]);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return path;
	}
}
