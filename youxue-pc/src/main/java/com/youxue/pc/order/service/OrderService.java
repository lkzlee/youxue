package com.youxue.pc.order.service;

import com.youxue.pc.order.dto.AddTradeOrderDto;

/***
 * 下单返回数据，返回订单Id
 * @author liyongchao
 *
 */
public interface OrderService
{
	String addOrder(AddTradeOrderDto orderData, String ip, String accountId);
}
