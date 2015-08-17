package com.lingcaibao.flow.santong;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.nutz.http.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.palm.commom.uitl.DateFormatUtil;
/**
 * <p>标题：大汉三通流量工具类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月22日 下午7:36:18</p>
 * <p>类全名：com.lingcaibao.flow.santong.STFlowUtil</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class STFlowUtil
{
	private static Logger		logger				= LoggerFactory.getLogger(STFlowUtil.class);
	//	final static private String	FLOW_URL			= "http://test.dahanfc.com:3429/if/FCOrderServlet";
	//	final static private String	ORDER_QUERY_URL		= "http://test.dahanfc.com:3429/if/FCSearchReportDataServlet";
	//	final static private String	PRODUCT_QUERY_URL	= "http://test.dahanfc.com:3429/if/FCSearchProductServlet";
	//	final static private String	PUSH_URL			= "http://test.lingcaibao.com/push/prize/st/flow";
	final static private String	FLOW_URL			= "http://if.dahanfc.com/FCOrderServlet";
	final static private String	ORDER_QUERY_URL		= "http://if.dahanfc.com/FCSearchReportDataServlet";
	final static private String	PRODUCT_QUERY_URL	= "http://if.dahanfc.com/FCSearchProductServlet";
	final static private String	PUSH_URL			= "http://www.lingcaibao.com/push/prize/st/flow";
	final static private String	ACCOUNT				= "lingcaibao";
	final static private String	PWD					= "lcb20110615";

	/**
	 * 流量直充接口
	 * @param mobile
	 * @param flow
	 * @return
	 */
	static public String doDeposit(String mobile, BigDecimal money)
	{
		logger.info("------------ do deposit mobile:{} money:{} -------------", mobile, money);
		// 组织参数
		Map<String,String> params = Maps.newHashMap();
		String timestamp = DateFormatUtil.getMillisTime(DateFormatUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		params.put("timestamp", timestamp);
		params.put("account", ACCOUNT);
		params.put("mobiles", mobile);
		params.put("packageSize", STFlow.getFlowByMoney(money));
		// 生成MD5签名
		StringBuffer buf = new StringBuffer();
		buf.append(ACCOUNT);
		buf.append(STFlowUtil.encryption(PWD));
		buf.append(timestamp);
		buf.append(mobile);
		params.put("sign", STFlowUtil.encryption(buf.toString()));
		// 请求充值接口
		try
		{
			String paramsStr = JSON.toJSONString(params);
			logger.info("----------- post url ----------:{}", FLOW_URL);
			logger.info("------------- paramsStr ---------------:{}", paramsStr);
			String result = "";
			URL urls = new URL(FLOW_URL);
			HttpURLConnection http = (HttpURLConnection) urls.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Accept-Charset", "utf-8");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setRequestProperty("Content-Length", String.valueOf(paramsStr.length()));
			OutputStream out = null;
			InputStream in = null;
			OutputStreamWriter writer = null;
			BufferedReader breader = null;
			try
			{
				out = http.getOutputStream();
				writer = new OutputStreamWriter(out);
				writer.write(paramsStr);
				writer.flush();
				in = http.getInputStream();
				InputStreamReader reader = new InputStreamReader(in, "utf-8");
				breader = new BufferedReader(reader);
				String content = null;
				while ((content = breader.readLine()) != null)
				{
					result += content;
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
				logger.error("---------- request err --------:{}", ex.getMessage());
			} finally
			{
				if (out != null)
				{
					out.close();
				}
				if (writer != null)
				{
					writer.close();
				}
				if (in != null)
				{
					in.close();
				}
				if (breader != null)
				{
					breader.close();
				}
			}
			// 解析调用接口返回信息
			logger.info("----------- result -----------:{}", result);
			if (StringUtils.isNotEmpty(result))
			{
				JSONObject obj = JSON.parseObject(result);
				String resultCode = obj.getString("resultCode");
				logger.info("----------- resultCode -----------:{}", resultCode);
				// 接口调用成功,查询订单状态
				if (StringUtils.equals(resultCode, OrderRsp.M00.getCode()))
				{
					return obj.getString("clientOrderId");
					/*
					String orderid = obj.getString("clientOrderId");
					logger.info("----------- orderid -----------:{}", orderid);
					List<OrderInfo> list = STFlowUtil.doReportQuery(orderid, mobile, null);
					if (list.size() > 0)
					{
						OrderInfo info = list.get(0);
						logger.info("----------- OrderInfo -----------:{}", JSON.toJSONString(info));
						if (StringUtils.equals(info.getStatus(), "0"))
						{
							return info.getClientOrderId();
						}
					}
					*/
				}
				// 接口调用失败,抛异常信息
				else
				{
					throw new java.lang.RuntimeException(OrderRsp.getMsg(resultCode));
				}
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("---------- doDeposit err --------:{}", ex.getMessage());
		}
		return null;
	}

	/**
	 * 流量消息推送
	 * @param clientOrderId
	 * @param mobile
	 * @param status
	 * @return
	 */
	static public String flowPush(String clientOrderId, String mobile, String status)
	{
		// 组织参数
		Map<String,String> params = Maps.newHashMap();
		params.put("clientOrderId", clientOrderId);
		params.put("mobile", ACCOUNT);
		params.put("reportTime", DateFormatUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		params.put("status", status);
		// 请求通知接口
		try
		{
			String paramsStr = JSON.toJSONString(params);
			logger.info("------------- paramsStr ---------------:{}", paramsStr);
			String result = "";
			URL urls = new URL(PUSH_URL);
			HttpURLConnection http = (HttpURLConnection) urls.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Accept-Charset", "utf-8");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setRequestProperty("Content-Length", String.valueOf(paramsStr.length()));
			OutputStream out = null;
			InputStream in = null;
			OutputStreamWriter writer = null;
			BufferedReader breader = null;
			try
			{
				out = http.getOutputStream();
				writer = new OutputStreamWriter(out);
				writer.write(paramsStr);
				writer.flush();
				in = http.getInputStream();
				InputStreamReader reader = new InputStreamReader(in, "utf-8");
				breader = new BufferedReader(reader);
				String content = null;
				while ((content = breader.readLine()) != null)
				{
					result += content;
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
				logger.error("---------- request err --------:{}", ex.getMessage());
			} finally
			{
				if (out != null)
				{
					out.close();
				}
				if (writer != null)
				{
					writer.close();
				}
				if (in != null)
				{
					in.close();
				}
				if (breader != null)
				{
					breader.close();
				}
			}
			// 解析调用接口返回信息
			logger.info("----------- result -----------:{}", result);
			return result;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("---------- push err --------:{}", ex.getMessage());
		}
		return null;
	}

	/**
	 * 订单状态查询
	 * @param orderid
	 * @return
	 */
	static public List<OrderInfo> doReportQuery(String orderid, String mobile, String status)
	{
		List<OrderInfo> list = Lists.newArrayList();
		Map<String,Object> params = Maps.newHashMap();
		params.put("clientOrderId", orderid);
		params.put("mobile", mobile);
		params.put("status", status);
		String result = Http.post(ORDER_QUERY_URL, params, 5000);
		if (StringUtils.isNotEmpty(result))
		{
			JSONArray arr = JSON.parseArray(result);
			for (int i = 0; i < arr.size(); i++)
			{
				list.add(arr.getObject(i, OrderInfo.class));
			}
		}
		return list;
	}

	/**
	 * 账户产品查询
	 * @return
	 */
	static public List<ProductInfo> doProductQuery()
	{
		List<ProductInfo> list = Lists.newArrayList();
		Map<String,Object> params = Maps.newHashMap();
		params.put("account", ACCOUNT);
		String result = Http.post(PRODUCT_QUERY_URL, params, 5000);
		logger.info("---------- product list ---------:{}", result);
		if (StringUtils.isNotEmpty(result))
		{
			JSONObject obj = JSON.parseObject(result);
			String resultCode = obj.getString("resultCode");
			if (StringUtils.equals(resultCode, ProductRsp.M00.getCode()))
			{
				JSONArray arr = obj.getJSONArray("information");
				for (int i = 0; i < arr.size(); i++)
				{
					list.add(arr.getObject(i, ProductInfo.class));
				}
			}
		}
		logger.info("----------- products -----------:{}", JSON.toJSONString(list));
		return list;
	}

	/**
	 * MD5的32位加密
	 * @param plainText
	 * @return
	 */
	static public String encryption(String plainText)
	{
		String re_md5 = new String();
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++)
			{
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			re_md5 = buf.toString();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return re_md5;
	}

	public static void main(String[] args)
	{
		//String orderid = STFlowUtil.doDeposit("13718154066", new BigDecimal(3.0));
		//System.err.println("orderid == " + orderid);
		//List<ProductInfo> list = STFlowUtil.doProductQuery();
		//System.err.println(JSON.toJSONString(list));
		List<OrderInfo> list = STFlowUtil.doReportQuery("da6bc3e8606a42bc82e19fdfadf1841f", "13718154066", null);
		System.err.println(JSON.toJSONString(list));
	}
}
