package com.youxue.core.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.SysUserDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.SysUser;

/**
 * 后台用户
 * @author www.inxedu.com
 */
@Repository("sysUserDao")
public class SysUserDaoImpl extends BaseDao implements SysUserDao
{

	public int createSysUser(SysUser sysuser)
	{
		sqlSessionTemplate.insert("SysUserMapper.createSysUser", sysuser);
		return sysuser.getUserId();
	}

	public void updateSysUser(SysUser sysuser)
	{
		sqlSessionTemplate.update("SysUserMapper.updateSysUser", sysuser);
	}

	public SysUser querySysUserByUserId(int userId)
	{
		return sqlSessionTemplate.selectOne("SysUserMapper.querySysUserByUserId", userId);
	}

	public Page<SysUser> querySysUserPage(String sysUser, Page<SysUser> page)
	{
		return getPageList(page, "", "", sysUser);
	}

	public int validateLoginName(String userLoginName)
	{
		return sqlSessionTemplate.selectOne("SysUserMapper.validateLoginName", userLoginName);
	}

	public SysUser queryLoginUser(SysUser sysUser)
	{
		return sqlSessionTemplate.selectOne("SysUserMapper.queryLoginUser", sysUser);
	}

	public void updateUserPwd(SysUser sysUser)
	{
		sqlSessionTemplate.update("SysUserMapper.updateUserPwd", sysUser);
	}

	public void updateDisableOrstartUser(Map<String, Object> map)
	{
		sqlSessionTemplate.update("SysUserMapper.updateDisableOrstartUser", map);
	}

	public void updateUserLoginLog(Map<String, Object> map)
	{
		sqlSessionTemplate.update("SysUserMapper.updateUserLoginLog", map);
	}

}
