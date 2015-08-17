package com.lingcaibao.pay;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.palm.commom.uitl.DateFormatUtil;
/**
 * <p>标题：支付平台接口工具 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月10日 上午10:17:42</p>
 * <p>类全名：com.lingcaibao.util.PayUtil</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class PayUtil
{
	private static Logger	logger	= LoggerFactory.getLogger(PayUtil.class);

	/**
	 * 请求支付平台付款
	 * @param payScope 交易适用范围 WWW或者WAP
	 * @param tradeName 交易名称
	 * @param description 交易描述
	 * @param tradeTime 交易时间
	 * @param expireTime 交易过期时间
	 * @param merchantRemark 商户备注
	 * @param tradeAmount 交易金额
	 * @param currency 货比种类
	 * @return 交易订单号
	 */
	/*
	static public String doPay(String payScope, String tradeName, String description, String tradeTime, String expireTime, String merchantRemark, String tradeAmount, String currency)
	{
		// 请求交易平台
		HttpURLConnection connection = null;
		URL serverAddress = null;
		BufferedReader rd = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try
		{
			Map<String,String> params = Maps.newHashMap();
			params.put("version", PayConstant.PAY_VERSION);
			params.put("service", PayConstant.SERVICE_PAY);
			params.put("partnerNo", PayConstant.PARTNER_NO);
			params.put("payScope", payScope);
			// 生成交易内容
			String orderNo = UUIDUtils.getSerialID().toString();
			String content = getPayContent(PayConstant.SERVICE_PAY, orderNo, tradeName, description, tradeTime, expireTime, merchantRemark, tradeAmount, currency, PayConstant.PAY_NOTIFY_URL,
					PayConstant.PAY_SUCCESS_CALL_BACK_URL, PayConstant.PAY_FAIL_CALL_BACK_URL);
			params.put("content", content);
			// 生成签名URL
			String payUrl = getPaySignUrl(PayConstant.PAY_URL, params, SignConstant.SIGN_TYPE_MD5);
			serverAddress = new URL(payUrl);
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.connect();
			rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = rd.readLine()) != null)
			{
				sb.append(line + "\n");
			}
			// 解析交易结果
			JSONObject result = JSON.parseObject(sb.toString());
			// 交易异常
			if (result.containsKey("resultCode"))
			{
				throw new RuntimeException(PayResult.getMessage(result.getString("resultCode")));
			}
			// 交易成功
			else
			{
				return orderNo;
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("connect payment service err:{}", ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		} finally
		{
			try
			{
				if (rd != null)
				{
					rd.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	*/
	/**
	 * 获取CONTENT(DES)
	 * @param orderNo
	 * @param tradeAmount
	 * @return
	 */
	static public String getPayContent(String orderNo, String tradeAmount, String payScope)
	{
		// 构建交易内容
		String content = null;
		try
		{
			Map<String,String> params = Maps.newHashMap();
			params.put("service", PayConstant.SERVICE_PAY);
			params.put("orderNo", orderNo);
			params.put("tradeName", PayConstant.PAY_NAME_DEPOSIT);
			params.put("description", PayConstant.PAY_DESCRIPTION_DEPOSIT);
			String tradeDate = DateFormatUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
			params.put("tradeTime", tradeDate);
			params.put("expireTime", DateFormatUtil.addHour(tradeDate, 24));
			params.put("merchantRemark", PayConstant.PAY_DESCRIPTION_DEPOSIT);
			params.put("tradeAmount", tradeAmount);
			params.put("currency", PayConstant.DEFAULT_CURRENCY);
			params.put("notifyUrl", PayConstant.PAY_NOTIFY_URL);
			params.put("successCallbackUrl", StringUtils.equals(payScope, PayConstant.PAY_SCOPE_WEB) ? PayConstant.WEB_PAY_SUCCESS_CALL_BACK_URL : PayConstant.WAP_PAY_SUCCESS_CALL_BACK_URL);
			params.put("failCallbackUrl", StringUtils.equals(payScope, PayConstant.PAY_SCOPE_WEB) ? PayConstant.WEB_PAY_FAIL_CALL_BACK_URL : PayConstant.WAP_PAY_FAIL_CALL_BACK_URL);
			params.put("forPayLayerUrl", PayConstant.WEB_FOR_PAY_LAYER_URL);
			logger.info("--------------- getPayContent -----------------:{}", params);
			content = DESUtil.encrypt(JSON.toJSONString(params), PayConstant.DES_KEY, "UTF-8");
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("------------ des encrypt err --------------:{}", ex.getMessage());
		}
		return content;
	}

	/**
	 * 获取支付平台签名URL
	 * @param orderNo
	 * @param tradeAmount
	 * @param payScope
	 * @param signType
	 * @return
	 */
	static public String getPaySignUrl(String orderNo, String tradeAmount, String payScope, String method, String signType, HttpServletRequest request)
	{
		String url = null;
		try
		{
			BaseTradeDto baseTradeDto = new BaseTradeDto();
			baseTradeDto.setService(PayConstant.SERVICE_PAY);
			baseTradeDto.setPartnerNo(PayConstant.PARTNER_NO);
			baseTradeDto.setSignType(signType);
			baseTradeDto.setVersion(PayConstant.PAY_VERSION);
			baseTradeDto.setContent(getPayContent(orderNo, tradeAmount, payScope));
			baseTradeDto.setPayScope(payScope);
			String sign = CommonSignUtil.sign(signType, baseTradeDto);
			System.err.println(CommonSignUtil.objectToMap(baseTradeDto));
			baseTradeDto.setMerchantNo(PayMethod.getMerchantNo(Integer.valueOf(method)));
			baseTradeDto.setSign(sign);
			url = PayConstant.PAY_URL + "?" + CommonSignUtil.toUrlParams(baseTradeDto);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("-------------create pay sign url err---------------:{}", ex);
		}
		return url;
	}
}
