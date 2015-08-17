package com.lingcaibao.pay;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>标题：上行支付签名实体 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月11日 下午4:27:39</p>
 * <p>类全名：com.lingcaibao.pay.BaseTradeDto</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class BaseTradeDto
{
	
	public static final String PAYSCOPE_WWW="WWW";
	public static final String PAYSCOPE_WAP="WAP";
	
	/**
	 * 协议版本
	 */
	private String	version;
	/**
	 * 交易协议
	 */
	private String	service;
	/**
	 * 商户号
	 */
	private String	partnerNo;
	/**
	 * 交易数据
	 */
	private String	content;
	/**
	 * 商户使用签名方式
	 */
	private String	signType;
	/**
	 * 商户签名串
	 */
	private String	sign;
	/**
	 * 下行支付商户编号
	 */
	private String	merchantNo;
	/**
	 * 下行支付交易适用范围 WWW或者WAP
	 */
	private String	payScope;
	/**
	 * 请求端ip地址
	 */
	private String	ipAddr;

	@NotEmpty(message = "sss")
	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getPartnerNo()
	{
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo)
	{
		this.partnerNo = partnerNo;
	}

	public String getSignType()
	{
		return signType;
	}

	public void setSignType(String signType)
	{
		this.signType = signType;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getService()
	{
		return service;
	}

	public void setService(String service)
	{
		this.service = service;
	}

	public String getIpAddr()
	{
		return ipAddr;
	}

	public void setIpAddr(String ipAddr)
	{
		this.ipAddr = ipAddr;
	}

	public String getPayScope()
	{
		return payScope;
	}

	public void setPayScope(String payScope)
	{
		this.payScope = payScope;
	}

	public String getMerchantNo()
	{
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo)
	{
		this.merchantNo = merchantNo;
	}
}
