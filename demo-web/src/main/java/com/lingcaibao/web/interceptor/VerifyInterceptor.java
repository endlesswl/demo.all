package com.lingcaibao.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lingcaibao.statuscode.RegexType;
import com.lingcaibao.util.RegexUtils;

public class VerifyInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			VerifyKey annotation = method
					.getAnnotation(VerifyKey.class);
			if (annotation != null) {
				// 获取对象的成员的注解信息
				Verify[] vs = annotation.value();
				for (Verify v : vs) {
					if (!validate(v, request.getParameter(v.key()), response)) {
						return false;
					}
				}
				return true;
			}
			// 没有注解通过拦截
			return true;
		}
		return true;
	}

	public static boolean validate(Verify v, Object value,
			HttpServletResponse response) throws Exception {

		String description;
		boolean isAble = v.isAble();
		int maxLen = v.maxLen();
		int minLen = v.minLen();
		description = v.desc();

		/************* 注解解析工作开始 ******************/
		// 获取是否为空
		if (!isAble) {
			if (StringUtils.isEmpty(value)) {
				return responseResult(description + "不能为空", response);
			}
		}

		if (StringUtils.isEmpty(value)) {
			return true;
		}

		if (value.toString().length() > maxLen && maxLen != 0) {
			return responseResult(description + "长度不能超过" + maxLen, response);
		}

		if (value.toString().length() < minLen && minLen != 0) {
			return responseResult(description + "长度不能小于" + minLen, response);
		}

		for (RegexType type : v.regexType()) {
			if (type != RegexType.NONE) {
				switch (type) {
				case NONE:
					break;
				case SPECIALCHAR:
					if (RegexUtils.hasSpecialChar(value.toString())) {
						return responseResult(description + "不能含有特殊字符",
								response);
					}
					break;
				case CHINESE:
					if (RegexUtils.isChinese2(value.toString())) {
						return responseResult(description + "不能含有中文字符",
								response);
					}
					break;
				case EMAIL:
					if (!RegexUtils.isEmail(value.toString())) {
						return responseResult(description + "格式不正确", response);
					}
					break;
				case IP:
					if (!RegexUtils.isIp(value.toString())) {
						return responseResult(description + "格式不正确", response);
					}
					break;
				case NUMBER:
					if (!RegexUtils.isNumber(value.toString())) {
						return responseResult(description + "格式不正确", response);
					}
					break;
				case MOBILE:
					if (!RegexUtils.isPhoneNumber(value.toString())) {
						return responseResult(description + "格式错误", response);
					}
					break;
				case TIME:
					if (!RegexUtils.isTime(value.toString())) {
						return responseResult(description + "格式错误", response);
					}
					break;
				case FREQUENCY:
					if (!RegexUtils.isFrequency(value.toString())) {
						return responseResult(description + "格式错误", response);
					}
					break;
				case URL:
					if(!RegexUtils.isUrl(value.toString())) {
						return responseResult(description + "格式错误", response);
					}
				default:
					break;
				}
			}
		}

		return true;
		/************* 注解解析工作结束 ******************/
	}

	/**
	 * 验证异常，跳转，返回JSON串
	 * 
	 * @param str
	 * @param response
	 */
	public static boolean responseResult(String str,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/text; charset=utf-8");
			out = response.getWriter();
			out.append("{\"result\":\"false\",\"message\":\"" + str + "\"}");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return false;
	}
}
