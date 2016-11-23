package com.youxue.core.dao;

import java.util.List;

import com.youxue.core.vo.OrderPersonVo;

public interface OrderPersonDao
{
	int insert(OrderPersonVo record);

	int insertSelective(OrderPersonVo record);

	void batchInsertOrderPerson(List<OrderPersonVo> personList);
}