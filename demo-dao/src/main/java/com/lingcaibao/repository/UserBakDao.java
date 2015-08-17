package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.UserBak;

@Repository("userBakDao")
public interface UserBakDao {

	UserBak get(Long id);

	List<UserBak> search(Map<String, Object> parameters);

	Page<UserBak> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page pageable);

	void insert(UserBak userBak);

	void delete(Long id);

	void update(UserBak userBak);

}
