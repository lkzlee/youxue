package com.youxue.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.OrderPersonDao;
import com.youxue.core.vo.OrderPersonVo;

@Repository
public class OrderPersonDaoImpl extends BaseDao implements OrderPersonDao
{

	@Override
	public int insert(OrderPersonVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(OrderPersonVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void batchInsertOrderPerson(List<OrderPersonVo> personList)
	{
		// TODO Auto-generated method stub

	}

}
