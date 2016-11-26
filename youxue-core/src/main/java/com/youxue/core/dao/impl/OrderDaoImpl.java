package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.vo.OrderVo;
import com.youxue.core.vo.Page;

@Repository
public class OrderDaoImpl extends BaseDao implements OrderDao
{

	@Override
	public int deleteByPrimaryKey(String orderId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.OrderDao.deleteByPrimaryKey", orderId);
	}

	@Override
	public int insert(OrderVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.OrderDao.insert", record);
	}

	@Override
	public int insertSelective(OrderVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.OrderDao.insertSelective", record);
	}

	@Override
	public OrderVo selectByPrimaryKey(String orderId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.OrderDao.selectByPrimaryKey", orderId);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.OrderDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(OrderVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.OrderDao.updateByPrimaryKey", record);
	}

	@Override
	public void batchInsertOrder(List<OrderVo> orderList)
	{
		sqlSessionTemplate.insert("com.youxue.core.dao.OrderDao.batchInsertOrder", orderList);
	}

	@Override
	public List<OrderVo> selectOrderByLogicOrderId(String logicOrderId, boolean lock)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lock", lock);
		param.put("logicOrderId", logicOrderId);
		return sqlSessionTemplate.selectList("com.youxue.core.dao.OrderDao.selectOrderByLogicOrderId", param);
	}

	@Override
	public Page<OrderVo> selectPageOrderListByType(Page<OrderVo> page, int orderType, String accountId)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", orderType);
		param.put("accountId", accountId);
		return getPageList(page, "com.youxue.core.dao.OrderDao.selectPageOrderListByType",
				"selectCountPageOrderListByType", param);
	}
}
