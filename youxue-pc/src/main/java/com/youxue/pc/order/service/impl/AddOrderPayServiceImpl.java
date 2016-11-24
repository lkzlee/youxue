package com.youxue.pc.order.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.lkzlee.pay.service.PayService;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.pc.order.dto.AddTradeOrderDto;
import com.youxue.pc.order.service.AddOrderPayService;
import com.youxue.pc.order.service.OrderService;

@Service("addOrderPayService")
public class AddOrderPayServiceImpl implements AddOrderPayService
{
	protected final static Log LOG = LogFactory.getLog(AddOrderPayServiceImpl.class);
	@Resource(name = "weiXinOrderPayService")
	private PayService weiXinPayService;
	@Resource(name = "aliPayOrderPayService")
	private PayService aliPayService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderDao orderDao;

	/***
	 * 下单总体流程处理
	 */
	@Override
	public BaseResponseDto addTradeOrderService(AddTradeOrderDto orderData, String ip, String accountId)
	{
		/***
		 * 插入数据库订单数据
		 */
		String logicOrderId = orderService.addOrder(orderData, ip, accountId);

		PayService payService = getPayService(orderData.getPayType());

		/**
		 * 向第三方下单
		 */
		//		Object param = payService.addThirdPayOrderService();
		/***
		 * 解析构造下单结果，并返回
		 */
		return null;
	}

	private PayService getPayService(Integer payType)
	{
		if (PayTypeEnum.ALIPAY.getValue() == payType)
			return aliPayService;
		return weiXinPayService;
	}

}
