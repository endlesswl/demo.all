package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.lingcaibao.entity.Role;

@Repository("roleDao")
public interface RoleDao {

	Role get(Long id);

	List<Role> search(Map<String, Object> parameters);

	Page<Role> searchPage(@Param("searchFields") Map<String, Object> searchParams, Page pageable);

	void insert(Role role);

	void delete(Long id);

	void update(Role role);
	
	List<Map<String, Object>> findPermissionLinks(Long roleId);

}
