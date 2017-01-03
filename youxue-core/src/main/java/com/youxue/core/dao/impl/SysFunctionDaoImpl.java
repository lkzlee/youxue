package com.youxue.core.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.SysFunctionDao;
import com.youxue.core.vo.SysFunction;

/**
 * 后台系统权限
 * @author www.inxedu.com
 *
 */
@Repository("sysFunctionDao")
public class SysFunctionDaoImpl extends BaseDao implements SysFunctionDao
{

	public List<SysFunction> queryAllSysFunction()
	{
		return sqlSessionTemplate.selectList("SysFunctionMapper.queryAllSysFunction", null);
	}

	public int cresateSysFunction(SysFunction sysFunction)
	{
		sqlSessionTemplate.insert("SysFunctionMapper.cresateSysFunction", sysFunction);
		return sysFunction.getFunctionId();
	}

	public void updateFunction(SysFunction sysFunction)
	{
		sqlSessionTemplate.update("SysFunctionMapper.updateFunction", sysFunction);
	}

	public void updateFunctionParentId(Map<String, Object> paramrs)
	{
		sqlSessionTemplate.update("SysFunctionMapper.updateFunctionParentId", paramrs);
	}

	public void deleteFunctionByIds(String ids)
	{
		sqlSessionTemplate.delete("SysFunctionMapper.deleteFunctionByIds", ids);
	}

	public List<SysFunction> querySysUserFunction(int userId)
	{
		return sqlSessionTemplate.selectList("SysFunctionMapper.querySysUserFunction", userId);
	}

}
