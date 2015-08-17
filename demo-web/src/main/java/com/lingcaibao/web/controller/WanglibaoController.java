package com.lingcaibao.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lingcaibao.entity.User;
import com.lingcaibao.service.UserService;
import com.lingcaibao.shiro.UserToken;
import com.lingcaibao.util.AccountContent;
import com.palm.commom.uitl.PalmBuildParams;

/**
 * 网利宝跳转接口
 * @author h8wangjiabao
 */
@Controller
@RequestMapping(value = "/wanglibao")
public class WanglibaoController {

	@Autowired
	private UserService userService;

	/**
	 * 网利宝用户入口
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String wanglibao(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "sign", required = false) String sign,
			HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		if(StringUtils.isBlank(username) || StringUtils.isBlank(sign)){
			return "redirect:/login";
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("username", username);
		String mySign = PalmBuildParams.buildRequestMysign(paramMap, "wanglibao");
		if (!mySign.equals(sign)) {
			return "redirect:/login";
		}
		
		User user = userService.findUserByLoginName(username);
		if(user == null){
			return "redirect:/login";
		}
		
		// 授权登录
		Subject currentUser = SecurityUtils.getSubject();
		UserToken token = new UserToken();
		token.setUsername(user.getUsername());
		String pwd = user.getUsername().substring(5);
		token.setPassword(pwd.toCharArray());
		token.setRememberMe(false);
		token.isChaAuto(true);
		token.setUserType(AccountContent.TYPE_USER);
		currentUser.login(token);
		// Session session = currentUser.getSession(true);
		//session.setAttribute(CommonStatus.SESSION_USER_NAME, user);
		
		return "redirect:/home/user/info2";
	}
}