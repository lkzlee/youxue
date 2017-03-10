package com.youxue.core.service.order.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkzlee.pay.bean.AlipayConfigBean;
import com.lkzlee.pay.bean.WeiXinConfigBean;
import com.lkzlee.pay.constant.ConfigConstant;
import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.service.PayService;
import com.lkzlee.pay.third.alipay.dto.request.AliPayRefundOrderDto;
import com.lkzlee.pay.third.weixin.dto.request.WeiXinRefundOrderDto;
import com.lkzlee.pay.utils.CommonUtil;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.dao.RefundDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.service.order.RefundService;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.RefundVo;

@Service("refundService")
public class RefundServiceImpl implements RefundService
{
	private final static Log log = LogFactory.getLog(RefundServiceImpl.class);
	@Resource
	private OrderService orderService;
	@Autowired
	private LogicOrderDao logicOrderDao;
	@Resource
	private RefundDao refundDao;
	@Resource(name = "aliPayOrderPayService")
	private PayService aliPayService;
	@Resource(name = "weiXinOrderPayService")
	private PayService weiXinPayService;

	@Override
	public Object addRefund(String orderId)
	{
		log.info("-----@@进入退款，orderId=" + orderId);
		LogicOrderVo logicOrder = orderService.refundOrder(orderId);
		RefundVo refund = refundDao.selectByPrimaryKey(orderId);
		Object result = execRefundRequest(refund, logicOrder);
		log.info("-----@@退款已进入队列执行，result=" + result);
		return result;
	}

	private Object execRefundRequest(RefundVo refund, LogicOrderVo logicOrder)
	{
		PayTypeEnum payType = PayTypeEnum.getByValue(refund.getPayType());
		switch (payType)
		{
			case ALIPAY:
			{
				return alipayRefundRequest(refund, logicOrder);
			}
			case WEIXIN_PAY:
			case WEIXIN_JS_API:
			{
				return wxpayRefundRequest(refund, logicOrder);
			}
			default:
			{
				log.fatal("支付方式不正确，不能退款，refund=" + refund);
				throw new BusinessException("支付方式不正确，不能退款，orderId=" + refund.getOrderId() + ",logicOrderId="
						+ refund.getLogicOrderId());
			}
		}
	}

	/***
	 * 支付宝退款类
	 * @author lkzlee
	 *
	 */

	public Object alipayRefundRequest(RefundVo refund, LogicOrderVo logicOrder)
	{
		AliPayRefundOrderDto refundDto = new AliPayRefundOrderDto();
		refundDto.setBatch_no(refund.getOrderId());
		refundDto.setBatch_num(1 + "");
		refundDto.setDetail_data(logicOrder.getPlatformOrderId() + "^"
				+ CommonUtil.formatBigDecimal(refund.getRefundAmount()) + "^用户取消订单");
		String notifyUrl = AlipayConfigBean.getPayConfigValue(ConfigConstant.ALIPAY_REFUND_URL,
				"http://127.0.0.1:8080/notify/refund.do");
		refundDto.setNotify_url(notifyUrl);
		String refundDate = DateUtil.formatDate(DateUtil.getCurrentTimestamp(), DateUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
		refundDto.setRefund_date(refundDate);

		try
		{
			log.info("@@支付宝退款请求参数，refundDto=" + refundDto);
			Object result = aliPayService.refundToPayService(refundDto);
			log.info("@@支付宝退款执行结果，result=" + result);
			return result;
		}
		catch (Exception e)
		{
			log.fatal("支付宝执行退款异常，请检查,refundDto=" + refundDto + ",msg:" + e.getMessage(), e);
		}
		return null;
	}

	/***
	 * 微信退款类
	 * @author lkzlee
	 *
	 */

	public Object wxpayRefundRequest(RefundVo refund, LogicOrderVo logicOrder)
	{
		WeiXinRefundOrderDto refundDto = new WeiXinRefundOrderDto();
		refundDto.setOut_trade_no(refund.getLogicOrderId());
		refundDto.setOut_refund_no(refund.getOrderId());
		int refundFee = refund.getRefundAmount().multiply(new BigDecimal(100)).intValue();
		refundDto.setRefund_fee(refundFee);
		refundDto.setRefund_fee_type("CNY");
		int totalFee = logicOrder.getTotalPayPrice().multiply(new BigDecimal(100)).intValue();
		refundDto.setTotal_fee(totalFee);
		refundDto.setOp_user_id(WeiXinConfigBean.getPayConfigValue(ConfigConstant.WEIXIN_MCH_ID));
		try
		{
			log.info("@@微信退款请求参数，refundDto=" + refundDto);
			Object result = weiXinPayService.refundToPayService(refundDto);
			log.info("@@微信退款执行结果，result=" + result);
			return result;
		}
		catch (Exception e)
		{
			log.fatal("微信执行退款异常，请检查,refundDto=" + refundDto + ",msg:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public Object refundRequest(String orderId)
	{
		log.info("-----@@进入退款，orderId=" + orderId);
		RefundVo refund = refundDao.selectByPrimaryKey(orderId);
		LogicOrderVo logicOrder = logicOrderDao.selectByPrimaryKey(refund.getLogicOrderId(), false);
		Object result = execRefundRequest(refund, logicOrder);
		log.info("-----@@退款已进入队列执行，result=" + result);
		return result;

	}
}
