package com.lingcaibao.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lingcaibao.entity.UserLuckdraw;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.repository.UserLuckdrawDao;
import com.lingcaibao.statuscode.LuckDrawInTypeEnum;
import com.lingcaibao.util.MathUtil;
import com.palm.commom.plugin.paginator.mybatis.page.Common;

/**
 * @Title: 用户抽奖服务类
 * @Description:
 * @Author jhe
 * @Date 2015
 * @Version V1.0
 * @Copyright © 2015 零彩宝. All rights reserved.
 */
// Spring Service Bean的标识.
@Service
@Transactional
public class UserLuckdrawService {
	private static Logger logger = LoggerFactory.getLogger(UserLuckdrawService.class);
	@Autowired
	private UserLuckdrawDao userLuckdrawDao;
	@Autowired
	private UserLotteryService userLotteryService;

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<UserLuckdraw> searchPage(Map<String, Object> searchParams, Page<UserLuckdraw> pageable) {
		return userLuckdrawDao.searchPage(searchParams, pageable);
	}

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public List<Map<String, Object>> searchPageByMarketType(Map<String, Object> searchParams) {
		return userLuckdrawDao.searchPageByMarketType(searchParams);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 * @return
	 */
	public List<UserLuckdraw> search(Map<String, Object> searchParas) {
		return userLuckdrawDao.search(searchParas);
	}
	
	public List<UserLuckdraw> searchMiUser(Long marketId, Long userId, Integer prizelevel) {
		return userLuckdrawDao.searchMiUser(marketId, userId, prizelevel);
	}

	public UserLuckdraw get(Long id) {
		return userLuckdrawDao.get(id);
	}

	public void insert(UserLuckdraw userLuckdraw) {
		userLuckdraw.setCreatetime(new Date());
		userLuckdrawDao.insert(userLuckdraw);
	}

	public UserLuckdraw save(Long userId, Long marketId, Long prizeId, String prizeName, HttpServletRequest request) {
		UserLuckdraw uld = new UserLuckdraw();
		uld.setIp(Common.getIpAddr(request));
		uld.setIntype(LuckDrawInTypeEnum.getType(request.getParameter("inType")));
		uld.setMarketid(marketId);
		uld.setPrizeid(prizeId);
		uld.setRemark(prizeName);
		uld.setUserid(userId);
		uld.setShorturl(request.getParameter("shorturl"));
		insert(uld);
		return uld;
	}

	public void update(UserLuckdraw userLuckdraw) {
		userLuckdrawDao.update(userLuckdraw);
	}

	public void delete(Long id) {
		userLuckdrawDao.delete(id);
	}

	public List<UserLuckdraw> getNewestDraw(Long userid, Long marketid, int top) {
		return userLuckdrawDao.getNewestDraw(userid, marketid, top);
	}

	/**
	 * 用户管理模块 客户维护提醒
	 * 
	 * @param searchParams
	 *            时间 用户id
	 * @param pageRequest
	 * @return
	 */
	public Page<Map<String, Object>> searchCustomer(@Param("searchFields") Map<String, Object> searchParams,
			Page<Map<String, Object>> pageRequest) {
		return userLuckdrawDao.searchCustomer(searchParams, pageRequest);
	}

	/**
	 * 获取用户参与活动的次数
	 * 
	 * @param userid
	 * @param marketid
	 * @return
	 */
	public int getUserVisitNum(Long userid, Long marketid) {
		return userLuckdrawDao.getUserVisitNum(userid, marketid);
	}

	/**
	 * 根据时间限制,获取用户参与活动的次数
	 * 
	 * @param userid
	 * @param marketid
	 * @return
	 */
	public int getUserVisitNumByTime(Long userid, Long marketid, String stime, String etime) {
		return userLuckdrawDao.getUserVisitNumByTime(userid, marketid, stime, etime);
	}

	/**
	 * 活动互动的总人数
	 * 
	 * @param userid
	 * @param marketid
	 * @param stime
	 * @param etime
	 * @return
	 */
	public int getInteractionPersonCount(Long marketid, Date stime, Date etime) {
		return userLuckdrawDao.getInteractionPersonCount(marketid, stime, etime);
	}

	/**
	 * 热门活动
	 * 
	 * @param top
	 * @return
	 */
	public List<Map<String, Object>> getHotActivities(int top) {
		return userLuckdrawDao.getHotActivities(top);
	}

	/**
	 * 根据计划id或用户id 统计参与活动的互动次数
	 */
	public int getCountUserLuckdraw(Long marketid, Long userid) {
		return userLuckdrawDao.getCountUserLuckdraw(marketid, userid);
	}

	/**
	 * 根据计划id或用户id 统计参与活动的互动次数
	 */
	public int getCountUserLuckdrawByTime(Long marketid, Long userid, Date time) {
		return userLuckdrawDao.getCountUserLuckdrawByTime(marketid, userid, time);
	}

	/**
	 * 用户活动列表
	 * 
	 * @return
	 */
	public Page<Map<String, Object>> searchMarketPrizePage(@Param("searchFields") Map<String, Object> searchParams,
			Page<Map<String, Object>> pageRequest) {
		return userLuckdrawDao.searchMarketPrizePage(searchParams, pageRequest);
	}
	
	/**
	 * 查询个人福利列表
	 * 
	 * @return
	 */
	public Page<Map<String, Object>> searchH5MarketPrizePage(@Param("searchFields") Map<String, Object> searchParams,
			Page<Map<String, Object>> pageRequest) {
		return userLuckdrawDao.searchH5MarketPrizePage(searchParams, pageRequest);
	}


	/**
	 * 查询用户活动详情
	 * 
	 */
	public Map<String, Object> searchMarketPrizeDetial(Long userid, Long prizeid) {
		return userLuckdrawDao.searchMarketPrizeDetial(userid, prizeid);
	}

	/**
	 * 用户管理模块 用户列表
	 * 
	 * @param searchParams
	 *            时间 用户id
	 * @param pageRequest
	 * @return
	 */
	public Page<Map<String, Object>> searchJoinActivityUser(@Param("searchFields") Map<String, Object> searchParams,
			Page<Map<String, Object>> pageRequest) {
		return userLuckdrawDao.searchJoinActivityUser(searchParams, pageRequest);
	}

	/**
	 * 用户互动
	 * 
	 * @param pageRequest
	 * @return
	 */
	public List<Map<String, Object>> getUserListCount(Long marketId) {
		return userLuckdrawDao.getUserListCount(marketId);
	}

	/**
	 * 获取参数用户活动的报表信息
	 * 
	 * @param marketId
	 * @param timeType
	 * @param stime
	 * @param etime
	 * @return
	 */
	public Map<String, Object> getUserCountReport(Long marketId, String timeType, String stime, String etime,
			Date marketStime) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("marketId", marketId);
		searchParams.put("timeType", timeType);
		searchParams.put("stime", stime);
		searchParams.put("etime", etime);
		List<Map<String, Object>> list = Lists.newArrayList();
		if ("d".equals(timeType)) {
			list = getUserCountByDay(searchParams);
		} else if ("w".equals(timeType)) {
			Integer weekNum = getMarketStimeWeed(marketStime);
			weekNum = StringUtils.isEmpty(weekNum) ? 0 : weekNum;
			searchParams.put("marketStime", weekNum);
			list = getUserCountByWeek(searchParams);
		} else if ("m".equals(timeType)) {
			list = getUserCountByMonth(searchParams);
		}

		List<Object> xList = Lists.newArrayList();
		List<Object> yList = Lists.newArrayList();

		for (Map<String, Object> map : list) {
			xList.add(map.get("time"));
			yList.add(map.get("count"));
		}

		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("xList", xList);
		resultMap.put("yList", yList);
		return resultMap;
	}


	/**
	 * 活动 每天用户量统计
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> getUserCountByDay(Map<String, Object> searchParams) {
		return userLuckdrawDao.getUserCountByDay(searchParams);
	}

	/**
	 * 活动 每周用户量统计
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> getUserCountByWeek(Map<String, Object> searchParams) {
		return userLuckdrawDao.getUserCountByWeek(searchParams);
	}

	/**
	 * 获取活动开始时间的周数
	 * 
	 * @param stime
	 * @return
	 */
	public Integer getMarketStimeWeed(Date stime) {
		return userLuckdrawDao.getMarketStimeWeed(stime);
	}

	/**
	 * 活动 每月用户量统计
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> getUserCountByMonth(Map<String, Object> searchParams) {
		return userLuckdrawDao.getUserCountByMonth(searchParams);
	}

	/**
	 * 获取活动每天 奖品数量 奖品金额 列表
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> getAllPrizeMoneyByDay(@Param("searchFields") Map<String, Object> searchParams,
			String prizecode) {
		logger.info("=== 开始获取获取活动每天 奖品数量 奖品金额 列表 ==");
		return userLuckdrawDao.getAllPrizeMoneyByDay(searchParams, prizecode);
	}

	/**
	 * 获取活动每周 奖品数量 奖品金额 列表
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> getAllPrizeMoneyByWeek(@Param("searchFields") Map<String, Object> searchParams,
			String prizecode) {
		return userLuckdrawDao.getAllPrizeMoneyByWeek(searchParams, prizecode);
	}

	/**
	 * 获取活动每月 奖品数量 奖品金额 列表
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> getAllPrizeMoneyByMonth(@Param("searchFields") Map<String, Object> searchParams,
			String prizecode) {
		return userLuckdrawDao.getAllPrizeMoneyByMonth(searchParams, prizecode);
	}

	/**
	 * 用户系统分布 报表
	 * 
	 * @param marketId
	 * @return
	 */
	public Map<String, Object> getSystemDistributionReport(Long marketId) {
		List<Map<String, Object>> intypes = getIntypeCount(marketId);
		List<Map<String, Object>> reCList = Lists.newArrayList();
		List<Map<String, Object>> rePList = Lists.newArrayList();
		Integer count = 0;
		for (Map<String, Object> intype : intypes) {
			String name = LuckDrawInTypeEnum.getDesc(intype.get("intype").toString());
			Integer co = Integer.valueOf(intype.get("count").toString());
			count += co;
			Map<String, Object> reCMap = Maps.newHashMap();
			reCMap.put("name", name);
			reCMap.put("value", co);
			reCList.add(reCMap);
		}

		for (Map<String, Object> intype : intypes) {
			String name = LuckDrawInTypeEnum.getDesc(intype.get("intype").toString());
			Integer co = Integer.valueOf(intype.get("count").toString());
			Map<String, Object> rePMap = Maps.newHashMap();
			rePMap.put("name", name);
			rePMap.put("value", MathUtil.percent(co, count));
			rePList.add(rePMap);
		}

		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("count", reCList);
		resultMap.put("percent", rePList);
		return resultMap;
	}

	/**
	 * 根据活动ID获取接入类型的数量
	 * 
	 * @param marketId
	 * @return
	 */
	public List<Map<String, Object>> getIntypeCount(Long marketId) {
		return userLuckdrawDao.getIntypeCount(marketId);
	}

	/**
	 * 根据活动ID获取 活动用户手机号
	 * 
	 * @param marketId
	 * @return
	 */
	public List<Map<String, Object>> getUserMobileCount(Long marketId) {
		return userLuckdrawDao.getUserMobileCount(marketId);
	}

	public List<Map<String, Object>> searchPageByMarketId(Map<String, Object> searchParams) {
		return userLuckdrawDao.searchPageByMarketId(searchParams);
	}

	public int getCountByMarketid(Long marketId) {
		return userLuckdrawDao.getCountByMarketid(marketId);
	}

	public Map<String, Object> getUserShareInfo(Long drawId) {
		return userLuckdrawDao.getUserShareInfo(drawId);
	}

	/**
	 * 查询用户领彩次数
	 * 
	 * @param userid
	 *            用户id
	 * @param marketId
	 *            计划ID
	 * @param tType
	 *            时间 （d:当前天；m：当前月；w：当前周；y：当前年）
	 * @return
	 */
	public int countUserMarketByTime(Long userid, Long marketId, String tType) {
		return userLuckdrawDao.countUserMarketByTime(userid, marketId, tType);
	}
}

class ValueComparator implements Comparator<String> {

	Map<String, Integer> base;

	public ValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}