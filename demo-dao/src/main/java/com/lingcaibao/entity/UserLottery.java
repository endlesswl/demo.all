package com.lingcaibao.entity;

import java.math.BigDecimal;


public class UserLottery {

	//alias
	public static final String TABLE_ALIAS = "UserLottery";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "userid";
	public static final String ALIAS_USERNAME = "username";
	public static final String ALIAS_LOTTERYID = "lotteryid";
	public static final String ALIAS_MARKETID = "marketid";
	public static final String ALIAS_DRAWID = "drawid";
	public static final String ALIAS_MONEY = "money";
	public static final String ALIAS_GAINPERCENT = "gainPercent";
	public static final String ALIAS_REQHEAD = "reqHead";
	public static final String ALIAS_REFERER = "referer";
	public static final String ALIAS_SOURCE = "source";
	public static final String ALIAS_AUTHOR = "author";
	public static final String ALIAS_SCANIP = "scanip";
	public static final String ALIAS_CREATETIME = "createtime";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_ISSEND = "isSend";
	public static final String ALIAS_GROUPID = "groupid";
	public static final String ALIAS_CID = "cid";
	public static final String ALIAS_ORDERID = "orderid";
	public static final String ALIAS_SERIALNO = "serialno";
	public static final String ALIAS_CHANNELORDER = "channelOrder";
	public static final String ALIAS_NOTIFY_STATUS = "notifyStatus";
	public static final String ALIAS_NOTIFY_TIMES = "notifyTimes";
	public static final String ALIAS_NOTIFY_WIN_STATUS = "notifyWinStatus";
	public static final String ALIAS_NOTIFY_WIN_TIMES = "notifyWinTimes";

	/**
	 * 
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userid;
	/**
	 * 用户名，这里指手机号做为用户名
	 */
	private String username;
	/**
	 * 购彩ID
	 */
	private Long lotteryid;
	/**
	 * 营销计划ID
	 */
	private Long marketid;
	/**
	 * 抽奖ID
	 */
	private Integer drawid;
	/**
	 * 彩票金额
	 */
	private BigDecimal money;
	/**
	 * 分得比例
	 */
	private Long gainPercent;
	/**
	 * 扫码请求头信息
	 */
	private String reqHead;
	/**
	 * 来路
	 */
	private String referer;
	/**
	 * 访问渠道
	 */
	private String source;
	/**
	 * 送祝福人，作者
	 */
	private String author;
	/**
	 * 访问IP
	 */
	private String scanip;
	/**
	 * 访问时间
	 */
	private java.util.Date createtime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否为发起人
	 */
	private Integer isSend;
	/**
	 * 用户组
	 */
	private Long groupid;
	/**
	 * 选择的战队
	 */
	private String cid;
	/**
	 * 
	 */
	private String orderid;
	/**
	 * 
	 */
	private String serialno;
	/**
	 * 
	 */
	private String channelOrder;
	/**
	 * 出票成功推送状态
	 */
	private String notifyStatus;
	/**
	 * 出票成功推送次数
	 */
	private Integer notifyTimes;
	/**
	 * 中奖推送状态
	 */
	private String notifyWinStatus;
	/**
	 * 中奖推送次数
	 */
	private Integer notifyWinTimes;

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
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	public java.lang.Long getLotteryid() {
		return this.lotteryid;
	}
	
	public void setLotteryid(java.lang.Long value) {
		this.lotteryid = value;
	}
	public java.lang.Long getMarketid() {
		return this.marketid;
	}
	
	public void setMarketid(java.lang.Long value) {
		this.marketid = value;
	}
	
	public Integer getDrawid() {
		return drawid;
	}

	public void setDrawid(Integer drawid) {
		this.drawid = drawid;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getGainPercent() {
		return this.gainPercent;
	}
	
	public void setGainPercent(Long value) {
		this.gainPercent = value;
	}
	public java.lang.String getReqHead() {
		return this.reqHead;
	}
	
	public void setReqHead(java.lang.String value) {
		this.reqHead = value;
	}
	public java.lang.String getReferer() {
		return this.referer;
	}
	
	public void setReferer(java.lang.String value) {
		this.referer = value;
	}
	public java.lang.String getSource() {
		return this.source;
	}
	
	public void setSource(java.lang.String value) {
		this.source = value;
	}
	public java.lang.String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(java.lang.String value) {
		this.author = value;
	}
	public java.lang.String getScanip() {
		return this.scanip;
	}
	
	public void setScanip(java.lang.String value) {
		this.scanip = value;
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
	public java.lang.Integer getIsSend() {
		return this.isSend;
	}
	
	public void setIsSend(java.lang.Integer value) {
		this.isSend = value;
	}
	public java.lang.Long getGroupid() {
		return this.groupid;
	}
	
	public void setGroupid(java.lang.Long value) {
		this.groupid = value;
	}
	public java.lang.String getCid() {
		return this.cid;
	}
	
	public void setCid(java.lang.String value) {
		this.cid = value;
	}
	public java.lang.String getOrderid() {
		return this.orderid;
	}
	
	public void setOrderid(java.lang.String value) {
		this.orderid = value;
	}
	public java.lang.String getSerialno() {
		return this.serialno;
	}
	
	public void setSerialno(java.lang.String value) {
		this.serialno = value;
	}
	public java.lang.String getChannelOrder() {
		return this.channelOrder;
	}
	
	public void setChannelOrder(java.lang.String value) {
		this.channelOrder = value;
	}
	public java.lang.String getNotifyStatus() {
		return this.notifyStatus;
	}
	
	public void setNotifyStatus(java.lang.String value) {
		this.notifyStatus = value;
	}
	public java.lang.Integer getNotifyTimes() {
		return this.notifyTimes;
	}
	
	public void setNotifyTimes(java.lang.Integer value) {
		this.notifyTimes = value;
	}
	public java.lang.String getNotifyWinStatus() {
		return this.notifyWinStatus;
	}
	
	public void setNotifyWinStatus(java.lang.String value) {
		this.notifyWinStatus = value;
	}
	public java.lang.Integer getNotifyWinTimes() {
		return this.notifyWinTimes;
	}
	
	public void setNotifyWinTimes(java.lang.Integer value) {
		this.notifyWinTimes = value;
	}
}