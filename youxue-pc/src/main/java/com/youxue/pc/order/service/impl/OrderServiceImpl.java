package com.youxue.pc.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.dao.OrderPersonDao;
import com.youxue.core.vo.CouponCodeVo;
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
	private CouponCodeDao couponCodeDao;
	@Resource
	private CommonDao commonDao;
	private final static Log log = LogFactory.getLog(OrderServiceImpl.class);

	/***
	 * 下单插入出行人，订单，逻辑订单等信息
	 */
	@Override
	@Transactional
	public String addOrder(AddTradeOrderDto orderData, String ip, String accountId)
	{
		LogicOrderVo logicOrderVo = buildLogicOrderInfo(orderData, ip, accountId);
		List<OrderVo> orderList = Lists.newArrayList();
		List<OrderPersonVo> personList = Lists.newArrayList();
		buildOrderListInfo(orderData, ip, accountId, logicOrderVo.getLogicOrderId(), orderList, personList);
		logicOrderDao.insertSelective(logicOrderVo);
		orderDao.batchInsertOrder(orderList);
		orderPersonDao.batchInsertOrderPerson(personList);
		return logicOrderVo.getLogicOrderId();
	}

	private List<OrderVo> buildOrderListInfo(AddTradeOrderDto orderData, String ip, String accountId,
			String logicOrderId, List<OrderVo> orderList, List<OrderPersonVo> personList)
	{
		AddTradeItemDto orders[] = orderData.getOrderList();
		if (ArrayUtils.isEmpty(orders))
			throw new BusinessException("订单数据为空，请检查");
		for (AddTradeItemDto ot : orders)
		{
			OrderVo orderVo = new OrderVo();
			String orderId = setOrderItemInfo(ip, accountId, logicOrderId, ot, orderVo);
			OrderPersonVo[] outPersonList = ot.getOutPersonList();
			if (ArrayUtils.isEmpty(outPersonList))
				throw new BusinessException("订单对应的出行人数据为空，请检查");
			for (OrderPersonVo po : outPersonList)
			{
				OrderPersonVo personVo = new OrderPersonVo();
				personVo.setOrderId(orderId);
				personVo.setPersonAddress(po.getPersonAddress());
				personVo.setPersonIdno(po.getPersonIdno());
				personVo.setPersonName(po.getPersonName());
				personVo.setPersonPhone(po.getPersonPhone());
				personList.add(personVo);
			}
			orderList.add(orderVo);
		}
		return orderList;
	}

	private String setOrderItemInfo(String ip, String accountId, String logicOrderId, AddTradeItemDto ot,
			OrderVo orderVo)
	{
		orderVo.setAccountId(accountId);
		orderVo.setCampsId(ot.getCampsId());
		orderVo.setCodeId(ot.getCodeId());
		orderVo.setCodePrice(ot.getCodePrice());
		orderVo.setCodeStatus(OrderVo.UNPAY);
		orderVo.setContactEmail(ot.getContactEmail());
		orderVo.setContactName(ot.getContactName());
		orderVo.setContactPhone(ot.getContactPhone());
		orderVo.setCreatTime(DateUtil.getCurrentTimestamp());
		orderVo.setLogicOrderId(logicOrderId);
		String orderId = commonDao.getIdByPrefix(CommonConstant.ORDER_ID_PREFIX);
		orderVo.setOrderId(orderId);
		orderVo.setOrderIp(ip);
		orderVo.setPayPrice(ot.getPayPrice());
		orderVo.setStatus(OrderVo.UNPAY);
		orderVo.setTotalCount(ot.getTotalCount());
		orderVo.setTotalPrice(ot.getTotalPrice());
		return orderId;
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
		BigDecimal totalMoney = BigDecimal.ZERO;
		BigDecimal totalPayMoney = BigDecimal.ZERO;
		for (AddTradeItemDto orderItem : orderData.getOrderList())
		{
			totalMoney = totalMoney.add(orderItem.getTotalPrice());
			totalPayMoney = totalPayMoney.add(orderItem.getPayPrice());
		}
		logicOrderVo.setTotalPrice(totalMoney);
		logicOrderVo.setTotalPayPrice(totalPayMoney);
		return logicOrderVo;
	}

	@Transactional
	@Override
	public void doPayNotify(String logicOrderId, String platformTradeId, Date notifyTime, Date payTime)
	{
		LogicOrderVo logicOrderVo = logicOrderDao.selectByPrimaryKey(logicOrderId, true);
		if (logicOrderVo == null)
			throw new BusinessException("订单号不存在，请检查，logicOrderId=" + logicOrderId + ",platformTradeId="
					+ platformTradeId + ",notifyTime=" + notifyTime + ",payTime=" + payTime);
		if (LogicOrderVo.PAY == logicOrderVo.PAY)
		{
			log.info("订单已处理，重复通知无需再次处理，logicOrderId=" + logicOrderId + ",platformTradeId=" + platformTradeId
					+ ",notifyTime=" + notifyTime + ",payTime=" + payTime);
			return;
		}
		logicOrderVo.setPayTime(payTime);
		logicOrderVo.setNotifyTime(notifyTime);
		logicOrderVo.setPayStatus(LogicOrderVo.PAY);
		logicOrderVo.setUpdateTime(DateUtil.getCurrentTimestamp());
		logicOrderDao.updateByPrimaryKeySelective(logicOrderVo);
		List<OrderVo> orderList = orderDao.selectOrderByLogicOrderId(logicOrderId, true);
		for (OrderVo order : orderList)
		{
			CouponCodeVo coupon = couponCodeDao.selectCouponByCode(order.getCodeId(), true);
			if (coupon == null || coupon.getStatus() != CouponCodeVo.NORMAL)
			{
				log.fatal("支付异常，对应的红包状态错误，或者不存在,logicOrderId=" + logicOrderId + ",order=" + order);
				throw new BusinessException("支付异常，对应的红包状态错误，或者不存在,logicOrderId=" + logicOrderId + ",orderId="
						+ order.getOrderId());
			}
			coupon.setUseCount(coupon.getUseCount() + order.getTotalCount());
			couponCodeDao.updateByPrimaryKeySelective(coupon);
			order.setCodeStatus(OrderVo.PAY);
			order.setUpdateTime(DateUtil.getCurrentTimestamp());
			order.setStatus(OrderVo.PAY);
			orderDao.updateByPrimaryKeySelective(order);
		}
	}

	@Override
	public String refundOrder(String orderId)
	{

		return null;
	}
}
