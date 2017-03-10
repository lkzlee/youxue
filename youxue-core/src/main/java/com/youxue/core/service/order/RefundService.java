package com.youxue.core.service.order;

/***
 * 退款业务处理
 * @author lkzlee
 *
 */
public interface RefundService
{
	Object addRefund(String orderId);

	Object refundRequest(String refundId);
}
