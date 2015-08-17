package com.lingcaibao.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lingcaibao.entity.User;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.util.AccountContent;
import com.lingcaibao.util.CommonStatus;
/**
 * <p>标题：平台统一跳转控制器 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月13日 下午2:33:49</p>
 * <p>类全名：com.lingcaibao.web.controller.RootController</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Controller
@RequestMapping
public class RootController
{
	@Autowired
	private UserService	userService;

	/**
	 * 用户主页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model)
	{
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		User user = userService.get(shiroUser.id);
		model.addAttribute("user", user);
		Session session = subject.getSession(true);
		session.setAttribute(CommonStatus.SESSION_USER_NAME, user);
		// TODO 和大龙协商前台实现
		// 用户需初始化密码
		/*
		if (user.getFlag() == AccountContent.FLAG_PWD_INITIALIZE)
		{
			return "account/initPwd";
		}
		*/
		// 个人用户
		if (user.getUserType() == AccountContent.TYPE_USER)
		{
			//return "person/basicmessage/PersonalUserInfo";
			//return "person/basicmessage/PersonalUserInfo2";
			return "redirect:/home/user/info2";
		}
		// 商家用户
		else if (user.getUserType() == AccountContent.TYPE_BUSINESS)
		{
			return "business/index/index";
		}
		// 代理商用户
		else if (user.getUserType() == AccountContent.TYPE_PROXY)
		{
			if (user.getFlag() == AccountContent.FLAG_AUDIT)
			{
				model.addAttribute("message", "尊敬的零彩宝用户，您申请的代理商账户正在审核中，请您耐心等待！");
				return "proxy/error";
			} else
			{
				return "proxy/myAccount";
			}
		}
		return "unknow";
	}
}
