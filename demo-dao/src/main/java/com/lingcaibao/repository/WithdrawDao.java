package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lingcaibao.plugin.page.Page;

import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.Withdraw;

@Repository("withdrawDao")
public interface WithdrawDao {

	Withdraw get(Long id);

	List<Map<String,Object>> search(Map<String, Object> parameters);
	
	List<Withdraw> getUntreatedList(Map<String, Object> parameters);

	Page<Map<String,Object>> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page<Map<String,Object>>pageable);

	void insert(Withdraw withdraw);

	void delete(Long id);

	void update(Withdraw withdraw);

	Map<String,Object> withdrawDetail(Long id);

}
