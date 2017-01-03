package com.youxue.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.SysUserLoginLogDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.SysUserLoginLog;

/**
 * 后台用户登录日志
 * @author www.inxedu.com
 *
 */
@Repository("sysUserLoginLogDao")
public class SysUserLoginLogDaoImpl extends BaseDao implements SysUserLoginLogDao
{

	public int createLoginLog(SysUserLoginLog loginLog)
	{
		sqlSessionTemplate.insert("SysUserLoginLogMapper.createLoginLog", loginLog);
		return loginLog.getLogId();
	}

	public Page<SysUserLoginLog> queryUserLogPage(int userId, Page<SysUserLoginLog> page)
	{
		return getPageList(page, "", "", userId);
	}

}
