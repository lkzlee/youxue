package com.youxue.pc.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.dao.OrderPersonDao;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.OrderPersonVo;
import com.youxue.core.vo.OrderVo;
import com.youxue.pc.order.dto.AddTradeItemDto;
import com.youxue.pc.order.dto.AddTradeOrderDto;
import com.youxue.pc.order.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService
{
	@Resource
	private OrderDao orderDao;
	@Resource
	private LogicOrderDao logicOrderDao;
	@Resource
	private OrderPersonDao orderPersonDao;
	@Resource
	private CommonDao commonDao;

	@Override
	@Transactional
	public String addOrder(AddTradeOrderDto orderData, String ip, String accountId)
	{
		LogicOrderVo logicOrderVo = buildLogicOrderInfo(orderData, ip, accountId);
		List<OrderVo> orderList = buildOrderListInfo(orderData, ip, accountId, logicOrderVo.getLogicOrderId());
		List<OrderPersonVo> personList = buildOrderPersonListInfo(orderData, orderList);
		logicOrderDao.insertSelective(logicOrderVo);
		orderDao.batchInsertOrder(orderList);
		orderPersonDao.batchInsertOrderPerson(personList);
		return logicOrderVo.getLogicOrderId();
	}

	private List<OrderPersonVo> buildOrderPersonListInfo(AddTradeOrderDto orderData, List<OrderVo> orderList)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private List<OrderVo> buildOrderListInfo(AddTradeOrderDto orderData, String ip, String accountId,
			String logicOrderId)
	{
		AddTradeItemDto orders[] = orderData.getOrderList();
		if (ArrayUtils.isEmpty(orders))
			throw new BusinessException("订单数据为空，请检查");
		List<OrderVo> orderList = Lists.newArrayList();
		for (AddTradeItemDto ot : orders)
		{
			OrderVo orderVo = new OrderVo();
			//			orderVo.setAccountId(accountId);
			//			orderVo.setCampsId(ot.getCampsId());
			//			orderVo.setCodeId(codeId);
			//			orderVo.setCodePrice(codePrice);
			//			orderVo.setCodeStatus(codeStatus);
			//			orderVo.setContactEmail(contactEmail);
			//			orderVo.setContactName(contactName);
			//			orderVo.setContactPhone(contactPhone);
			//			orderVo.setCreatTime(DateUtil.getCurrentTimestamp());
			//			orderVo.setLogicOrderId(logicOrderId);
			//			String orderId = commonDao.getIdByPrefix(CommonConstant.ORDER_ID_PREFIX);
			//			orderVo.setOrderId(orderId);
			//			orderVo.setOrderIp(ip);
			//			orderVo.setPayPrice(payPrice);
			//			orderVo.setStatus(OrderVo.UNPAY);
			//			orderVo.setTotalCount(totalCount);
			//			orderVo.setTotalPrice(totalPrice);
			orderList.add(orderVo);
		}
		return orderList;
	}

	private LogicOrderVo buildLogicOrderInfo(AddTradeOrderDto orderData, String ip, String accountId)
	{
		LogicOrderVo logicOrderVo = new LogicOrderVo();
		String logicOrderId = commonDao.getIdByPrefix(CommonConstant.LOGIC_ORDER_ID_PREFIX);
		logicOrderVo.setLogicOrderId(logicOrderId);
		logicOrderVo.setCreateTime(DateUtil.getCurrentTimestamp());
		logicOrderVo.setOrderIp(ip);
		logicOrderVo.setPayStatus(LogicOrderVo.UNPAY);
		logicOrderVo.setAccountId(accountId);
		logicOrderVo.setPayType(orderData.getPayType());
		return logicOrderVo;
	}
}
