package com.youxue.pc.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.notify.controller.AliPayRefundNotfiyController;
import com.lkzlee.pay.third.alipay.dto.response.AliPayRefundNotifyDto;
import com.lkzlee.pay.third.dto.AbstThirdPayDto;
import com.youxue.pc.order.service.OrderService;

@Controller
public class AliPayRefundNotifyController extends AliPayRefundNotfiyController
{
	protected final static Log LOG = LogFactory.getLog(AliPayRefundNotifyController.class);
	@Resource
	private OrderService orderService;

	@RequestMapping(path = "/notify/refund.do", method = RequestMethod.POST)
	public String refundNotify(HttpServletRequest request, HttpServletResponse response,
			AliPayRefundNotifyDto refundNotifyDto)
	{
		LOG.info("@@--收到支付宝退款成功通知，refundNotifyDto=" + refundNotifyDto);
		return super.payNotifyHandler(request, response, refundNotifyDto, true);
	}

	@Override
	protected void handlerServerPayNotify(AbstThirdPayDto payNotifyDto)
	{
		/***
		 * 对于我们每一笔订单都是一笔退款，因此是唯一的
		 */
		AliPayRefundNotifyDto refundDto = (AliPayRefundNotifyDto) payNotifyDto;
		if (!"1".equals(refundDto.getSuccess_num()))
		{
			LOG.fatal("退款数据错误，refundDto=" + refundDto);
			throw new BusinessException("退款数据有错误");
		}
		orderService.doRefundNotify(refundDto.getBatch_no());
	}
}
