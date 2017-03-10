package com.youxue.core.service.order;

/***
 * 退款业务处理
 * @author lkzlee
 *
 */
public interface RefundService
{
	void addRefund(String orderId);

	void refundRequest(String refundId);
}
