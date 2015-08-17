package com.lingcaibao.web.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lingcaibao.plugin.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.palm.commom.uitl.MemcacheKeyConstants;
import com.palm.commom.uitl.Servlets;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.lingcaibao.cache.memcached.MarketMemcachedClient;
import com.lingcaibao.entity.Dictionary;
import com.lingcaibao.service.DictionaryService;
import com.lingcaibao.util.DictionaryContent;
import com.lingcaibao.util.LcbJson;
/**
 * <p>标题：字典接口 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月13日 下午7:31:02</p>
 * <p>类全名：com.lingcaibao.web.controller.DictionaryController</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController
{
	private static Logger				logger				= LoggerFactory.getLogger(DictionaryController.class);
	@Autowired
	private DictionaryService			dictionaryService;
	@Autowired
	private MarketMemcachedClient		memcachedClient;
	private static final String			PAGE_SIZE			= "10";
	// 允许排序的字段
	private static final List<String>	ALLOW_SORT_COLUMNS	= ImmutableList.of("id");

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "id_") String sort, ServletRequest request, Model model)
	{
		Map<String,Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		// 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
		String sortStr = buildSort(sort);
		if (sortStr.length() > 0)
		{
			searchParams.put("sortColumns", sortStr);
		}
		Page<Dictionary> pageable = new Page<Dictionary>(pageNumber, pageSize);
		Page<Dictionary> page = dictionaryService.searchPage(searchParams, pageable);
		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return "dictionary/dictionaryList";
	}

	/**
	 * 获取字典内容
	 * @param dictno
	 * @param level
	 * @param parentcode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/items")
	public String getDictContent(@RequestParam(value = "dictno") String dictno, @RequestParam(value = "code", required = false) String code)
	{
		// 获取子级字典内容
		if (StringUtils.isNotBlank(code))
		{
			List<Dictionary> dicts = dictionaryService.getDictSubItems(dictno, code);
			Map<String,List<Dictionary>> resultMap = Maps.newHashMap();
			resultMap.put("dicts", dicts);
			return JSON.toJSONString(resultMap);
		}
		// 获取全部字典内容
		else
		{
			List<Dictionary> dicts = dictionaryService.getDictItems(dictno);
			Map<String,List<Dictionary>> resultMap = Maps.newHashMap();
			resultMap.put("dicts", dicts);
			return JSON.toJSONString(resultMap);
		}
	}

	/**
	 * 构建排序SQL
	 * 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
	 * @param sort
	 * @return
	 */
	public static String buildSort(String sort)
	{
		StringBuffer strf = new StringBuffer();
		for (String sortStr : sort.split("-"))
		{
			// 如果传入的字段不允许排序，则跳出
			if (!ALLOW_SORT_COLUMNS.contains(sortStr.replace("_", "")))
			{
				continue;
			}
			// 去除以下划线开头结尾的参数，只能以固定格式
			if (sortStr.startsWith("_") && sortStr.endsWith("_"))
			{
				continue;
			}
			if (sortStr.startsWith("_"))
			{// 以下划线开始为升序
				strf.append(sortStr.substring(1)).append(" ASC");
			} else if (sortStr.endsWith("_"))
			{// 以下划线结束为降序
				strf.append(sortStr.substring(0, sortStr.length() - 1)).append(" DESC");
			}
		}
		return strf.toString();
	}

	/**国家列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/countryList")
	public String getCountryList()
	{
		List<Dictionary> list = dictionaryService.getDictItems(DictionaryContent.DICT_NO_NODE);
		Map<String,Object> map = Maps.newHashMap();
		map.put("countryList", list);
		return LcbJson.toJSONString(map);
	}

	/**
	 * 省份列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/provinceList")
	public String getProvinceList(ServletRequest request, Model model)
	{
		String countryCode = request.getParameter("countryCode");//国家编码
		List<Dictionary> list = dictionaryService.getDictSubItems(DictionaryContent.DICT_NO_NODE, countryCode);//暂时展示中国的省份
		Map<String,Object> map = Maps.newHashMap();
		map.put("provinceList", list);
		return LcbJson.toJSONString(map);
	}

	/**
	 * 城市列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cityList")
	public String getCityList(ServletRequest request, Model model)
	{
		String provinceCode = request.getParameter("provinceCode");//省份编码
		List<Dictionary> list = dictionaryService.getDictSubItems(DictionaryContent.DICT_NO_NODE, provinceCode);
		Map<String,Object> map = Maps.newHashMap();
		map.put("cityList", list);
		return LcbJson.toJSONString(map);
	}

	/**
	 * 地区列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/areaList")
	public String getAreaList(ServletRequest request, Model model)
	{
		String cityCode = request.getParameter("cityCode");//省份编码
		List<Dictionary> list = dictionaryService.getDictSubItems(DictionaryContent.DICT_NO_NODE, cityCode);
		Map<String,Object> map = Maps.newHashMap();
		map.put("areaList", list);
		return LcbJson.toJSONString(map);
	}

	/**
	 * 获取字典项名称
	 * @param parentcode
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDictionary")
	public String getDictItemName(@RequestParam("dictno") String dictno, @RequestParam("code") String code)
	{
		Dictionary dictionary = dictionaryService.findDictByCode(dictno, code);
		Map<String,Object> map = Maps.newHashMap();
		map.put("dictionary", dictionary);
		return LcbJson.toJSONString(map);
	}

	@ResponseBody
	@RequestMapping(value = "/init")
	public void init(@RequestParam(value = "dictno", required = false) String dictno)
	{
		dictionaryService.init(dictno);
	}

	/**
	 * 获取银行名称信息列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bankList")
	public String getBankList()
	{
		Map<String,Object> map = Maps.newHashMap();
		List<Dictionary> list = dictionaryService.getDictItems(DictionaryContent.DICT_NO_BANK);
	    map.put("list", list);
		return LcbJson.toJSONString(map);
	}
}
