package com.youxue.core.service.order.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.youxue.core.dao.RefundDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.service.order.RefundService;
import com.youxue.core.util.ThreadPoolUtil;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.RefundVo;

@Service("refundService")
public class RefundServiceImpl implements RefundService
{
	private final static Log log = LogFactory.getLog(RefundServiceImpl.class);
	@Resource
	private OrderService orderService;
	@Resource
	private RefundDao refundDao;
	@Resource(name = "aliPayOrderPayService")
	private PayService aliPayService;
	@Resource(name = "weiXinOrderPayService")
	private PayService weiXinPayService;

	@Override
	public void addRefund(String orderId)
	{
		log.info("-----@@进入退款，orderId=" + orderId);
		LogicOrderVo logicOrder = orderService.refundOrder(orderId);
		RefundVo refund = refundDao.selectByPrimaryKey(orderId);
		Runnable r = getExecRunnable(refund, logicOrder);
		ThreadPoolUtil.exec(r);
		log.info("-----@@退款已进入队列执行，orderId=" + orderId);
	}

	private Runnable getExecRunnable(RefundVo refund, LogicOrderVo logicOrder)
	{
		PayTypeEnum payType = PayTypeEnum.getByValue(refund.getPayType());
		switch (payType)
		{
			case ALIPAY:
			{
				return new AliPayRefundRunnable(refund, aliPayService);
			}
			case WEIXIN_APY:
			{
				return new WeiXinPayRefundRunnable(refund, logicOrder, weiXinPayService);
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
	class AliPayRefundRunnable implements Runnable
	{
		private RefundVo refund;
		private PayService payService;

		public AliPayRefundRunnable(RefundVo refund, PayService payService)
		{
			super();
			this.refund = refund;
			this.payService = payService;
		}

		@Override
		public void run()
		{
			AliPayRefundOrderDto refundDto = new AliPayRefundOrderDto();
			refundDto.setBatch_no(refund.getOrderId());
			refundDto.setBatch_num(1 + "");
			refundDto.setDetail_data(refund.getLogicOrderId() + "^"
					+ CommonUtil.formatBigDecimal(refund.getRefundAmount()) + "^用户取消订单");
			String notifyUrl = AlipayConfigBean.getPayConfigValue(ConfigConstant.ALIPAY_REFUND_URL,
					"http://127.0.0.1:8080/notify/refund.do");
			refundDto.setNotify_url(notifyUrl);
			String refundDate = DateUtil.formatDate(DateUtil.getCurrentTimestamp(),
					DateUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
			refundDto.setRefund_date(refundDate);

			try
			{
				log.info("@@支付宝退款请求参数，refundDto=" + refundDto);
				Object result = payService.refundToPayService(refundDto);
				log.info("@@支付宝退款执行结果，result=" + result);
			}
			catch (Exception e)
			{
				log.fatal("支付宝执行退款异常，请检查,refundDto=" + refundDto + ",msg:" + e.getMessage(), e);
			}

		}
	}

	/***
	 * 微信退款类
	 * @author lkzlee
	 *
	 */
	class WeiXinPayRefundRunnable implements Runnable
	{
		private RefundVo refund;
		private PayService payService;
		private LogicOrderVo logicOrder;

		public WeiXinPayRefundRunnable(RefundVo refund, LogicOrderVo logicOrder, PayService payService)
		{
			super();
			this.refund = refund;
			this.logicOrder = logicOrder;
			this.payService = payService;
		}

		@Override
		public void run()
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
				Object result = payService.refundToPayService(refundDto);
				log.info("@@微信退款执行结果，result=" + result);
			}
			catch (Exception e)
			{
				log.fatal("微信执行退款异常，请检查,refundDto=" + refundDto + ",msg:" + e.getMessage(), e);
			}
		}

	}
}
