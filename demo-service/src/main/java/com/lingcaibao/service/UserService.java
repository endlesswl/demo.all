package com.lingcaibao.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.lingcaibao.cache.memcached.MarketMemcachedClient;
import com.lingcaibao.entity.Bank;
import com.lingcaibao.entity.Role;
import com.lingcaibao.entity.User;
import com.lingcaibao.entityenum.BankEnum;
import com.lingcaibao.entityenum.BankStatusEnum;
import com.lingcaibao.exception.LotteryException;
import com.lingcaibao.plugin.page.Page;
import com.lingcaibao.repository.UserDao;
import com.lingcaibao.statuscode.RealNameAuthFlags;
import com.lingcaibao.util.AccountContent;
import com.lingcaibao.util.CookieHelper;
import com.lingcaibao.util.DictionaryContent;
import com.lingcaibao.util.RegexUtils;
import com.palm.commom.plugin.paginator.mybatis.page.Common;
import com.palm.commom.uitl.Digests;
import com.palm.commom.uitl.Encodes;
import com.palm.commom.uitl.UUIDUtils;

/**
 * <p>
 * 标题：用户信息逻辑业务处理
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright (c) 2015
 * </p>
 * <p>
 * 公司: 北京零彩宝网络技术有限公司
 * </p>
 * <p>
 * 创建日期：2015年3月25日 上午11:33:55
 * </p>
 * <p>
 * 类全名：com.lingcaibao.service.UserService
 * </p>
 * <p>
 * 作者：JIJI
 * </p>
 * <p>
 * @version 1.0
 * </p>
 */
@Service
@Transactional
public class UserService {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private MarketMemcachedClient memcachedClient;
	@Autowired
	private UserDao userDao;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock writeLock = lock.writeLock();
	@Autowired
	BankService bankService;

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<User> searchPage(Map<String, Object> searchParams, Page<User> pageable) {
		return userDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 * @return
	 */
	public List<User> search(Map<String, Object> searchParas) {
		return userDao.search(searchParas);
	}

	public User get(Long id) {
		return userDao.get(id);
	}

	public void insert(User user) {
		userDao.insert(user);
	}

	/**
	 * 修改登录密码
	 * 
	 * @param user
	 * @param updatePwd
	 */
	public void modifyScrit(User user, String updatePwd) {
		byte[] salt = Encodes.decodeHex(user.getPwdSalt());
		byte[] hashPassword = Digests.sha1(updatePwd.getBytes(), salt, HASH_INTERATIONS);
		user.setPwd(Encodes.encodeHex(hashPassword));
		// 设置密码安全等级
		user.setPwdgrade(countPwdGrade(updatePwd));
		userDao.updateScrit(user);
	}

	/**
	 * 初始化登录密码
	 * 
	 * @param user
	 * @param updatePwd
	 */
	public void initPwd(User user, String updatePwd) {
		byte[] salt = Encodes.decodeHex(user.getPwdSalt());
		byte[] hashPassword = Digests.sha1(updatePwd.getBytes(), salt, HASH_INTERATIONS);
		user.setPwd(Encodes.encodeHex(hashPassword));
		// 设置密码安全等级
		user.setPwdgrade(countPwdGrade(updatePwd));
		userDao.initPwd(user);
	}

	// 修改支付密码
	public void modifyPayScrit(User user, String updatePwd) {
		byte[] salt = Encodes.decodeHex(user.getPayPwdSalt());
		byte[] hashPassword = Digests.sha1(updatePwd.getBytes(), salt, HASH_INTERATIONS);
		user.setPayPwd(Encodes.encodeHex(hashPassword));
		// 设置密码安全等级
		user.setPaypwdgrade(countPwdGrade(updatePwd));
		userDao.updatePayPwd(user);
	}

	/**
	 * 计算密码安全等级
	 * 
	 * @param pwd
	 * @return
	 */
	public int countPwdGrade(String pwd) {
		if (RegexUtils.idPwdBest(pwd)) {
			return 2;
		} else if (pwd.length() >= 8) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 验证登录密码
	 * 
	 * @description :
	 * @param :oldPwd 输入的原始密码
	 * @return ：
	 */
	public boolean checkPwd(User user, String oldPwd) {
		byte[] salt = Encodes.decodeHex(user.getPwdSalt());// 解密
		byte[] hashPassword = Digests.sha1(oldPwd.getBytes(), salt, HASH_INTERATIONS);
		String oldScrit = Encodes.encodeHex(hashPassword);// 加密
		return StringUtils.equals(user.getPwd(), oldScrit);
	}

	/**
	 * 验证支付密码
	 * 
	 * @description :
	 * @param :
	 * @return ：
	 */
	public boolean checkPayPwd(User user, String oldPwd) {
		byte[] salt = Encodes.decodeHex(user.getPayPwdSalt());// 解密
		byte[] hashPassword = Digests.sha1(oldPwd.getBytes(), salt, HASH_INTERATIONS);
		String oldScrit = Encodes.encodeHex(hashPassword);// 加密
		return StringUtils.equals(user.getPayPwd(), oldScrit);
	}

	// 更新用户的信息
	public void update(User user) {
		user.setModifyTime(new Date());
		userDao.update(user);
	}

	// 设置支付密码
	public void setPayPwd(User user, String updatePwd) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setPayPwdSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(updatePwd.getBytes(), salt, HASH_INTERATIONS);
		user.setPayPwd(Encodes.encodeHex(hashPassword));
		// 设置密码安全等级
		int grade = countPwdGrade(updatePwd);
		user.setPaypwdgrade(grade);
		userDao.updatePayPwdScrit(user);
	}

	// 找回支付密码
	public void findPayPwd(User user, String updatePwd) {
		byte[] salt = Encodes.decodeHex(user.getPayPwdSalt());// 解密
		byte[] hashPassword = Digests.sha1(updatePwd.getBytes(), salt, HASH_INTERATIONS);
		user.setPayPwd(Encodes.encodeHex(hashPassword));
		// 设置密码安全等级
		int grade = countPwdGrade(updatePwd);
		user.setPaypwdgrade(grade);
		userDao.updatePayPwdScrit(user);
	}

	// 更新绑定邮箱
	public void updateBindEmail(User user, String email) {
		user.setEmail(email);
		userDao.update(user);
	}

	// 更改绑定的手机
	public void updateBindMobile(User user) {
		userDao.update(user);
	}

	public void delete(Long id) {
		userDao.delete(id);
	}

	/**
	 * 个人手机版实名认证
	 * 
	 * @param user
	 * @param identifyNum
	 * @param realName
	 */
	public void updateIdentify(User user, String identifyNum, String realName) {
		user.setCardNo(identifyNum);
		user.setRealName(realName);
		user.setAuthflags(RealNameAuthFlags.AUTH_FLAGS_SUCCESS.ordinal());
		userDao.update(user);
	}

	/**
	 * 按照登录名查找用户
	 * 
	 * @param userName
	 * @return
	 */
	public User findUserByLoginName(String userName) {
		return userDao.findByUserName(userName);
	}

	/**
	 * 按照昵称查找用户
	 * 
	 * @param userName
	 * @return
	 */
	public User findUserByNickName(String nickname) {
		return userDao.findByNickName(nickname);
	}

	/**
	 * 查找手机号是否被绑定过
	 * 
	 * @param userName
	 * @return
	 */
	public User findUserByBindMobile(String mobile) {
		return userDao.findUserByBindMobile(mobile);
	}

	/**
	 * 查找邮箱是否被绑定过
	 * 
	 * @param userName
	 * @return
	 */
	public User findUserByBindEmail(String email) {
		return userDao.findUserByBindEmail(email);
	}

	public Long registerUserByMobile(String mobile, HttpServletRequest request) {
		User user = new User();
		user.setUsername(mobile);
		user.setPwd(mobile.substring(5));
		user.setUserType(AccountContent.TYPE_USER);
		user.setMobile(mobile);
		user.setRegip(Common.getIpAddr(request));
		// 设置密码安全等级
		user.setPwdgrade(countPwdGrade(mobile.substring(5)));
		user.setFlag(AccountContent.FLAG_PWD_INITIALIZE);
		user.setBindflags(AccountContent.BIND_FLAGS_MOBILE);
		// 设置引入渠道key
		user.setChannel(CookieHelper.getValue(request, "channelKey"));
		return registerUser(user);
	}

	public Long registerUser(User user) {
		entryptPassword(user);
		user.setCountry(DictionaryContent.DICT_CODE_CHINA);
		user.setCreateTime(new Date());
		user.setFlag(user.getUserType() == AccountContent.TYPE_PROXY ? AccountContent.FLAG_AUDIT : AccountContent.FLAG_NORMAL);
		user.setBalance(BigDecimal.ZERO);
		user.setPrize(BigDecimal.ZERO);
		user.setAmount(BigDecimal.ZERO);
		user.setBindflags(user.getBindflags() == null ? AccountContent.BIND_FLAGS_NONE : user.getBindflags());
		user.setAuthflags(RealNameAuthFlags.AUTH_FLAGS_UNAUTH.ordinal());
		user.setLogintimes(0);
		if (StringUtils.isEmpty(user.getNickname())) {
			user.setNickname("LCB" + UUIDUtils.getNumberBySix());
		}
		userDao.insert(user);
		return user.getId();
	}

	/**
	 * 根据用户名 + 登录模式获取用户
	 * 
	 * @param username
	 * @param userType
	 * @return
	 */
	public User findUserByUserType(String username, int userType) {
		return userDao.findUserByUserType(username, userType);
	}

	/**
	 * 更新用户登陆次数
	 * 
	 * @param user
	 */
	public void updateLogintimes(User user) {
		userDao.updateLogintimes(user);
	}

	/**
	 * 获得用户角色名称列表
	 *
	 * @param user
	 * @return
	 */
	public List<String> getRoleNameList(User user) {
		List<Role> roleList = userDao.findRoleLinks(user.getId());
		user.setRoles(roleList);
		List<String> roleNameList = Lists.newArrayList();
		for (Role role : roleList) {
			roleNameList.add(role.getName());
		}
		return roleNameList;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		user.setPwdgrade(countPwdGrade(user.getPwd()));
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setPwdSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(user.getPwd().getBytes(), salt, HASH_INTERATIONS);
		user.setPwd(Encodes.encodeHex(hashPassword));
	}

	/**
	 * 获取用户安全等级评分 总分：100 标识 bindflag
	 */
	public BigDecimal getUserGradeScores(User user) {
		BigDecimal gradeScores = BigDecimal.ZERO;
		// 登陆密码安全等级评分逻辑 case 2 :密码等级为高 case 1 :密码等级为中 case 0 :密码等级低
		if (user.getPwdgrade() != null) {
			switch (user.getPwdgrade()) {
			case 2:
				gradeScores = gradeScores.add(new BigDecimal("15"));
			case 1:
				gradeScores = gradeScores.add(new BigDecimal("10"));
			case 0:
				gradeScores = gradeScores.add(new BigDecimal("5"));
			}
		}
		// 支付密码安全等级评分逻辑 case 2 :密码等级为高 case 1 :密码等级为中 case 0 :密码等级低
		if (user.getPaypwdgrade() != null) {
			switch (user.getPaypwdgrade()) {
			case 2:
				gradeScores = gradeScores.add(new BigDecimal("15"));
			case 1:
				gradeScores = gradeScores.add(new BigDecimal("10"));
			case 0:
				gradeScores = gradeScores.add(new BigDecimal("5"));
			}
		}
		// 实名认证安全等级评分逻辑 分为实名认证和未实名认证
		if (user.getBindflags() != null) {
			if (user.isBindName()) {
				gradeScores = gradeScores.add(new BigDecimal("0"));
			} else {
				gradeScores = gradeScores.add(new BigDecimal("15"));
			}
		}
		// 手机绑定安全等级评分逻辑 绑定和未绑定
		if (user.getBindflags() != null) {
			if (user.isBindMobile()) {
				gradeScores = gradeScores.add(new BigDecimal("0"));
			} else {
				gradeScores = gradeScores.add(new BigDecimal("20"));
			}
		}
		// 邮箱绑定安全等级评分逻辑 绑定和未绑定
		if (user.getBindflags() != null) {
			if (user.isBindEmail()) {
				gradeScores = gradeScores.add(new BigDecimal("0"));
			} else {
				gradeScores = gradeScores.add(new BigDecimal("15"));
			}
		}
		// 银行卡绑定安全等级评分逻辑 分为两级 绑定和未绑定
		if (user.getBindflags() != null) {
			if (user.isBindBankCard()) {
				gradeScores = gradeScores.add(new BigDecimal("0"));
			} else {
				gradeScores = gradeScores.add(new BigDecimal("20"));
			}
		}
		return gradeScores;
	}

	/**
	 * 商家后台 账户明细 返回用户姓名和账户余额
	 * 
	 * @param id
	 *            用户id
	 * @return 姓名和账户余额
	 */
	public Map<String, Object> getUserNameAndAmount(Long id) {
		Map<String, Object> map = userDao.getUserNameAndAmount(id);
		return map;
	}

	/**
	 * 用户余额付款 付款原则 先balance,不足从prize扣除
	 * 
	 * @param userid
	 * @param amount
	 * @throws Exception
	 */
	public void doPay(Long userid, BigDecimal money) {
		writeLock.lock();
		try {
			User user = get(userid);
			if (user == null) {
				throw new LotteryException("无效的用户ID!");
			}
			if (user.getAmount().compareTo(money) < 0) {
				throw new LotteryException("用户余额不足!");
			}
			// balance 扣费
			if (user.getBalance().compareTo(money) >= 0) {
				userDao.doPay(user.getId(), money, null);
			}
			// balance + prize 扣费
			else {
				userDao.doPay(user.getId(), user.getBalance(), money.subtract(user.getBalance()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("------------pay err-------------:{}", userid);
			throw new RuntimeException(ex.getMessage());
		} finally {
			writeLock.unlock();
		}
	}

	public List<User> getUserListByUserName(String username) {
		return userDao.getUserListByUserName(username);
	}

	/**
	 * 获取用户零彩卷
	 */
	public BigDecimal getUserCoupon(long id) {
		return userDao.getUserCoupon(id);
	}

	/**
	 * 获取用户的总资产
	 */
	public List<Map<String, Object>> getUserTotalAssets(long id) {
		return userDao.getUserTotalAssets(id);
	}

	/**
	 * 获取当月用户总资产
	 */
	public BigDecimal getUserAssetsByMonth(long id) {
		return userDao.getUserAssetsByMonth(id);
	}

	/**
	 * 直接更新余额，不累加
	 *
	 * @param user
	 */
	public void updateBalanceNoPlus(User user) {
		userDao.updateBalanceNoPlus(user);
	}

	/**
	 * 更新余额，累加
	 *
	 * @param user
	 */
	public void updateBalance(User user) {
		userDao.updateBalance(user);
	}

	/**
	 * 更新奖金余额，累加
	 *
	 * @param user
	 */
	public void updatePrize(User user) {
		userDao.updatePrize(user);
	}

	/**
	 * 增加绑定标识位
	 * 
	 * @param bindflags
	 */
	public void addBindflags(User user) {
		userDao.addBindFlags(user);
	}

	/**
	 * 减少绑定标识位
	 * 
	 * @param bindflags
	 */
	public void subBindFlags(User user) {
		userDao.subBindFlags(user);
	}

	/**
	 * 更新用户授权零彩宝微信个数
	 * 
	 * @param user
	 */
	public void updateBindWxNum(User user) {
		userDao.updateBindWxNum(user);
	}

	public void updateUserInfo(User user, String cardNo, String realName, String cardno, String bankname, String abbreviation) throws Exception {
		// 实名验证
		user.setCardNo(cardNo);
		user.setRealName(realName);
		user.setAuthflags(RealNameAuthFlags.AUTH_FLAGS_SUCCESS.ordinal());
		// 手机绑定
		user.setBindflags(AccountContent.BIND_FLAGS_MOBILE);
		user.setMobile(user.getUsername());
		this.update(user);
		// 绑定银行卡
		// 处理银行卡绑定标识
		user.setBindflags(AccountContent.BIND_FLAGS_BANK);
		this.addBindflags(user);
		// 增加银行卡
		Bank newBank = new Bank();
		newBank.setAccountname(realName);
		newBank.setBankname(bankname);
		newBank.setCardno(cardno);
		newBank.setAbbreviation(abbreviation);
		newBank.setUserid(user.getId());
		newBank.setCardtype(BankEnum.BANK_CARDTYPEJ.getType());
		newBank.setDeleteFlag(BankStatusEnum.BANK_DELETEFLAGZ.ordinal());
		bankService.insert(newBank);
	}
}
