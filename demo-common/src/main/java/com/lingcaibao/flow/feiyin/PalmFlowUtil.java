package com.lingcaibao.flow.feiyin;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.palm.commom.uitl.UUIDUtils;
/**
 * <p>标题：掌上流量工具类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月12日 下午6:33:35</p>
 * <p>类全名：com.lingcaibao.flow.PalmFlowUtil</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class PalmFlowUtil
{
	final static private String	CHANNELID		= "lingcaibao";
	final static private String	DEPOSIT_URL		= "http://c.palmflow.com:8081/fc/openCardReq";
	final static private String	QUERY_CARD_URL	= "http://c.palmflow.com:8081/fc/qryCardInfoReq";
	private static Logger		logger			= LoggerFactory.getLogger(PalmFlowUtil.class);

	/**
	 * 掌上流量充值接口
	 * @param mobile
	 * @param cardCode
	 * @return
	 */
	static public String doDeposit(String mobile, String cardCode)
	{
		try
		{
			// 生成充值请求报文
			OpenCardReq req = new OpenCardReq();
			req.setCardCode(cardCode);
			req.setChannelId(CHANNELID);
			req.setPhoneNum(mobile);
			req.setTransactionId(UUIDUtils.getSerialID().toString());
			Document doc = PalmXmlUtil.obj2Xml(req);
			logger.info("----- OpenCardReq ----:{}", doc.asXML());
			// 请求充值接口
			URL urls = new URL(DEPOSIT_URL);
			HttpURLConnection http = (HttpURLConnection) urls.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod("POST");
			DataOutputStream out = null;
			InputStream in = null;
			XMLWriter writer = null;
			try
			{
				out = new DataOutputStream(http.getOutputStream());
				writer = new XMLWriter(out);
				writer.write(doc);
				out.flush();
				// 处理结果报文
				in = http.getInputStream();
				SAXReader saxReader = new SAXReader();
				Document result = saxReader.read(in);
				OpenCardRsp rsp = PalmXmlUtil.xml2Obj(result, OpenCardRsp.class);
				logger.info("------- OpenCardRsp -------:{}", JSON.toJSONString(rsp));
				if (rsp != null)
				{
					// 充值成功返回平台订单号
					if (StringUtils.equals(rsp.getRspcode(), OpenCardErrCode.Code0.getRspCode()))
					{
						return rsp.getTransactionid();
					}
					// 充值失败抛异常
					else
					{
						throw new RuntimeException(OpenCardErrCode.getResultMsg(rsp.getRspcode()));
					}
				}
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
		} catch (Exception ex)
		{
			// 异常记录日志
			ex.printStackTrace();
			logger.error("---------- do deposit err ---------:{}", ex.getMessage());
		}
		return null;
	}

	/**
	 * 掌上流量卡品查询
	 * @return
	 */
	static public List<CardInfo> querySaleCard()
	{
		try
		{
			// 生成查询请求报文
			QuerySaleCardReq req = new QuerySaleCardReq();
			req.setChannelId(CHANNELID);
			req.setTransactionId(UUIDUtils.getSerialID().toString());
			Document doc = PalmXmlUtil.obj2Xml(req);
			// 请求查询接口
			URL urls = new URL(QUERY_CARD_URL);
			HttpURLConnection http = (HttpURLConnection) urls.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod("POST");
			DataOutputStream out = new DataOutputStream(http.getOutputStream());
			XMLWriter writer = new XMLWriter(out);
			writer.write(doc);
			writer.close();
			out.flush();
			// 处理结果报文
			SAXReader saxReader = new SAXReader();
			Document result = saxReader.read(http.getInputStream());
			logger.info("------result:-----{}" + result.asXML());
			QuerySaleCardRsp rsp = PalmXmlUtil.xml2Obj(result, QuerySaleCardRsp.class);
			if (rsp != null)
			{
				// 充值成功返回平台订单号
				if (StringUtils.equals(rsp.getResultcode(), QueryCardInfoErrCode.Code0.getRspCode()))
				{
					return rsp.getCardinfolist();
				}
				// 充值失败抛异常
				else
				{
					throw new RuntimeException(QueryCardInfoErrCode.getResultMsg(rsp.getResultcode()));
				}
			}
		} catch (Exception ex)
		{
			// 异常记录日志
			ex.printStackTrace();
			logger.error("---------- do deposit err ---------:{}", ex.getMessage());
		}
		return null;
	}
}
