package com.lingcaibao.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.lingcaibao.plugin.page.Page;
import org.springframework.stereotype.Repository;
import com.lingcaibao.entity.Role;
import com.lingcaibao.entity.User;
@Repository("userDao")
public interface UserDao
{
	User get(Long id);

	List<User> search(Map<String,Object> parameters);

	Page<User> searchPage(@Param("searchFields") Map<String,Object> searchParams, Page<User> pageable);

	void insert(User user);

	void delete(Long id);

	/**
	 * 更新用户信息
	 */
	void update(User user);

	/**
	 * 修改登录密码
	 */
	void updateScrit(User user);
	
	/**
	 * 初始化登录密码
	 */
	void initPwd(User user);

	/**
	 * 更新支付密码
	 */
	void updatePayPwd(User user);

	/**商家后台 账户明细  返回用户姓名和账户余额
	 * @param id 用户id
	 * @return 姓名和账户余额
	 */
	Map<String,Object> getUserNameAndAmount(Long id);

	/**
	 * 
	 * 设置支付密码
	 */
	void updatePayPwdScrit(User user);

	User findByUserName(@Param("username") String username);
	
	User findByNickName(@Param("nickname") String nickname);

	User findUserByBindMobile(@Param("mobile") String mobile);
	
	User findUserByBindEmail(@Param("email") String email);

	User findUserByUserType(@Param("username") String username, @Param("userType") int userType);

	void updateLogintimes(User user);

	List<Role> findRoleLinks(Long userId);

	/**
	 * 用户付款
	 * @param balance
	 * @param prize
	 */
	void doPay(@Param("id") Long id, @Param("balance") BigDecimal balance, @Param("prize") BigDecimal prize);

	/**根据用户名模糊查找
	 * @param username
	 * @return
	 */
	List<User> getUserListByUserName(@Param("username") String username);

	/**
	 * 获取用户零彩卷
	 */
	BigDecimal getUserCoupon(long id);

	/**
	 * 获取用户的总资产
	 */
	List<Map<String,Object>> getUserTotalAssets(long id);

	/**
	 * 获取当月总资产
	 */
	BigDecimal getUserAssetsByMonth(long id);

	/**
	 * 更新用户余额
	 * @param user
	 */
	void updateBalance(User user);

	/**
	 * 更新用户奖金
	 * @param user
	 */
	void updatePrize(User user);

	/**
	 * 更新用户余额,无累加
	 * @param user
	 */
	void updateBalanceNoPlus(User user);
	
	/**
	 * 增加绑定标识位
	 * @param bindflags
	 */
	void addBindFlags(User user);
	
	/**
	 * 减少绑定标识位
	 * @param bindflags
	 */
	void subBindFlags(User user);

	void updateBindWxNum(User user);
}
