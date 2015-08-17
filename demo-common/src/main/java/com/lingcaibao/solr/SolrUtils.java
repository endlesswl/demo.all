package com.lingcaibao.solr;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.nutz.http.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lingcaibao.conf.Conf;
import com.lingcaibao.statuscode.MarketPlanType;
import com.lingcaibao.util.MarketplanContent;
import com.lingcaibao.util.SqlUtils;
/**
 * <p>标题：搜索引擎工具类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月21日 上午11:51:07</p>
 * <p>类全名：com.lingcaibao.solr.SolrUtils</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class SolrUtils
{
	private static Logger		logger	= LoggerFactory.getLogger(SolrUtils.class);
	final static public String	url		= Conf.get("lingcai.solr_market_plan_url");

	/**
	 * 搜索引擎查询接口(不分页)
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	static public String doQuery(Map<String,Object> params, String sort)
	{
		try
		{
			params.put("sort", SqlUtils.buildSort(sort));
			return Http.post(url, params, 1000);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("------------ do query err -------------:{}", ex);
		}
		return null;
	}

	/**
	 * 搜索引擎查询接口(分页)
	 * @param params
	 * @param sort
	 * @param page
	 * @return
	 */
	static public String doPageQuery(Map<String,Object> params, int pageNum, int pageSize, String sort, String appid)
	{
		try
		{
			params.put("start", (pageNum - 1) * pageSize);
			params.put("rows", pageSize);
			if (StringUtils.isNotEmpty(sort))
			{
				params.put("sort", SqlUtils.buildSort(sort));
			}
			params.put("notMarketType", "MONEYGIFT");
			logger.info("---------- do page query url ----------:{}", url);
			logger.info("---------- do page query params ----------:{}", params);
			String post = Http.post(url, params, 5000);
			JSONObject result = StringUtils.isNotEmpty(post) ? JSON.parseObject(post) : new JSONObject();
			result.put("pageNum", pageNum);
			result.put("pageSize", pageSize);
			int total = result.getIntValue("total");
			int pages = total / pageSize;
			result.put("pages", total % pageSize > 0 ? pages + 1 : pages);
			result.put("startRow", result.getIntValue("start"));
			result.put("endRow", result.getIntValue("start") + pageSize);
			// 处理活动微缩图
			JSONArray arr = result.getJSONArray("marketPlanList");
			if (arr != null)
			{
				for (int i = 0; i < arr.size(); i++)
				{
					JSONObject object = arr.getJSONObject(i);
					// 活动列表图标处理
					String marketType = object.getString("marketType");
					if ((!StringUtils.equals(marketType, MarketPlanType.FREE_COUPON.getType())) && (!StringUtils.equals(marketType, MarketPlanType.INTEGRAL_GAME.getType()))
							&& (!StringUtils.equals(marketType, MarketPlanType.LOTTERY_ALL.getType())) && (!StringUtils.isEmpty(MarketPlanType.getImage(object.getString("marketType")))))
					{
						object.put("image", MarketPlanType.getImage(object.getString("marketType")));
					}
				}
			}
			logger.info("--------- do page query result ---------:{}", result);
			result.put("inType", params.get("inType"));
			return result.toJSONString();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("------------ do query err -------------:{}", ex.getMessage());
		}
		return null;
	}
}
