package com.lingcaibao.service;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lingcaibao.plugin.page.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lingcaibao.entity.Bank;
import com.lingcaibao.repository.BankDao;
import com.lingcaibao.repository.UserDao;
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
public class BankService
{
	private static Logger	logger	= LoggerFactory.getLogger(BankService.class);
	@Autowired
	private UserDao			userDao;
	@Autowired
	private BankDao			bankDao;

	/**
	 * 分页查询
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Bank> searchPage(Map<String,Object> searchParams, Page<Bank> pageable)
	{
		logger.info("start ...........................searchPage{method}........");
		return bankDao.searchPage(searchParams, pageable);
	}

	/**
	 * 获取用户银行卡信息列表
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> getUserBankList(Long userid)
	{
		return bankDao.getUserBankList(userid);
	}

	public List<Bank> search(Map<String,Object> searchParas)
	{
		return bankDao.search(searchParas);
	}

	public Bank get(Long id)
	{
		return bankDao.get(id);
	}

	public void insert(Bank bank)
	{
		bankDao.insert(bank);
	}

	public void update(Bank bank)
	{
		bankDao.update(bank);
	}

	public void delete(Long id)
	{
		bankDao.delete(id);
	}

	/**
	 * 废弃银行卡
	 * @param params
	 */
	public void discardBank(Map<String,Object> params)
	{
		bankDao.discardBank(params);
	}
	
	/**
	 * 获取银行卡安全卡号
	 * @param cardno
	 * @return
	 */
	public static String getSafeCardNo(String cardno)
	{
		String safeno = null;
		if (StringUtils.isNotEmpty(cardno))
		{
			int len = cardno.length();
			if (len == 16)
			{
				safeno = cardno.substring(0, 4) + " **** **** " + cardno.substring(len - 4, len);
			} else
			{
				safeno = cardno.substring(0, 4) + " **** **** *** " + cardno.substring(len - 4, len);
			}
		}
		return safeno;
	}
}
