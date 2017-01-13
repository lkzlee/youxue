package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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

	@Override
	public int createSysUser(SysUser sysuser)
	{
		sqlSessionTemplate.insert("SysUserMapper.createSysUser", sysuser);
		return sysuser.getUserId();
	}

	@Override
	public void updateSysUser(SysUser sysuser)
	{
		sqlSessionTemplate.update("SysUserMapper.updateSysUser", sysuser);
	}

	@Override
	public SysUser querySysUserByUserId(int userId)
	{
		return sqlSessionTemplate.selectOne("SysUserMapper.querySysUserByUserId", userId);
	}

	@Override
	public int validateLoginName(String userLoginName)
	{
		return sqlSessionTemplate.selectOne("SysUserMapper.validateLoginName", userLoginName);
	}

	@Override
	public SysUser queryLoginUser(SysUser sysUser)
	{
		return sqlSessionTemplate.selectOne("SysUserMapper.queryLoginUser", sysUser);
	}

	@Override
	public void updateUserPwd(SysUser sysUser)
	{
		sqlSessionTemplate.update("SysUserMapper.updateUserPwd", sysUser);
	}

	@Override
	public void updateDisableOrstartUser(Map<String, Object> map)
	{
		sqlSessionTemplate.update("SysUserMapper.updateDisableOrstartUser", map);
	}

	@Override
	public void updateUserLoginLog(Map<String, Object> map)
	{
		sqlSessionTemplate.update("SysUserMapper.updateUserLoginLog", map);
	}

	@Override
	public Page<SysUser> querySysUserPage(Page<SysUser> page, String userName, String tel)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(userName))
			param.put("userName", userName);
		if (StringUtils.isNotBlank(tel))
			param.put("tel", tel);
		return getPageList(page, "SysUserMapper.querySysUserPage", "SysUserMapper.queryCountSysUserPage", param);
	}
}
