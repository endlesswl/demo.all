package com.lingcaibao.service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.nutz.http.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lingcaibao.conf.Conf;
import com.lingcaibao.util.CookieHelper;
import com.palm.commom.uitl.DateFormatUtil;
/**
 * <p>标题：用户行为数据服务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年8月3日 下午2:39:10</p>
 * <p>类全名：com.lingcaibao.service.UserBehaviorService</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Service
@Transactional
public class UserBehaviorService
{
	private static Logger	logger	= LoggerFactory.getLogger(UserBehaviorService.class);
	static private String	SPM_URL	= Conf.get("lingcai.spm_url");

	public boolean keepUserBehavior(String spmid, String inType, HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("---------- keep user behavior ---------:{}", spmid);
		if (StringUtils.isNotEmpty(spmid))
		{
			String url = SPM_URL;
			String userid = CookieHelper.getValue(request, "lcb_spm_user");
			if (StringUtils.isNotEmpty(userid))
			{
				Map<String,Object> paramMap = Maps.newHashMap();
				String createTime = DateFormatUtil.getMillisTime(DateFormatUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				paramMap.put("createTime", createTime);
				String channel = CookieHelper.getValue(request, "channelKey");
				paramMap.put("userId", userid);
				paramMap.put("spm", spmid);
				String platform = StringUtils.isEmpty(inType) ? "h5" : inType;
				paramMap.put("platform", platform);
				if (StringUtils.isNotEmpty(channel))
				{
					paramMap.put("channel", channel);
				}
				// 游客身份
				if (StringUtils.startsWith(userid, "nm"))
				{
					logger.info("---------- keep tourist behavior ---------:{}", userid);
					try
					{
						// 记录游客行为数据
						paramMap.put("loginFlag", "0");
						String result = Http.post(url, paramMap, 2000);
						logger.info("--------- result --------:{}", result);
						String spmsStr = CookieHelper.getValue(request, "lcb_user_spms");
						logger.info("-------- user spms -------:{}", spmsStr);
						List<Map<String,Object>> spmList = StringUtils.isEmpty(spmsStr) ? Lists.newArrayList() : Lists.newArrayList(JSON.parseObject(URLDecoder.decode(spmsStr, "utf-8"), List.class).iterator());
						logger.info("-------- spm list -------:{}", JSON.toJSONString(spmList));
						if (spmList.size() >= 20)
						{
							spmList.remove(0);
						}
						spmList.add(Maps.newHashMap(paramMap));
						CookieHelper.addCookie(response, "lcb_user_spms", URLEncoder.encode(JSON.toJSONString(spmList), "utf-8"));
						return true;
					} catch (Exception ex)
					{
						ex.printStackTrace();
						logger.error("-------- keep tourist behavior err -------:{}", ex.getMessage());
						return false;
					}
				}
				// 用户身份
				else
				{
					logger.info("---------- keep user behavior ---------:{}", userid);
					try
					{
						paramMap.put("loginFlag", "1");
						// 处理用户历史行为数据
						String spmsStr = CookieHelper.getValue(request, "lcb_user_spms");
						logger.info("-------- user spms -------:{}", spmsStr);
						List<Map<String,Object>> spmList = StringUtils.isEmpty(spmsStr) ? null : Lists.newArrayList(JSON.parseObject(URLDecoder.decode(spmsStr, "utf-8"), List.class).iterator());
						logger.info("-------- spm list -------:{}", JSON.toJSONString(spmList));
						if (spmList != null)
						{
							paramMap.put("browsHistory", JSON.toJSONString(spmList));
						}
						// 记录用户行为数据(本次 + 历史)
						String result = Http.post(url, paramMap, 2000);
						// 记录成功删除用户历史数据
						if (StringUtils.equals("success", result))
						{
							CookieHelper.removeCookies(request, response, "lcb_user_spms");
						}
						logger.info("--------- result --------:{}", result);
						return StringUtils.equals("success", result);
					} catch (Exception ex)
					{
						ex.printStackTrace();
						logger.error("-------- keep user behavior err -------:{}", ex.getMessage());
						return false;
					}
				}
			}
		}
		return false;
	}
}
