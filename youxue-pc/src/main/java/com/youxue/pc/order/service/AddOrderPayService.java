package com.youxue.pc.order.service;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.service.order.dto.AddTradeOrderDto;

public interface AddOrderPayService
{

	BaseResponseDto addTradeOrderService(AddTradeOrderDto orderData, String ip, String accountId);

	BaseResponseDto addTradeOrderServiceById(String logicOrderId, String ip, String accountId, String openId);

}
