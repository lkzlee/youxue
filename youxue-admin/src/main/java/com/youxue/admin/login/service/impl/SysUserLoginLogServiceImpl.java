package com.youxue.admin.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youxue.admin.login.service.SysUserLoginLogService;
import com.youxue.core.dao.SysUserLoginLogDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.SysUserLoginLog;

/**
 * 后台用户登录日志
 * @author www.inxedu.com
 */
@Service("sysUserLoginLogService")
public class SysUserLoginLogServiceImpl implements SysUserLoginLogService
{

	@Autowired
	private SysUserLoginLogDao sysUserLoginLogDao;

	public int createLoginLog(SysUserLoginLog loginLog)
	{
		return sysUserLoginLogDao.createLoginLog(loginLog);
	}

	public Page<SysUserLoginLog> queryUserLogPage(int userId, Page<SysUserLoginLog> page)
	{
		return sysUserLoginLogDao.queryUserLogPage(userId, page);
	}

}
