package com.youxue.core.dao;

import java.util.Map;

import com.youxue.core.vo.Page;
import com.youxue.core.vo.SysUser;

/**
 * 后台用户
 * @author www.inxedu.com
 */
public interface SysUserDao
{
	/***
	 * 创建用户
	 * @param sysuser 用户实体
	 * @return 用户ID
	 */
	public int createSysUser(SysUser sysuser);

	/**
	 * 更新用户信息
	 * @param sysuser 用户实体
	 */
	public void updateSysUser(SysUser sysuser);

	/**
	 * 通过ID，查询用户实体信息
	 * @param userId 用户ID
	 * @return SysUser
	 */
	public SysUser querySysUserByUserId(int userId);

	/**
	 * 验证用户帐户是否存在
	 * @param userLoginName
	 */
	public int validateLoginName(String userLoginName);

	/**
	 * 查询登录用户
	 * @param sysUser 查询条件
	 * @return SysUser
	 */
	public SysUser queryLoginUser(SysUser sysUser);

	/**
	 * 修改用户密码
	 * @param sysUser
	 */
	public void updateUserPwd(SysUser sysUser);

	/**
	 *禁用或启用后台用户 
	 * @param map 修改条件  userId 用户ID type 1启用 2禁用
	 */
	public void updateDisableOrstartUser(Map<String, Object> map);

	/***
	 * 修改用户登录最后登录时间和IP
	 * @param map
	 */
	public void updateUserLoginLog(Map<String, Object> map);

	public Page<SysUser> querySysUserPage(Page<SysUser> page, String userName, String tel);
}
