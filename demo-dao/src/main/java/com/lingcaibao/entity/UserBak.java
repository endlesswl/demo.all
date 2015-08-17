package com.lingcaibao.entity;

import java.math.BigDecimal;


public class UserBak {

	//alias
	public static final String TABLE_ALIAS = "UserBak";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERNAME = "username";
	public static final String ALIAS_PWD = "pwd";
	public static final String ALIAS_PWDSALT = "pwdSalt";
	public static final String ALIAS_PAYPWD = "payPwd";
	public static final String ALIAS_PAYPWDSALT = "payPwdSalt";
	public static final String ALIAS_REALNAME = "realName";
	public static final String ALIAS_CARDTYPE = "cardType";
	public static final String ALIAS_CARDNO = "cardNo";
	public static final String ALIAS_EMAIL = "email";
	public static final String ALIAS_MOBILE = "mobile";
	public static final String ALIAS_MOBILEAREA = "mobileArea";
	public static final String ALIAS_GENDER = "gender";
	public static final String ALIAS_QQ = "qq";
	public static final String ALIAS_PHONE = "phone";
	public static final String ALIAS_ADDRESS = "address";
	public static final String ALIAS_ZIPCODE = "zipcode";
	public static final String ALIAS_AVATAR = "avatar";
	public static final String ALIAS_REGIP = "regip";
	public static final String ALIAS_COMPANY = "company";
	public static final String ALIAS_LICENSESCAN = "licenseScan";
	public static final String ALIAS_USERTYPE = "userType";
	public static final String ALIAS_BALANCE = "balance";
	public static final String ALIAS_SALT = "salt";
	public static final String ALIAS_LASTTIME = "lastTime";
	public static final String ALIAS_PRIZE = "prize";
	public static final String ALIAS_LASTPRIZETIME = "lastPrizeTime";
	public static final String ALIAS_FLAG = "flag";
	public static final String ALIAS_SOURCE = "source";
	public static final String ALIAS_LOGINTIMES = "logintimes";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_CREATETIME = "createTime";
	public static final String ALIAS_MODIFYTIME = "modifyTime";
	public static final String ALIAS_OPENID = "openid";

	/**
	 * 
	 */
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 密码校验标识
	 */
	private String pwdSalt;
	/**
	 * 支付密码
	 */
	private String payPwd;
	/**
	 * 支付密码校验标识
	 */
	private String payPwdSalt;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 证件类型
	 */
	private Integer cardType;
	/**
	 * 证件号码
	 */
	private String cardNo;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 手机注册地
	 */
	private String mobileArea;
	/**
	 *  性别1:男 2:女
	 */
	private Integer gender;
	/**
	 * QQ号码
	 */
	private String qq;
	/**
	 * 固话
	 */
	private String phone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String zipcode;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 注册IP
	 */
	private String regip;
	/**
	 * 公司名称
	 */
	private String company;
	/**
	 * 营业执照副本扫描件
	 */
	private String licenseScan;
	/**
	 * 用户类型1:个人用户 2:商户 3:管理员
	 */
	private Integer userType;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 余额校验标识
	 */
	private String salt;
	/**
	 * 余额最后修改时间
	 */
	private java.util.Date lastTime;
	/**
	 * 奖金
	 */
	private BigDecimal prize;
	/**
	 * 奖金最后修改时间
	 */
	private java.util.Date lastPrizeTime;
	/**
	 * 用户状态 0:正常 1:锁定 2:删除
	 */
	private Integer flag;
	/**
	 * 注册来源，1：手机 2：网站
	 */
	private Integer source;
	/**
	 * 登录次数
	 */
	private Integer logintimes;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 注册时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date modifyTime;
	/**
	 * 微信用户唯一标识 
	 */
	private String openid;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	public java.lang.String getPwd() {
		return this.pwd;
	}
	
	public void setPwd(java.lang.String value) {
		this.pwd = value;
	}
	public java.lang.String getPwdSalt() {
		return this.pwdSalt;
	}
	
	public void setPwdSalt(java.lang.String value) {
		this.pwdSalt = value;
	}
	public java.lang.String getPayPwd() {
		return this.payPwd;
	}
	
	public void setPayPwd(java.lang.String value) {
		this.payPwd = value;
	}
	public java.lang.String getPayPwdSalt() {
		return this.payPwdSalt;
	}
	
	public void setPayPwdSalt(java.lang.String value) {
		this.payPwdSalt = value;
	}
	public java.lang.String getRealName() {
		return this.realName;
	}
	
	public void setRealName(java.lang.String value) {
		this.realName = value;
	}
	public Integer getCardType() {
		return this.cardType;
	}
	
	public void setCardType(Integer value) {
		this.cardType = value;
	}
	public java.lang.String getCardNo() {
		return this.cardNo;
	}
	
	public void setCardNo(java.lang.String value) {
		this.cardNo = value;
	}
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
	public java.lang.String getMobileArea() {
		return this.mobileArea;
	}
	
	public void setMobileArea(java.lang.String value) {
		this.mobileArea = value;
	}
	public Integer getGender() {
		return this.gender;
	}
	
	public void setGender(Integer value) {
		this.gender = value;
	}
	public java.lang.String getQq() {
		return this.qq;
	}
	
	public void setQq(java.lang.String value) {
		this.qq = value;
	}
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	public java.lang.String getZipcode() {
		return this.zipcode;
	}
	
	public void setZipcode(java.lang.String value) {
		this.zipcode = value;
	}
	public java.lang.String getAvatar() {
		return this.avatar;
	}
	
	public void setAvatar(java.lang.String value) {
		this.avatar = value;
	}
	public java.lang.String getRegip() {
		return this.regip;
	}
	
	public void setRegip(java.lang.String value) {
		this.regip = value;
	}
	public java.lang.String getCompany() {
		return this.company;
	}
	
	public void setCompany(java.lang.String value) {
		this.company = value;
	}
	public java.lang.String getLicenseScan() {
		return this.licenseScan;
	}
	
	public void setLicenseScan(java.lang.String value) {
		this.licenseScan = value;
	}
	public Integer getUserType() {
		return this.userType;
	}
	
	public void setUserType(Integer value) {
		this.userType = value;
	}
	
	public java.lang.String getSalt() {
		return this.salt;
	}
	
	public void setSalt(java.lang.String value) {
		this.salt = value;
	}
	public java.util.Date getLastTime() {
		return this.lastTime;
	}
	
	public void setLastTime(java.util.Date value) {
		this.lastTime = value;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getPrize() {
		return prize;
	}

	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	public java.util.Date getLastPrizeTime() {
		return this.lastPrizeTime;
	}
	
	public void setLastPrizeTime(java.util.Date value) {
		this.lastPrizeTime = value;
	}
	public Integer getFlag() {
		return this.flag;
	}
	
	public void setFlag(Integer value) {
		this.flag = value;
	}
	public Integer getSource() {
		return this.source;
	}
	
	public void setSource(Integer value) {
		this.source = value;
	}
	public java.lang.Integer getLogintimes() {
		return this.logintimes;
	}
	
	public void setLogintimes(java.lang.Integer value) {
		this.logintimes = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getModifyTime() {
		return this.modifyTime;
	}
	
	public void setModifyTime(java.util.Date value) {
		this.modifyTime = value;
	}
	public java.lang.String getOpenid() {
		return this.openid;
	}
	
	public void setOpenid(java.lang.String value) {
		this.openid = value;
	}
}