package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.entity.UserRole;

@Repository("userRoleDao")
public interface UserRoleDao {

	UserRole get(Long id);

	List<UserRole> search(Map<String, Object> parameters);

	Page<UserRole> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page pageable);

	void insert(UserRole userRole);

	void delete(Long id);

	void update(UserRole userRole);

}
