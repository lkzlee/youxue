package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.vo.OrderDetailVo;
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
	public OrderVo selectByPrimaryKey(String orderId, boolean lock)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lock", lock);
		param.put("orderId", orderId);
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.OrderDao.selectByPrimaryKey", param);
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
	public Page<OrderDetailVo> selectPageOrderListByType(Page<OrderDetailVo> page, int orderType, String accountId)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", orderType);
		param.put("accountId", accountId);
		return getPageList(page, "com.youxue.core.dao.OrderDao.selectPageOrderListByType",
				"com.youxue.core.dao.OrderDao.selectCountPageOrderListByType", param);
	}

	@Override
	public Page<OrderDetailVo> selectPageOrderListByInfo(Page<OrderDetailVo> page, int status, PayTypeEnum pType,
			String orderId, String mobile, String campsName)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		if (status != -1)
			param.put("status", status);
		if (pType != null && PayTypeEnum.UNKNOW_PAY != pType)
			param.put("payType", pType.getValue());
		if (StringUtils.isNotBlank(orderId))
			param.put("orderId", orderId);
		if (StringUtils.isNotBlank(mobile))
			param.put("accountId", mobile);
		if (StringUtils.isNotBlank(campsName))
			param.put("campsName", campsName);
		return getPageList(page, "com.youxue.core.dao.OrderDao.selectPageOrderListByInfo",
				"com.youxue.core.dao.OrderDao.selectCountPageOrderListByInfo", param);
	}

	@Override
	public List<OrderVo> selectUnPayOrder()
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.OrderDao.selectUnPayOrder");
	}

	@Override
	public List<OrderVo> selectUnfinishedOrder()
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.OrderDao.selectUnfinishedOrder");
	}
}
