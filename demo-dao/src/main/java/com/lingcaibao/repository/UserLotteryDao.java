package com.lingcaibao.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.lingcaibao.plugin.page.Page;

import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.UserLottery;

@Repository("userLotteryDao")
public interface UserLotteryDao {

	UserLottery get(Long id);

	List<UserLottery> search(Map<String, Object> parameters);
	
	List<UserLottery> searchAndDrawId(Long marketId);

	List<UserLottery> searchAndDrawIdPage(Long marketId);
	
	List<Long> searchNoDrawId();

	Page<UserLottery> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page pageable);

	void insert(UserLottery userLottery);

	void delete(Long id);

	void update(UserLottery userLottery);
	
	public void updateDrawIdByChnnelOrder(String channelOrder, Long drawId, Long id);

	public void updateDrawIdByChnnelOrderUserId(String channelOrder, Long drawId, Long userId);

	Page<Map<String, Object>> getMyLottery(@Param("searchFields") Map<String, Object> searchParams, Page<Map<String, Object>> pageable);
	
	/**
	 * 彩票福利
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	Page<Map<String, Object>> getH5MyLottery(@Param("searchFields") Map<String, Object> searchParams, Page<Map<String, Object>> pageable);
	
	public Map<String, Object> getLotteryContent(@Param("id") Long id);
	
	//获取当月的彩票资产
	BigDecimal getUserLottertByMonth(Long id);
	//获取彩票资产
	Map<String,Object> getLotteryMoney(Long id);

	Map<String, Object> getMyLotteryDetail(@Param("searchFields") Map<String,Object>searchParams);

	/**
	 * 获取详细彩票详情
	 * @param searchParams
	 * @return
	 */
	Map<String, Object> getH5MyLotteryDetail(@Param("searchFields") Map<String,Object>searchParams);
	
	Long getLotteryIdByDrawId(Long drawId);
	
    /**
     * 统计互动次数
     *
     * @param map
     * @return
     */
    int countInteraction(@Param("searchFields") Map<String, Object> map);

	String getPrizeBallByIssueNo(@Param("issueNo") String issueNo);
	Map<String, Object> getUserPrizeByUserLotteryId(@Param("userLotteryId") Long userLotteryId);
}
