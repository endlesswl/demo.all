package com.lingcaibao.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
/**
 * <p>标题：Cookie工具类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年8月3日 下午3:12:49</p>
 * <p>类全名：com.lingcaibao.util.CookieHelper</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class CookieHelper
{
	/**
	 * 获取Cookie属性
	 * @param request
	 * @param cname
	 * @return
	 */
	static public String getValue(HttpServletRequest request, String cname)
	{
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie coo : cookies)
			{
				if (StringUtils.equals(coo.getName(), cname))
				{
					return coo.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 按照前缀获取Cookie列表
	 * @param request
	 * @param prefix
	 * @return
	 */
	static public List<Cookie> getCookies(HttpServletRequest request, String prefix)
	{
		List<Cookie> ls = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie coo : cookies)
			{
				if (StringUtils.startsWith(coo.getName(), prefix))
				{
					if (ls == null)
					{
						ls = Lists.newArrayList();
					}
					ls.add(coo);
				}
			}
		}
		return ls;
	}

	/**
	 * 添加cookie，当然，这个其实是将cookie的内容转换成为字符串，然后添加到header。 
	 * 例如： 
	 * response.addHeader("Set-Cookie",  "__wsidd=hhghgh ;Domain=localhost; Path=/; Max-Age=36000; Secure; HTTPOnly;"); 
	 * 例子二： 
	 * Set-Cookie：customer=huangxp; path=/foo; domain=.ibm.com; 
	 * expires= Wednesday, 19-OCT-05 23:12:40 GMT; [secure] 
	 * 注意，expires已经被Max-Age所代替。 
	 * @param response
	 * @param _cookie
	 */
	public static void addCookie(HttpServletResponse response, CookieUnit _cookie)
	{
		if (response == null || _cookie == null)
		{
			return;
		}
		if (StringUtils.isEmpty(_cookie.key))
		{
			return;
		}
		StringBuilder sb = getCookieStr(_cookie);
		response.addHeader("Set-Cookie", sb.toString());
	}
	
	/**
	 * 增加Cookie
	 * @param response
	 * @param key
	 * @param value
	 */
	public static void addCookie(HttpServletResponse response, String key, String value)
	{
		Cookie coo = new Cookie(key, value);
		coo.setMaxAge(-1);
		coo.setPath("/");
		response.addCookie(coo);
	}

	/**
	 * 删除Cookie
	 * @param response
	 * @param cname
	 */
	public static void removeCookie(HttpServletResponse response, String cname)
	{
		Cookie cookie = new Cookie(cname, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	/**
	 * 删除Cookies
	 * @param request
	 * @param response
	 * @param prefix
	 */
	public static void removeCookies(HttpServletRequest request, HttpServletResponse response, String prefix)
	{
		List<Cookie> list = getCookies(request, prefix);
		if (list != null)
		{
			for (Cookie coo : list)
			{
				Cookie cookie = new Cookie(coo.getName(), null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	private static StringBuilder getCookieStr(CookieUnit _cookie)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(_cookie.key.trim());
		sb.append('=');
		if (StringUtils.isNotEmpty(_cookie.value))
		{
			sb.append(_cookie.value.trim());
		}
		//--max age属性或者expires属性，两者只能选择其中一个  
		if (_cookie.expires != null)
		{
			//--只有expires存在的情况下才能设置domain。  
			sb.append("; expires=");
			//--格林威志时间格式化，注意，这是这个参数的格式要求。  
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
			sb.append(sdf.format(_cookie.expires));
		}
		if (_cookie.Max_Age != null)
		{
			sb.append("; max-age=");
			sb.append(_cookie.Max_Age);
		}
		//--domain字符串  
		if (StringUtils.isNotEmpty(_cookie.Domain))
		{
			sb.append("; domain=");
			sb.append(_cookie.Domain.trim());
		}
		//--构造path字符串  
		if (StringUtils.isNotEmpty(_cookie.Path))
		{
			sb.append("; path=");
			sb.append(_cookie.Path.trim());
		}
		//--构造secure属性  
		if (_cookie.Secure)
		{
			sb.append("; Secure");
		}
		//--构造httponly属性  
		if (_cookie.HTTPOnly)
		{
			sb.append("; HttpOnly");
		}
		return sb;
	}

	/**
	 * <p>标题：Cookie单元 </p>
	 * <p>功能： </p>
	 * <p>版权： Copyright (c) 2015</p>
	 * <p>公司: 北京零彩宝网络技术有限公司 </p>
	 * <p>创建日期：2015年8月3日 下午3:21:43</p>
	 * <p>类全名：com.lingcaibao.util.CookieUnit</p>
	 * <p>作者：JIJI </p>
	 * <p>@version 1.0</p>
	 */
	static public class CookieUnit
	{
		public String	key			= null;
		public String	value		= null;
		public String	Max_Age		= null;
		public String	Domain		= null;
		public String	Path		= null;
		public boolean	Secure		= false;
		public boolean	HTTPOnly	= false;
		public Date		expires		= null;

		public CookieUnit(String _key, String _value, String _domain, String _path, Date _expires, boolean _secure, boolean _httpOnly)
		{
			key = _key;
			value = _value;
			expires = _expires;
			Domain = _domain;
			Path = _path;
			Secure = _secure;
			HTTPOnly = _httpOnly;
		}

		public CookieUnit(String _key, String _value, String _path, String _max_age, boolean _secure, boolean _httpOnly)
		{
			key = _key;
			value = _value;
			Max_Age = _max_age;
			Path = _path;
			Secure = _secure;
			HTTPOnly = _httpOnly;
		}

		public CookieUnit(String _key, String _value, String _maxAage, boolean _secure, boolean _httpOnly)
		{
			key = _key;
			value = _value;
			Max_Age = _maxAage;
			Secure = _secure;
			HTTPOnly = _httpOnly;
		}

		public CookieUnit(String _key, String _value, Date _exipres, boolean _secure, boolean _httpOnly)
		{
			key = _key;
			value = _value;
			expires = _exipres;
			Secure = _secure;
			HTTPOnly = _httpOnly;
		}
	}
}
