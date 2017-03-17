package com.youxue.pc.order.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.lkzlee.pay.bean.WeiXinConfigBean;
import com.lkzlee.pay.constant.ConfigConstant;
import com.lkzlee.pay.notify.controller.WeiXinPayNotfiyController;
import com.lkzlee.pay.third.dto.AbstThirdPayDto;
import com.lkzlee.pay.third.weixin.dto.response.WeiXinOrderResultDto;
import com.lkzlee.pay.third.weixin.dto.response.WeiXinPayNotifyResultDto;
import com.lkzlee.pay.utils.CommonUtil;
import com.lkzlee.pay.utils.DateUtil;
import com.lkzlee.pay.utils.IOStreamTools;
import com.lkzlee.pay.utils.XstreamUtil;
import com.lkzlee.pay.wx.bean.TemplateMsgDataDto;
import com.lkzlee.pay.wx.helper.MessageHelper;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.OrderVo;
import com.youxue.core.vo.UserInfoVo;

@Controller
public class WeiXinPayNotifyController extends WeiXinPayNotfiyController
{
	protected final static Log LOG = LogFactory.getLog(AliPayNotfiyController.class);

	@Resource
	private OrderService orderService;
	@Resource
	private LogicOrderDao logicOrderDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private CampsDao campsDao;
	@Resource
	private UserInfoDao userInfoDao;
	@Autowired
	private JedisProxy jedisProxy;

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
	public void payNotify(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String xmlResonse = IOStreamTools.inputStream2String(request.getInputStream());
			LOG.info("@@--收到微信支付通知，xmlResponse=" + xmlResonse);

			WeiXinPayNotifyResultDto payNotfiyDto = (WeiXinPayNotifyResultDto) XstreamUtil.fromXml(xmlResonse,
					WeiXinPayNotifyResultDto.class);
			LOG.info("@@--收到微信支付通知，payNotfiyDto=" + payNotfiyDto);
			String msg = super.payNotifyHandler(request, response, payNotfiyDto, true);
			response.getWriter().println(msg);
			return;
		}
		catch (IOException e)
		{
			LOG.fatal("通知异常，请检查，msg:" + e.getMessage(), e);
		}
		return;
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
		LogicOrderVo order = logicOrderDao.selectByPrimaryKey(logicOrderId, false);
		if (order.getPayType() == PayTypeEnum.WEIXIN_PAY.getValue())
		{
			jedisProxy.del(RedisConstant.getAddUserOrderKey(order.getAccountId(), logicOrderId));
		}
		else if (order.getPayType() == PayTypeEnum.WEIXIN_JS_API.getValue())
		{
			jedisProxy.del(RedisConstant.getAddUserOrderKeyWXJSAPI(order.getAccountId(), logicOrderId));
		}

		try
		{
			UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(order.getAccountId());
			if (userInfo != null && StringUtils.isNotBlank(userInfo.getOpenId()))
			{
				String openId = userInfo.getOpenId();
				String templateId = WeiXinConfigBean.getPayConfigValue(ConfigConstant.WEIXIN_MSG_TEMPLATE_ID);
				String[] templates = templateId.split(";");
				for (String t : templates)
				{
					if (t.indexOf("noitfy_msg") >= 0)
					{
						templateId = t.split(":")[1];
					}
				}
				List<OrderVo> list = orderDao.selectOrderByLogicOrderId(order.getLogicOrderId(), false);
				for (OrderVo o : list)
				{
					try
					{
						CampsVo campsVo = campsDao.selectByPrimaryKey(o.getCampsId());
						TemplateMsgDataDto data = new TemplateMsgDataDto(openId, templateId,
								"http://qg.igalaxy.com.cn/wxwap/my_order.jsp");
						data.push("first", "尊敬的客户 " + o.getAccountId());
						data.push("orderId", o.getOrderId());
						data.push("orderPrice", CommonUtil.formatBigDecimal(o.getPayPrice()));
						data.push("orderStatus", "待审核");
						data.push("productName", campsVo.getCampsName());
						data.push("remark", "我们已收到您的订单，请耐心等待审核");
						JSONObject result = MessageHelper.templateSend(data);
						LOG.info("【wx push msg】推送微信消息结果 result=" + result);
					}
					catch (Exception e)
					{
						LOG.fatal("【wx push msg】推送微信消息异常 o=" + o, e);
					}
				}

			}

		}
		catch (Exception e)
		{
			LOG.fatal("微信发送消息失败，请检查，msg:" + e.getMessage(), e);
		}
	}
}
