package com.lingcaibao.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.lingcaibao.entity.User;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.shiro.ShiroDbRealm;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.util.CommonStatus;
/**
 * <p>标题：用户登录过滤器 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月18日 上午11:14:38</p>
 * <p>类全名：com.lingcaibao.shiro.UserFormAuthenticationFilter</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class UserFormAuthenticationFilter extends FormAuthenticationFilter
{
	private static Logger	logger			= LoggerFactory.getLogger(UserFormAuthenticationFilter.class);
	@Autowired
	private UserService		userService;
	private String			userTypeParam	= "userType";

	private String getUserTypeParam()
	{
		return userTypeParam;
	}

	protected int getUserType(ServletRequest request)
	{
		return Integer.parseInt(WebUtils.getCleanParam(request, getUserTypeParam()));
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response)
	{
		try
		{
			logger.info("----------- createToken ----------");
			String username = getUsername(request);
			String password = getPassword(request);
			String channel = request.getParameter("channel");
			int userType = getUserType(request);
			boolean rememberMe = isRememberMe(request);
			rememberMe = false;
			String host = getHost(request);
			logger.info("----------- login info ----------:{}", username + "-" + password + "-" + channel + "-" + userType + "-" + rememberMe + "-" + host);
			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password))
			{
				return new UserToken(username, password.toCharArray(), rememberMe, userType, host, StringUtils.isNotEmpty(channel));
			}
			return new UsernamePasswordToken(username, password, rememberMe, host);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("---------- createToken err ----------:{}", ex.getMessage());
			return null;
		}
	}

	/**
	 * 判断用户是否需要登录
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
	{
		try
		{
			logger.info("----------- isAccessAllowed ----------");
			Subject subject = getSubject(request, response);
			logger.info("----------- subject ----------");
			//如果 isAuthenticated 为 false 证明不是登录过的，同时 isRememberd 为true 证明是没登陆直接通过记住我功能进来的
			if (!subject.isAuthenticated() && subject.isRemembered())
			{
				ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
				logger.info("----------- shiroUser ----------:{}", JSON.toJSONString(shiroUser));
				User user = userService.findUserByLoginName(shiroUser.userName);
				logger.info("----------- user ----------:{}", JSON.toJSONString(user));
				subject.login(new UserToken(shiroUser.userName, shiroUser.password.toCharArray(), subject.isRemembered(), shiroUser.userType, getHost(request)));
				Session session = subject.getSession(true);
				if (session.getAttribute(CommonStatus.SESSION_USER_NAME) == null)
				{
					session.setAttribute(CommonStatus.SESSION_USER_NAME, user);
				}
			}
			return subject.isAuthenticated() || subject.isRemembered();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("---------- isAccessAllowed err ----------:{}", ex.getMessage());
			return false;
		}
	}
}
