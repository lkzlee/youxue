package com.youxue.pc.order.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.third.alipay.dto.response.AliPayOrderNotifyDto;
import com.lkzlee.pay.third.dto.AbstThirdPayDto;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.service.order.OrderService;

@Controller
public class AliPayNotfiyController extends com.lkzlee.pay.notify.controller.AliPayNotfiyController
{
	protected final static Log LOG = LogFactory.getLog(AliPayNotfiyController.class);

	@Resource
	private OrderService orderService;

	@RequestMapping("/payNotify/aliPayNotfiy.do")
	@ResponseBody
	public String payNotify(HttpServletRequest request, HttpServletResponse response, AliPayOrderNotifyDto payNotfiyDto)
	{
		LOG.info("@@--收到支付宝支付通知，payNotfiyDto=" + payNotfiyDto);
		return super.payNotifyHandler(request, response, payNotfiyDto, true);
	}

	@Override
	protected void handlerServerPayNotify(AbstThirdPayDto payNotifyDto)
	{
		AliPayOrderNotifyDto aliPayDto = (AliPayOrderNotifyDto) payNotifyDto;
		String logicOrderId = aliPayDto.getOut_trade_no();
		String platformTradeId = aliPayDto.getTrade_no();
		Date notifyTime = DateUtil.formatToDate(aliPayDto.getNotify_time(), DateUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
		Date payTime = DateUtil.formatToDate(aliPayDto.getGmt_payment(), DateUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
		orderService.doPayNotify(logicOrderId, platformTradeId, notifyTime, payTime);
	}
}
