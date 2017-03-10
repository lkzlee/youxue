package com.youxue.pc.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.youxue.core.dao.RefundDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.service.order.RefundService;
import com.youxue.core.vo.RefundVo;

@Service
@DependsOn(value =
{ "aliPayConfigBean", "weiXinConfigBean" })
public class AliPayRefundQueryTask
{
	private final static Log log = LogFactory.getLog(AliPayRefundQueryTask.class);
	@Resource
	private RefundDao refundDao;
	@Resource
	private RefundService refundService;
	@Resource
	private OrderService orderService;
	private static final ExecutorService exec = Executors.newSingleThreadExecutor();

	@PostConstruct
	public void init()
	{
		log.info("----AliPayRefundQueryTask 初始化.......");
		exec.execute(new AliPayQueryRefundRunnable());
		log.info("@----AliPayRefundQueryTask 初始化完成.....");

	}

	class AliPayQueryRefundRunnable implements Runnable
	{

		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					List<RefundVo> refundList = refundDao.selectInitRefundByPay(PayTypeEnum.ALIPAY.getValue());
					if (CollectionUtils.isEmpty(refundList))
					{
						log.info("要进行退款的订单为空，额外休息20分钟.....");
						Thread.sleep(20l * 60 * 1000); //休息20分钟
					}
					else
					{
						for (RefundVo r : refundList)
						{
							refundService.refundRequest(r.getOrderId());
							log.info("支付宝退款未返回通知，推送一次退款请求:refundVo=" + r);

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
