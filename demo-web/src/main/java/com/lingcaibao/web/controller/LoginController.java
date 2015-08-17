package com.lingcaibao.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lingcaibao.charge.duiba.DBChargeUtil;
import com.lingcaibao.entity.User;
import com.lingcaibao.service.SmsService;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.shiro.UserToken;
import com.lingcaibao.util.CommonStatus;
/**
 * <p>标题：用户登录 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月18日 上午10:48:06</p>
 * <p>类全名：com.lingcaibao.web.controller.LoginController</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController
{
	private static Logger			logger	= LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService				userService;
	@Autowired
	private SmsService				smsService;

	@RequestMapping()
	public String login(HttpServletRequest request)
	{
		Subject currentUser = SecurityUtils.getSubject();
		logger.info("------------ isAuthenticated --------------:{}", currentUser.isAuthenticated());
		logger.info("------------ isRemembered --------------:{}", currentUser.isRemembered());
		if (currentUser.isAuthenticated() || currentUser.isRemembered())
		{
			return "redirect:/home";
		}
		return "business/login/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, @RequestParam("userType") String userType, Model model)
	{
		logger.info("------------ login fail --------------");
		Subject currentUser = SecurityUtils.getSubject();
		logger.info("------------ isAuthenticated --------------:{}", currentUser.isAuthenticated());
		logger.info("------------ isRemembered --------------:{}", currentUser.isRemembered());
		if (currentUser.isAuthenticated() || currentUser.isRemembered())
		{
			ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
			User user = userService.get(shiroUser.id);
			Session session = currentUser.getSession(true);
			session.setAttribute(CommonStatus.SESSION_USER_NAME, user);
			return "redirect:/home";
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		String err = "用户名或密码错误！";
		model.addAttribute("userType", userType);
		model.addAttribute("errstr", err);
		return "business/login/login";
	}

	/**
	 * ajax用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/entry")
	@ResponseBody
	public String ajaxLogin(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "rememberMe", defaultValue = "true") boolean rememberMe)
	{
		//登录
		Map<String,String> resultMap = Maps.newHashMap();
		User user = userService.findUserByLoginName(username);
		if (user == null)
		{
			resultMap.put("result", "false");
			resultMap.put("message", "用户名不存在!");
		} else
		{
			Subject currentUser = SecurityUtils.getSubject();
			UserToken token = new UserToken();
			token.setUsername(username);
			token.setPassword(password.toCharArray());
			token.setRememberMe(rememberMe);
			token.setUserType(user.getUserType());
			token.isChaAuto(false);
			try
			{
				currentUser.login(token);
				Session session = currentUser.getSession(true);
				session.setAttribute(CommonStatus.SESSION_USER_NAME, user);
				resultMap.put("result", "true");
				resultMap.put("message", "登录成功!");
			} catch (Exception e)
			{
				logger.error(e.getMessage());
				resultMap.put("result", "false");
				resultMap.put("message", "用户名或密码错误!");
			}
		}
		return JSON.toJSONString(resultMap);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttributes, HttpServletRequest request)
	{
		// 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
		request.getSession().invalidate();
		redirectAttributes.addFlashAttribute("message", "您已安全退出");
		return "redirect:/login";
	}

	@RequestMapping(value = "exchange")
	@ResponseBody
	public String exchangeObject(@RequestParam(value = "quantity") String quantity, @RequestParam(value = "phone") String phone, HttpServletRequest request)
	{
		return (DBChargeUtil.doRecharge(quantity, phone) != null) + "";
	}

}
