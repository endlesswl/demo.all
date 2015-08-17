package com.lingcaibao.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.UserPrize;
import com.lingcaibao.plugin.page.Page;

@Repository("userPrizeDao")
public interface UserPrizeDao {

	UserPrize get(Long id);

	List<UserPrize> search(Map<String, Object> parameters);

	Page<UserPrize> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page<UserPrize> pageable);
	
    void insert(UserPrize userPrize);

	void delete(Long id);

	void update(UserPrize userPrize);
	
	/**福利详情页获取中奖信息
	 * @param userLotteryId
	 * @return
	 */
	Map<String, Object> getPrizeContent(@Param("userLotteryId") Long userLotteryId);
	Map<String,Object> getUserPrizeById(Long id);

	public Page<Map<String, Object>> getUserPrizeByMonth(Page pageable,@Param("searchParas")Map<String, Object> searchParas);
	public Page<Map<String, Object>> getUserPrizeByYear(Page pageable,@Param("searchParas")Map<String, Object> searchParas);
	public BigDecimal getUserPrizeMoney(@Param("id") Long id);
}
