package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.lingcaibao.entity.UserShare;

@Repository("userShareDao")
public interface UserShareDao {

	UserShare get(Long id);

	List<UserShare> search(Map<String, Object> parameters);

	Page<UserShare> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(UserShare userShare);

	void delete(Long id);

	void update(UserShare userShare);
	
	int getUserSharePersonsToday(@Param("marketid") Long marketid, @Param("shareuserid") Long shareuserid);
	
	int getUserSharePersons(@Param("marketid") Long marketid, @Param("shareuserid") Long shareuserid, @Param("stime") String stime, @Param("etime") String etime);

}
