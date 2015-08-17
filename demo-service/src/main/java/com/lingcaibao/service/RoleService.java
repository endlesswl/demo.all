package com.lingcaibao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lingcaibao.plugin.page.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springside.modules.utils.DateProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lingcaibao.entity.Permission;
import com.lingcaibao.entity.Role;
import com.lingcaibao.repository.PermissionDao;
import com.lingcaibao.repository.RoleDao;
/**
* @Title: 
* @Description: 
* @Author jhe   
* @Date 2013 - 2015
* @Version V1.0
* @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
*/
// Spring Service Bean的标识.
@Service
@Transactional
public class RoleService
{
	private static Logger	logger	= LoggerFactory.getLogger(RoleService.class);
	@Autowired
	private RoleDao			roleDao;
	@Autowired
	private PermissionDao	permissionDao;

	/**
	 * 分页查询
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Role> searchPage(Map<String,Object> searchParams, Page pageable)
	{
		return roleDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * @param searchParas
	 * @return
	 */
	public List<Role> search(Map<String,Object> searchParas)
	{
		return roleDao.search(searchParas);
	}

	public Role get(Long id)
	{
		return roleDao.get(id);
	}

	public void insert(Role role)
	{
		roleDao.insert(role);
	}

	public void update(Role role)
	{
		roleDao.update(role);
	}

	public void delete(Long id)
	{
		roleDao.delete(id);
	}

	public List<String> getPermissionNameList(Role role)
	{
		// 查询出所有权限
		List<Permission> permisList = permissionDao.search(null);
		Map<String,String> map = Maps.newHashMap();
		for (Permission permission : permisList)
		{
			map.put(permission.getId() + "", permission.getName());
		}
		// 查询角色权限关联数据
		List<Map<String,Object>> permissionLinks = roleDao.findPermissionLinks(role.getId());
		List<String> permissionNameList = Lists.newArrayList();
		// 根据角色关联ID置入组装后的权限表达式
		for (Map<String,Object> record : permissionLinks)
		{
			String permissionids = (String) record.get("permissionid");
			String[] permissionid = permissionids.split("_");
			for (String pidString : permissionid)
			{
				permissionNameList.add(map.get(pidString));
			}
		}
		return permissionNameList;
	}
}
