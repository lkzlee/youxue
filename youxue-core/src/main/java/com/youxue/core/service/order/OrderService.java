package com.youxue.core.service.order;

import java.util.Date;

import com.youxue.core.service.order.dto.AddTradeOrderDto;
import com.youxue.core.vo.LogicOrderVo;

/***
 * 下单返回数据，返回订单Id
 * @author lkzlee
 *
 */
public interface OrderService
{
	String addOrder(AddTradeOrderDto orderData, String ip, String accountId);

	void doPayNotify(String logicOrderId, String platformTradeId, Date notifyTime, Date payTime);

	LogicOrderVo refundOrder(String orderId);

	void doRefundNotify(String orderId);
}
