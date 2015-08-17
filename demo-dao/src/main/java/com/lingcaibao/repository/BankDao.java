package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.Bank;

@Repository("bankDao")
public interface BankDao {

	Bank get(Long id);
	List<Bank> search(Map<String, Object> parameters);
	
	/**
	 * 获取用户银行卡信息列表
	 * @param userid
	 * @return
	 */
	List<Map<String,Object>> getUserBankList(@Param("userid") Long userid);
	
	Page<Bank> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page<Bank> pageRequest);
	
	void insert(Bank bank);

	void delete(Long id);

	void update(Bank bank);
	
	/**
	 * 废弃银行卡
	 * @param parameters
	 */
	void discardBank(Map<String, Object> parameters);

}
