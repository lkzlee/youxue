package com.youxue.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.SysRoleDao;
import com.youxue.core.vo.SysRole;

/**
 * 后台用户角色
 * @author www.inxedu.com
 */
@Repository("sysRoleDao")
public class SysRoleDaoImpl extends BaseDao implements SysRoleDao
{

	public int createRoel(SysRole sysRole)
	{
		sqlSessionTemplate.insert("SysRoleMapper.createRoel", sysRole);
		return sysRole.getRoleId();
	}

	public void updateRole(SysRole sysRole)
	{
		sqlSessionTemplate.update("SysRoleMapper.updateRole", sysRole);
	}

	public List<SysRole> queryAllRoleList()
	{
		return sqlSessionTemplate.selectList("SysRoleMapper.queryAllRoleList", null);
	}

	public void deleteRoleByIds(String ids)
	{
		sqlSessionTemplate.delete("SysRoleMapper.deleteRoleByIds", ids);

	}

	public void deleteRoleFunctionByRoleId(int roleId)
	{
		sqlSessionTemplate.delete("SysRoleMapper.deleteRoleFunctionByRoleId", roleId);

	}

	public void createRoleFunction(String value)
	{
		sqlSessionTemplate.insert("SysRoleMapper.createRoleFunction", value);

	}

	public List<Integer> queryRoleFunctionIdByRoleId(int roleId)
	{
		return sqlSessionTemplate.selectList("SysRoleMapper.queryRoleFunctionIdByRoleId", roleId);
	}

}
