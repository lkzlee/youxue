package com.youxue.admin.login.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youxue.admin.login.service.SysFunctionService;
import com.youxue.core.dao.SysFunctionDao;
import com.youxue.core.vo.SysFunction;

/**
 * 后台系统权限
 * @author www.inxedu.com
 */
@Service("sysFunctionService")
public class SysFunctionServiceImpl implements SysFunctionService
{

	@Autowired
	private SysFunctionDao sysFunctionDao;

	public List<SysFunction> queryAllSysFunction()
	{
		return sysFunctionDao.queryAllSysFunction();
	}

	public int cresateSysFunction(SysFunction sysFunction)
	{
		return sysFunctionDao.cresateSysFunction(sysFunction);
	}

	public void updateFunction(SysFunction sysFunction)
	{
		sysFunctionDao.updateFunction(sysFunction);

	}

	public void updateFunctionParentId(int parentId, int functionId)
	{
		Map<String, Object> paramrs = new HashMap<String, Object>();
		paramrs.put("parentId", parentId);
		paramrs.put("functionId", functionId);
		sysFunctionDao.updateFunctionParentId(paramrs);

	}

	public void deleteFunctionByIds(String ids)
	{
		if (ids != null && ids.trim().length() > 0)
		{
			if (ids.trim().endsWith(","))
			{
				ids = ids.trim().substring(0, ids.trim().length() - 1);
			}
			sysFunctionDao.deleteFunctionByIds(ids);
		}
	}

	public List<SysFunction> querySysUserFunction(int userId)
	{
		return sysFunctionDao.querySysUserFunction(userId);
	}

}
