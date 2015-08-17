package com.lingcaibao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lingcaibao.entity.Bill;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.repository.BillDao;
/**
 * <p>标题：资金流水记录 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月24日 下午3:53:35</p>
 * <p>类全名：com.lingcaibao.service.BillService</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Service
@Transactional
public class BillService
{
	private static Logger	logger	= LoggerFactory.getLogger(BillService.class);
	@Autowired
	private BillDao			billDao;

	/**
	 * 分页查询
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Bill> searchPage(Map<String,Object> searchParams, Page<Bill> pageable)
	{
		return billDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * @param searchParas
	 * @return
	 */
	public List<Bill> search(Map<String,Object> searchParas)
	{
		return billDao.search(searchParas);
	}

	public Bill get(Long id)
	{
		return billDao.get(id);
	}

	public Bill getBillByOrderid(Long orderid)
	{
		return billDao.getBillByOrderid(orderid);
	}

	public Bill getBillByChannel(Long orderid, int billchannel)
	{
		return billDao.getBillByChannel(orderid, billchannel);
	}
	
	public void deleteBillByChannel(Long orderid, int billchannel)
	{
		billDao.deleteBillByChannel(orderid, billchannel);
	}

	public void insert(Bill bill)
	{
		bill.setCreatetime(new Date().getTime());
		billDao.insert(bill);
	}

	public void update(Bill bill)
	{
		bill.setModifytime(new Date().getTime());
		billDao.update(bill);
	}

	public void delete(Long id)
	{
		billDao.delete(id);
	}

	public Map<String,Object> getUserBillDetial(Long id)
	{
		return billDao.getUserBillDetial(id);
	}

	/**
	 * 获取代理商交易记录
	 * @param searchParams
	 * @param page
	 * @return
	 */
	public Page searchProxyBillPage(Map<String,Object> searchParams, Page page)
	{
		return billDao.searchProxyBillPage(searchParams, page);
	}
}
