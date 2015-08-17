package com.lingcaibao.web.dto;

import java.math.BigDecimal;

public class MarketplanDto {
	/**
	 * 
	 */
	private Long				id;
	/**
	 * 营销计划名称
	 */
	private String				marketName;
	/**
	 * 营销计划描述
	 */
	private String				marketDesc;
	/**
	 * 营销计划标题
	 */
	private String				marketTitle;
	/**
	 * 用户ID
	 */
	private Long				userId;
	/**
	 * 开始时间
	 */
	private java.util.Date		startTime;
	/**
	 * 结束时间
	 */
	private java.util.Date		endTime;
	/**
	 * 单个用户赠送频次
	 */
	private String				frequency;
	/**
	 * 总价
	 */
	private BigDecimal			totalPrice;
	/**
	 * 其他奖励计划:1 实物奖励;2 礼券奖励;3 现金奖励
	 */
	private Integer				otherReward;
	/**
	 * 营销计划类型:1 信息推送;2 广告问答;3 调查问卷;4 任务式营销;5 积分换零彩;6新年祝福;7私人专属定制,8微信营销
	 */
	private String				marketType;
	/**
	 * 状态 
	 */
	private Integer				status;
	/**
	 * 支付标识 0未付款 ;1 已付款
	 */
	private Integer				payFlag;
	/**
	 * 审核标识
	 */
	private Integer				auditFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date		createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date		modifyTime;
	/**
	 * 彩种类型：1体育彩票；2福利彩票
	 */
	private Integer				lotteryType;
	/**
	 * 彩种名称
	 */
	private String				lotteryName;
	/**
	 * 是否清算 0已清算 1未清算
	 */
	private Integer				balanceFixed;
	/**
	 * 
	 */
	private Long				ruleid;
	/**
	 * 备注
	 */
	private String				remark;
	/**
	 * 
	 */
	private String				marketAppKey;
	/**
	 * 
	 */
	private String				marketAppSecret;
	/**
	 * 零彩后台发布标识: 1:允许后台发布
	 */
	private Integer				pubFlags;
	/**
	 * 活动海报图片
	 */
	private String				posterPath;
	/**
	 * 头图
	 */
	private String				topPath;
	/**
	 * 使用提取码标志 0:不使用 1:使用
	 */
	private Integer				codeFlags;
	/**
	 * 
	 */
	private Long				useMoney;
	/**
	 * 底部广告标识 0:不使用 1:使用
	 */
	private Integer				footadFlags;
	/**
	 * 底部广告背景图
	 */
	private String				footadPath;
	/**
	 * 底部广告标语
	 */
	private String				footadSlogan;
	/**
	 * 底部按钮文字
	 */
	private String				footbtnTitle;
	/**
	 * 底部按钮位置 0:居左 1:居中 2:居右
	 */
	private Integer				footbtnPosition;
	/**
	 * 底部按钮链接
	 */
	private String				footbtnUrl;
	/**
	 * 互动次数
	 */
	private BigDecimal			interactnum;
	/**
	 * 活动乏值
	 */
	private BigDecimal			lackrate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getMarketDesc() {
		return marketDesc;
	}
	public void setMarketDesc(String marketDesc) {
		this.marketDesc = marketDesc;
	}
	public String getMarketTitle() {
		return marketTitle;
	}
	public void setMarketTitle(String marketTitle) {
		this.marketTitle = marketTitle;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getOtherReward() {
		return otherReward;
	}
	public void setOtherReward(Integer otherReward) {
		this.otherReward = otherReward;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayFlag() {
		return payFlag;
	}
	public void setPayFlag(Integer payFlag) {
		this.payFlag = payFlag;
	}
	public Integer getAuditFlag() {
		return auditFlag;
	}
	public void setAuditFlag(Integer auditFlag) {
		this.auditFlag = auditFlag;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public Integer getBalanceFixed() {
		return balanceFixed;
	}
	public void setBalanceFixed(Integer balanceFixed) {
		this.balanceFixed = balanceFixed;
	}
	public Long getRuleid() {
		return ruleid;
	}
	public void setRuleid(Long ruleid) {
		this.ruleid = ruleid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMarketAppKey() {
		return marketAppKey;
	}
	public void setMarketAppKey(String marketAppKey) {
		this.marketAppKey = marketAppKey;
	}
	public String getMarketAppSecret() {
		return marketAppSecret;
	}
	public void setMarketAppSecret(String marketAppSecret) {
		this.marketAppSecret = marketAppSecret;
	}
	public Integer getPubFlags() {
		return pubFlags;
	}
	public void setPubFlags(Integer pubFlags) {
		this.pubFlags = pubFlags;
	}
	public String getPosterPath() {
		return posterPath;
	}
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	public String getTopPath() {
		return topPath;
	}
	public void setTopPath(String topPath) {
		this.topPath = topPath;
	}
	public Integer getCodeFlags() {
		return codeFlags;
	}
	public void setCodeFlags(Integer codeFlags) {
		this.codeFlags = codeFlags;
	}
	public Long getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(Long useMoney) {
		this.useMoney = useMoney;
	}
	public Integer getFootadFlags() {
		return footadFlags;
	}
	public void setFootadFlags(Integer footadFlags) {
		this.footadFlags = footadFlags;
	}
	public String getFootadPath() {
		return footadPath;
	}
	public void setFootadPath(String footadPath) {
		this.footadPath = footadPath;
	}
	public String getFootadSlogan() {
		return footadSlogan;
	}
	public void setFootadSlogan(String footadSlogan) {
		this.footadSlogan = footadSlogan;
	}
	public String getFootbtnTitle() {
		return footbtnTitle;
	}
	public void setFootbtnTitle(String footbtnTitle) {
		this.footbtnTitle = footbtnTitle;
	}
	public Integer getFootbtnPosition() {
		return footbtnPosition;
	}
	public void setFootbtnPosition(Integer footbtnPosition) {
		this.footbtnPosition = footbtnPosition;
	}
	public String getFootbtnUrl() {
		return footbtnUrl;
	}
	public void setFootbtnUrl(String footbtnUrl) {
		this.footbtnUrl = footbtnUrl;
	}
	public BigDecimal getInteractnum() {
		return interactnum;
	}
	public void setInteractnum(BigDecimal interactnum) {
		this.interactnum = interactnum;
	}
	public BigDecimal getLackrate() {
		return lackrate;
	}
	public void setLackrate(BigDecimal lackrate) {
		this.lackrate = lackrate;
	}
	
}
