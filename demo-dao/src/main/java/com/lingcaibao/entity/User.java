package com.lingcaibao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.lingcaibao.statuscode.RealNameAuthFlags;
public class User implements Serializable
{
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long	serialVersionUID		= 1L;
	// alias
	public static final String	TABLE_ALIAS				= "User";
	public static final String	ALIAS_ID				= "id";
	public static final String	ALIAS_USERNAME			= "username";
	public static final String	ALIAS_USERNAME2			= "username2";
	public static final String	ALIAS_PWD				= "pwd";
	public static final String	ALIAS_PWDSALT			= "pwdSalt";
	public static final String	ALIAS_PAYPWD			= "payPwd";
	public static final String	ALIAS_PAYPWDSALT		= "payPwdSalt";
	public static final String	ALIAS_REALNAME			= "realName";
	public static final String	ALIAS_CARDTYPE			= "cardType";
	public static final String	ALIAS_CARDNO			= "cardNo";
	public static final String	ALIAS_EMAIL				= "email";
	public static final String	ALIAS_MOBILE			= "mobile";
	public static final String	ALIAS_MOBILEAREA		= "mobileArea";
	public static final String	ALIAS_GENDER			= "gender";
	public static final String	ALIAS_QQ				= "qq";
	public static final String	ALIAS_PHONE				= "phone";
	public static final String	ALIAS_ADDRESS			= "address";
	public static final String	ALIAS_ZIPCODE			= "zipcode";
	public static final String	ALIAS_AVATAR			= "avatar";
	public static final String	ALIAS_REGIP				= "regip";
	public static final String	ALIAS_COMPANY			= "company";
	public static final String	ALIAS_LICENSESCAN		= "licenseScan";
	public static final String	ALIAS_USERTYPE			= "userType";
	public static final String	ALIAS_AMOUNT			= "amount";
	public static final String	ALIAS_LASTAMOUNTTIME	= "lastAmountTime";
	public static final String	ALIAS_BALANCE			= "balance";
	public static final String	ALIAS_SALT				= "salt";
	public static final String	ALIAS_LASTTIME			= "lastTime";
	public static final String	ALIAS_PRIZE				= "prize";
	public static final String	ALIAS_LASTPRIZETIME		= "lastPrizeTime";
	public static final String	ALIAS_FLAG				= "flag";
	public static final String	ALIAS_SOURCE			= "source";
	public static final String	ALIAS_LOGINTIMES		= "logintimes";
	public static final String	ALIAS_REMARK			= "remark";
	public static final String	ALIAS_CREATETIME		= "createTime";
	public static final String	ALIAS_MODIFYTIME		= "modifyTime";
	public static final String	ALIAS_OPENID			= "openid";
	public static final String	ALIAS_WARNBALANCE		= "warnBalance";
	public static final String	ALIAS_WARNFLAG			= "warnFlag";
	public static final String	ALIAS_CID				= "cid";
	public static final String	ALIAS_VERSIONCODE		= "versionCode";
	public static final String	ALIAS_GRADE				= "grade";
	public static final String	ALIAS_PWDGRADE			= "pwdgrade";
	public static final String	ALIAS_PAYPWDGRADE		= "paypwdgrade";
	public static final String	ALIAS_GRADESCORES		= "gradescores";
	public static final String	ALIAS_FAX				= "fax";
	public static final String	ALIAS_BIRTHDAY			= "birthday";
	public static final String	ALIAS_LIVENESS			= "liveness";
	public static final String	ALIAS_BINDFLAGS			= "bindflags";
	public static final String	ALIAS_COUNTRY			= "country";
	public static final String	ALIAS_PROVINCE			= "province";
	public static final String	ALIAS_CITY				= "city";
	public static final String	ALIAS_AREA				= "area";
	public static final String	ALIAS_NICKNAME			= "nickname";
	public static final String	ALIAS_CHANNEL			= "channel";
	/**
	 * 
	 */
	private Long				id;
	/**
	 * 用户名
	 */
	private String				username;
	/**
	 * 用户名2
	 */
	private String				username2;
	/**
	 * 密码
	 */
	private String				pwd;
	/**
	 * 密码校验标识
	 */
	private String				pwdSalt;
	/**
	 * 支付密码
	 */
	private String				payPwd;
	/**
	 * 支付密码校验标识
	 */
	private String				payPwdSalt;
	/**
	 * 真实姓名
	 */
	private String				realName;
	/**
	 * 证件类型
	 */
	private Integer				cardType;
	/**
	 * 证件号码
	 */
	private String				cardNo;
	/**
	 * 邮箱
	 */
	private String				email;
	/**
	 * 手机
	 */
	private String				mobile;
	/**
	 * 手机注册地
	 */
	private String				mobileArea;
	/**
	 * 性别1:男 2:女
	 */
	private Integer				gender;
	/**
	 * QQ号码
	 */
	private String				qq;
	/**
	 * 固话
	 */
	private String				phone;
	/**
	 * 地址
	 */
	private String				address;
	/**
	 * 邮编
	 */
	private String				zipcode;
	/**
	 * 头像
	 */
	private String				avatar;
	/**
	 * 注册IP
	 */
	private String				regip;
	/**
	 * 公司名称
	 */
	private String				company;
	/**
	 * 营业执照副本扫描件
	 */
	private String				licenseScan;
	/**
	 * 用户类型1:个人用户 2:商户 3:管理员
	 */
	private Integer				userType;
	/**
	 * 总额
	 */
	private BigDecimal			amount;
	/**
	 * 总额最后修改时间
	 */
	private java.util.Date		lastAmountTime;
	/**
	 * 余额
	 */
	private BigDecimal			balance;
	/**
	 * 余额校验标识
	 */
	private String				salt;
	/**
	 * 余额最后修改时间
	 */
	private java.util.Date		lastTime;
	/**
	 * 奖金
	 */
	private BigDecimal			prize;
	/**
	 * 奖金最后修改时间
	 */
	private java.util.Date		lastPrizeTime;
	/**
	 * 用户状态 0:正常 1:锁定 2:删除
	 */
	private Integer				flag;
	/**
	 * 注册来源，1：手机 2：网站
	 */
	private Integer				source;
	/**
	 * 登录次数
	 */
	private Integer				logintimes;
	/**
	 * 备注
	 */
	private String				remark;
	/**
	 * 注册时间
	 */
	private java.util.Date		createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date		modifyTime;
	/**
	 * 微信用户唯一标识
	 */
	private String				openid;
	/**
	 * 余额预警值
	 */
	private Long				warnBalance;
	/**
	 * 是否已预警Y/N
	 */
	private String				warnFlag;
	/**
	 * 用户中心ID
	 */
	private String				cid;
	/**
	 * 用户中心版本号
	 */
	private Integer				versionCode;
	/**
	 * 代理商等级
	 */
	private Integer				grade;
	/**
	 * 登录密码安全等级
	 */
	private Integer				pwdgrade;
	/**
	 * 交易密码安全等级
	 */
	private Integer				paypwdgrade;
	/**
	 * 安全等级评分
	 */
	private BigDecimal			gradescores;
	/**
	 * 传真
	 */
	private String				fax;
	/**
	 * 生日
	 */
	private java.util.Date		birthday;
	/**
	 * 用户活跃度
	 */
	private BigDecimal			liveness;
	/**
	 * 角色列表
	 */
	private List<Role>			roles;
	/**
	 * 用户信息完善百分数
	 */
	private BigDecimal			percents;
	/**
	 * 绑定标识 1:手机绑定 2:邮箱绑定 4:银行卡绑定
	 */
	private int					bindflags;
	/**
	 * 身份证图片地址
	 */
	private String				idcardimg;
	/**
	 * 实名认证标志
	 */
	private int					authflags;
	/**
	 * 实名认证结果
	 */
	private String				authmsg;
	/**
	 * 国别
	 */
	private String				country;
	/**
	 * 省份
	 */
	private String				province;
	/**
	 * 城市
	 */
	private String				city;
	/**
	 * 区
	 */
	private String				area;
	/**
	 * 昵称
	 */
	private String				nickname;
	/**
	 * 微信平台授权微信数量
	 */
	private Integer				bindWxNum;
	/**
	 * 注册渠道key
	 */
	private String				channel;

	public java.lang.Long getId()
	{
		return this.id;
	}

	public void setId(java.lang.Long value)
	{
		this.id = value;
	}

	public java.lang.String getUsername()
	{
		return this.username;
	}

	public void setUsername(java.lang.String value)
	{
		this.username = value;
	}

	public java.lang.String getUsername2()
	{
		return this.username2;
	}

	public void setUsername2(java.lang.String value)
	{
		this.username2 = value;
	}

	public java.lang.String getPwd()
	{
		return this.pwd;
	}

	public void setPwd(java.lang.String value)
	{
		this.pwd = value;
	}

	public java.lang.String getPwdSalt()
	{
		return this.pwdSalt;
	}

	public void setPwdSalt(java.lang.String value)
	{
		this.pwdSalt = value;
	}

	public java.lang.String getPayPwd()
	{
		return this.payPwd;
	}

	public void setPayPwd(java.lang.String value)
	{
		this.payPwd = value;
	}

	public java.lang.String getPayPwdSalt()
	{
		return this.payPwdSalt;
	}

	public void setPayPwdSalt(java.lang.String value)
	{
		this.payPwdSalt = value;
	}

	public java.lang.String getRealName()
	{
		return this.realName;
	}

	public void setRealName(java.lang.String value)
	{
		this.realName = value;
	}

	public Integer getCardType()
	{
		return this.cardType;
	}

	public void setCardType(Integer value)
	{
		this.cardType = value;
	}

	public java.lang.String getCardNo()
	{
		return this.cardNo;
	}

	public void setCardNo(java.lang.String value)
	{
		this.cardNo = value;
	}

	public String getSafeCardNo()
	{
		if (StringUtils.isEmpty(cardNo))
		{
			return null;
		}
		String top = cardNo.substring(0, 1);
		String boot = cardNo.substring(cardNo.length() - 1, cardNo.length());
		for (int i = 0; i < cardNo.length() - 2; i++)
		{
			top += "*";
		}
		top += boot;
		return top;
	}

	public String getSafeMobile()
	{
		if (StringUtils.isEmpty(mobile))
		{
			return null;
		}
		String top = mobile.substring(0, 3);
		String boot = mobile.substring(mobile.length() - 4, mobile.length());
		for (int i = 0; i < mobile.length() - 7; i++)
		{
			top += "*";
		}
		top += boot;
		return top;
	}

	public java.lang.String getEmail()
	{
		return this.email;
	}

	public void setEmail(java.lang.String value)
	{
		this.email = value;
	}

	public java.lang.String getMobile()
	{
		return this.mobile;
	}

	public void setMobile(java.lang.String value)
	{
		this.mobile = value;
	}

	public java.lang.String getMobileArea()
	{
		return this.mobileArea;
	}

	public void setMobileArea(java.lang.String value)
	{
		this.mobileArea = value;
	}

	public Integer getGender()
	{
		return this.gender;
	}

	public void setGender(Integer value)
	{
		this.gender = value;
	}

	public java.lang.String getQq()
	{
		return this.qq;
	}

	public void setQq(java.lang.String value)
	{
		this.qq = value;
	}

	public java.lang.String getPhone()
	{
		return this.phone;
	}

	public void setPhone(java.lang.String value)
	{
		this.phone = value;
	}

	public java.lang.String getAddress()
	{
		return this.address;
	}

	public void setAddress(java.lang.String value)
	{
		this.address = value;
	}

	public java.lang.String getZipcode()
	{
		return this.zipcode;
	}

	public void setZipcode(java.lang.String value)
	{
		this.zipcode = value;
	}

	public java.lang.String getAvatar()
	{
		return this.avatar;
	}

	public void setAvatar(java.lang.String value)
	{
		this.avatar = value;
	}

	public java.lang.String getRegip()
	{
		return this.regip;
	}

	public void setRegip(java.lang.String value)
	{
		this.regip = value;
	}

	public java.lang.String getCompany()
	{
		return this.company;
	}

	public void setCompany(java.lang.String value)
	{
		this.company = value;
	}

	public java.lang.String getLicenseScan()
	{
		return this.licenseScan;
	}

	public void setLicenseScan(java.lang.String value)
	{
		this.licenseScan = value;
	}

	public Integer getUserType()
	{
		return this.userType;
	}

	public void setUserType(Integer value)
	{
		this.userType = value;
	}

	public BigDecimal getAmount()
	{
		return this.amount;
	}

	public void setAmount(BigDecimal value)
	{
		this.amount = value;
	}

	public java.util.Date getLastAmountTime()
	{
		return this.lastAmountTime;
	}

	public void setLastAmountTime(java.util.Date value)
	{
		this.lastAmountTime = value;
	}

	public BigDecimal getBalance()
	{
		return this.balance;
	}

	public void setBalance(BigDecimal value)
	{
		this.balance = value;
	}

	public java.lang.String getSalt()
	{
		return this.salt;
	}

	public void setSalt(java.lang.String value)
	{
		this.salt = value;
	}

	public java.util.Date getLastTime()
	{
		return this.lastTime;
	}

	public void setLastTime(java.util.Date value)
	{
		this.lastTime = value;
	}

	public BigDecimal getPrize()
	{
		return this.prize;
	}

	public void setPrize(BigDecimal value)
	{
		this.prize = value;
	}

	public java.util.Date getLastPrizeTime()
	{
		return this.lastPrizeTime;
	}

	public void setLastPrizeTime(java.util.Date value)
	{
		this.lastPrizeTime = value;
	}

	public Integer getFlag()
	{
		return this.flag;
	}

	public void setFlag(Integer value)
	{
		this.flag = value;
	}

	public Integer getSource()
	{
		return this.source;
	}

	public void setSource(Integer value)
	{
		this.source = value;
	}

	public java.lang.Integer getLogintimes()
	{
		return this.logintimes;
	}

	public void setLogintimes(java.lang.Integer value)
	{
		this.logintimes = value;
	}

	public java.lang.String getRemark()
	{
		return this.remark;
	}

	public void setRemark(java.lang.String value)
	{
		this.remark = value;
	}

	public java.util.Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value)
	{
		this.createTime = value;
	}

	public java.util.Date getModifyTime()
	{
		return this.modifyTime;
	}

	public void setModifyTime(java.util.Date value)
	{
		this.modifyTime = value;
	}

	public java.lang.String getOpenid()
	{
		return this.openid;
	}

	public void setOpenid(java.lang.String value)
	{
		this.openid = value;
	}

	public Long getWarnBalance()
	{
		return this.warnBalance;
	}

	public void setWarnBalance(Long value)
	{
		this.warnBalance = value;
	}

	public java.lang.String getWarnFlag()
	{
		return this.warnFlag;
	}

	public void setWarnFlag(java.lang.String value)
	{
		this.warnFlag = value;
	}

	public java.lang.String getCid()
	{
		return this.cid;
	}

	public void setCid(java.lang.String value)
	{
		this.cid = value;
	}

	public java.lang.Integer getVersionCode()
	{
		return this.versionCode;
	}

	public void setVersionCode(java.lang.Integer value)
	{
		this.versionCode = value;
	}

	public Integer getGrade()
	{
		return this.grade;
	}

	public void setGrade(Integer value)
	{
		this.grade = value;
	}

	public Integer getPwdgrade()
	{
		return this.pwdgrade;
	}

	public void setPwdgrade(Integer value)
	{
		this.pwdgrade = value;
	}

	public Integer getPaypwdgrade()
	{
		return this.paypwdgrade;
	}

	public void setPaypwdgrade(Integer value)
	{
		this.paypwdgrade = value;
	}

	public BigDecimal getGradescores()
	{
		return this.gradescores;
	}

	public void setGradescores(BigDecimal value)
	{
		this.gradescores = value;
	}

	public java.lang.String getFax()
	{
		return this.fax;
	}

	public void setFax(java.lang.String value)
	{
		this.fax = value;
	}

	public java.util.Date getBirthday()
	{
		return this.birthday;
	}

	public void setBirthday(java.util.Date value)
	{
		this.birthday = value;
	}

	public BigDecimal getLiveness()
	{
		return liveness;
	}

	public void setLiveness(BigDecimal liveness)
	{
		this.liveness = liveness;
	}

	public List<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(List<Role> roles)
	{
		this.roles = roles;
	}

	public BigDecimal getPercents()
	{
		return percents;
	}

	public void setPercents(BigDecimal percents)
	{
		this.percents = percents;
	}

	public Integer getBindflags()
	{
		return bindflags;
	}

	public void setBindflags(Integer bindflags)
	{
		this.bindflags = bindflags;
	}

	/**
	 * 是否已实名认证
	 * 
	 * @return
	 */
	public boolean isBindName()
	{
		return (authflags == RealNameAuthFlags.AUTH_FLAGS_SUCCESS.ordinal());
	}

	/**
	 * 是否已绑定手机
	 * 
	 * @return
	 */
	public boolean isBindMobile()
	{
		return (bindflags & 1) != 0;
	}

	/**
	 * 是否已绑定邮箱
	 * 
	 * @return
	 */
	public boolean isBindEmail()
	{
		return (bindflags & 2) != 0;
	}

	/**
	 * 是否已绑定银行卡
	 * 
	 * @return
	 */
	public boolean isBindBankCard()
	{
		return (bindflags & 4) != 0;
	}

	public String getIdcardimg()
	{
		return idcardimg;
	}

	public void setIdcardimg(String idcardimg)
	{
		this.idcardimg = idcardimg;
	}

	public Integer getAuthflags()
	{
		return authflags;
	}

	public void setAuthflags(Integer authflags)
	{
		this.authflags = authflags;
	}

	public String getAuthmsg()
	{
		return authmsg;
	}

	public void setAuthmsg(String authmsg)
	{
		this.authmsg = authmsg;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public Integer getBindWxNum()
	{
		return bindWxNum;
	}

	public void setBindWxNum(Integer bindWxNum)
	{
		this.bindWxNum = bindWxNum;
	}

	public String getChannel()
	{
		return channel;
	}

	public void setChannel(String channel)
	{
		this.channel = channel;
	}
}