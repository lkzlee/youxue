package com.youxue.core.dao;

import java.util.List;

import com.youxue.core.vo.OrderVo;

public interface OrderDao
{
	int deleteByPrimaryKey(String orderId);

	int insert(OrderVo record);

	int insertSelective(OrderVo record);

	OrderVo selectByPrimaryKey(String orderId);

	int updateByPrimaryKeySelective(OrderVo record);

	int updateByPrimaryKey(OrderVo record);

	void batchInsertOrder(List<OrderVo> orderList);
}