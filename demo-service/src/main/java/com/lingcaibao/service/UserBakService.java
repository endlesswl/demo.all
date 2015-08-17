package com.lingcaibao.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lingcaibao.plugin.page.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springside.modules.utils.DateProvider;

import com.lingcaibao.entity.UserBak;
import com.lingcaibao.repository.UserBakDao;
import com.lingcaibao.service.ServiceException;

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
public class UserBakService {

	private static Logger logger = LoggerFactory
			.getLogger(UserBakService.class);

	@Autowired
	private UserBakDao userBakDao;

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
	public Page<UserBak> searchPage(Map<String, Object> searchParams,
			Page pageable) {
		return userBakDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<UserBak> search(Map<String, Object> searchParas) {
		return userBakDao.search(searchParas);
	}

	
	public UserBak get(Long id) {
		return userBakDao.get(id);
	}

	public void insert(UserBak userBak) {
		//userBak.setCreateTime(dateProvider.getDate());
		userBakDao.insert(userBak);
	}
	
	public void update(UserBak userBak) {
		//userBak.setModifyTime(dateProvider.getDate());
		userBakDao.update(userBak);
	}

	public void delete(Long id) {
		userBakDao.delete(id);
	}

	//public void setDateProvider(DateProvider dateProvider) {
	//	this.dateProvider = dateProvider;
	//}
}
