package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.vo.LogicOrderVo;

@Repository
public class LogicOrderDaoImpl extends BaseDao implements LogicOrderDao
{

	@Override
	public int deleteByPrimaryKey(String logicOrderId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.LogicOrderDao.deleteByPrimaryKey", logicOrderId);
	}

	@Override
	public int insert(LogicOrderVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.LogicOrderDao.insert", record);
	}

	@Override
	public int insertSelective(LogicOrderVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.LogicOrderDao.insertSelective", record);
	}

	@Override
	public LogicOrderVo selectByPrimaryKey(String logicOrderId, boolean lock)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lock", lock);
		param.put("logicOrderId", logicOrderId);
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.LogicOrderDao.selectByPrimaryKey", param);
	}

	@Override
	public int updateByPrimaryKeySelective(LogicOrderVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.LogicOrderDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(LogicOrderVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.LogicOrderDao.updateByPrimaryKey", record);
	}

}
