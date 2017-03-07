package com.youxue.core.dao;

import java.util.List;

import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.vo.OrderDetailVo;
import com.youxue.core.vo.OrderVo;
import com.youxue.core.vo.Page;

public interface OrderDao
{
	int deleteByPrimaryKey(String orderId);

	int insert(OrderVo record);

	int insertSelective(OrderVo record);

	OrderVo selectByPrimaryKey(String orderId, boolean lock);

	int updateByPrimaryKeySelective(OrderVo record);

	int updateByPrimaryKey(OrderVo record);

	void batchInsertOrder(List<OrderVo> orderList);

	List<OrderVo> selectOrderByLogicOrderId(String logicOrderId, boolean lock);

	Page<OrderDetailVo> selectPageOrderListByType(Page<OrderDetailVo> page, List<Integer> statusList, String accountId);

	Page<OrderDetailVo> selectPageOrderListByInfo(Page<OrderDetailVo> page, int status, PayTypeEnum pType,
			String orderId, String mobile, String campsName);

	List<OrderVo> selectUnPayOrder();

	List<OrderVo> selectUnfinishedOrder();
}