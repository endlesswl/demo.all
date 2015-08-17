package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.Sms;

@Repository("smsDao")
public interface SmsDao {

	Sms get(Long id);

	List<Sms> search(Map<String, Object> parameters);
	
	List<Sms> getLastHourList(@Param("mobile") String mobile);

	Page<Sms> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page<Sms> pageable);

	void insert(Sms sms);

	void delete(Long id);

	void update(Sms sms);

}
