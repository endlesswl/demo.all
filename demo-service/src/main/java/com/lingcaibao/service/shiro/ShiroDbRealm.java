package com.lingcaibao.service.shiro;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.lingcaibao.entity.Role;
import com.lingcaibao.entity.User;
import com.lingcaibao.service.RoleService;
import com.lingcaibao.service.UserService;
import com.lingcaibao.shiro.UserToken;
import com.lingcaibao.util.AccountContent;
import com.palm.commom.uitl.Digests;
import com.palm.commom.uitl.Encodes;
/**
 * <p>标题：用户登陆认证类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月13日 下午4:18:20</p>
 * <p>类全名：com.lingcaibao.service.shiro.ShiroDbRealm</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class ShiroDbRealm extends AuthorizingRealm
{
	protected UserService	userService;
	protected RoleService	roleService;
	private static Logger	logger	= LoggerFactory.getLogger(ShiroDbRealm.class);

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException
	{
		try
		{
			logger.info("----------- doGetAuthenticationInfo ----------");
			UserToken token = (UserToken) authcToken;
			logger.info("----------- token ----------:{}", JSON.toJSONString(token));
			User user = userService.findUserByUserType(token.getUsername(), token.getUserType());
			logger.info("----------- user ----------:{}", JSON.toJSONString(user));
			if (user != null)
			{
				if (user.getFlag() == AccountContent.FLAG_LOCKED)
				{
					throw new LockedAccountException("账号 [" + user.getUsername() + "] 已锁定.");
				}
				/*if (user.getFlag() == AccountContent.FLAG_AUDIT)
				{
					throw new LockedAccountException("账号 [" + user.getUsername() + "] 正在审核中，审核结果会第一时间通知您，请耐心等待。");
				}*/
				userService.updateLogintimes(user);
				byte[] pwdSalt = Encodes.decodeHex(user.getPwdSalt());
				String password = new String(token.getPassword());
				// 渠道授权
				if (token.isChaAuto())
				{
					byte[] hashPassword = Digests.sha1(password.getBytes(), Encodes.decodeHex(user.getPwdSalt()), UserService.HASH_INTERATIONS);
					user.setPwd(Encodes.encodeHex(hashPassword));
				}
				logger.info("----------------登录认证------------------{}", user.getUsername());
				return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getUsername(), password, user.getRealName(), user.getUserType()), user.getPwd(), ByteSource.Util.bytes(pwdSalt),
						getName());
			} else
			{
				return null;
			}
		} catch (Exception ex)
		{
			if (ex instanceof AuthenticationException)
			{
				throw ex;
			} else
			{
				throw new AuthenticationException(ex.getMessage());
			}
		}
	}

	/**
	 * 权限初始化
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		try
		{
			logger.info("----------- doGetAuthorizationInfo ----------");
			ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
			logger.info("----------- shiroUser ----------:{}", JSON.toJSONString(shiroUser));
			User user = userService.findUserByUserType(shiroUser.userName, shiroUser.userType);
			logger.info("----------- user ----------:{}", JSON.toJSONString(user));
			if (user == null)
			{
				return null;
			}
			if (user.getFlag() == AccountContent.FLAG_LOCKED)
			{
				throw new LockedAccountException("账号 [" + user.getUsername() + "] 已锁定。");
			}
			/*
			if (user.getFlag() == AccountContent.FLAG_AUDIT)
			{
				throw new LockedAccountException("账号 [" + user.getUsername() + "] 正在审核中，审核结果会第一时间通知您，请耐心等待。");
			}
			*/
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 初始化角色数据
			List<String> roleList = userService.getRoleNameList(user);
			// 如果用户类型为个人用户，默认置角色为user
			if (user.getUserType() == AccountContent.TYPE_USER)
			{
				roleList.add(AccountContent.ROLE_USER);
			}
			// 如果用户类型为商家，默认置角色为business
			if (user.getUserType() == AccountContent.TYPE_BUSINESS)
			{
				roleList.add(AccountContent.ROLE_BUSINESS);
			}
			// 如果用户类型为代理商，默认置角色为proxy
			if (user.getUserType() == AccountContent.TYPE_PROXY)
			{
				roleList.add(AccountContent.ROLE_PROXY);
			}
			logger.info("----------- roleList ----------:{}", JSON.toJSONString(roleList));
			info.addRoles(roleList);
			// 初始化权限数据
			for (Role role : user.getRoles())
			{
				info.addStringPermissions(roleService.getPermissionNameList(role));
			}
			logger.info("----------- info ----------:{}", JSON.toJSONString(info));
			logger.info("--------------权限初始化成功----------------{}", shiroUser.userName);
			return info;
		} catch (Exception ex)
		{
			if (ex instanceof AuthenticationException)
			{
				throw ex;
			} else
			{
				throw new AuthenticationException(ex.getMessage());
			}
		}
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher()
	{
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(userService.HASH_ALGORITHM);
		matcher.setHashIterations(userService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	@Autowired
	public void setuserService(UserService userService)
	{
		this.userService = userService;
	}

	@Autowired
	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable
	{
		private static final long	serialVersionUID	= -1373760761780840081L;
		public Long					id;
		public String				userName;
		public String				password;
		public String				realName;
		public int					userType;

		public ShiroUser(Long id, String userName, String password, String realName, int userType)
		{
			this.id = id;
			this.userName = userName;
			this.password = password;
			this.realName = realName;
			this.userType = userType;
		}

		public String getRealName()
		{
			return realName;
		}

		public int getUserType()
		{
			return userType;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString()
		{
			return userName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode()
		{
			return Objects.hashCode(userName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (userName == null)
			{
				if (other.userName != null)
				{
					return false;
				}
			} else if (!userName.equals(other.userName))
			{
				return false;
			}
			return true;
		}
	}
}
