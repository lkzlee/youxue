package com.youxue.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.vo.OrderVo;

@Repository
public class OrderDaoImpl extends BaseDao implements OrderDao
{

	@Override
	public int deleteByPrimaryKey(String orderId)
	{
		return 0;
	}

	@Override
	public int insert(OrderVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(OrderVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderVo selectByPrimaryKey(String orderId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(OrderVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(OrderVo record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void batchInsertOrder(List<OrderVo> orderList)
	{
		// TODO Auto-generated method stub

	}

}
