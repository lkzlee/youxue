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
		return sqlSessionTemplate.insert("com.youxue.core.dao.OrderPersonDao.insert", record);
	}

	@Override
	public int insertSelective(OrderPersonVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.OrderPersonDao.insertSelective", record);
	}

	@Override
	public void batchInsertOrderPerson(List<OrderPersonVo> personList)
	{
		sqlSessionTemplate.insert("com.youxue.core.dao.OrderPersonDao.batchInsertOrderPerson", personList);
	}

	@Override
	public List<OrderPersonVo> getOrderPersonById(String orderId)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.OrderPersonDao.getOrderPersonById", orderId);
	}

}
