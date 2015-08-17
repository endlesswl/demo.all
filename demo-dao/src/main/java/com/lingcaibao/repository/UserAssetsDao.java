package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.stereotype.Repository;
import com.lingcaibao.entity.UserAssets;
@Repository("userAssetsDao")
public interface UserAssetsDao
{
	UserAssets get(Long id);

	List<UserAssets> search(Map<String,Object> parameters);

	Page<UserAssets> searchPage(@Param("searchFields") Map<String,Object> searchParams, Page<UserAssets> pageable);

	void insert(UserAssets userAssets);

	void delete(Long id);

	void update(UserAssets userAssets);

	Page<Map<String,Object>> getMyAssets(@Param("searchFields") Map<String,Object> searchParas, Page<Map<String,Object>> pageable);
	
	/**
	 * 我的福利
	 * @param searchParas
	 * @param pageable
	 * @return
	 */
	Page<Map<String,Object>> getH5MyAssets(@Param("searchFields") Map<String,Object> searchParas, Page<Map<String,Object>> pageable);

	UserAssets getByDrawid(Long drawid);

	UserAssets getAssetsByOrderid(String orderid);
}
