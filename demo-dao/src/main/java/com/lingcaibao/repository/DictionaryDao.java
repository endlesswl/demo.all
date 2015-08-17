package com.lingcaibao.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.stereotype.Repository;
import com.lingcaibao.entity.Dictionary;
@Repository("dictionaryDao")
public interface DictionaryDao
{
	Dictionary get(Long id);

	List<Dictionary> findBank();

	List<Dictionary> search(Map<String,Object> parameters);

	Page<Dictionary> searchPage(@Param("searchFields") Map<String,Object> searchParams, Page pageRequest);

	void insert(Dictionary dictionary);

	void delete(Long id);

	void update(Dictionary dictionary);

	List<Dictionary> getDictContent(@Param("dictno") String dictno, @Param("level") int level, @Param("parentcode") String parentcode);
}
