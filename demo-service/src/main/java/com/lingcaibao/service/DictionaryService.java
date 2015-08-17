package com.lingcaibao.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lingcaibao.plugin.page.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lingcaibao.cache.memcached.MarketMemcachedClient;
import com.lingcaibao.entity.Dictionary;
import com.lingcaibao.repository.DictionaryDao;
import com.lingcaibao.util.DictionaryContent;
import com.palm.commom.uitl.MemcacheKeyConstants;
import com.palm.commom.uitl.StringUtil;
/**
 * <p>标题：字典服务类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月13日 下午7:22:38</p>
 * <p>类全名：com.lingcaibao.service.DictionaryService</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Service
@Transactional
public class DictionaryService
{
	private static Logger			logger	= LoggerFactory.getLogger(DictionaryService.class);
	@Autowired
	private DictionaryDao			dictionaryDao;
	@Autowired
	private MarketMemcachedClient	memcachedClient;

	/**
	 * 分页查询
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Dictionary> searchPage(Map<String,Object> searchParams, Page<Dictionary> pageable)
	{
		return dictionaryDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * @param searchParas
	 * @return
	 */
	public List<Dictionary> search(Map<String,Object> searchParas)
	{
		return dictionaryDao.search(searchParas);
	}

	public Dictionary get(Long id)
	{
		return dictionaryDao.get(id);
	}

	public void insert(Dictionary dictionary)
	{
		dictionaryDao.insert(dictionary);
	}

	public void update(Dictionary dictionary)
	{
		dictionaryDao.update(dictionary);
	}

	public void delete(Long id)
	{
		dictionaryDao.delete(id);
	}

	/**
	 * 按照级次获取字典
	 * @param dictno
	 * @param level
	 * @return
	 */
	public List<Dictionary> getDictItems(String dictno)
	{
		List<Dictionary> dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + dictno);
		if (dicts == null)
		{
			init(dictno);
			dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + dictno);
		}
		return dicts;
	}

	/**
	 * 获取字典下级数据
	 * @param dictno
	 * @param parentcode
	 * @return
	 */
	public List<Dictionary> getDictSubItems(String dictno, String parentcode)
	{
		List<Dictionary> subDicts = null;
		List<Dictionary> dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + dictno);
		if (dicts == null)
		{
			init(dictno);
			dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + dictno);
		}
		Dictionary parentDict = findDictByCode(dicts, parentcode);
		if (parentDict != null)
		{
			subDicts = parentDict.getSubDicts();
		}
		return subDicts;
	}

	/**
	 * 按照字典项编码获取字典项目
	 * @param dictno
	 * @param code
	 * @return
	 */
	public Dictionary findDictByCode(String dictno, String code)
	{
		List<Dictionary> dicts = getDictItems(dictno);
		if (dicts != null && dicts.size() > 0)
		{
			return findDictByCode(dicts, code);
		}
		return null;
	}

	/**
	 * 按编码查找字典项
	 * @param dicts
	 * @param code
	 * @return
	 */
	private Dictionary findDictByCode(List<Dictionary> dicts, String code)
	{
		Dictionary target = null;
		if (dicts == null || StringUtils.isBlank(code))
		{
			return target;
		}
		for (int i = 0; i < dicts.size(); i++)
		{
			Dictionary dict = dicts.get(i);
			if (StringUtils.equals(dict.getCode(), code))
			{
				target = dict;
				break;
			} else
			{
				target = findDictByCode(dict.getSubDicts(), code);
				if (target != null)
				{
					break;
				}
			}
		}
		return target;
	}

	/**
	 * 按照字典项编码获取字典项目
	 * @param dictno
	 * @param code
	 * @return
	 */
	public Dictionary findDictByName(String dictno, String name)
	{
		List<Dictionary> dicts = getDictItems(dictno);
		if (dicts != null && dicts.size() > 0)
		{
			return findDictByName(dicts, name);
		}
		return null;
	}

	/**
	 * 按照名称查找字典项
	 * @param dicts
	 * @param name
	 * @return
	 */
	private Dictionary findDictByName(List<Dictionary> dicts, String name)
	{
		Dictionary target = null;
		if (dicts == null || StringUtils.isBlank(name))
		{
			return target;
		}
		for (int i = 0; i < dicts.size(); i++)
		{
			Dictionary dict = dicts.get(i);
			if (StringUtils.equals(dict.getName(), name))
			{
				target = dict;
				break;
			} else
			{
				target = findDictByName(dict.getSubDicts(), name);
				if (target != null)
				{
					break;
				}
			}
		}
		return target;
	}

	/**
	 * 获取字典项名称
	 * @param dictno
	 * @param code
	 * @return
	 */
	public String getDictItemName(String dictno, String code)
	{
		Dictionary dict = findDictByCode(dictno, code);
		return dict == null ? null : dict.getName();
	}
	
	/**
	 * 获取字典项名称
	 * @param dictno
	 * @param code
	 * @return
	 */
	public String getDictItemName(String code) {
		String dict = memcachedClient.get(DictionaryContent.DICTIONARY_KEY_CITY_CODE_NAME + code);
		if (StringUtil.isEmpty(dict)) {
			Map<String, Object> params = Maps.newHashMap();
			params.put("code", code);
			List<Dictionary> list = search(params);
			for (Dictionary dictionary : list) {
				memcachedClient.set(DictionaryContent.DICTIONARY_KEY_CITY_CODE_NAME + dictionary.getCode(), 0,
						dictionary.getName());
				dict = dictionary.getName();
			}
		}
		return dict;
	}
	
	/**
	 * 缓存城市相关编码
	 */
	public void initEasyDictByDictno(String dictno) {
		Map<String, Object> params = Maps.newHashMap();
		if (StringUtils.isNotBlank(dictno)) {
			params.put("dictno", dictno);
		}
		List<Dictionary> list = search(params);
		for (Dictionary dictionary : list) {
			memcachedClient.set(DictionaryContent.DICTIONARY_KEY_CITY_CODE_NAME + dictionary.getCode(), 0,
					dictionary.getName());
		}
	}

	public void init(String dictno)
	{
		Map<String,Object> params = Maps.newHashMap();
		if (StringUtils.isNotBlank(dictno))
		{
			params.put("dictno", dictno);
		}
		List<Dictionary> list = search(params);
		if (list != null)
		{
			Map<String,List<Dictionary>> dicts = Maps.newHashMap();
			Map<String,Dictionary> parentMap = Maps.newHashMap();
			for (int i = 0; i < list.size(); i++)
			{
				Dictionary thisDict = list.get(i);
				if (thisDict.getLastflag() != 1)
				{
					String parentKey = thisDict.getDictno() + "-" + thisDict.getLevel() + "-" + thisDict.getCode();
					parentMap.put(parentKey, thisDict);
				}
				if (thisDict.getLevel() == 1)
				{
					List<Dictionary> dictList = dicts.get(thisDict.getDictno());
					if (dictList == null)
					{
						dictList = new ArrayList<Dictionary>();
					}
					dictList.add(thisDict);
					dicts.put(thisDict.getDictno(), dictList);
				} else
				{
					String parentKey = thisDict.getDictno() + "-" + (thisDict.getLevel() - 1) + "-" + thisDict.getParentcode();
					Dictionary parentDict = parentMap.get(parentKey);
					if (parentDict != null)
					{
						parentDict.addSubDict(thisDict);
					}
				}
			}
			if (dicts.size() > 0)
			{
				Iterator<String> keys = dicts.keySet().iterator();
				while (keys.hasNext())
				{
					String key = keys.next();
					memcachedClient.set(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + key, 0, dicts.get(key));
					System.err.println("dictKey == " + key);
					System.err.println("dictData == " + JSON.toJSONString(dicts.get(key)));
				}
			}
		}
	}


	/**返回国家列表
	 * @return
	 */
	public List<Dictionary> findCountryList()
	{
		List<Dictionary> dicts = getDictItems(DictionaryContent.DICT_NO_NODE);
		return dicts;
		
	}

	/**根据国家编码返回省份列表
	 * @param parentcode
	 * @return
	 */
	public List<Dictionary> findProviceListByCountryCode(String parentcode)
	{
		List<Dictionary> subDicts = null;
		List<Dictionary> dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + DictionaryContent.DICT_NO_NODE);
		if (dicts == null)
		{
			init(DictionaryContent.DICT_NO_NODE);
			dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + DictionaryContent.DICT_NO_NODE);
		}
		Dictionary parentDict = findDictByCode(dicts, parentcode);
		if (parentDict != null)
		{
			subDicts = parentDict.getSubDicts();
		}
		return subDicts;
	}

	/**根据省份编码返回城市列表
	 * @param parentcode
	 * @return
	 */
	public List<Dictionary> findCityListByProvinceCode(String parentcode)
	{
		List<Dictionary> subDicts = null;
		List<Dictionary> dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + DictionaryContent.DICT_NO_NODE);
		if (dicts == null)
		{
			init(DictionaryContent.DICT_NO_NODE);
			dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + DictionaryContent.DICT_NO_NODE);
		}
		Dictionary parentDict = findDictByCode(dicts, parentcode);
		if (parentDict != null)
		{
			subDicts = parentDict.getSubDicts();
		}
		return subDicts;
	}

	/**根据城市编码返回地区列表
	 * @param parentcode
	 * @return
	 */
	public List<Dictionary> findAreaListByCityCode(String parentcode)
	{
		List<Dictionary> subDicts = null;
		List<Dictionary> dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + DictionaryContent.DICT_NO_NODE);
		if (dicts == null)
		{
			init(DictionaryContent.DICT_NO_NODE);
			dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + DictionaryContent.DICT_NO_NODE);
		}
		Dictionary parentDict = findDictByCode(dicts, parentcode);
		if (parentDict != null)
		{
			subDicts = parentDict.getSubDicts();
		}
		return subDicts;
	}

	public List<Dictionary> findBank()
	{
		List<Dictionary> dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + DictionaryContent.DICT_NO_BANK);
		if (dicts == null)
		{
			init(DictionaryContent.DICT_NO_BANK);
			dicts = memcachedClient.get(MemcacheKeyConstants.MARKET_DICTIONARY_KEY_PREFIX + DictionaryContent.DICT_NO_BANK);
		}
		return dicts;	
	}

	
}
