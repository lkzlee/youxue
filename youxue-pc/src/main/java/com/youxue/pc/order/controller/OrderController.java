package com.youxue.pc.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.exceptions.BusinessException;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.util.NetUtil;
import com.youxue.pc.order.dto.AddTradeOrderDto;
import com.youxue.pc.order.service.AddOrderPayService;

/**
 * 订单相关处理
 * @author lkzlee
 *
 */
public class OrderController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(OrderController.class);
	@Autowired
	JedisProxy jedisProxy;
	@Resource
	private AddOrderPayService addOrderPayService;

	/***
	 * 下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/addTradeOrder.do", method = RequestMethod.POST)
	@ResponseBody
	public String addTrade(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AddTradeOrderDto orderData)
	{
		try
		{
			checkIfParamValid(orderData);

			String ip = NetUtil.getCurrentLoginUserIp(request);
			String accountId = getCurrentLoginUserName(request);
			BaseResponseDto responseDto = addOrderPayService.addTradeOrderService(orderData, ip, accountId);
			return JsonUtil.serialize(responseDto);
		}
		catch (BusinessException e)
		{
			LOG.error("下单参数校验及流程处理，orderData=" + orderData + ",msg:" + e.getMessage());
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc(e.getMessage()));
		}
		catch (Exception e)
		{
			LOG.error("下单处理流程异常，orderData=" + orderData + ",msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后！"));
		}
	}

	private void checkIfParamValid(AddTradeOrderDto orderData)
	{
		if (orderData == null || orderData.getPayType() == null)
			throw new BusinessException("参数非法");
		if (ArrayUtils.isEmpty(orderData.getOrderList()))
			throw new BusinessException("无任何订单信息");
		/***
		 * 校验下单的人数和 出行人人数，校验下单金额，校验手机号、email合法性，校验支付方式，校验优惠券合法性
		 */
	}
}
