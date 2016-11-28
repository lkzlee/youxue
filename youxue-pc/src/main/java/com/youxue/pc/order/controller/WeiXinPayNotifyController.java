package com.youxue.pc.order.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.notify.controller.WeiXinPayNotfiyController;
import com.lkzlee.pay.third.dto.AbstThirdPayDto;
import com.lkzlee.pay.third.weixin.dto.response.WeiXinPayNotifyResultDto;
import com.lkzlee.pay.utils.DateUtil;
import com.lkzlee.pay.utils.IOStreamTools;
import com.lkzlee.pay.utils.XstreamUtil;
import com.youxue.core.service.order.OrderService;

@Controller
public class WeiXinPayNotifyController extends WeiXinPayNotfiyController
{
	protected final static Log LOG = LogFactory.getLog(AliPayNotfiyController.class);

	@Resource
	private OrderService orderService;

	@RequestMapping("/payNotify/wxPayNotfiy.do")
	@ResponseBody
	public String payNotify(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String xmlResonse = IOStreamTools.inputStream2String(request.getInputStream());
			LOG.info("@@--收到微信支付通知，payNotfiyDto=" + xmlResonse);
			WeiXinPayNotifyResultDto payNotfiyDto = XstreamUtil.fromXml(xmlResonse, WeiXinPayNotifyResultDto.class);
			return super.payNotifyHandler(request, response, payNotfiyDto, true);
		}
		catch (IOException e)
		{
			LOG.fatal("通知异常，请检查，msg:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected void handlerServerPayNotify(AbstThirdPayDto payNotifyDto)
	{
		WeiXinPayNotifyResultDto wxPayNotfiyDto = (WeiXinPayNotifyResultDto) payNotifyDto;
		String logicOrderId = wxPayNotfiyDto.getOut_trade_no();
		String platformTradeId = wxPayNotfiyDto.getTransaction_id();
		Date notifyTime = DateUtil.getCurrentTimestamp();
		Date payTime = DateUtil.formatToDate(wxPayNotfiyDto.getTime_end(), DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
		orderService.doPayNotify(logicOrderId, platformTradeId, notifyTime, payTime);
	}

}
