package com.lingcaibao.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lingcaibao.plugin.page.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springside.modules.utils.DateProvider;




import com.lingcaibao.entity.UserPrize;
import com.lingcaibao.repository.UserPrizeDao;

/**
* @Title: 
* @Description: 
* @Author jhe   
* @Date 2013 - 2015
* @Version V1.0
* @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
*/
// Spring Service Bean的标识.
@Service
@Transactional
public class UserPrizeService {

	private static Logger logger = LoggerFactory
			.getLogger(UserPrizeService.class);

	@Autowired
	private UserPrizeDao userPrizeDao;

	//private DateProvider dateProvider = DateProvider.DEFAULT;

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 *            查询条件
	 * @param pageable
	 *            分页参数
	 * @return
	 */
	public Page<UserPrize> searchPage(Map<String, Object> searchParams,
			Page<UserPrize> pageable) {
		return userPrizeDao.searchPage(searchParams, pageable);
	}
	public Map<String,Object> getUserPrizeById(long id)
	{
		return userPrizeDao.getUserPrizeById(id);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<UserPrize> search(Map<String, Object> searchParas) {
		return userPrizeDao.search(searchParas);
	}

	
	public UserPrize get(Long id) {
		return userPrizeDao.get(id);
	}

	public void insert(UserPrize userPrize) {
		//userPrize.setCreateTime(dateProvider.getDate());
		userPrizeDao.insert(userPrize);
	}
	
	public void update(UserPrize userPrize) {
		//userPrize.setModifyTime(dateProvider.getDate());
		userPrizeDao.update(userPrize);
	}

	public void delete(Long id) {
		userPrizeDao.delete(id);
	}

	//public void setDateProvider(DateProvider dateProvider) {
	//	this.dateProvider = dateProvider;
	//}
	
	public Map<String, Object> getPrizeContent(Long userLotteryId){
		return userPrizeDao.getPrizeContent(userLotteryId);
	}
	public Page<Map<String, Object>> getUserPrizeByMonth( Page pageable,Map<String, Object> searchParas){
		return userPrizeDao.getUserPrizeByMonth(pageable,searchParas);
	}
	public Page<Map<String, Object>> getUserPrizeByYear(Page pageable,Map<String, Object> searchParas){
		return userPrizeDao.getUserPrizeByYear(pageable,searchParas);
	}
	
	public BigDecimal getUserPrizeMoney(Long id){
		return userPrizeDao.getUserPrizeMoney(id);
	}	
}
