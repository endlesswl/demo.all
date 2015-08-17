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

import com.lingcaibao.entity.UserRole;
import com.lingcaibao.repository.UserRoleDao;
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
public class UserRoleService {

	private static Logger logger = LoggerFactory
			.getLogger(UserRoleService.class);

	@Autowired
	private UserRoleDao userRoleDao;

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
	public Page<UserRole> searchPage(Map<String, Object> searchParams,
			Page pageable) {
		return userRoleDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<UserRole> search(Map<String, Object> searchParas) {
		return userRoleDao.search(searchParas);
	}

	
	public UserRole get(Long id) {
		return userRoleDao.get(id);
	}

	public void insert(UserRole userRole) {
		//userRole.setCreateTime(dateProvider.getDate());
		userRoleDao.insert(userRole);
	}
	
	public void update(UserRole userRole) {
		//userRole.setModifyTime(dateProvider.getDate());
		userRoleDao.update(userRole);
	}

	public void delete(Long id) {
		userRoleDao.delete(id);
	}

	//public void setDateProvider(DateProvider dateProvider) {
	//	this.dateProvider = dateProvider;
	//}
}
