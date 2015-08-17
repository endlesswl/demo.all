package com.lingcaibao.flow.oufei;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.nutz.http.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Maps;
import com.lingcaibao.conf.Conf;
import com.lingcaibao.flow.feiyin.PalmXmlUtil;
import com.palm.commom.uitl.MD5;
import com.palm.commom.uitl.UUIDUtils;
/**
 * <p>标题：欧飞流量接口工具 </p>
 * <p>功能：流量直充/订单查询 </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月19日 下午6:33:14</p>
 * <p>类全名：com.lingcaibao.flow.oufei.SPFlowUtil</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class SPFlowUtil
{
	private static Logger		logger			= LoggerFactory.getLogger(SPFlowUtil.class);
	final static private String	FLOW_URL		= "http://api2.ofpay.com/flowOrder.do";		// SP流量直充URL
	final static private String	BILL_QUERY_URL	= "http://api2.ofpay.com/api/query.do";		// SP订单查询URL
	final static private String	USERID			= "A1088418";									// SP编码
	final static private String	USERPWS			= "LCB123456";									// SP接入密码
	final static private String	KEYSTR			= "OFCARD";
	//final static private String	RETURL		= Conf.get("lingcai.sp_ret_url");				// SP通知URL
	final static private String	VERSION			= "6.0";										// 版本号

	/**
	 * 流量直充接口
	 * @param mobile
	 * @param flow
	 * @return
	 */
	static public String doDeposit(String mobile, String flow)
	{
		// 组织参数
		String orderid = UUIDUtils.getSerialID().toString();
		Map<String,String> params = Maps.newHashMap();
		params.put("userid", USERID);
		String userpws = SPFlowUtil.encryption(USERPWS);
		params.put("userpws", userpws);
		params.put("phoneno", mobile);
		String perValue = SPFlow.getMoney(flow);
		String flowValue = SPFlow.getFlow(flow);
		params.put("perValue", perValue);
		params.put("flowValue", flowValue);
		params.put("range", SPFlowStatus.RANGE_COUNTRY);
		params.put("effectStartTime", SPFlowStatus.EFFECT_START_TIME_CUR_DAY);
		params.put("effectTime", SPFlowStatus.EFFECT_TIME_CUR_MONTH);
		params.put("sporderId", orderid);
		params.put("netType", SPFlowStatus.NET_TYPE_4G);
		// 生成MD5签名
		StringBuffer buf = new StringBuffer();
		buf.append(USERID);
		buf.append(userpws);
		buf.append(mobile);
		buf.append(perValue);
		buf.append(flowValue);
		buf.append(SPFlowStatus.RANGE_COUNTRY);
		buf.append(SPFlowStatus.EFFECT_START_TIME_CUR_DAY);
		buf.append(SPFlowStatus.EFFECT_TIME_CUR_MONTH);
		buf.append(SPFlowStatus.NET_TYPE_4G);
		buf.append(orderid);
		buf.append(KEYSTR);
		String md5Str = SPFlowUtil.encryption(buf.toString()).toUpperCase();
		params.put("md5Str", md5Str);
		//params.put("retUrl", "www.baidu.com");
		params.put("version", VERSION);
		// 生成URL参数
		StringBuffer parameterBuffer = new StringBuffer();
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			String value = StringUtils.isEmpty(params.get(key)) ? "" : params.get(key);
			parameterBuffer.append(key).append("=").append(value);
			if (iterator.hasNext())
			{
				parameterBuffer.append("&");
			}
		}
		// 请求充值接口
		try
		{
			logger.info("----------- post url ----------:{}", FLOW_URL + "?" + parameterBuffer.toString());
			URL urls = new URL(FLOW_URL);
			HttpURLConnection http = (HttpURLConnection) urls.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Accept-Charset", "utf-8");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));
			OutputStream out = null;
			InputStream in = null;
			OutputStreamWriter writer = null;
			OrderInfo info = null;
			try
			{
				out = http.getOutputStream();
				writer = new OutputStreamWriter(out);
				writer.write(parameterBuffer.toString());
				writer.flush();
				in = http.getInputStream();
				SAXReader saxReader = new SAXReader();
				Document result = saxReader.read(in);
				info = PalmXmlUtil.xml2Obj(result, OrderInfo.class);
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
			}
			// 处理结果信息
			String retcode = info.getRetcode();
			String gameState = null;
			// 接口调用成功
			if (StringUtils.equals(retcode, ReturnCode.R0001.getCode()))
			{
				gameState = info.getGamestate();
			}
			// 其他未知错误,需要调用订单状态查询接口
			else if (StringUtils.equals(retcode, ReturnCode.R0105.getCode()) || StringUtils.equals(retcode, ReturnCode.R0334.getCode()) || StringUtils.equals(retcode, ReturnCode.R1043.getCode())
					|| StringUtils.equals(retcode, ReturnCode.R9999.getCode()))
			{
				gameState = SPFlowUtil.doBillQuery(orderid);
			}
			// 接口调用失败
			else
			{
				throw new RuntimeException(info.getErrmsg());
			}
			// 按照返回状态处理结果
			if (StringUtils.equals(gameState, GameState.WAITING.getCode()) || StringUtils.equals(gameState, GameState.SUCCESS.getCode()))
			{
				return orderid;
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("---------- doDeposit err --------:{}", ex.getMessage());
		}
		return null;
	}

	/**
	 * 订单状态查询接口
	 * @param orderid
	 * @return
	 */
	static public String doBillQuery(String orderid)
	{
		Map<String,Object> params = Maps.newHashMap();
		params.put("userid", USERID);
		params.put("spbillid", orderid);
		return Http.post(BILL_QUERY_URL, params, 5000);
	}

	public static void main(String[] args)
	{
		String orderid = SPFlowUtil.doDeposit("13718154066", "M2");
		System.err.println("orderid === " + orderid);
		//		String state = SPFlowUtil.doBillQuery("111111");
		//		System.err.println("state === " + state);
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
}
