package com.lingcaibao.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.lingcaibao.cache.memcached.MarketMemcachedClient;
import com.lingcaibao.email.E3Api;
import com.lingcaibao.encrypt.UCEncryptor;
import com.lingcaibao.entity.Dictionary;
import com.lingcaibao.entity.ResultPage;
import com.lingcaibao.entity.User;
import com.lingcaibao.entityenum.UserInfoPercent;
import com.lingcaibao.entityenum.UserMsgTemp;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.service.DictionaryService;
import com.lingcaibao.service.UserLotteryService;
import com.lingcaibao.service.UserLuckdrawService;
import com.lingcaibao.service.UserPrizeService;
import com.lingcaibao.service.UserService;
import com.lingcaibao.service.cache.RedisListOpsService;
import com.lingcaibao.service.shiro.ShiroDbRealm.ShiroUser;
import com.lingcaibao.statuscode.PrizeType;
import com.lingcaibao.statuscode.RealNameAuthFlags;
import com.lingcaibao.util.AccountContent;
import com.lingcaibao.util.ArticleContent;
import com.lingcaibao.util.DictionaryContent;
import com.lingcaibao.util.LcbJson;
import com.lingcaibao.util.MathUtil;
import com.lingcaibao.util.RegexUtils;
import com.lingcaibao.util.WebUtils;
import com.palm.commom.uitl.Servlets;
import com.palm.commom.uitl.UUIDUtils;

/**
 * <p>
 * 标题：用户相关接口
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright (c) 2015
 * </p>
 * <p>
 * 公司: 北京零彩宝网络技术有限公司
 * </p>
 * <p>
 * 创建日期：2015年3月25日 上午11:33:05
 * </p>
 * <p>
 * 类全名：com.lingcaibao.web.controller.UserController
 * </p>
 * <p>
 * 作者：JIJI
 * </p>
 * <p>
 * 
 * @version 1.0
 *          </p>
 */
@Controller
@RequestMapping(value = "/home/user")
public class UserController {
	@Autowired
	private MarketMemcachedClient memcachedClient;
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserLotteryService userLotteryService;
	@Autowired
	private UserPrizeService userPrizeService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private UserLuckdrawService userLuckdrawService;
	@Autowired
	private RedisListOpsService redisListOpsService;
	private static final String PAGE_SIZE = "10";
	// 允许排序的字段
	private static final List<String> ALLOW_SORT_COLUMNS = ImmutableList.of("id");

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize, @RequestParam(value = "sort", defaultValue = "id_") String sort, ServletRequest request, Model model) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		// 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
		String sortStr = buildSort(sort);
		if (sortStr.length() > 0) {
			searchParams.put("sortColumns", sortStr);
		}
		Page<User> pageable = new Page<User>(pageNumber, pageSize);
		Page<User> page = userService.searchPage(searchParams, pageable);
		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return "user/userList";
	}

	@RequestMapping(value = "/info2")
	public String getUserInfo2(Model model, HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		User user = userService.get(shiroUser.id);// 1087258L
		
		model.addAttribute("user", user);

		Map<String, Object> map = LcbJson.toMap(user);
		map.put("bindMobileFlag", user.isBindMobile());
		map.put("bindEmailFlag", user.isBindEmail());
		map.put("bindBankFlag", user.isBindBankCard());
		map.put("bindNameFlag", user.isBindName());
		
		if (user.isBindName() && user.isBindMobile() && user.isBindBankCard()) {

			return "person/basicmessage/PersonalUserInfo3";
		} else {

			return "person/basicmessage/PersonalUserInfo2";
		}
	}

	@ResponseBody
	@RequestMapping("/updateUserInfo")
	public String updateUserInfo(@RequestParam("cardNo") String cardNo, // 身份证号
			@RequestParam("realName") String realName,// 真实姓名
			@RequestParam("cardno") String cardno,// 卡号
			@RequestParam("bankname") String bankname,// 开户行全称
			@RequestParam("abbreviation") String abbreviation,// 开户行简写
			@RequestParam(value = "captcha") String captcha// 验证码
	) {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		User user = userService.get(shiroUser.id);

		Map<String, String> resultMap = Maps.newHashMap();
		// 校验合法用户
		if (user == null) {
			resultMap.put("result", "false");
			resultMap.put("message", "该用户不存在！");
			return JSON.toJSONString(resultMap);
		}
		// 校验验证码
		String msgKey = "CAPTCHA_" + UserMsgTemp.BIND_MOBILE.name() + "_" + user.getUsername();
		String captchaInfo = memcachedClient.get(msgKey);
		if (!StringUtils.equals(captchaInfo, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		
		try {
			userService.updateUserInfo(user, cardNo, realName, cardno, bankname, abbreviation);
			resultMap.put("result", "true");
			resultMap.put("message", "基本信息保存成功!");
		} catch (Exception e) {
			resultMap.put("result", "false");
			resultMap.put("message", "系统繁忙，请稍后重试！");
			e.printStackTrace();
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 个人手机后台 安全中心-----实名认证
	 * 
	 * @param cardNo
	 *            (身份证号)
	 * @param realName
	 *            (真实姓名)
	 * @param idcardimg
	 *            (扫描件地址)
	 * @param userId
	 *            (用户ID)
	 */
	@ResponseBody
	@RequestMapping("/updateIdentify")
	public String certification(@RequestParam("cardNo") String cardNo, @RequestParam("realName") String realName) {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		User user = userService.get(shiroUser.id);

		Map<String, String> resultMap = Maps.newHashMap();
		// 校验合法用户
		if (user == null) {
			resultMap.put("result", "false");
			resultMap.put("message", "该用户不存在！");
			return JSON.toJSONString(resultMap);
		}
		try {
			userService.updateIdentify(user, cardNo, realName);
			resultMap.put("result", "true");
			resultMap.put("message", "实名认证成功!");
		} catch (Exception e) {
			resultMap.put("result", "false");
			resultMap.put("message", "系统繁忙，请稍后重试！");
			logger.error("------------update user err------------:{}", user.getId());
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 获取登陆用户信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info")
	public String getUserInfo() {
		logger.info("start.................................getUserInfo()");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		User user = userService.get(shiroUser.id);// 1087258L
		// 计算用户完善资料百分数
		BigDecimal percents = UserInfoPercent.getUserInfoPercent(user);
		user.setPercents(percents);
		// 计算用户安全等级评分
		BigDecimal gradescores = userService.getUserGradeScores(user);
		user.setGradescores(gradescores);
		Map<String, Object> map = LcbJson.toMap(user);
		map.put("bindMobileFlag", user.isBindMobile());
		map.put("bindEmailFlag", user.isBindEmail());
		map.put("bindBankFlag", user.isBindBankCard());
		map.put("bindNameFlag", user.isBindName());
		String country = user.getCountry();
		if (StringUtils.isNotEmpty(country)) {
			map.put("countryName", dictionaryService.getDictItemName(DictionaryContent.DICT_NO_NODE, country));
		}
		String province = user.getProvince();
		if (StringUtils.isNotEmpty(province)) {
			map.put("provinceName", dictionaryService.getDictItemName(DictionaryContent.DICT_NO_NODE, province));
		}
		String city = user.getCity();
		if (StringUtils.isNotEmpty(city)) {
			map.put("cityName", dictionaryService.getDictItemName(DictionaryContent.DICT_NO_NODE, city));
		}
		String area = user.getArea();
		if (StringUtils.isNotEmpty(area)) {
			map.put("areaName", dictionaryService.getDictItemName(DictionaryContent.DICT_NO_NODE, area));
		}
		// 处理未读消息数量
		// int emails = userInboxService.getUserInboxCount(user.getId(),
		// UserInboxFlag.EMAIL_NO_READ.ordinal());
		int articles = 0;
		Map<String, Object> params = Maps.newHashMap();
		params.put("type", ArticleContent.ARTICLE_TYPE_NOTICE);
		params.put("userid", user.getId());
		/*
		 * List<Map<String,Object>> list =
		 * userSubscribeService.userSubscribeArticles(params); if (list != null)
		 * { for (Map<String,Object> article : list) { String noticeKey =
		 * RedisKey.USER_NOTICE_INFO_PREFIX + user.getId() + "_" +
		 * article.get("channel") + "_" + article.get("id"); if
		 * (redisListOpsService.get(RedisKey.USER_READ_NOTICES_KEY, noticeKey)
		 * == null) { articles++; } } }
		 */
		// map.put("emails", emails);
		map.put("articles", articles);
		// map.put("messages", emails + articles);
		return LcbJson.toJSONString(map);
	}

	/**
	 * 获取用户姓名，账户余额，活动占用余额
	 * 
	 * @return
	 */
	@ResponseBody()
	@RequestMapping(value = "/balanceMessage")
	public String getUserMessage(ServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		// map中返回的是用户姓名和账户余额
		Map<String, Object> map = userService.getUserNameAndAmount(shiroUser.id);
		// 获取活动占用余额
		//BigDecimal totalPrice = marketplanService.getUserTotalPrice(shiroUser.id);
		BigDecimal totalPrice = new BigDecimal(0);
		User user = userService.get(shiroUser.id);
		// 计算百分比
		BigDecimal percents = UserInfoPercent.getUserInfoPercent(user);
		user.setPercents(percents);
		userService.update(user);// 更新用户数据
		BigDecimal amount = new BigDecimal(map.get("amount").toString());
		jsonObject.put("userName", map.get("username"));// 用户名
		jsonObject.put("nickname", map.get("nickname"));// 昵称
		jsonObject.put("amount", amount.toString());// 账户余额
		//jsonObject.put("totalPrice", totalPrice);// 活动占用余额
		jsonObject.put("totalPrice", totalPrice);// 活动占用余额
		jsonObject.put("avatar", map.get("avatar"));// 头像地址
		jsonObject.put("percents", percents);// 资料完善百分比
		return LcbJson.toJSONString(jsonObject);
	}

	/**
	 * 实名认证
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/bindRealName")
	public String certification(@RequestParam("realName") String realName, @RequestParam(value = "cardType", defaultValue = "1") int cardType, @RequestParam("cardNo") String cardNo, @RequestParam("idCardimg") String idCardimgInfo) {
		// 获取当前用户
		User user = getLoginUser();
		Map<String, String> resultMap = Maps.newHashMap();
		try {
			user.setRealName(realName);
			user.setCardType(1);
			user.setCardNo(cardNo);
			user.setIdcardimg(idCardimgInfo);
			user.setAuthflags(RealNameAuthFlags.AUTH_FLAGS_SUCCESS.ordinal());
			userService.update(user);
			resultMap.put("result", "true");
			resultMap.put("message", "实名申请发送成功，请等待审核！");
		} catch (Exception e) {
			resultMap.put("result", "false");
			resultMap.put("message", "系统繁忙，请稍后重试！");
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 绑定手机号
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindMobile")
	public String bindMobile(@RequestParam("captchaInfo") String captchaInfo, @RequestParam("mobile") String mobile) {
		// 获取当前用户
		User user = getLoginUser();
		Map<String, String> resultMap = Maps.newHashMap();
		if (!RegexUtils.isPhoneNumber(mobile)) {
			resultMap.put("result", "false");
			resultMap.put("message", "你输入的手机号有误,请重新输入！");
			JSON.toJSONString(resultMap);
		}
		if (user == null) {
			resultMap.put("result", "false");
			resultMap.put("message", "无效的用户！");
			JSON.toJSONString(resultMap);
		} else {
			String msgKey = "CAPTCHA_" + UserMsgTemp.BIND_MOBILE.name() + "_" + mobile;
			String captcha = memcachedClient.get(msgKey);
			// 进行验证码匹配
			if (StringUtils.equals(captcha, captchaInfo)) {
				try {
					user.setBindflags(AccountContent.BIND_FLAGS_MOBILE);
					user.setMobile(mobile);
					userService.update(user);
				} catch (Exception e) {
					resultMap.put("result", "false");
					resultMap.put("message", "系统繁忙，请稍后重试！");
				}
				resultMap.put("result", "true");
				resultMap.put("message", "手机绑定成功!");
			} else {
				resultMap.put("result", "fasle");
				resultMap.put("message", "验证码错误,请重新输入！");
			}
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 绑定邮箱 第一步： 发送邮件
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/bindEmail")
	public String bindEmail(HttpServletRequest request, String email) throws UnsupportedEncodingException {
		// 获取当前用户
		User user = getLoginUser();
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
		UCEncryptor encryptor = new UCEncryptor();
		String msgKey = "CAPTCHA_" + UserMsgTemp.BIND_EMAIL.name() + "_" + email;
		String captcha = memcachedClient.get(msgKey);
		// 如果已有验证码 则用原来的 没有则新建一个
		if (captcha == null) {
			captcha = UUIDUtils.getNumberBySix();
			memcachedClient.set(msgKey, 60 * 30 * 1, captcha);
		}
		String captchaStr = URLEncoder.encode(encryptor.encrypt(captcha), "utf-8");
		String emailStr = URLEncoder.encode(encryptor.encrypt(user.getUsername()), "utf-8");
		StringBuffer content = new StringBuffer();
		content.append("亲爱的零彩宝用户，请点击链接，绑定您的邮箱:<a href=\"");
		content.append(tempContextUrl);
		content.append("register/bindEmail?username=");
		content.append(emailStr);
		content.append("&captcha=");
		content.append(captchaStr);
		content.append("&email=");
		content.append(email);
		content.append("\">");
		content.append(tempContextUrl);
		content.append("register/bindEmail?username=");
		content.append(emailStr);
		content.append("&captcha=");
		content.append(captchaStr);
		content.append("&email=");
		content.append(email);
		content.append("</a>");
		// 发送邮件到指定的邮箱
		boolean flag = E3Api.sendPost(email, UserMsgTemp.BIND_EMAIL.getTitle(), content.toString());
		Map<String, String> resultMap = Maps.newHashMap();
		if (flag) {
			resultMap.put("result", "true");
			resultMap.put("message", "邮件已发送!");
		} else {
			resultMap.put("result", "false");
			resultMap.put("message", "邮件发送失败，请稍后重试!");
		}
		return JSON.toJSONString(resultMap);
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model)// 对非Ajax有效
	{
		model.addAttribute("user", new User());
		model.addAttribute("action", "create");
		return "user/userForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid User newUser, RedirectAttributes redirectAttributes) {
		userService.insert(newUser);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/admin/user";
	}

	/**
	 * 修改登录密码逻辑
	 * 
	 * @param captcha
	 * @param pwd
	 * @param newPwd1
	 * @param newPwd2
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/modifyScrit")
	public String updateScrit(@RequestParam(value = "captcha") String captcha, @RequestParam(value = "pwd") String pwd, @RequestParam(value = "newPwd1") String newPwd1, @RequestParam(value = "newPwd2") String newPwd2) {
		logger.info("start.................................updageScrit()");
		User user = getLoginUser();
		Map<String, String> resultMap = Maps.newHashMap();
		String msgKey = "CAPTCHA_" + UserMsgTemp.MODIFY_PWD.name() + "_" + (user.getUserType() == AccountContent.TYPE_USER ? user.getMobile() : user.getEmail());
		String captchaContent = memcachedClient.get(msgKey);
		logger.info("---------- msgKey ----------:{}", msgKey);
		logger.info("---------- captchaContent ----------:{}", captchaContent);
		logger.info("---------- captcha ----------:{}", captcha);
		if (!StringUtils.equals(captchaContent, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		if (!StringUtils.equals(newPwd1, newPwd2)) {
			resultMap.put("result", "false");
			resultMap.put("message", "两次输入密码不一致,请重新输入！");
			return JSON.toJSONString(resultMap);
		}
		if (!userService.checkPwd(user, pwd)) {
			resultMap.put("result", "false");
			resultMap.put("message", "原密码不正确,请重新输入！");
			return JSON.toJSONString(resultMap);
		}
		try {
			userService.modifyScrit(user, newPwd1);
			resultMap.put("result", "true");
			resultMap.put("message", "密码修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("------------update user err------------:{}", user.getId());
			resultMap.put("result", "false");
			resultMap.put("messsage", e.getMessage());
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 用户初始化密码
	 * 
	 * @param captcha
	 * @param newPwd1
	 * @param newPwd2
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initPwd")
	public String initPwd(@RequestParam(value = "captcha") String captcha, @RequestParam(value = "newPwd1") String newPwd1, @RequestParam(value = "newPwd2") String newPwd2) {
		logger.info("start.................................initPwd()");
		User user = getLoginUser();
		Map<String, String> resultMap = Maps.newHashMap();
		String msgKey = "CAPTCHA_" + UserMsgTemp.MODIFY_PWD.name() + "_" + (user.getUserType() == AccountContent.TYPE_USER ? user.getMobile() : user.getEmail());
		String captchaContent = memcachedClient.get(msgKey);
		logger.info("---------- msgKey ----------:{}", msgKey);
		logger.info("---------- captchaContent ----------:{}", captchaContent);
		logger.info("---------- captcha ----------:{}", captcha);
		if (!StringUtils.equals(captchaContent, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		if (!StringUtils.equals(newPwd1, newPwd2)) {
			resultMap.put("result", "false");
			resultMap.put("message", "两次输入密码不一致,请重新输入！");
			return JSON.toJSONString(resultMap);
		}
		try {
			userService.initPwd(user, newPwd1);
			resultMap.put("result", "true");
			resultMap.put("message", "密码修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("------------update user err------------:{}", user.getId());
			resultMap.put("result", "false");
			resultMap.put("messsage", e.getMessage());
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 修改支付密码逻辑
	 * 
	 * @description :
	 * @param :
	 * @return ：
	 */
	@ResponseBody
	@RequestMapping("/modifyPayScrit")
	public String updatePayScrit(@RequestParam(value = "captcha") String captcha, @RequestParam(value = "payPwd") String payPwd, @RequestParam(value = "newPayPwd1") String newPayPwd1, @RequestParam(value = "newPayPwd2") String newPayPwd2) {
		logger.info("start.................................updatePayScrit()");
		User user = getLoginUser();
		String msgKey = "CAPTCHA_" + UserMsgTemp.MODIFY_PAYPWD.name() + "_" + (user.getUserType() == AccountContent.TYPE_USER ? user.getMobile() : user.getEmail());
		String captchaContent = memcachedClient.get(msgKey);
		Map<String, String> resultMap = Maps.newHashMap();
		if (!StringUtils.equals(captchaContent, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		if (!StringUtils.equals(newPayPwd1, newPayPwd2)) {
			resultMap.put("result", "false");
			resultMap.put("message", "两次输入密码不一致,请重新输入！");
			return JSON.toJSONString(resultMap);
		}
		if (!userService.checkPayPwd(user, payPwd)) {
			resultMap.put("result", "false");
			resultMap.put("message", "原密码不正确,请重新输入！");
			return JSON.toJSONString(resultMap);
		}
		try {
			userService.modifyPayScrit(user, newPayPwd1);
			resultMap.put("result", "true");
			resultMap.put("message", "密码修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("------------update user err------------:{}", user.getId());
			resultMap.put("result", "false");
			resultMap.put("messsage", e.getMessage());
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 设置支付密码
	 * 
	 * @description :
	 * @param :
	 * @return ：
	 */
	@ResponseBody
	@RequestMapping("/designPayPwd")
	public String setPayPwd(@RequestParam("captcha") String captcha, @RequestParam("payPwd1") String payPwd1, @RequestParam("payPwd2") String payPwd2) {
		logger.info("start.................................setPayPwd()");
		User user = getLoginUser();
		String msgKey = "CAPTCHA_" + UserMsgTemp.SET_PAYPWD.name() + "_" + (user.getUserType() == AccountContent.TYPE_USER ? user.getMobile() : user.getEmail());
		String captchaContent = memcachedClient.get(msgKey);
		Map<String, String> resultMap = Maps.newHashMap();
		if (!StringUtils.equals(captchaContent, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		if (!StringUtils.equals(payPwd1, payPwd2)) {
			resultMap.put("result", "false");
			resultMap.put("message", "两次输入密码不一致,请重新输入！");
			return JSON.toJSONString(resultMap);
		}
		try {
			userService.setPayPwd(user, payPwd1);
			resultMap.put("result", "true");
			resultMap.put("message", "密码设置成功！");
			return JSON.toJSONString(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("------------update user err------------:{}", user.getId());
			resultMap.put("result", "false");
			resultMap.put("messsage", e.getMessage());
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 更新用户的基本信息
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateUser")
	public String updateUser(@Valid User user) {
		logger.info("start.................................updateUser()");
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		Map<String, String> resultMap = Maps.newHashMap();
		try {
			user.setId(shiroUser.id);
			logger.debug("--- user info ---:{}", JSON.toJSONString(user));
			userService.update(user);
			resultMap.put("result", "true");
			resultMap.put("message", "更新用户信息成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("------------update user err------------:{}", user.getId());
			resultMap.put("result", "false");
			resultMap.put("message", "更新用户信息失败!");
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 校验修改绑定手机验证码(原绑定手机号)
	 * 
	 * @param mobile
	 * @param captcha
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkBindMobile")
	public String checkBindMobile(@RequestParam("mobile") String mobile, @RequestParam("captcha") String captcha) {
		Map<String, String> resultMap = Maps.newHashMap();
		String msgKey = "CAPTCHA_" + UserMsgTemp.BIND_MOBILE.name() + "_" + mobile;
		String captchaContent = memcachedClient.get(msgKey);
		if (!StringUtils.equals(captchaContent, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		} else {
			resultMap.put("result", "true");
			resultMap.put("message", "验证码正确！");
			return JSON.toJSONString(resultMap);
		}
	}

	/**
	 * 更新绑定手机(新手机号)
	 * 
	 * @param mobile
	 * @param captcha
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateBindMobile")
	public String updateBindMoble(@RequestParam("mobile") String mobile, @RequestParam("captcha") String captcha) {
		User user = getLoginUser();
		Map<String, String> resultMap = Maps.newHashMap();
		if (!RegexUtils.isPhoneNumber(mobile)) {
			resultMap.put("result", "false");
			resultMap.put("message", "你输入的手机号有误,请重新输入！");
			JSON.toJSONString(resultMap);
		}
		if (user == null) {
			resultMap.put("result", "false");
			resultMap.put("message", "该用户不存在！");
			return JSON.toJSONString(resultMap);
		}
		String msgKey = "CAPTCHA_" + UserMsgTemp.BIND_MOBILE.name() + "_" + mobile;
		String captchaContent = memcachedClient.get(msgKey);
		if (!StringUtils.equals(captchaContent, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		// 如果手机未绑定
		if (!user.isBindMobile()) {
			resultMap.put("result", "false");
			resultMap.put("message", "你的手机未绑定,请绑定你的手机！");
			return JSON.toJSONString(resultMap);
		}
		try {
			user.setMobile(mobile);
			userService.updateBindMobile(user);
			resultMap.put("result", "true");
			resultMap.put("message", "修改手机成功！");
		} catch (Exception e) {
			resultMap.put("result", "false");
			resultMap.put("message", "修改手机失败！");
			logger.error("------------update user err------------:{}", user.getId());
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 找回支付密码
	 * 
	 * @description :
	 * @param :
	 * @return ：
	 */
	@ResponseBody
	@RequestMapping(value = "/findPayPwd")
	public String findPayPwd(@RequestParam("captcha") String captcha, @RequestParam("payPwd1") String payPwd1, @RequestParam("payPwd2") String payPwd2) {
		logger.info("start.................................findPayPwd()");
		User user = getLoginUser();
		String msgKey = "CAPTCHA_" + UserMsgTemp.FIND_PAYPWD.name() + "_" + (user.getUserType() == AccountContent.TYPE_USER ? user.getMobile() : user.getEmail());
		String captchaContent = memcachedClient.get(msgKey);
		Map<String, String> resultMap = Maps.newHashMap();
		if (!StringUtils.equals(captchaContent, captcha)) {
			resultMap.put("result", "false");
			resultMap.put("message", "请输入正确的验证码！");
			return JSON.toJSONString(resultMap);
		}
		if (!StringUtils.equals(payPwd1, payPwd2)) {
			resultMap.put("result", "false");
			resultMap.put("message", "两次输入密码不一致,请重新输入！");
			return JSON.toJSONString(resultMap);
		}
		try {
			userService.findPayPwd(user, payPwd1);
			resultMap.put("result", "true");
			resultMap.put("message", "密码设置成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("------------update user err------------:{}", user.getId());
			resultMap.put("result", "false");
			resultMap.put("messsage", e.getMessage());
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		userService.update(user);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		userService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/user";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("user", userService.get(id));
		}
	}

	/**
	 * 构建排序SQL 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
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
				strf.append(sortStr.substring(0, sortStr.length() - 1)).append(" DESC");
			}
		}
		return strf.toString();
	}

	/**
	 * 返回当前登录用户
	 * 
	 * @description :
	 * @param :
	 * @return ：
	 */
	public User getLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		return userService.get(shiroUser.id);
	}

	/**
	 * 获取用户账户信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserAccountDetail")
	public String getUserAccountDetail() {
		Map<String, Object> resultMap = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		User user = userService.get(shiroUser.id);
		
		resultMap.put("bindMobileFlag", user.isBindMobile());
		resultMap.put("bindBankFlag", user.isBindBankCard());
		resultMap.put("bindNameFlag", user.isBindName());
		
		long id = user.getId();
		// 查询可提现金额 不可提现金额
		BigDecimal amount = user.getAmount();
		BigDecimal coupon = userService.getUserCoupon(id);
		if (amount == null) {
			amount = BigDecimal.ZERO;
		}
		if (coupon == null) {
			coupon = BigDecimal.ZERO;
		}
		// 可提现
		resultMap.put("amount", MathUtil.getNumTwoDigitsAfter(amount));
		// 零彩卷 不可提现
		resultMap.put("coupon", MathUtil.getNumTwoDigitsAfter(coupon));
		// 总资产
		resultMap.put("total", MathUtil.getNumTwoDigitsAfter(coupon.add(amount)));
		List<Map<String, Object>> list = userService.getUserTotalAssets(id);
		for (Map<String, Object> map : list) {
			if (map.get("name").equals(PrizeType.getName("话费"))) {
				map.put("pictureURL", PrizeType.CALL.getIcon());
			}
			if (map.get("name").equals(PrizeType.getName("流量"))) {
				map.put("pictureURL", PrizeType.FLOW.getIcon());
			}
			// 此处少对默认图片的处理
		}
		// 彩票资产
		Map<String, Object> moneyMap = userLotteryService.getLotteryMoney(shiroUser.id);
		Object assetstotal = moneyMap == null ? null : moneyMap.get("money");
		Map<String, Object> map = Maps.newHashMap();
		map.put("name", "彩票");
		map.put("assetstotal", MathUtil.getNumTwoDigitsAfter(assetstotal));
		map.put("pictureURL", PrizeType.LOTTERY.getIcon());
		// 将彩票资产加入list
		list.add(map);
		resultMap.put("assets", list);
		BigDecimal total = BigDecimal.ZERO;
		for (int i = 0; i < list.size(); i++) {
			BigDecimal sum = MathUtil.toDecimal(list.get(i).get("assetstotal"));
			if (sum == null) {
				sum = BigDecimal.ZERO;
			}
			total = total.add(sum);
		}
		// 总资产
		resultMap.put("assetsTotal", MathUtil.getNumTwoDigitsAfter(total));
		// 当月除彩票外的其他类型的资产
		BigDecimal monthAssets = userService.getUserAssetsByMonth(id);
		// 当月彩票资产
		BigDecimal mp = userLotteryService.getUserLottertByMonth(id);
		BigDecimal lotteryAssets = null;
		if (mp == null) {
			lotteryAssets = BigDecimal.ZERO;
		} else {
			lotteryAssets = mp;
		}
		if (monthAssets == null) {
			monthAssets = BigDecimal.ZERO;
		}
		resultMap.put("assetsTotalMonth", MathUtil.getNumTwoDigitsAfter(monthAssets.add(lotteryAssets)));
		return LcbJson.toJSONString(resultMap);
	}

	/**
	 * 用户领彩总计金额
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userPrizeMoney")
	public String getUserLotteryMoney(ServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		BigDecimal decimal = userPrizeService.getUserPrizeMoney(shiroUser.id);
		Map<String, Object> map = Maps.newHashMap();
		map.put("lotteryMoney", MathUtil.getNumTwoDigitsAfter(decimal));
		return LcbJson.toJSONString(map);
	}

	/**
	 * 用户管理 用户列表信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/userList")
	public String getJoinActivityUser(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize, ServletRequest request, Model model) {
		String userName = request.getParameter("userName");// 用户昵称
		String marketName = request.getParameter("marketname");// 活动名称
		String searchTime = request.getParameter("time");// 时间范围 一个月 两个月 三个月
		String clustno = request.getParameter("clustno");// 群组编码
		Map<String, Object> searchParams = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		searchParams.put("id", shiroUser.id);
		searchParams.put("nickname", userName);// 用户昵称
		searchParams.put("marketName", marketName);// 活动名称
		searchParams.put("clustno", clustno);// 群组编码
		int month = (searchTime != null && !"".equals(searchTime)) ? Integer.parseInt(searchTime) : 0;
		searchParams.put("days", month * 30);
		searchParams.put("time", searchTime);
		Page<Map<String, Object>> pageable = new Page<Map<String, Object>>(pageNumber, pageSize);
		Page<Map<String, Object>> page = userLuckdrawService.searchJoinActivityUser(searchParams, pageable);
		ResultPage resultPage = new ResultPage(page);
		resultPage = new ResultPage(page);
		if (resultPage.getList().size() > 0) {
			for (Map<String, Object> map : (List<Map<String, Object>>) resultPage.getList()) {
				if (map.get("city") != null) {
					Dictionary dictionary = dictionaryService.findDictByCode("D0001", map.get("city").toString());
					map.put("city", dictionary.getName());
				} else {
					map.put("city", "");
				}
				// 統計用戶活躍度
				int count = userLuckdrawService.getCountUserLuckdrawByTime(!StringUtils.isEmpty(marketName) ? Long.valueOf(map.get("id").toString()) : null, Long.valueOf(map.get("userid").toString()), null);
				map.put("liveness", count);
				// 处理手机号码
				map.put("mobile", map.get("mobile") == null ? "" : WebUtils.hideMobile(map.get("mobile").toString()));
			}
		}
		return LcbJson.toJSONString(resultPage);
	}

}
