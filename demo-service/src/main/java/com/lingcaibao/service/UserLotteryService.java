package com.lingcaibao.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.lingcaibao.cache.memcached.MarketMemcachedClient;
import com.lingcaibao.entity.UserLottery;
import com.lingcaibao.exception.LotteryException;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.repository.UserLotteryDao;
import com.lingcaibao.service.cache.RedisListOpsService;
import com.lingcaibao.util.LcbJson;
import com.palm.commom.plugin.paginator.mybatis.page.Common;
import com.palm.commom.uitl.MD5;

/**
 * @Title:
 * @Description:
 * @Author jhe
 * @Date 2015
 * @Version V1.0
 * @Copyright © 2015 零彩宝. All rights reserved.
 */
// Spring Service Bean的标识.
@Service
@Transactional
public class UserLotteryService {

	private static Logger logger = LoggerFactory.getLogger(UserLotteryService.class);

	@Autowired
	private UserLotteryDao userLotteryDao;

	@Autowired
	private MarketMemcachedClient memcachedClient;

	@Autowired
	private UserService userService;

	@Autowired
	private UserLuckdrawService userLuckdrawService;

	@Autowired
	private RedisListOpsService redisListOpsService;

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock readLock = lock.readLock();

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 *            查询条件
	 * @param pageable
	 *            分页参数
	 * @return
	 */
	public Page<UserLottery> searchPage(Map<String, Object> searchParams, Page pageable) {
		return userLotteryDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<UserLottery> search(Map<String, Object> searchParas) {
		return userLotteryDao.search(searchParas);
	}

	public List<UserLottery> searchAndDrawId(Long marketId) {
		return userLotteryDao.searchAndDrawId(marketId);
	}
	
	public List<UserLottery> searchAndDrawIdPage(Long marketId) {
		return userLotteryDao.searchAndDrawIdPage(marketId);
	}
	
	public List<Long> searchNoDrawId() {
		return userLotteryDao.searchNoDrawId();
	}

	public UserLottery get(Long id) {
		return userLotteryDao.get(id);
	}

	public void insert(UserLottery userLottery) {
		userLotteryDao.insert(userLottery);
	}

	public void update(UserLottery userLottery) {
		userLotteryDao.update(userLottery);
	}

	public void updateDrawIdByChnnelOrder(String channelOrder, Long drawId, Long id) {
		userLotteryDao.updateDrawIdByChnnelOrder(channelOrder, drawId, id);
	}
	
	public void updateDrawIdByChnnelOrderUserId(String channelOrder, Long drawId, Long UserId) {
		userLotteryDao.updateDrawIdByChnnelOrderUserId(channelOrder, drawId, UserId);
	}

	public void delete(Long id) {
		userLotteryDao.delete(id);
	}

	public Page<Map<String, Object>> getMyLottery(@Param("searchFields") Map<String, Object> searchParams, Page pageable) {
		return userLotteryDao.getMyLottery(searchParams, pageable);
	}

	/**
	 * 彩票福利
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Map<String, Object>> getH5MyLottery(@Param("searchFields") Map<String, Object> searchParams, Page pageable) {
		return userLotteryDao.getH5MyLottery(searchParams, pageable);
	}

	public Map<String, Object> getLotteryContent(Long id) {
		return userLotteryDao.getLotteryContent(id);
	}

	// 获取当月的彩票资产
	public BigDecimal getUserLottertByMonth(Long id) {

		return userLotteryDao.getUserLottertByMonth(id);
	}

	public Map<String, Object> getLotteryMoney(Long id) {

		return userLotteryDao.getLotteryMoney(id);
	}

	public Map<String, Object> getMyLotteryDetail(Map<String, Object> searchParams) {

		return userLotteryDao.getMyLotteryDetail(searchParams);
	}
	
	public Map<String, Object> getH5MyLotteryDetail(Map<String, Object> searchParams) {
		return userLotteryDao.getH5MyLotteryDetail(searchParams);
	}

	public Long getLotteryIdByDrawId(Long drawId) {
		return userLotteryDao.getLotteryIdByDrawId(drawId);
	}

	/**
	 * 限制IP
	 * 
	 * @param request
	 */
	public void versinIpAddress(HttpServletRequest request) throws LotteryException {
		String ip = Common.getIpAddr(request);
		String ipkey = MD5.Md5For16(ip);
		ip = memcachedClient.get(ipkey);
		if (null != ip) {
			throw new LotteryException("亲，很抱歉，您的网络访问受限，请联系管理员！微信服务号：lingcaibaowx", ExceptionCode.NORMAL);
		}
	}

	/**
	 * 校验客户端是否合法
	 * <p/>
	 * 1.校验客户端IP+UA，判断是否是同一个手机重复领取
	 * 
	 * @param request
	 */
	public void versinClient(String shorturl, HttpServletRequest request) throws LotteryException {
		String ua = request.getHeader("user-agent");
		String ip = Common.getIpAddr(request);
		String md5 = MD5.Md5For16(ua + ip + shorturl);
		Integer num = memcachedClient.get(md5);
		if (null != num && num > 10) {
			throw new LotteryException("亲，很抱歉，您领取过于频繁，请分享给更多朋友吧！", ExceptionCode.RECEIVE_COUNT_ERROE);
		} else {
			if (null == num) {
				num = 0;
			}
			memcachedClient.set(md5, 60 * 60 * 24, num + 1);
		}
	}
	/**
	 * 保存异常出票数据
	 * 
	 * @param drawId
	 *            抽奖ID
	 * @param userId
	 *            用户ID
	 * @param marketAppKey
	 *            渠道KEY
	 * @param mobile
	 *            用户名 手机号
	 * @param moneyType
	 *            面额
	 * @param gameId
	 *            彩票编号 SSQ
	 */
	public void saveErrorLottery(Long drawId, Long userId, Long marketId, Long prizeId, String marketAppKey,
			String mobile, String moneyType, String gameId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("drawId", drawId);
		jsonObject.put("userId", userId);
		jsonObject.put("marketId", marketId);
		jsonObject.put("prizeId", prizeId);
		jsonObject.put("channel", marketAppKey);
		jsonObject.put("mobile", mobile);
		jsonObject.put("moneyType", moneyType);
		jsonObject.put("gameId", gameId);
		redisListOpsService.in("LOTTERY_ERROR_LIST", LcbJson.toJSONString(jsonObject));
	}


	/**
	 * 获取错误出票列表
	 * 	
	 * @return
	 */
	public List<String> getErrorLottery() {
		return redisListOpsService.range("LOTTERY_ERROR_LIST", 0, redisListOpsService.length("LOTTERY_ERROR_LIST")
				.intValue());
	}

	public String getPrizeBallByIssueNo(String issueNo) {
		return userLotteryDao.getPrizeBallByIssueNo(issueNo);
	}
	
	public Map<String, Object> getUserPrizeByUserLotteryId(Long userLotteryId) {
		return userLotteryDao.getUserPrizeByUserLotteryId(userLotteryId);
	}
}
