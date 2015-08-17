package com.lingcaibao.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.UserLuckdraw;
import com.lingcaibao.plugin.page.Page;

@Repository("userLuckdrawDao")
public interface UserLuckdrawDao {

	UserLuckdraw get(Long id);

	List<UserLuckdraw> search(Map<String, Object> parameters);

	List<UserLuckdraw> searchMiUser(Long marketId, Long userId, Integer prizelevel);
	
	Page<UserLuckdraw> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page<UserLuckdraw> pageable);

	List<Map<String, Object>> searchPageByMarketType(@Param("searchFields") Map<String, Object> searchParams);

	List<Map<String, Object>> searchPageByMarketId(@Param("searchFields") Map<String, Object> searchParams);

	void insert(UserLuckdraw userLuckdraw);

	void delete(Long id);

	void update(UserLuckdraw userLuckdraw);

	List<UserLuckdraw> getNewestDraw(@Param("userid") Long userid, @Param("marketid") Long marketid,
			@Param("top") int top);

	/**
	 * 用户管理模块 客户维护提醒
	 * 
	 * @param searchParams
	 *            用户姓名 和 活动名称
	 * @param pageRequest
	 * @return
	 */
	Page<Map<String, Object>> searchCustomer(@Param("searchFields") Map<String, Object> searchParams,
			Page<Map<String, Object>> pageRequest);

	/**
	 * 获取用户参与活动的次数
	 * 
	 * @param userid
	 * @param marketid
	 * @return
	 */
	int getUserVisitNum(@Param("userid") Long userid, @Param("marketid") Long marketid);

	/**
	 * 根据时间限制,获取用户参与活动的次数
	 * 
	 * @param userid
	 * @param marketid
	 * @return
	 */
	int getUserVisitNumByTime(@Param("userid") Long userid, @Param("marketid") Long marketid,
			@Param("stime") String stime, @Param("etime") String etime);

	/**
	 * 活动互动的总人数
	 * 
	 * @param userid
	 * @param marketid
	 * @param stime
	 * @param etime
	 * @return
	 */
	int getInteractionPersonCount(@Param("marketid") Long marketid, @Param("stime") Date stime,
			@Param("etime") Date etime);

	/**
	 * 热门活动
	 * 
	 * @param top
	 * @return
	 */
	List<Map<String, Object>> getHotActivities(@Param("top") int top);

	/**
	 * 根据计划id或用户id 统计参与活动的互动次数
	 */
	int getCountUserLuckdraw(@Param("marketid") Long marketid, @Param("userid") Long userid);

	/**
	 * 根据计划id或用户id 统计参与活动的互动次数
	 */
	public int getCountUserLuckdrawByTime(@Param("marketid") Long marketid, @Param("userid") Long userid,
			@Param("time") Date time);

	/**
	 * 用户活动列表
	 * 
	 * @return
	 */
	Page<Map<String, Object>> searchMarketPrizePage(@Param("searchFields") Map<String, Object> searchParams,
			Page<Map<String, Object>> pageRequest);
	
	/**
	 * 查询个人福利列表
	 * 
	 * @return
	 */
	Page<Map<String, Object>> searchH5MarketPrizePage(@Param("searchFields") Map<String, Object> searchParams,
			Page<Map<String, Object>> pageRequest);

	/**
	 * 查询用户活动详情
	 */
	Map<String, Object> searchMarketPrizeDetial(@Param("userid") Long userid, @Param("prizeid") Long prizeid);

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	Page<Map<String, Object>> searchJoinActivityUser(@Param("searchFields") Map<String, Object> searchParams,
			Page<Map<String, Object>> pageRequest);

	/**
	 * 用户互动
	 * 
	 * @param marketId
	 * @return
	 */
	List<Map<String, Object>> getUserListCount(Long marketId);

	/**
	 * 活动 每天用户量统计
	 * 
	 * @param searchParams
	 * @return
	 */
	List<Map<String, Object>> getUserCountByDay(@Param("searchFields") Map<String, Object> searchParams);

	/**
	 * 活动 每周用户量统计
	 * 
	 * @param searchParams
	 * @return
	 */
	List<Map<String, Object>> getUserCountByWeek(@Param("searchFields") Map<String, Object> searchParams);

	/**
	 * 获取活动开始时间的周数
	 * 
	 * @param stime
	 * @return
	 */
	Integer getMarketStimeWeed(Date stime);

	/**
	 * 活动 每月用户量统计
	 * 
	 * @param searchParams
	 * @return
	 */
	List<Map<String, Object>> getUserCountByMonth(@Param("searchFields") Map<String, Object> searchParams);

	/**
	 * 获取活动每天 奖品数量 奖品金额 列表
	 * 
	 * @param searchParams
	 * @return
	 */
	List<Map<String, Object>> getAllPrizeMoneyByDay(@Param("searchFields") Map<String, Object> searchParams,
			String prizecode);

	/**
	 * 获取活动每周 奖品数量 奖品金额 列表
	 * 
	 * @param searchParams
	 * @return
	 */
	List<Map<String, Object>> getAllPrizeMoneyByWeek(@Param("searchFields") Map<String, Object> searchParams,
			String prizecode);

	/**
	 * 获取活动每月 奖品数量 奖品金额 列表
	 * 
	 * @param searchParams
	 * @return
	 */
	List<Map<String, Object>> getAllPrizeMoneyByMonth(@Param("searchFields") Map<String, Object> searchParams,
			String prizecode);

	/**
	 * 根据活动ID获取接入类型的数量
	 * 
	 * @param marketId
	 * @return
	 */
	List<Map<String, Object>> getIntypeCount(Long marketId);

	/**
	 * 根据活动ID获取 活动用户手机号
	 * 
	 * @param marketId
	 * @return
	 */
	List<Map<String, Object>> getUserMobileCount(Long marketId);

	int getCountByMarketid(Long marketId);

	Map<String, Object> getUserShareInfo(Long drawId);

	int countUserMarketByTime(@Param("userid") Long userid, @Param("marketId") Long marketId,
			@Param("tType") String tType);
}
