package com.youxue.core.dao;

import com.youxue.core.vo.LogicOrderVo;

public interface LogicOrderDao
{
	int deleteByPrimaryKey(String logicOrderId);

	int insert(LogicOrderVo record);

	int insertSelective(LogicOrderVo record);

	LogicOrderVo selectByPrimaryKey(String logicOrderId, boolean lock);

	int updateByPrimaryKeySelective(LogicOrderVo record);

	int updateByPrimaryKey(LogicOrderVo record);
}