package com.youxue.core.dao.impl;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(LogicOrderVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(LogicOrderVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LogicOrderVo selectByPrimaryKey(String logicOrderId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(LogicOrderVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(LogicOrderVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
