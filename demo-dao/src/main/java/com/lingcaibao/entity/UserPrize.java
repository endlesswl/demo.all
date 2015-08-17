package com.lingcaibao.entity;

import java.math.BigDecimal;


public class UserPrize {

	//alias
	public static final String TABLE_ALIAS = "UserPrize";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "userid";
	public static final String ALIAS_PRIZEID = "prizeid";
	public static final String ALIAS_LOTTERYID = "lotteryid";
	public static final String ALIAS_GAMEID = "gameid";
	public static final String ALIAS_GAMETYPE = "gametype";
	public static final String ALIAS_ISSUENO = "issueNo";
	public static final String ALIAS_PRIZEMONEY = "prizeMoney";
	public static final String ALIAS_PRIZEMONEYAFTERTAX = "prizeMoneyAfterTax";
	public static final String ALIAS_TAX = "tax";
	public static final String ALIAS_ISFIXED = "isfixed";
	public static final String ALIAS_CREATETIME = "createtime";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_USERLOTTERYID = "userLotteryId";

	/**
	 * 
	 */
	private Long id;
	/**
	 * 用户
	 */
	private Long userid;
	/**
	 * 中奖记录
	 */
	private Long prizeid;
	/**
	 * 投注彩票
	 */
	private Long lotteryid;
	/**
	 * 彩种
	 */
	private String gameid;
	/**
	 * 彩种类型
	 */
	private Boolean gametype;
	/**
	 * 期号
	 */
	private String issueNo;
	/**
	 * 拆分后的奖金
	 */
	private BigDecimal prizeMoney;
	/**
	 * 拆分后的税后奖金
	 */
	private BigDecimal prizeMoneyAfterTax;
	/**
	 * 拆分后的税金
	 */
	private BigDecimal tax;
	/**
	 * 是否兑奖 0否 1是
	 */
	private Boolean isfixed;
	/**
	 * 更新时间
	 */
	private java.util.Date createtime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 用户领彩ID
	 */
	private Long userLotteryId;

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
	public java.lang.Long getPrizeid() {
		return this.prizeid;
	}
	
	public void setPrizeid(java.lang.Long value) {
		this.prizeid = value;
	}
	public java.lang.Long getLotteryid() {
		return this.lotteryid;
	}
	
	public void setLotteryid(java.lang.Long value) {
		this.lotteryid = value;
	}
	public java.lang.String getGameid() {
		return this.gameid;
	}
	
	public void setGameid(java.lang.String value) {
		this.gameid = value;
	}
	public java.lang.Boolean getGametype() {
		return this.gametype;
	}
	
	public void setGametype(java.lang.Boolean value) {
		this.gametype = value;
	}
	public java.lang.String getIssueNo() {
		return this.issueNo;
	}
	
	public void setIssueNo(java.lang.String value) {
		this.issueNo = value;
	}
	
	public BigDecimal getPrizeMoney() {
		return prizeMoney;
	}

	public void setPrizeMoney(BigDecimal prizeMoney) {
		this.prizeMoney = prizeMoney;
	}

	public BigDecimal getPrizeMoneyAfterTax() {
		return prizeMoneyAfterTax;
	}

	public void setPrizeMoneyAfterTax(BigDecimal prizeMoneyAfterTax) {
		this.prizeMoneyAfterTax = prizeMoneyAfterTax;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public java.lang.Boolean getIsfixed() {
		return this.isfixed;
	}
	
	public void setIsfixed(java.lang.Boolean value) {
		this.isfixed = value;
	}
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.lang.Long getUserLotteryId() {
		return this.userLotteryId;
	}
	
	public void setUserLotteryId(java.lang.Long value) {
		this.userLotteryId = value;
	}
}