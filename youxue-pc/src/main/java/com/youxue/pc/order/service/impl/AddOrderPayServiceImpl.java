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

	@Override
	public BaseResponseDto addTradeOrderService(AddTradeOrderDto orderData, String ip, String accountId)
	{
		String logicOrderId = orderService.addOrder(orderData, ip, accountId);
		PayService payService = getPayService(orderData.getPayType());
		//		Object param = payService.addThirdPayOrderService();
		return null;
	}

	private PayService getPayService(Integer payType)
	{
		if (PayTypeEnum.ALIPAY.getValue() == payType)
			return aliPayService;
		return weiXinPayService;
	}

}
