package com.youxue.core.service.message;

import com.youxue.core.enums.MessageEnum;

public interface MessageService
{

	/**
	 * @param msgEnum
	 * @param accountId
	 * @param orderId
	 * 添加订单相关消息提醒
	 */
	void addOrderMessage(MessageEnum msgEnum, String accountId, String orderId);

}
