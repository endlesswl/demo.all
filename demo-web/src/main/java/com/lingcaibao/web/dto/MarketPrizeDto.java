package com.lingcaibao.web.dto;

import java.math.BigDecimal;

public class MarketPrizeDto {
	/**
	 * 用户奖励索引
	 */
	private int					index;
	/**
	 * ID
	 */
	private Long				id;
	/**
	 * 活动ID
	 */
	private Long				marketid;
	/**
	 * 奖品等级
	 */
	private Integer				prizelevel;
	/**
	 * 奖品类型 0:平台介质 1:自定义
	 */
	private Integer				prizeflag;
	/**
	 * 奖品编号
	 */
	private String				prizecode;
	/**
	 * 奖品名称
	 */
	private String				prizename;
	/**
	 * 奖品数量
	 */
	private Long				prizenum;
	/**
	 * 频率单位 0:不限 h:小时 d:日 w:周 m:月 q:季度 y:年
	 */
	private String				unit;
	/**
	 * 奖品面额
	 */
	private BigDecimal			quota;
	/**
	 * 中奖概率
	 */
	private BigDecimal			probability;
	/**
	 * 创建时间
	 */
	private java.util.Date		createtime;
	/**
	 * 修改时间
	 */
	private java.util.Date		modifytime;
	/**
	 * 奖品使用数量
	 */
	private Long				prizeusenum;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMarketid() {
		return marketid;
	}
	public void setMarketid(Long marketid) {
		this.marketid = marketid;
	}
	public Integer getPrizelevel() {
		return prizelevel;
	}
	public void setPrizelevel(Integer prizelevel) {
		this.prizelevel = prizelevel;
	}
	public Integer getPrizeflag() {
		return prizeflag;
	}
	public void setPrizeflag(Integer prizeflag) {
		this.prizeflag = prizeflag;
	}
	public String getPrizecode() {
		return prizecode;
	}
	public void setPrizecode(String prizecode) {
		this.prizecode = prizecode;
	}
	public String getPrizename() {
		return prizename;
	}
	public void setPrizename(String prizename) {
		this.prizename = prizename;
	}
	public Long getPrizenum() {
		return prizenum;
	}
	public void setPrizenum(Long prizenum) {
		this.prizenum = prizenum;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getQuota() {
		return quota;
	}
	public void setQuota(BigDecimal quota) {
		this.quota = quota;
	}
	public BigDecimal getProbability() {
		return probability;
	}
	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}
	public java.util.Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	public java.util.Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(java.util.Date modifytime) {
		this.modifytime = modifytime;
	}
	public Long getPrizeusenum() {
		return prizeusenum;
	}
	public void setPrizeusenum(Long prizeusenum) {
		this.prizeusenum = prizeusenum;
	}
	
}
