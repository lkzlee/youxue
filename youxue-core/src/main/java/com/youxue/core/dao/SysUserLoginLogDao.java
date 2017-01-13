package com.youxue.core.dao;

import com.youxue.core.vo.Page;
import com.youxue.core.vo.SysUserLoginLog;

/**
 * 后台用户登录日志
 * @author www.inxedu.com
 */
public interface SysUserLoginLogDao
{
	/**
	 * 添加登录日志
	 * @param loginLog
	 * @return 日志ID
	 */
	public int createLoginLog(SysUserLoginLog loginLog);

	/**
	 * 查询用户登录日志
	 * @param userId 用户ID
	 * @param page 分页条件
	 * @return List<SysUserLoginLog>
	 */
	public Page<SysUserLoginLog> queryUserLogPage(int userId, Page<SysUserLoginLog> page);

}
