package com.lingcaibao.entity;

import java.math.BigDecimal;


public class Withdraw {

	//alias
	public static final String TABLE_ALIAS = "Withdraw";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "userid";
	public static final String ALIAS_BANKID = "bankid";
	public static final String ALIAS_ORDERID = "orderid";
	public static final String ALIAS_AMOUNT = "amount";
	public static final String ALIAS_CREATETIME = "createtime";
	public static final String ALIAS_AUDITTIME = "audittime";
	public static final String ALIAS_OPERATOR = "operator";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_PROCESSTIME = "processtime";
	public static final String ALIAS_WXSERVERID = "wxserverid";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_DELETEFLAG = "deleteFlag";
	public static final String ALIAS_SOURCE = "source";
	public static final String ALIAS_SERIALNO = "serialNo";
	public static final String ALIAS_TRADENO = "tradeNo";
	public static final String ALIAS_PREBALANCE = "prebalance";
	public static final String ALIAS_AFTERBALANCE = "afterbalance";
	/**
	 * 
	 */
	private Long id;
	/**
	 * 用户
	 */
	private Long userid;
	/**
	 * 银行卡
	 */
	private Long bankid;
	/**
	 * 定单号
	 */
	private Long orderid;
	/**
	 * 提现金额
	 */
	private BigDecimal amount;
	/**
	 * 申请时间
	 */
	private java.util.Date createtime;
	/**
	 * 审核时间
	 */
	private java.util.Date audittime;
	/**
	 * 操作员
	 */
	private Long operator;
	/**
	 * 状态 0提交 1审核通过 2审核不通过 3转账成功 4转账不成功
	 */
	private Integer status;
	/**
	 * 处理时间
	 */
	private java.util.Date processtime;
	/**
	 * 
	 */
	private Long wxserverid;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除标识 0正常 1删除
	 */
	private Integer deleteFlag;
	/**
	 * 提现来源
	 */
	private String source;
	/**
	 * 渠道方提现流水号
	 */
	private String serialNo;
	/**
	 * 支付宝交易号
	 */
	private String tradeNo;
	/**
	 * 发生前余额
	 * @return
	 */
	private BigDecimal prebalance;
	/**
	 * 发生后余额
	 */
	private BigDecimal afterbalance;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getUserid() {
		return this.userid;
	}
	
	public void setUserid(java.lang.Long value) {
		this.userid = value;
	}
	public java.lang.Long getBankid() {
		return this.bankid;
	}
	
	public void setBankid(java.lang.Long value) {
		this.bankid = value;
	}
	public java.lang.Long getOrderid() {
		return this.orderid;
	}
	
	public void setOrderid(java.lang.Long value) {
		this.orderid = value;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}
	public java.util.Date getAudittime() {
		return this.audittime;
	}
	
	public void setAudittime(java.util.Date value) {
		this.audittime = value;
	}
	public java.lang.Long getOperator() {
		return this.operator;
	}
	
	public void setOperator(java.lang.Long value) {
		this.operator = value;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	public java.util.Date getProcesstime() {
		return this.processtime;
	}
	
	public void setProcesstime(java.util.Date value) {
		this.processtime = value;
	}
	public java.lang.Long getWxserverid() {
		return this.wxserverid;
	}
	
	public void setWxserverid(java.lang.Long value) {
		this.wxserverid = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}
	
	public void setDeleteFlag(Integer value) {
		this.deleteFlag = value;
	}
	public java.lang.String getSource() {
		return this.source;
	}
	
	public void setSource(java.lang.String value) {
		this.source = value;
	}
	public java.lang.String getSerialNo() {
		return this.serialNo;
	}
	
	public void setSerialNo(java.lang.String value) {
		this.serialNo = value;
	}
	public java.lang.String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(java.lang.String value) {
		this.tradeNo = value;
	}

	public BigDecimal getPrebalance() {
		return prebalance;
	}

	public void setPrebalance(BigDecimal prebalance) {
		this.prebalance = prebalance;
	}

	public BigDecimal getAfterbalance() {
		return afterbalance;
	}

	public void setAfterbalance(BigDecimal afterbalance) {
		this.afterbalance = afterbalance;
	}
}