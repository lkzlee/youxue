package com.youxue.admin.login.service;

import java.util.Date;

import com.youxue.core.vo.SysUser;

/**
 * 后台用户
 * @author www.inxedu.com 
 */
public interface SysUserService
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
	 * @return false存在 true不存在
	 */
	public boolean validateLoginName(String userLoginName);

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
	 * @param userId 用户ID
	 * @param type 1启用 2禁用 
	 */
	public void updateDisableOrstartUser(int userId, int type);

	/***
	 * 修改用户登录最后登录时间和IP
	 * @param userId 用户ID
	 * @param time 登录 时间
	 * @param ip 登录 IP
	 */
	public void updateUserLoginLog(int userId, Date time, String ip);

}
