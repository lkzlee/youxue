package com.youxue.pc.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.lkzlee.pay.third.weixin.dto.request.WeiXinQueryRefundDto;
import com.lkzlee.pay.third.weixin.dto.response.WeiXinQueryRefundResultDto;
import com.lkzlee.pay.third.weixin.service.WeiXinOrderPayService;
import com.youxue.core.dao.RefundDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.service.order.RefundService;
import com.youxue.core.vo.RefundVo;

@Service
public class WeiXinRefundQueryTask
{
	private final static Log log = LogFactory.getLog(WeiXinRefundQueryTask.class);
	@Resource
	private RefundDao refundDao;
	@Resource(name = "weiXinOrderPayService")
	private WeiXinOrderPayService weiXinPayService;
	@Resource
	private RefundService refundService;
	@Resource
	private OrderService orderService;
	private static final ExecutorService exec = Executors.newSingleThreadExecutor();

	@PostConstruct
	public void init()
	{
		log.info("----WeiXinRefundQueryTask 初始化.......");
		exec.execute(new WeiXinQueryRefundRunnable());
		log.info("@----WeiXinRefundQueryTask 初始化完成.....");

	}

	class WeiXinQueryRefundRunnable implements Runnable
	{

		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					List<RefundVo> refundList = refundDao.selectInitRefundByPay(PayTypeEnum.WEIXIN_PAY.getValue());
					if (CollectionUtils.isEmpty(refundList))
					{
						log.info("要进行退款的订单为空，额外休息20分钟.....");
						Thread.sleep(20l * 60 * 1000); //休息20分钟
					}
					else
					{
						for (RefundVo r : refundList)
						{
							WeiXinQueryRefundDto refundDto = new WeiXinQueryRefundDto();
							refundDto.setOut_refund_no(r.getOrderId());

							WeiXinQueryRefundResultDto refundResult = (WeiXinQueryRefundResultDto) weiXinPayService
									.queryRefundOrderService(refundDto);
							log.info("微信退款查询返回结果:refundResult=" + refundResult);
							if ("REFUNDNOTEXIST".equalsIgnoreCase(refundResult.getErr_code()))
							{
								refundService.addRefund(r.getOrderId());
								return;
							}
							if ("SUCCESS".equalsIgnoreCase(refundResult.getRefund_status_0())
									&& r.getOrderId().equals(refundResult.getOut_refund_no_0()))
							{
								orderService.doRefundNotify(r.getOrderId());
							}
							else if ("PROCESSING".equalsIgnoreCase(refundResult.getRefund_status_0()))
							{
								log.info("微信退款正在处理中，请稍后,r=" + r);
							}
							else
							{
								log.fatal("微信退款异常，请检查,r=" + r);
							}

						}
					}
					Thread.sleep(10l * 60 * 1000); //休息10分钟
				}
				catch (Exception e)
				{
					log.fatal("微信查询退款异常，请检查，msg:" + e.getMessage(), e);
				}
			}
		}
	}
}
