package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.entity.Bill;
@Repository("billDao")
public interface BillDao
{
	Bill get(Long id);

	Bill getBillByOrderid(@Param("orderid") Long orderid);
	
	Bill getBillByChannel(@Param("orderid") Long orderid, @Param("billchannel") int billchannel);
	
	void deleteBillByChannel(@Param("orderid") Long orderid, @Param("billchannel") int billchannel);

	List<Bill> search(Map<String,Object> parameters);

	Page<Bill> searchPage(@Param("searchFields") Map<String,Object> searchParams, Page<Bill> page);

	void insert(Bill bill);

	void delete(Long id);

	void update(Bill bill);

	Map<String ,Object> getUserBillDetial(Long id);

	/**
	 * 获取代理商交易记录
	 * @param searchParams
	 * @param page
	 * @return
	 */
	Page searchProxyBillPage(@Param("searchFields") Map<String, Object> searchParams, Page page);
}
