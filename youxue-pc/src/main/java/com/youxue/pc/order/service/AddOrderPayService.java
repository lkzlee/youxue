package com.youxue.pc.order.service;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.pc.order.dto.AddTradeOrderDto;

public interface AddOrderPayService
{

	BaseResponseDto addTradeOrderService(AddTradeOrderDto orderData, String ip, String accountId);

}
