package com.lingcaibao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lingcaibao.plugin.page.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lingcaibao.entity.Sms;
import com.lingcaibao.repository.SmsDao;
import com.lingcaibao.service.ServiceException;
import com.palm.commom.uitl.DateFormatUtil;

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
public class SmsService {

	private static Logger logger = LoggerFactory
			.getLogger(SmsService.class);

	@Autowired
	private SmsDao smsDao;

	//private DateProvider dateProvider = DateProvider.DEFAULT;

	/**
	 * 分页查询
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Sms> searchPage(Map<String, Object> searchParams,
			Page pageable) {
		return smsDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * @param searchParas
	 * @return
	 */
	public List<Sms> search(Map<String, Object> searchParas) {
		return smsDao.search(searchParas);
	}

	
	public Sms get(Long id) {
		return smsDao.get(id);
	}

	public void insert(Sms sms) {
		sms.setCreatetime(DateFormatUtil.toDate(DateFormatUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
		smsDao.insert(sms);
	}
	
	public void update(Sms sms) {
		smsDao.update(sms);
	}

	public void delete(Long id) {
		smsDao.delete(id);
	}

	/**
	 * 获取该手机号最近一小时的发送列表
	 * @param mobile
	 * @return
	 */
	public List<Sms> getLastHourList(String mobile)
	{
		return smsDao.getLastHourList(mobile);
	}
}
