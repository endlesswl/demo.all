package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.UserBinds;

@Repository("userBindsDao")
public interface UserBindsDao {

	UserBinds get(Long id);

	List<UserBinds> search(Map<String, Object> parameters);

	Page<UserBinds> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page pageable);

	void insert(UserBinds userBinds);

	void delete(Long id);

	void update(UserBinds userBinds);

}
