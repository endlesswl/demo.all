package com.lingcaibao.listener;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lingcaibao.conf.Conf;
import com.lingcaibao.util.UrlDoMain;
/**
 * @author xin.pang
 */
public class GlobalParameterListener implements ServletContextListener
{
	private static final Logger	logger	= LoggerFactory.getLogger(GlobalParameterListener.class);
	private static String		contextPath;

	public static String getContextPath()
	{
		return contextPath;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		ServletContext application = sce.getServletContext();
		//设置baseurl 零彩宝前段所用的url
		application.setAttribute("lzzUrl", Conf.get("lzz.url"));
		application.setAttribute("lzzURL", Conf.get("lzz.url"));
		application.setAttribute("lzzurl", Conf.get("lzz.url"));
		application.setAttribute("lzzURl", Conf.get("lzz.url"));
		application.setAttribute("lzzUrL", Conf.get("lzz.url"));
		
        //		application.setAttribute("ctx", getContextPath(application));
		
		//        application.setAttribute("clientUrl", Conf.get("client.domain"));
		//        application.setAttribute("htmlUrl", Conf.get("html.url"));
		contextPath = application.getRealPath("/");
		if (contextPath.endsWith("/"))
		{
			contextPath = contextPath.substring(0, contextPath.length() - 1);
		}
		
		application.setAttribute("realPath", contextPath);
		
		logger.info("=================================================================");
		if(application.getContextPath().equals("/")){
			application.setAttribute("ctx", "");
		}else{
			application.setAttribute("ctx", application.getContextPath());
		}
		
		
		logger.info(application.getContextPath());
		logger.info("=================================================================");
		//        CookieUtils.setDefaultDomain(Conf.get("cookie_domain"));
//		// 模拟用户登陆
//		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("D://lingcai_branch//lingcai.market.all//market-web//src//main//resources//shiro-realm.ini");
//		// 得到SecurityManager实例并绑定给SecurityUtils
//		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
//		SecurityUtils.setSecurityManager(securityManager);
//		// 得到Subject及创建用户名/密码身份验证Token(即用户身份/凭证)
//		Subject subject = SecurityUtils.getSubject();
//		//UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
//		String username = "593979962@qq.com";
////		String password = "jiji452";
//		String password = "9c10ff5cabeb5e7de9b5fc2a6da73313d5a12495";
//		
//		UserToken token = new UserToken();
//		token.setUsername(username);
//		token.setPassword(password.toCharArray());
//		token.setRememberMe(true);
//		token.setLoginMode(2);
//		try
//		{
//			subject.login(token);
//		} catch (AuthenticationException e)
//		{
//			System.err.println(e.getMessage());
//		}
		//断言用户已经登录
		//Assert.assertEquals(true, subject.isAuthenticated());
		//退出
		//subject.logout();
	}

	public static String readVer() throws IOException
	{
		InputStream is = GlobalParameterListener.class.getResourceAsStream("/.ver");
		byte[] ver = new byte[4096];
		is.read(ver);
		String verS = new String(ver).trim();
		is.close();
		return verS;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		ServletContext application = sce.getServletContext();
		application.removeAttribute("qzzUrl");
	}
}
