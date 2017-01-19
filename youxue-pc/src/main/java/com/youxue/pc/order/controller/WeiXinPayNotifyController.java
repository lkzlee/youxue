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
import com.lkzlee.pay.third.weixin.dto.response.WeiXinOrderResultDto;
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

	public static void main(String[] args)
	{
		StringBuilder sbs = new StringBuilder();
		sbs.append("<xml>" + "" + "" + "" + "<return_code><![CDATA[FAIL]]></return_code> ");
		sbs.append("<return_msg>" + "" + "<![CDATA[appid参数长度有误]]>" + "</return_msg>");
		sbs.append("</xml>");
		WeiXinOrderResultDto resultDto = (WeiXinOrderResultDto) XstreamUtil.fromXml(sbs.toString(),
				WeiXinOrderResultDto.class);
		System.out.println(resultDto);

		StringBuffer sb = new StringBuffer();
		sb.append("<xml><appid><![CDATA[wx855e27a3d08ed53f]]></appid>");
		sb.append("<bank_type><![CDATA[CFT]]></bank_type>");
		sb.append("<cash_fee><![CDATA[1]]></cash_fee>");
		sb.append("<device_info><![CDATA[WEB]]></device_info>");
		sb.append("<fee_type><![CDATA[CNY]]></fee_type>");
		sb.append("<is_subscribe><![CDATA[N]]></is_subscribe>");
		sb.append("<mch_id><![CDATA[1370242202]]></mch_id>");
		sb.append("<nonce_str><![CDATA[MW8DQ2TVYEYM]]></nonce_str>");
		sb.append("<openid><![CDATA[ojdUWwfPHlWyJjBlijgO-siBPK_k]]></openid>");
		sb.append("<out_trade_no><![CDATA[20170119180656LDd2512d35]]></out_trade_no>");
		sb.append("<result_code><![CDATA[SUCCESS]]></result_code>");
		sb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
		sb.append("<sign><![CDATA[2E58F88BEFC4E076F1A83F6D469B77B8]]></sign>");
		sb.append("<time_end><![CDATA[20170119180735]]></time_end>");
		sb.append("<total_fee>1</total_fee>");
		sb.append("<trade_type><![CDATA[NATIVE]]></trade_type>");
		sb.append("<transaction_id><![CDATA[4004372001201701196866254045]]></transaction_id>");
		sb.append("</xml>");
		WeiXinPayNotifyResultDto payNotfiyDto = (WeiXinPayNotifyResultDto) XstreamUtil.fromXml(sb.toString(),
				WeiXinPayNotifyResultDto.class);
		System.out.println(payNotfiyDto);

	}

	@RequestMapping("/payNotify/wxPayNotfiy.do")
	@ResponseBody
	public String payNotify(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String xmlResonse = IOStreamTools.inputStream2String(request.getInputStream());
			LOG.info("@@--收到微信支付通知，xmlResponse=" + xmlResonse);

			WeiXinPayNotifyResultDto payNotfiyDto = (WeiXinPayNotifyResultDto) XstreamUtil.fromXml(xmlResonse,
					WeiXinPayNotifyResultDto.class);
			LOG.info("@@--收到微信支付通知，payNotfiyDto=" + payNotfiyDto);
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
